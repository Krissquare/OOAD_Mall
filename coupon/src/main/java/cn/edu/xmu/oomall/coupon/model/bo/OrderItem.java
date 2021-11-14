package cn.edu.xmu.oomall.coupon.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xincong yao
 * @date 2020-11-16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

	private Long id;
	private Long skuId;
	private Long orderId;
	private String name;
	private Long categoryId;
	private Integer quantity;
	private Long price;
	private Long discount;
	private Long couponActivityId;
	private Long beShareId;


}
