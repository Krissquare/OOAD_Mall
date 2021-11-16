package cn.edu.xmu.oomall.freight.microservice;

import cn.edu.xmu.oomall.core.util.ReturnObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * @author RenJieZheng 22920192204334
 */
@Component
@FeignClient(value = "goods",url="http://localhost:8081")
public interface ProductService {
    /**
     * 通过商品id获得运费模块id
     * @param id
     * @return 运费模块id
     */
    @GetMapping(value = "/internal/product/{id}")
    ReturnObject<Long> getFreightIdByProductId(@PathVariable Long id);


    /**
     * 判断是否存在上架销售商品，有不能删除运费模板
     * @param id
     * @return 是否可以删除
     */
    @GetMapping(value = "/internal/freightmodel/{id}/deletion")
    ReturnObject<Boolean> isToBeDelete(@PathVariable Long id);

    /**
     * 根据模板id同步删除与商品的关系
     * @param id 模板id
     * @return 删除结果
     */
    @PutMapping(value = "/internal/freightmodel/{id}/deletion")
    ReturnObject deleteRelationshipWithFreightModel(@PathVariable Long id);

}
