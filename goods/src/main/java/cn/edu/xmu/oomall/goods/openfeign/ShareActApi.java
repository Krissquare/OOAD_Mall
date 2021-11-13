package cn.edu.xmu.oomall.goods.openfeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author YuJie 22920192204242
 * @date 2021/11/12
 */
@FeignClient(value = "share",url = "http://localhost:8081")
public interface ShareActApi {

    @GetMapping("share_activity/{id}/name")
    String getName(@PathVariable  Long id);
}