package cn.edu.xmu.oomall.activity.dao;

import cn.edu.xmu.oomall.activity.constant.GroupOnState;
import cn.edu.xmu.oomall.activity.mapper.GroupOnActivityPoMapper;
import cn.edu.xmu.oomall.activity.model.bo.GroupOnActivity;
import cn.edu.xmu.oomall.activity.model.po.GroupOnActivityPo;
import cn.edu.xmu.oomall.activity.model.po.GroupOnActivityPoExample;
import cn.edu.xmu.oomall.activity.model.vo.GroupOnActivityVo;
import cn.edu.xmu.oomall.activity.model.vo.GroupOnStrategyVo;
import cn.edu.xmu.oomall.activity.model.vo.PageInfoVo;
import cn.edu.xmu.oomall.activity.model.vo.SimpleGroupOnActivityVo;
import cn.edu.xmu.oomall.core.util.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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

    @Value("${redispara.groupon.expiretime}")
    private long timeout;

    public ReturnObject insertActivity(GroupOnActivity bo) {
        try {
            GroupOnActivityPo po = (GroupOnActivityPo) Common.cloneVo(bo, GroupOnActivityPo.class);
            po.setStrategy(JacksonUtil.toJson(bo.getStrategy()));
            Common.setPoCreatedFields(po, 1L, "admin");
            mapper.insert(po);
            redisUtil.set("groupon_" + po.getId().toString(), po, timeout);
            bo.setId(po.getId());
            return new ReturnObject(Common.cloneVo(bo, SimpleGroupOnActivityVo.class));
        } catch (Exception e) {
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR);
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
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR);
        }
    }

    public ReturnObject getGroupOnActivity(Long id, GroupOnState state, Long shopId) {
        try {
            var po = (GroupOnActivityPo) redisUtil.get("groupon_" + id.toString());
            if (po == null) {
                po = mapper.selectByPrimaryKey(id);
            }
            if (po == null) {
                return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST, "未找到指定ID对应的团购活动");
            }
            if (state != null && !po.getState().equals(state.getCode().byteValue())) {
                return new ReturnObject<>(ReturnNo.RESOURCE_FALSIFY, "团购活动未上线");
            }
            if (shopId != null && !po.getShopId().equals(shopId)) {
                return new ReturnObject<>(ReturnNo.RESOURCE_ID_NOTEXIST, "指定店铺中不存在指定ID对应的团购活动");
            }
            var ret = (GroupOnActivity) Common.cloneVo(po, GroupOnActivity.class);
            ret.setStrategy(JacksonUtil.parseObjectList(po.getStrategy(), GroupOnStrategyVo.class));
            return new ReturnObject(ret);
        } catch (Exception e) {
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR);
        }
    }
}
