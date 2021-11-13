package cn.edu.xmu.oomall.activity.service;

import cn.edu.xmu.oomall.activity.dao.GroupOnActivityDao;
import cn.edu.xmu.oomall.activity.enums.GroupOnState;
import cn.edu.xmu.oomall.activity.model.bo.GroupOnActivityBo;
import cn.edu.xmu.oomall.activity.model.po.GroupOnActivityPoExample;
import cn.edu.xmu.oomall.activity.model.vo.*;
import cn.edu.xmu.oomall.activity.openfeign.ShopApi;
import cn.edu.xmu.oomall.core.util.JacksonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gao Yanfeng
 * @date 2021/11/11
 */
@Service
public class GroupOnService {

    @Autowired
    private GroupOnActivityDao dao;

    @Resource
    private ShopApi shopApi;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public List<StateVo> getGroupOnStates() {
        var res = new ArrayList<StateVo>();
        for (var v : GroupOnState.values()) {
            res.add(new StateVo(v.getCode(), v.getName()));
        }
        return res;
    }

    public GroupOnActivityBo addActivity(Long shopId, GroupOnActivityPostVo vo) {
        LocalDateTime begin = LocalDateTime.parse(vo.getBeginTime(), FORMATTER);
        LocalDateTime end = LocalDateTime.parse(vo.getEndTime(), FORMATTER);
        if (begin.isAfter(end)) {
            return null;
        } else {
            var bo = new GroupOnActivityBo();
            bo.setName(vo.getName());
            bo.setShopId(shopId);
            bo.setStrategy(vo.getStrategy());
            bo.setBeginTime(begin);
            bo.setEndTime(end);
            bo.setState(GroupOnState.DRAFT);
            dao.insertActivity(bo, getShopName(shopId));
            return bo;
        }
    }

    public PageInfoVo<SimpleGroupOnActivityVo> getGroupOnActivities(Long productId, Long shopId, String beginTime,
                                                                 String endTime, GroupOnState state,
                                                                 Integer page, Integer pageSize) {
        var example = new GroupOnActivityPoExample();
        var criteria = example.createCriteria();
        if (productId != null) {
            // TODO: 通过productId找到shopId
        }
        if (shopId != null) {
            criteria.andShopIdEqualTo(shopId);
        }
        if (beginTime != null) {
            var begin = LocalDateTime.parse(beginTime, FORMATTER);
            criteria.andBeginTimeGreaterThanOrEqualTo(begin);
        }
        if (endTime != null) {
            var end = LocalDateTime.parse(endTime, FORMATTER);
            criteria.andEndTimeLessThanOrEqualTo(end);
        }
        if (state != null) {
            criteria.andStateEqualTo(state.getCode().byteValue());
        }
        var pageInfo = dao.getGroupActivities(example, page, pageSize);
        return new PageInfoVo<>(pageInfo);
    }

    public GroupOnActivityBo getGroupOnActivity(Long id) {
        return dao.getGroupOnActivity(id);
    }

    private String getShopName(Long shopId) {
        SimpleShopVo simpleShopVo = JacksonUtil.parseObject(JacksonUtil.toJson(shopApi.getShopInfo(shopId)), "data", SimpleShopVo.class);
        return simpleShopVo.getName();
    }
}
