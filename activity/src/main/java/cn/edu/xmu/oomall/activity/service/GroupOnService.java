package cn.edu.xmu.oomall.activity.service;

import cn.edu.xmu.oomall.activity.dao.GroupOnActivityDao;
import cn.edu.xmu.oomall.activity.enums.GroupOnState;
import cn.edu.xmu.oomall.activity.model.bo.GroupOnActivityBo;
import cn.edu.xmu.oomall.activity.model.po.GroupOnActivityPoExample;
import cn.edu.xmu.oomall.activity.model.vo.*;
import cn.edu.xmu.oomall.activity.openfeign.GoodsApi;
import cn.edu.xmu.oomall.activity.openfeign.ShopApi;
import cn.edu.xmu.oomall.core.util.JacksonUtil;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    @Resource
    private GoodsApi goodsApi;

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
        Object shopInfoObj = shopApi.getShopInfo(shopId);
        SimpleShopVo simpleShopVo = JacksonUtil.parseObject(JacksonUtil.toJson(shopInfoObj), "data", SimpleShopVo.class);
        return simpleShopVo.getName();
    }

    private Long getGroupOnActivityIdOfProduct(Long productId) {
        int page = 1;
        int pages = -1;
        do {
            Object onSalesOfProductObj = goodsApi.getOnsSlesOfProduct(productId, page, 10);
            PageInfoVo pageInfo = JacksonUtil.parseObject(JacksonUtil.toJson(onSalesOfProductObj), "data", PageInfoVo.class);
            for (var obj : pageInfo.getList()) {
                Long id = JacksonUtil.parseInteger(JacksonUtil.toJson(obj), "id").longValue();
                Object onSaleObj = goodsApi.getOnSale(id);
                OnSaleVo onSaleVo = JacksonUtil.parseObject(JacksonUtil.toJson(onSaleObj), "data", OnSaleVo.class);
                if (onSaleVo.getType() == 2) {
                    return onSaleVo.getActivityId();
                }
            }
            if (pages == -1) {
                pages = pageInfo.getPages();
            }
        } while (page <= pages);
        return null;
    }
}
