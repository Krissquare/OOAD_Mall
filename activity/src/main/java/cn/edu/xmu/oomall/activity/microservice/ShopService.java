package cn.edu.xmu.oomall.activity.microservice;

import cn.edu.xmu.oomall.activity.microservice.vo.SimpleShopVo;
import cn.edu.xmu.oomall.activity.microservice.vo.ShopInfoVO;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Gao Yanfeng
 * @date 2021/11/12
 */
@FeignClient(name = "Shop")
public interface ShopService {
    @GetMapping("/shops/{id}")
    ReturnObject<SimpleShopVo> getShopInfo(@PathVariable Long id);

    /**
     * 获得商铺信息
     * @param id 商铺id
     * @return
     */
    @GetMapping("/shops/{id}")
    public ReturnObject<ShopInfoVO> getShop(@PathVariable("id")Long id);

}
