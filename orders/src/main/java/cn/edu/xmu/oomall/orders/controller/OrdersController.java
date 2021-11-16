package cn.edu.xmu.oomall.orders.controller;


import cn.edu.xmu.oomall.core.util.Common;
import cn.edu.xmu.oomall.orders.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 *
 * @author RenJieZheng 22920192204334
 * @date 2021/11/16
 */
@RequestMapping(value = "/",produces = "application/json;charset=UTF-8")
@RestController
public class OrdersController {

    @Autowired
    OrderService orderService;

    /**
     * 查看优惠活动模块的所有活动
     * @return List<Map<String, Object>>
     */
    @GetMapping("orders/states")
    public Object showAllState(){
        return  Common.decorateReturnObject(orderService.showAllState());
    }


    public Object showCustomerOwnOrderInfo(@RequestParam(required = false)String orderSn,
                                           @RequestParam(required = false)Byte state,
                                           @RequestParam(required = false)LocalDateTime beginTime,
                                           @RequestParam(required = false)LocalDateTime endTime,
                                           @RequestParam Integer page,
                                           @RequestParam Integer pageSize,
                                           Long userId,
                                           String userName) {
        if()

    }
}
