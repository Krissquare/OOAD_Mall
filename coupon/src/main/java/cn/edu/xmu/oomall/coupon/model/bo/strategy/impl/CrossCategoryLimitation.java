package cn.edu.xmu.oomall.coupon.model.bo.strategy.impl;

import cn.edu.xmu.oomall.coupon.model.bo.OrderItem;
import cn.edu.xmu.oomall.coupon.model.bo.strategy.BaseCouponLimitation;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author zhongyu wang 22920192204295
 * 跨品类优惠
 */
@NoArgsConstructor
public class CrossCategoryLimitation extends BaseCouponLimitation {
    public CrossCategoryLimitation(long value) {
        super(value);
    }
    @Override
    public boolean pass(List<OrderItem> orderItems) {
        long categoryCount = orderItems.stream().map(OrderItem::getCategoryId).distinct().count();
        return categoryCount >= value;
    }
}
