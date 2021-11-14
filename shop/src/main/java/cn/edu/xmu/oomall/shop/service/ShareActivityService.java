package cn.edu.xmu.oomall.shop.service;

import cn.edu.xmu.oomall.core.util.Common;
import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.shop.dao.ShareActivityDao;
import cn.edu.xmu.oomall.shop.microservice.OnSaleService;
import cn.edu.xmu.oomall.shop.model.bo.OnSale;
import cn.edu.xmu.oomall.shop.model.bo.ShareActivity;
import cn.edu.xmu.oomall.shop.model.po.ShareActivityPo;
import cn.edu.xmu.oomall.shop.model.vo.OnSaleRetVo;
import cn.edu.xmu.oomall.shop.model.vo.ShareActivityVo;
import cn.edu.xmu.oomall.shop.model.vo.ShopSimpleRetVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Created with IntelliJ IDEA.
 *
 * @author BingShuai Liu
 * @studentId 22920192204245
 * @date 2021/11/13/15:43
 */
@Service
@Component
public class ShareActivityService {

    @Autowired
    ShareActivityDao shareActivityDao;

    @Autowired
    OnSaleService onSaleService;

    public ReturnObject<ShareActivity> getShareActivityByShareActivityId(Long id){
        return shareActivityDao.getShareActivityById(id);
    }

    /**
     * 管理员在已有销售上增加分享
     * @param id OnSale id
     * @param sid 分享活动 id
     * @return OnSale
     */
    @Transactional(rollbackFor=Exception.class)
    public ReturnObject addShareActivityOnOnSale(Long id, Long sid){
        try {
            ReturnObject onSale;
            ReturnObject shareActivity;
            onSale= onSaleService.getOnSaleById(id);
            shareActivity= getShareActivityByShareActivityId(sid);
            if(onSale==null||shareActivity.getData()==null){
                return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST);
            }else{
                OnSale onSale1 = (OnSale) onSale.getData();
                if(onSale1.getState().equals(OnSale.State.Online.getCode())){
                    ShareActivity shareActivity1=(ShareActivity) shareActivity.getData();
                    if(!shareActivity1.getState().equals(ShareActivity.State.Offline.getCode())){
                        if (onSaleService.updateAddOnSaleShareActId(id,sid)){
                            OnSaleRetVo onSaleRetVo = (OnSaleRetVo) Common.cloneVo(onSale1,OnSaleRetVo.class);
                            return new ReturnObject<>(onSaleRetVo);
                        }else{
                            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR);
                        }
                    }
                    else{
                        //分享活动需为草稿态或上线态 否则出507错误
                        return new ReturnObject(ReturnNo.STATENOTALLOW);
                    }
                }
                else{
                    //OnSale需为上线状态 否则返回507
                    return new ReturnObject(ReturnNo.STATENOTALLOW);
                }
            }
        }catch (Exception e){
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR);
        }
    }

    /**
     * 管理员取消已有销售上的分享
     * @param id
     * @param sid
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    public ReturnObject deleteShareActivityOnOnSale(Long id, Long sid){
        try {
            ReturnObject onSale;
            ReturnObject shareActivity;
            onSale= onSaleService.getOnSaleById(id);
            shareActivity= getShareActivityByShareActivityId(sid);
            if(onSale==null||shareActivity.getData()==null){
                return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST);
            }
            else{
                OnSale onSale1 = (OnSale) onSale.getData();
                if(onSaleService.updateDeleteOnSaleShareActId(id,sid)){
                    return new ReturnObject(ReturnNo.OK);
                }else {
                    return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR);
                }
            }
        }catch (Exception e){
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR);
        }

    }

    /**
     * 管理员修改平台分享活动的内容
     * @param id
     * @param shareActivityVo
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    public ReturnObject modifyShareActivity(Long id, ShareActivityVo shareActivityVo,Long loginUser, String loginUsername){
        if(shareActivityVo.getBeginTime().compareTo(shareActivityVo.getEndTime())>0){
            return new ReturnObject(ReturnNo.ACT_LATE_BEGINTIME);
        }
        ShareActivity shareActivity= new ShareActivity();;
        shareActivity.setId(id);
        shareActivity.setGmtModified(LocalDateTime.now());
        shareActivity.setName(shareActivityVo.getName());
        shareActivity.setBeginTime(shareActivityVo.getBeginTime());
        shareActivity.setEndTime(shareActivityVo.getEndTime());
        shareActivity.setStrategy(shareActivityVo.getStrategy().toString());
        Common.setPoModifiedFields(shareActivity,loginUser,loginUsername);
        var x = getShareActivityByShareActivityId(id).getData();
        if (x==null){
            return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST);
        }else{
            if(!x.getState().equals(ShareActivity.State.Draft.getCode().byteValue())){
                return new ReturnObject(ReturnNo.STATENOTALLOW);
            }else{
                ReturnObject ret = shareActivityDao.modifyShareActivity(id,shareActivity);
                return ret;
            }
        }
    }

    /**
     * 管理员删除草稿状态的分享活动
     * @param id
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    public ReturnObject deleteShareActivity(Long id,Long loginUser, String loginUsername){
        var x = getShareActivityByShareActivityId(id).getData();
        if (x==null){
            return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST);
        }
        else{
            if(!x.getState().equals(ShareActivity.State.Draft.getCode().byteValue())){
                return new ReturnObject(ReturnNo.STATENOTALLOW);
            }
            else{
                ReturnObject ret = shareActivityDao.deleteShareActivity(id);
                return ret;
            }
        }
    }

    /**
     * 根据分享活动id上线分享活动
     * @param id 分享活动id
     * @return 执行结果
     */
    @Transactional(rollbackFor=Exception.class)
    public ReturnObject onlineShareActivity(Long id,Long loginUser, String loginUsername){
        var x = getShareActivityByShareActivityId(id).getData();
        if (x==null){
            return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST);
        }
        else{
            System.out.println(x.getState());
            if(x.getState().equals(ShareActivity.State.Online.getCode())){
                return new ReturnObject(ReturnNo.STATENOTALLOW);
            }
            else{
                x.setState((byte) 1);
                Common.setPoModifiedFields(x,loginUser,loginUsername);
                ReturnObject ret=shareActivityDao.updateShareActivityState(x);
                return ret;
            }
        }
    }

    /**
     * 根据分享活动id下线分享活动
     * @param id 分享活动id
     * @return 执行结果
     */
    @Transactional(rollbackFor=Exception.class)
    public ReturnObject offlineShareActivity(Long id,Long loginUser, String loginUsername){
        var x = getShareActivityByShareActivityId(id).getData();
        if (x==null){
            return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST);
        }
        else{
            if(x.getState().equals(ShareActivity.State.Offline.getCode())){
                return new ReturnObject(ReturnNo.STATENOTALLOW);
            }
            else{
                x.setState((byte) 2);
                Common.setPoModifiedFields(x,loginUser,loginUsername);
                ReturnObject ret=shareActivityDao.updateShareActivityState(x);
                return ret;
            }
        }
    }
}
