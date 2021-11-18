package cn.edu.xmu.oomall.freight.microservice;

import cn.edu.xmu.oomall.core.util.ReturnObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author ziyi guo
 * @date 2021/11/17
 */
@FeignClient(name = "FreightModel")
public interface FreightModelService {
    @GetMapping("/internal/shops/{shopId}/freightmodels/{id}/type")
    ReturnObject getFreightModelType(Long id);
}
