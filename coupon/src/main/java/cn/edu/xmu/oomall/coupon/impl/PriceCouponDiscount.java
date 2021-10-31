package cn.edu.xmu.oomall.coupon.impl;

import cn.edu.xmu.oomall.coupon.bo.OrderItem;
import cn.edu.xmu.oomall.coupon.BaseCouponDiscount;
import cn.edu.xmu.oomall.coupon.BaseCouponLimitation;

import java.util.List;

/**
 * @author xincong yao
 * @date 2020-11-18
 */
public class PriceCouponDiscount extends BaseCouponDiscount {

	public PriceCouponDiscount(){}

	public PriceCouponDiscount(BaseCouponLimitation limitation, long value) {
		super(limitation, value);
	}

	@Override
	public void calcAndSetDiscount(List<OrderItem> orderItems) {
		long total = 0L;
		for (OrderItem oi : orderItems) {
			total += oi.getPrice() * oi.getQuantity();
		}

		for (OrderItem oi : orderItems) {
			long discount = oi.getPrice() - (long) ((1.0 * oi.getQuantity() * oi.getPrice() / total) * value / oi.getQuantity());
			oi.setDiscount(discount);
		}
	}
}
