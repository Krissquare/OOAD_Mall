package cn.edu.xmu.oomall.activity.service;

import cn.edu.xmu.oomall.activity.dao.GroupOnActivityDao;
import cn.edu.xmu.oomall.activity.constant.GroupOnState;
import cn.edu.xmu.oomall.activity.model.bo.GroupOnActivity;
import cn.edu.xmu.oomall.activity.model.po.GroupOnActivityPoExample;
import cn.edu.xmu.oomall.activity.model.vo.*;
import cn.edu.xmu.oomall.activity.microservice.GoodsService;
import cn.edu.xmu.oomall.activity.microservice.ShopService;
import cn.edu.xmu.oomall.core.util.Common;
import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
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

    /**
     * 获得团购的所有状态
     *
     * @return ReturnObject
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public ReturnObject getGroupOnStates() {
        var res = new ArrayList<StateVo>();
        for (var v : GroupOnState.values()) {
            res.add(new StateVo(v.getCode().byteValue(), v.getName()));
        }
        return new ReturnObject(res);
    }

    /**
     * 添加活动
     *
     * @param shopId 店铺id
     * @param vo     添加活动的提交信息
     * @return ReturnObject
     */
    @Transactional(rollbackFor = Exception.class)
    public ReturnObject addActivity(Long shopId, GroupOnActivityPostVo vo, Long createBy, String createName) {
            var bo = (GroupOnActivity) Common.cloneVo(vo, GroupOnActivity.class);
            bo.setShopId(shopId);
            bo.setShopName(shopService.getShopInfo(shopId).getData().getName());
            bo.setState(GroupOnState.DRAFT);
            return dao.insertActivity(bo, createBy, createName);
    }

    /**
     * 根据条件查询符合条件的团购活动
     *
     * @param productId 货品id
     * @param shopId    商铺id
     * @param beginTime 不早于此开始时间
     * @param endTime   不晚于此结束时间
     * @param state     状态
     * @param page      页码
     * @param pageSize  每页大小
     * @return ReturnObject
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public ReturnObject getGroupOnActivities(Long productId, Long shopId, LocalDateTime beginTime,
                                             LocalDateTime endTime, GroupOnState state,
                                             Integer page, Integer pageSize) {
        var example = new GroupOnActivityPoExample();
        var criteria = example.createCriteria();
        if (productId != null) {
            var ids = getGroupOnActivitiesOfProduct(productId);
            criteria.andIdIn(ids);
        }
        if (shopId != null) {
            criteria.andShopIdEqualTo(shopId);
        }
        if (beginTime != null) {
            criteria.andBeginTimeGreaterThanOrEqualTo(beginTime);
        }
        if (endTime != null) {
            criteria.andEndTimeLessThanOrEqualTo(endTime);
        }
        if (state != null) {
            criteria.andStateEqualTo(state.getCode().byteValue());
        }
        return dao.getGroupActivities(example, page, pageSize);
    }

    /**
     * 根据id获得团购活动BO，可以指定状态/店铺id
     *
     * @param id 团购活动id
     * @param state 团购活动状态
     * @param shopId 店铺id
     * @return ReturnObject
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public ReturnObject getGroupOnActivity(Long id, GroupOnState state, Long shopId) {
        return dao.getGroupOnActivity(id, state, shopId);
    }


    private List<Long> getGroupOnActivitiesOfProduct(Long productId) {
        int page = 1;
        int pages = -1;
        var list = new ArrayList<Long>();
        do {
            var ret = goodsService.getOnsSlesOfProduct(productId, page, 10);
            for (var simpleOnSale : ret.getData().getList()) {
                var onSale = goodsService.getOnSale(simpleOnSale.getId());
                if (onSale.getData().getType() == 2) {
                    list.add(onSale.getData().getActivityId());
                }
            }
            if (pages == -1) {
                pages = ret.getData().getPages();
            }
            page++;
        } while (page <= pages);
        return list;
    }
}
