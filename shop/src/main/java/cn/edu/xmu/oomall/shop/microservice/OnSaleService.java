package cn.edu.xmu.oomall.shop.microservice;

import cn.edu.xmu.oomall.core.util.ReturnObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Created with IntelliJ IDEA.
 *
 * @author BingShuai Liu
 * @studentId 22920192204245
 * @date 2021/11/14/12:03
 */
@FeignClient(value = "onsale")
public interface OnSaleService {
    @GetMapping(path = "/internal/onsales/{id}")
    ReturnObject getOnSaleById(@PathVariable("id") Long id);

    @PostMapping("/internal/onSales/{id}/shareActivities/{sid}")
    ReturnObject updateAddOnSaleShareActId(@PathVariable("id") Long id,@PathVariable("sid") Long sid);

    @DeleteMapping("/internal/onSales/{id}/shareActivities/{sid}")
    ReturnObject updateDeleteOnSaleShareActId(@PathVariable("id") Long id,@PathVariable("sid") Long sid);
}
