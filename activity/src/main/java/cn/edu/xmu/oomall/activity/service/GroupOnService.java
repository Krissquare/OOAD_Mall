package cn.edu.xmu.oomall.activity.service;

import cn.edu.xmu.oomall.activity.dao.GroupOnActivityDao;
import cn.edu.xmu.oomall.activity.constant.GroupOnState;
import cn.edu.xmu.oomall.activity.model.bo.GroupOnActivity;
import cn.edu.xmu.oomall.activity.model.po.GroupOnActivityPoExample;
import cn.edu.xmu.oomall.activity.model.vo.*;
import cn.edu.xmu.oomall.activity.microservice.GoodsService;
import cn.edu.xmu.oomall.activity.microservice.ShopService;
import cn.edu.xmu.oomall.core.util.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * @return 团购的状态列表
     */
    public List<StateVo> getGroupOnStates() {
        var res = new ArrayList<StateVo>();
        for (var v : GroupOnState.values()) {
            res.add(new StateVo(v.getCode(), v.getName()));
        }
        return res;
    }

    /**
     * 添加活动
     *
     * @param shopId 店铺id
     * @param vo     添加活动的提交信息
     * @return 成功添加的活动的BO
     */
    public GroupOnActivity addActivity(Long shopId, GroupOnActivityPostVo vo) {
        if (vo.getBeginTime().isAfter(vo.getEndTime())) {
            return null;
        } else {
            var bo = (GroupOnActivity) Common.cloneVo(vo, GroupOnActivity.class);
            bo.setShopId(shopId);
            bo.setShopName(shopService.getShopInfo(shopId).getData().getName());
            bo.setBeginTime(vo.getBeginTime());
            bo.setEndTime(vo.getEndTime());
            bo.setState(GroupOnState.DRAFT);
            dao.insertActivity(bo);
            return bo;
        }
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
     * @return 符合条件的团购活动的VO的列表（分页）
     */
    public PageInfoVo<SimpleGroupOnActivityVo> getGroupOnActivities(Long productId, Long shopId, LocalDateTime beginTime,
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
        var pageInfo = dao.getGroupActivities(example, page, pageSize);
        return new PageInfoVo<>(pageInfo);
    }

    /**
     * 根据id获得团购活动BO
     *
     * @param id 团购活动id
     * @return 团购活动BO
     */
    public GroupOnActivity getGroupOnActivity(Long id) {
        return dao.getGroupOnActivity(id);
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
