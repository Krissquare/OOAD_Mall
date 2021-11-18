package cn.edu.xmu.oomall.coupon;

import cn.edu.xmu.oomall.coupon.model.bo.OrderItem;
import cn.edu.xmu.oomall.coupon.model.bo.strategy.impl.AmountCouponLimitation;
import cn.edu.xmu.oomall.coupon.model.bo.strategy.impl.ComplexCouponLimitation;
import cn.edu.xmu.oomall.coupon.model.bo.strategy.impl.CrossCategoryLimitation;
import cn.edu.xmu.oomall.coupon.model.bo.strategy.impl.PriceCouponLimitation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhongyu wang 22920192204295
 */
class ComplexCouponLimitationTest {
    @Test
    public void pass() {
        List<OrderItem> orderItemList = new ArrayList<>(10);

        CrossCategoryLimitation crossCategoryLimitation1 = new CrossCategoryLimitation(3);
        CrossCategoryLimitation crossCategoryLimitation2 = new CrossCategoryLimitation(2);
        PriceCouponLimitation priceCouponLimitation1 = new PriceCouponLimitation(200);
        AmountCouponLimitation amountCouponLimitation1 = new AmountCouponLimitation(5);
        ComplexCouponLimitation complexCouponLimitation1 = new ComplexCouponLimitation(crossCategoryLimitation1, priceCouponLimitation1);
        ComplexCouponLimitation complexCouponLimitation2 = new ComplexCouponLimitation(crossCategoryLimitation2, amountCouponLimitation1);

        // 跨3类满200测试
        for(int i = 0;i<10;i++) {
            OrderItem orderItem = new OrderItem();
            orderItem.setId((long) i);
            orderItem.setPrice(20L);
            orderItem.setQuantity(1);
            orderItem.setCategoryId((long) i);

            orderItemList.add(orderItem);
        }

        Assertions.assertTrue(complexCouponLimitation1.pass(orderItemList));

        orderItemList.clear();
        for(int i = 0;i<10;i++) {
            OrderItem orderItem = new OrderItem();
            orderItem.setId((long) i);
            orderItem.setPrice(19L);
            orderItem.setQuantity(1);
            orderItem.setCategoryId((long) i);

            orderItemList.add(orderItem);
        }

        Assertions.assertFalse(complexCouponLimitation1.pass(orderItemList));

        orderItemList.clear();
        for(int i = 0;i<10;i++) {
            OrderItem orderItem = new OrderItem();
            orderItem.setId((long) i);
            orderItem.setPrice(19L);
            orderItem.setQuantity(1);
            orderItem.setCategoryId(1L);

            orderItemList.add(orderItem);
        }

        Assertions.assertFalse(complexCouponLimitation1.pass(orderItemList));

        // 跨2类满5件测试
        orderItemList.clear();
        for(int i = 0;i<10;i++) {
            OrderItem orderItem = new OrderItem();
            orderItem.setId((long) i);
            orderItem.setPrice(20L);
            orderItem.setQuantity(1);
            orderItem.setCategoryId((long) i);

            orderItemList.add(orderItem);
        }

        Assertions.assertTrue(complexCouponLimitation2.pass(orderItemList));

        orderItemList.clear();
        for(int i = 0;i<10;i++) {
            OrderItem orderItem = new OrderItem();
            orderItem.setId((long) i);
            orderItem.setPrice(20L);
            orderItem.setQuantity(1);
            orderItem.setCategoryId(1L);

            orderItemList.add(orderItem);
        }

        Assertions.assertFalse(complexCouponLimitation2.pass(orderItemList));

        orderItemList.clear();
        for(int i = 0;i<4;i++) {
            OrderItem orderItem = new OrderItem();
            orderItem.setId((long) i);
            orderItem.setPrice(20L);
            orderItem.setQuantity(1);
            orderItem.setCategoryId((long) i);

            orderItemList.add(orderItem);
        }

        Assertions.assertFalse(complexCouponLimitation2.pass(orderItemList));
    }
}