package cn.edu.xmu.oomall.goods.microservice;

import cn.edu.xmu.oomall.core.util.ReturnObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author YuJie
 */
@FeignClient(value = "shop")
public interface ShopService {

    @GetMapping("shops/{id}")
    ReturnObject getInfo(@PathVariable Long id);
}