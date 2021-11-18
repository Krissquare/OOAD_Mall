package cn.edu.xmu.oomall.activity.microservice;

import cn.edu.xmu.oomall.activity.microservice.vo.SimpleSaleInfoVO;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import com.github.pagehelper.PageInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author xiuchen lang 22920192204222
 * @date 2021/11/13 12:21
 */
@FeignClient(value = "goods-service")
public interface GoodsService {

    /**
     * 管理员查询所有商品的价格浮动
     * //TODO: 注：/internal/products/{id}/onsales 管理员查询所有商品的价格浮动（2021-1-3） 这个api没有type 等 活动id 为了正确得到,自己加的
     *
     * @param id
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("internal/onsales/{id}")
    public ReturnObject<PageInfo<SimpleSaleInfoVO>> getOnSalesByProductId(@PathVariable("id") Long id, @RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize);

}
