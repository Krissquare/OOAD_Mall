package cn.edu.xmu.oomall.coupon.model.bo.strategy.impl;

import cn.edu.xmu.oomall.coupon.model.bo.OrderItem;
import cn.edu.xmu.oomall.coupon.model.bo.strategy.BaseCouponLimitation;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhongyu wang 22920192204295
 * 复合优惠限制类
 */
@NoArgsConstructor
public class ComplexCouponLimitation extends BaseCouponLimitation {
    private List<BaseCouponLimitation> couponLimitationList;

    public ComplexCouponLimitation(BaseCouponLimitation... couponLimitations) {
        couponLimitationList = new ArrayList<>(couponLimitations.length);
        couponLimitationList.addAll(Arrays.asList(couponLimitations));
    }

    @Override
    public boolean pass(List<OrderItem> orderItems) {
        for(BaseCouponLimitation couponLimitation : couponLimitationList) {
            if (!couponLimitation.pass(orderItems)) {
                return false;
            }
        }
        return true;
    }
}
