package cn.edu.xmu.oomall.activity.dao;

import cn.edu.xmu.oomall.activity.mapper.ShareActivityPoMapper;
import cn.edu.xmu.oomall.activity.model.bo.ShareActivityBo;
import cn.edu.xmu.oomall.activity.model.po.ShareActivityPo;
import cn.edu.xmu.oomall.activity.model.po.ShareActivityPoExample;
import cn.edu.xmu.oomall.activity.model.vo.*;
import cn.edu.xmu.oomall.core.util.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiuchen lang 22920192204222
 * @date 2021/11/12 12:55
 */
@Repository
public class ShareActivityDao {
    private static final Logger logger = LoggerFactory.getLogger(ShareActivityDao.class);
    @Autowired
    private ShareActivityPoMapper shareActivityPoMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Value("${oomall.activity.share.expiretime}")
    private long shareActivityExpireTime;

    /**
     * 显示分享活动列表
     *
     * @param bo       shareActivity bo对象
     * @param shareIds 相关的分享活动id 集合
     * @param page     页码
     * @param pageSize 每页数目
     * @return
     */
    public ReturnObject getShareByShopId(ShareActivityBo bo, List<Long> shareIds, Integer page, Integer pageSize) {
        ShareActivityPoExample example = new ShareActivityPoExample();
        ShareActivityPoExample.Criteria criteria = example.createCriteria();
        if(bo.getShopId()!=null){
            criteria.andShopIdEqualTo(bo.getShopId());
        }
        if (shareIds != null && !shareIds.isEmpty()) {
            criteria.andIdIn(shareIds);
        }
        if (bo.getBeginTime() != null) {
            criteria.andBeginTimeGreaterThanOrEqualTo(bo.getBeginTime());
        }
        if (bo.getEndTime() != null) {
            criteria.andEndTimeLessThanOrEqualTo(bo.getEndTime());
        }
        if(bo.getShopId()!=null){
            criteria.andShopIdEqualTo(bo.getShopId());
        }
        if (bo.getState() != null) {
            criteria.andStateEqualTo(bo.getState());
        }
        try {
            PageHelper.startPage(page, pageSize);
            List<ShareActivityPo> shareActivityPos = shareActivityPoMapper.selectByExample(example);
            PageInfo pageInfo = new PageInfo(shareActivityPos);
            List<ShareActivityBo> shareActivityBos = new ArrayList<>();
            for (ShareActivityPo shareActivityPo : shareActivityPos) {
                ShareActivityBo shareActivityBo = (ShareActivityBo) Common.cloneVo(shareActivityPo, ShareActivityBo.class);
                shareActivityBos.add(shareActivityBo);
            }
            pageInfo.setList(shareActivityBos);
            return new ReturnObject(pageInfo);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR, e.getMessage());
        }
    }

    /**
     * 管理员新增分享活动
     *
     * @param shareActivityBo   shareActivityBo对象
     * @return
     */
    public ReturnObject addShareAct(ShareActivityBo shareActivityBo) {
        ShareActivityPo shareActivityPo = (ShareActivityPo) Common.cloneVo(shareActivityBo, ShareActivityPo.class);
        shareActivityPo.setStrategy(JacksonUtil.toJson(shareActivityBo.getStrategy()));
        try {
            int flag = shareActivityPoMapper.insert(shareActivityPo);
            if (flag == 0) {
                return new ReturnObject(ReturnNo.FIELD_NOTVALID);
            }
            ShareActivityBo shareActivityBo1 = (ShareActivityBo) Common.cloneVo(shareActivityPo,ShareActivityBo.class);
            if (shareActivityPo.getStrategy() != null) {
                List<StrategyVo> strategyVos = (List<StrategyVo>) JacksonUtil.toObj(shareActivityPo.getStrategy(), new ArrayList<StrategyVo>().getClass());
                shareActivityBo1.setStrategy(strategyVos);
            }

            return new ReturnObject(shareActivityBo1);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR, e.getMessage());
        }
    }


    /**
     * 查看分享活动详情 只显示上线状态的分享活动
     *
     * @param id 分享活动Id
     * @return
     */
    public ReturnObject getShareActivityById(Long id) {
        String key = "shareactivivybyid_" + id;
        try {
            Serializable serializable = redisUtil.get(key);
            if (serializable != null) {
                ShareActivityBo shareActivityBo = JacksonUtil.toObj(serializable.toString(), ShareActivityBo.class);
                return new ReturnObject(shareActivityBo);
            }
            ShareActivityPo shareActivityPo = shareActivityPoMapper.selectByPrimaryKey(id);
            if (shareActivityPo==null) {
                return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST);
            }
            //使用clonevo
            ShareActivityBo shareActivityBo = (ShareActivityBo) Common.cloneVo(shareActivityPo, ShareActivityBo.class);
            if (shareActivityPo.getStrategy() != null) {
                List<StrategyVo> strategyVos = (List<StrategyVo>) JacksonUtil.toObj(shareActivityPo.getStrategy(), new ArrayList<StrategyVo>().getClass());
                shareActivityBo.setStrategy(strategyVos);
            }
            //查不到插入redis设置超时时间
            redisUtil.set(key, JacksonUtil.toJson(shareActivityBo), shareActivityExpireTime);
            return new ReturnObject(shareActivityBo);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR, e.getMessage());
        }
    }

    /**
     * 查看特定分享活动详情,显示所有状态的分享活动
     *
     * @param shopId 店铺Id
     * @param id     分享活动Id
     * @return
     */
    public ReturnObject getShareActivityByShopIdAndId(Long shopId, Long id) {
        String key = "shareactivivyid_"+id+"_shopid_"+ shopId;
        try {
            Serializable serializable = redisUtil.get(key);
            if (serializable != null) {
                ShareActivityBo shareActivityBo = JacksonUtil.toObj(serializable.toString(), ShareActivityBo.class);
                return new ReturnObject(shareActivityBo);
            }
            ShareActivityPoExample shareActivityPoExample = new ShareActivityPoExample();
            ShareActivityPoExample.Criteria criteria = shareActivityPoExample.createCriteria();
            criteria.andIdEqualTo(id);
            criteria.andShopIdEqualTo(shopId);
            List<ShareActivityPo> shareActivityPos = shareActivityPoMapper.selectByExample(shareActivityPoExample);
            if (shareActivityPos.isEmpty()) {
                return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST);
            }
            ShareActivityPo shareActivityPo = shareActivityPos.get(0);
            ShareActivityBo shareActivityBo= (ShareActivityBo) Common.cloneVo(shareActivityPo,ShareActivityBo.class);
            if (shareActivityPo.getStrategy() != null) {
                List<StrategyVo> strategyVos = (List<StrategyVo>) JacksonUtil.toObj(shareActivityPo.getStrategy(), new ArrayList<StrategyVo>().getClass());
                shareActivityBo.setStrategy(strategyVos);
            }
            redisUtil.set(key, JacksonUtil.toJson(shareActivityBo), shareActivityExpireTime);
            return new ReturnObject(shareActivityBo);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR, e.getMessage());
        }

    }
}
