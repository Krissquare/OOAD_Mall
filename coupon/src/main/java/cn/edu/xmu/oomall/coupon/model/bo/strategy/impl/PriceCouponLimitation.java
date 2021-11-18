package cn.edu.xmu.oomall.coupon.model.bo.strategy.impl;

import cn.edu.xmu.oomall.coupon.model.bo.OrderItem;
import cn.edu.xmu.oomall.coupon.model.bo.strategy.BaseCouponLimitation;

import java.util.List;

/**
 * @author xincong yao
 * @date 2020-11-18
 * modified by zhongyu wang
 * date 2021-11-12
 */
public class PriceCouponLimitation extends BaseCouponLimitation {

	public PriceCouponLimitation() {

	}

	public PriceCouponLimitation(long value) {
		super(value);
	}

	@Override
	public boolean pass(List<OrderItem> orderItems) {
		long t = 0;
		for (OrderItem oi : orderItems) {
			t += oi.getQuantity() * oi.getPrice();
		}
		return t >= value;
	}

}
