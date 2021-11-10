package cn.edu.xmu.oomall.coupon.model.bo.strategy.impl;

import cn.edu.xmu.oomall.coupon.model.bo.OrderItem;
import cn.edu.xmu.oomall.coupon.model.bo.strategy.BaseCouponLimitation;

import java.util.List;

/**
 * @author xincong yao
 * @date 2020-11-19
 */
public class AmountCouponLimitation extends BaseCouponLimitation {

	public AmountCouponLimitation(){}

	public AmountCouponLimitation(long value) {
		super(value);
	}

	@Override
	public boolean pass(List<OrderItem> orderItems) {
		long t = 0;
		for (OrderItem oi : orderItems) {
			t += oi.getQuantity();
		}
		return t >= value;
	}
}
