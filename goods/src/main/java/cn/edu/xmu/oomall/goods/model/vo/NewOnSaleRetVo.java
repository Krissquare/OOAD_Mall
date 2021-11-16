package cn.edu.xmu.oomall.goods.model.vo;

import cn.edu.xmu.oomall.goods.model.bo.OnSale;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author yujie lin
 * @date 2021/11/10
 */
@Data
public class NewOnSaleRetVo {
    private Long id;

    private Long price;

    private LocalDateTime beginTime;

    private LocalDateTime endTime;

    private Integer quantity;

    public NewOnSaleRetVo(OnSale onsale) {
        this.id = onsale.getId();
        this.price=onsale.getPrice();
        this.beginTime=onsale.getBeginTime();
        this.endTime=onsale.getEndTime();
        this.quantity= onsale.getQuantity();
    }

}
