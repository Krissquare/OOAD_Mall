package cn.edu.xmu.oomall.shop.service;

import cn.edu.xmu.oomall.core.util.Common;
import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.shop.dao.ShareActivityDao;
import cn.edu.xmu.oomall.shop.microservice.OnSaleService;
import cn.edu.xmu.oomall.shop.model.bo.OnSale;
import cn.edu.xmu.oomall.shop.model.bo.ShareActivity;
import cn.edu.xmu.oomall.shop.model.vo.OnSaleRetVo;
import cn.edu.xmu.oomall.shop.model.vo.ShareActivityVo;
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

    @Transactional(rollbackFor=Exception.class)
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
    public ReturnObject addShareActivityOnOnSale(Long id, Long sid, Long loginUser, String loginUsername){
        ReturnObject onSale;
        ReturnObject shareActivity;
        onSale= onSaleService.getOnSaleById(id);
        shareActivity= getShareActivityByShareActivityId(sid);
        if(onSale.getData()==null||shareActivity.getData()==null){
            return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST);
        }
        OnSale onSale1 = (OnSale) onSale.getData();
        if(!onSale1.getState().equals(OnSale.State.Online.getCode())){
            return new ReturnObject(ReturnNo.STATENOTALLOW);
        }
        ShareActivity shareActivity1=(ShareActivity) shareActivity.getData();
        if(shareActivity1.getState().equals(ShareActivity.State.Offline.getCode())){
            return new ReturnObject(ReturnNo.STATENOTALLOW);
        }
        Boolean updateRet= (Boolean) onSaleService.updateAddOnSaleShareActId(id,sid).getData();
        if (!updateRet){
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR);
        }
        OnSaleRetVo onSaleRetVo = (OnSaleRetVo) Common.cloneVo(onSale1,OnSaleRetVo.class);
        return new ReturnObject<>(onSaleRetVo);
    }

    /**
     * 管理员取消已有销售上的分享
     * @param id
     * @param sid
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    public ReturnObject deleteShareActivityOnOnSale(Long id, Long sid, Long loginUser, String loginUsername){
        ReturnObject onSale;
        ReturnObject shareActivity;
        onSale= onSaleService.getOnSaleById(id);
        shareActivity= getShareActivityByShareActivityId(sid);
        if(onSale.getData()==null||shareActivity.getData()==null){
            return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST);
        }
        Boolean updateRet= (Boolean) onSaleService.updateAddOnSaleShareActId(id,sid).getData();
        if(!updateRet){
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR);
        }
        return new ReturnObject(ReturnNo.OK);
    }

    /**
     * 管理员修改平台分享活动的内容
     * @param id
     * @param shareActivityVo
     * @return
     */
    @Transactional(rollbackFor=Exception.class)
    public ReturnObject modifyShareActivity(Long id, ShareActivityVo shareActivityVo,Long loginUser, String loginUsername){
        ShareActivity shareActivity = (ShareActivity)Common.cloneVo(shareActivityVo,ShareActivity.class);
        Common.setPoModifiedFields(shareActivity,loginUser,loginUsername);
        var x = getShareActivityByShareActivityId(id).getData();
        if (x==null){
            return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST);
        }
        if(!x.getState().equals(ShareActivity.State.Draft.getCode().byteValue())){
            return new ReturnObject(ReturnNo.STATENOTALLOW);
        }
        ReturnObject ret = shareActivityDao.modifyShareActivity(id,shareActivity);
        return ret;
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
        if(!x.getState().equals(ShareActivity.State.Draft.getCode().byteValue())){
            return new ReturnObject(ReturnNo.STATENOTALLOW);
        }
        ReturnObject ret = shareActivityDao.deleteShareActivity(id);
        return ret;
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
        if(x.getState().equals(ShareActivity.State.Online.getCode())){
            return new ReturnObject(ReturnNo.STATENOTALLOW);
        }
        x.setState((byte) 1);
        Common.setPoModifiedFields(x,loginUser,loginUsername);
        ReturnObject ret=shareActivityDao.updateShareActivityState(x);
        return ret;
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
        if(x.getState().equals(ShareActivity.State.Offline.getCode())){
            return new ReturnObject(ReturnNo.STATENOTALLOW);
        }
        x.setState((byte) 2);
        Common.setPoModifiedFields(x,loginUser,loginUsername);
        ReturnObject ret=shareActivityDao.updateShareActivityState(x);
        return ret;
    }
}
