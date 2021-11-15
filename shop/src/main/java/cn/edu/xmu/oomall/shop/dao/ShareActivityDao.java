package cn.edu.xmu.oomall.shop.dao;

import cn.edu.xmu.oomall.core.util.Common;
import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.shop.mapper.ShareActivityPoMapper;
import cn.edu.xmu.oomall.shop.model.bo.ShareActivity;
import cn.edu.xmu.oomall.shop.model.po.ShareActivityPo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * 分享活动Dao层
 *
 * @author BingShuai Liu
 * @studentId 22920192204245
 * @date 2021/11/13/15:46
 */
@Repository
public class ShareActivityDao {
    private static final Logger logger = LoggerFactory.getLogger(ShareActivityDao.class);

    @Autowired
    private ShareActivityPoMapper shareActivityPoMapper;

    /**
     * 根据分享活动id查找分享活动
     * @param id 分享活动id
     * @return shareActivity
     */
    public ReturnObject<ShareActivity> getShareActivityById(Long id){
        ShareActivityPo shareActivityPo;
        try {
            shareActivityPo=shareActivityPoMapper.selectByPrimaryKey(id);
            if(shareActivityPo==null){
                return new ReturnObject<>(ReturnNo.RESOURCE_ID_NOTEXIST);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ReturnObject<>(ReturnNo.INTERNAL_SERVER_ERR,e.getMessage());
        }
        ShareActivity shareActivity = (ShareActivity) Common.cloneVo(shareActivityPo,ShareActivity.class);
        return new ReturnObject<>(shareActivity);
    }

    /**
     * 根据分享活动的id, 修改对应表项
     * @param id
     * @param shareActivity
     * @return
     */
    public ReturnObject modifyShareActivity(Long id, ShareActivity shareActivity){
        ShareActivityPo shareActivityPo;
        int ret;
        try {
            shareActivityPo = (ShareActivityPo) Common.cloneVo(shareActivity,ShareActivityPo.class);
            ret = shareActivityPoMapper.updateByPrimaryKeySelective(shareActivityPo);
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR,e.getMessage());
        }
        if(ret == 0){
            return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST);
        }else{
            return new ReturnObject(ReturnNo.OK);
        }
    }

    /**
     * 根据分享活动的id 删除对应表项
     * 需要判断state是否为0(草稿态)
     * @param id
     * @return
     */
    public ReturnObject deleteShareActivity(Long id){
        int ret;
        try {
            ret=shareActivityPoMapper.deleteByPrimaryKey(id);
        }catch (Exception e){
            logger.error(e.getMessage());
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR,e.getMessage());
        }
        if(ret==0){
            return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST);
        }else{
            return new ReturnObject(ReturnNo.OK);
        }
    }

    /**
     * 修改分享活动的state
     * @param shareActivity
     * @return
     */
    public ReturnObject updateShareActivityState(ShareActivity shareActivity){
        ShareActivityPo shareActivityPo = (ShareActivityPo) Common.cloneVo(shareActivity,ShareActivityPo.class);
        int ret;
        try{
            shareActivityPo.setGmtModified(LocalDateTime.now());
            ret = shareActivityPoMapper.updateByPrimaryKey(shareActivityPo);
        }
        catch (Exception e){
            logger.error(e.getMessage());
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR,e.getMessage());
        }
        if(ret == 0){
            return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST);
        }else{
            return new ReturnObject();
        }
    }
}
