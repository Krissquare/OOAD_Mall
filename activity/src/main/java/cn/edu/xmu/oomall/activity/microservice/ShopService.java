package cn.edu.xmu.oomall.activity.microservice;

import cn.edu.xmu.oomall.activity.microservice.vo.ShopInfoVO;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author xiuchen lang 22920192204222
 * @date 2021/11/13 13:54
 */
@FeignClient(value = "goods-service")
public interface ShopService {
    /**
     * 获得商铺信息
     * @param id 商铺id
     * @return
     */
    @GetMapping("/shops/{id}")
    public ReturnObject<ShopInfoVO> getShop(@PathVariable("id")Long id);

}
