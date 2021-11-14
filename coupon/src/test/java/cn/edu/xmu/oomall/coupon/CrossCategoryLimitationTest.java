package cn.edu.xmu.oomall.coupon;

import cn.edu.xmu.oomall.coupon.model.bo.OrderItem;
import cn.edu.xmu.oomall.coupon.model.bo.strategy.impl.CrossCategoryLimitation;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhongyu wang 22920192204295
 */
class CrossCategoryLimitationTest {
    private Logger logger = LoggerFactory.getLogger(CrossCategoryLimitationTest.class);
    @Test
    public void pass() throws JsonProcessingException {
        List<OrderItem> orderItemList = new ArrayList<>(10);
        for(int i = 0;i<10;i++) {
            OrderItem orderItem = new OrderItem();
            orderItem.setId((long) i);
            orderItem.setCategoryId((long) i);

            orderItemList.add(orderItem);
        }

        CrossCategoryLimitation crossCategoryLimitation = new CrossCategoryLimitation(3);

        Assertions.assertTrue(crossCategoryLimitation.pass(orderItemList));

        orderItemList.clear();
        for(int i = 0;i<10;i++) {
            OrderItem orderItem = new OrderItem();
            orderItem.setId((long) i);
            orderItem.setCategoryId(1L);

            orderItemList.add(orderItem);
        }

        Assertions.assertFalse(crossCategoryLimitation.pass(orderItemList));
    }
}