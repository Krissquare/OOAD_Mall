package cn.edu.xmu.oomall.freight.controller;

import cn.edu.xmu.oomall.core.util.ReturnObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiuchen lang 22920192204222
 * @date 2021/11/16 21:30
 */
@RestController
@RequestMapping(value = "/", produces = "application/json;charset=UTF-8")
public class WeightFreightController {
    @PostMapping("/shops/{shopId}/freightmodels/{id}/weightItems")
    public ReturnObject addWeightFreight(Long createId,String createName,@PathVariable("shopId")Long shopId,@PathVariable("id")Long id){
        createId=666L;
        createName="lxc";

        return null;
    }

}
