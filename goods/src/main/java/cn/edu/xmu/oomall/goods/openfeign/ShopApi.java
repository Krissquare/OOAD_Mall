package cn.edu.xmu.oomall.goods.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author YuJie
 */
@FeignClient(value = "shop",url = "http://localhost:8081")
public interface ShopApi {

    @GetMapping("shops/{id}/name")
    String getName(@PathVariable Long id);
}