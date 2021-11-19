package cn.edu.xmu.oomall.activity.dao;

import cn.edu.xmu.oomall.activity.constant.GroupOnState;
import cn.edu.xmu.oomall.activity.mapper.GroupOnActivityPoMapper;
import cn.edu.xmu.oomall.activity.model.bo.GroupOnActivity;
import cn.edu.xmu.oomall.activity.model.po.GroupOnActivityPo;
import cn.edu.xmu.oomall.activity.model.po.GroupOnActivityPoExample;
import cn.edu.xmu.oomall.activity.model.vo.GroupOnStrategyVo;
import cn.edu.xmu.oomall.activity.model.vo.PageInfoVo;
import cn.edu.xmu.oomall.activity.model.vo.SimpleGroupOnActivityVo;
import cn.edu.xmu.oomall.core.util.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gao Yanfeng
 * @date 2021/11/11
 */
@Repository
public class GroupOnActivityDao {

    @Autowired
    private GroupOnActivityPoMapper mapper;

    @Autowired
    private RedisUtil redisUtil;

    @Value("${oomall.activity.groupon.expiretime}")
    private long timeout;

    private static Logger logger = LoggerFactory.getLogger(Common.class);

    public ReturnObject insertActivity(GroupOnActivity bo, Long createdBy, String createName) {
        try {
            GroupOnActivityPo po = (GroupOnActivityPo) Common.cloneVo(bo, GroupOnActivityPo.class);
            po.setStrategy(JacksonUtil.toJson(bo.getStrategy()));
            Common.setPoCreatedFields(po, createdBy, createName);
            mapper.insert(po);
            bo.setId(po.getId());
            redisUtil.set("groupon_" + po.getId().toString(), bo, timeout);
            return new ReturnObject(Common.cloneVo(bo, SimpleGroupOnActivityVo.class));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR, e.getMessage());
        }
    }

    public ReturnObject getGroupActivities(GroupOnActivityPoExample example, Integer page, Integer pageSize) {
        try {
            PageHelper.startPage(page, pageSize);
            List<GroupOnActivityPo> poList = mapper.selectByExample(example);
            var voList = new ArrayList<SimpleGroupOnActivityVo>();
            for (var po : poList) {
                voList.add((SimpleGroupOnActivityVo) Common.cloneVo(po, SimpleGroupOnActivityVo.class));
            }
            var pageInfo = new PageInfo<>(voList);
            pageInfo.setPages(PageInfo.of(poList).getPages());
            pageInfo.setTotal(PageInfo.of(poList).getTotal());
            pageInfo.setPageNum(page);
            pageInfo.setPageSize(pageSize);
            return new ReturnObject(new PageInfoVo<>(pageInfo));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR, e.getMessage());
        }
    }

    public ReturnObject getGroupOnActivity(Long id, GroupOnState state, Long shopId) {
        try {
            GroupOnActivityPo po;
            var bo = (GroupOnActivity) redisUtil.get("groupon_" + id.toString());
            if (bo == null) {
                po = mapper.selectByPrimaryKey(id);
                if (po == null) {
                    return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST, "未找到指定ID对应的团购活动");
                }
                bo = (GroupOnActivity) Common.cloneVo(po, GroupOnActivity.class);
                bo.setStrategy(JacksonUtil.parseObjectList(po.getStrategy(), GroupOnStrategyVo.class));
            }
            if (state != null && !bo.getState().equals(state)) {
                return new ReturnObject<>(ReturnNo.STATENOTALLOW, "团购活动未上线");
            }
            if (shopId != null && !bo.getShopId().equals(shopId)) {
                return new ReturnObject<>(ReturnNo.RESOURCE_ID_NOTEXIST, "指定店铺中不存在指定ID对应的团购活动");
            }
            return new ReturnObject(bo);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR, e.getMessage());
        }
    }
}
