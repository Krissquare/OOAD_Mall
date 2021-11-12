package cn.edu.xmu.oomall.shop.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "pay",url = "http://localhost:8081")
public interface PayApi {
    @GetMapping("payment/shops/{shopId}/settlement")
    Boolean isSettled(Long shopId);
    @PostMapping("payment/deposit/{shopId}")
    Boolean paybackDeposit(@PathVariable("shopId") Long shopId);
}