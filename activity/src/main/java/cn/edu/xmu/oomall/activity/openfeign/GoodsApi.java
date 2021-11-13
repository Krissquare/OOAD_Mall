package cn.edu.xmu.oomall.activity.openfeign;

import cn.edu.xmu.oomall.activity.openfeign.vo.goods.OnSaleInfoDTO;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author: xiuchen lang 22920192204222
 * @Date: 2021/11/13 12:21
 */
@FeignClient(value = "goods-service")
public interface GoodsApi {
    /**
     * 获得对应product的分享活动id
     * @param id productId
     * @return
     */
    @GetMapping("internal/onsales/{id}")
    public ReturnObject<OnSaleInfoDTO> getOnSaleByProductId(@PathVariable("id") Long id);


}
