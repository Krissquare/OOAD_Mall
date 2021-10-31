package cn.edu.xmu.oomall.coupon;

import cn.edu.xmu.oomall.coupon.bo.OrderItem;

import java.util.List;

/**
 * @author xincong yao
 * @date 2020-11-18
 */
public interface Computable {

	List<OrderItem> compute(List<OrderItem> orderItems);
}
