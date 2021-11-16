package cn.edu.xmu.oomall.orders.dao;

import cn.edu.xmu.oomall.orders.mapper.OrderItemPoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author RenJieZheng 22920192204334
 * @date 2021/11/16
 */
@Repository
public class OrderItemDao {
    @Autowired
    OrderItemPoMapper orderItemPoMapper;

}
