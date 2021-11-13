package cn.edu.xmu.oomall.coupon;

import cn.edu.xmu.oomall.coupon.model.bo.strategy.BaseCouponDiscount;
import cn.edu.xmu.oomall.coupon.model.bo.strategy.impl.PriceCouponDiscount;
import cn.edu.xmu.oomall.coupon.model.bo.strategy.impl.PriceCouponLimitation;
import com.fasterxml.jackson.core.JsonProcessingException;


import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.lang.reflect.InvocationTargetException;

/**
 * @author xincong yao
 * @date 2020-11-23
 * modified by: Ming Qiu
 * date: 2021-11-11
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class BaseCouponDiscountTest {

	@Test
	public void toJsonStringTest() throws JsonProcessingException {
		BaseCouponDiscount discount1 = new PriceCouponDiscount(new PriceCouponLimitation(10), 1);
		String s = discount1.toJsonString();
	}

	@Test
	public void getInstanceTest() throws JsonProcessingException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
		BaseCouponDiscount discount1 = new PriceCouponDiscount(new PriceCouponLimitation(10), 1);
		String s = discount1.toJsonString();

		BaseCouponDiscount d = discount1.getInstance(s);

		Assert.assertEquals(("{\"value\":1,\"className\":\"cn.edu.xmu.oomall.coupon.model.bo.strategy.impl.PriceCouponDiscount\",\"couponLimitation\":{\"value\":10,\"className\":\"cn.edu.xmu.oomall.coupon.model.bo." +
				"strategy.impl.PriceCouponLimitation\"}}"), d.toJsonString());
	}
}
