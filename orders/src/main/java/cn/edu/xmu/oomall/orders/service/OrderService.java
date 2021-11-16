package cn.edu.xmu.oomall.orders.service;

import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.orders.dao.OrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 *
 * @author RenJieZheng 22920192204334
 * @date 2021/11/16
 */
@Service
public class OrderService {
    @Autowired
    OrderDao orderDao;

    /**
     * 查看订单模块的所有活动
     * @return List<StateRetVo>list
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public ReturnObject<List<Map<String, Object>>> showAllState(){
        return orderDao.showAllState();
    }


}
