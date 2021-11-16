package cn.edu.xmu.oomall.orders.dao;

import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.orders.mapper.OrderPoMapper;
import cn.edu.xmu.oomall.orders.model.bo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author RenJieZheng 22920192204334
 * @date 2021/11/16
 */
@Repository
public class OrderDao {
    @Autowired
    OrderPoMapper orderPoMapper;

    /**
     * 查看订单模块的所有活动
     * @return ReturnObject<List<Map<String, Object>>>
     */
    public ReturnObject<List<Map<String, Object>>> showAllState() {
        List<Map<String, Object>> stateList = new ArrayList<>();
        for (Order.State states : Order.State.values()) {
            Map<String, Object> temp = new HashMap<>();
            temp.put("code", states.getCode());
            temp.put("name", states.getDescription());
            stateList.add(temp);
        }
        return new ReturnObject<>(stateList);
    }
}
