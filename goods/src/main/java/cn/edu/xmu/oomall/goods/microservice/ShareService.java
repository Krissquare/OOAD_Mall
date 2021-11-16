package cn.edu.xmu.oomall.goods.microservice;

import cn.edu.xmu.oomall.core.util.ReturnObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author YuJie 22920192204242
 * @date 2021/11/12
 */
@FeignClient(value = "share")
public interface ShareService {

    @GetMapping("shareactivities/{id}")
    ReturnObject getInfo(@PathVariable  Long id);
}