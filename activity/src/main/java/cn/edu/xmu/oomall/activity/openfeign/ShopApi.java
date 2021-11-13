package cn.edu.xmu.oomall.activity.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Gao Yanfeng
 * @date 2021/11/12
 */
@FeignClient(value = "shop",url = "http://localhost:8081")
public interface ShopApi {
    @GetMapping("/shops/{id}")
    Object getShopInfo(@PathVariable Long id);
}