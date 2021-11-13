package cn.edu.xmu.oomall.activity.service;

import cn.edu.xmu.oomall.activity.dao.GroupOnActivityDao;
import cn.edu.xmu.oomall.activity.enums.GroupOnState;
import cn.edu.xmu.oomall.activity.model.bo.GroupOnActivityBo;
import cn.edu.xmu.oomall.activity.model.po.GroupOnActivityPoExample;
import cn.edu.xmu.oomall.activity.model.vo.*;
import cn.edu.xmu.oomall.activity.microservice.GoodsService;
import cn.edu.xmu.oomall.activity.microservice.ShopService;
import cn.edu.xmu.oomall.activity.microservice.vo.OnSaleVo;
import cn.edu.xmu.oomall.activity.microservice.vo.SimpleShopVo;
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
    private ShopService shopService;

    @Resource
    private GoodsService goodsService;

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
            Long activityId = getGroupOnActivityIdOfProduct(productId);
            if (activityId != null) {
                criteria.andIdEqualTo(activityId);
            }
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
        var ret = shopService.getShopInfo(shopId);
        return ret.getData().getName();
    }

    private Long getGroupOnActivityIdOfProduct(Long productId) {
        int page = 1;
        int pages = -1;
        do {
            var ret = goodsService.getOnsSlesOfProduct(productId, page, 10);
            for (var simpleOnSale : ret.getData().getList()) {
                var onSale = goodsService.getOnSale(simpleOnSale.getId());
                if (onSale.getData().getType() == 2) {
                    return onSale.getData().getActivityId();
                }
            }
            if (pages == -1) {
                pages = ret.getData().getPages();
            }
        } while (page <= pages);
        return null;
    }
}
