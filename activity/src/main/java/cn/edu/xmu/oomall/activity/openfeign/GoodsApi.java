package cn.edu.xmu.oomall.activity.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Gao Yanfeng
 * @date 2021/11/13
 */
@FeignClient(value = "goods",url = "http://localhost:8081")
public interface GoodsApi {
    @GetMapping("/internal/products/{id}/onsales")
    Object getOnsSlesOfProduct(@PathVariable Long id, @RequestParam Integer page, @RequestParam Integer pageSize);

    @GetMapping("/internal/onsales/{id}")
    Object getOnSale(@PathVariable Long id);
}