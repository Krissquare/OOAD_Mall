package cn.edu.xmu.oomall.shop.microservice;

import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.shop.microservice.vo.RefundDepositVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "Reconciliation")
public interface ReconciliationService
{
    @GetMapping("/internal/shops/{shopId}/reconsiliation/clear")
    ReturnObject isClean(Long shopId);
}