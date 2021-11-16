package cn.edu.xmu.oomall.goods.model.vo;

import cn.edu.xmu.oomall.goods.model.bo.OnSale;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author yujie lin
 * @date 2021/11/10
 */
@Data
public class NewOnSaleVo {

    private Long price;

    @NotBlank(message="开始时间不能为空")
    private String beginTime;

    @NotBlank(message="结束时间不能为空")
    private String endTime;

    @Min(1)
    private Integer quantity;

    private Integer type;

    public OnSale createOnsale(Long shopId, Long productId){

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        OnSale onsale = new OnSale();
        onsale.setShopId(shopId);
        onsale.setProductId(productId);
        onsale.setPrice(this.price);

        onsale.setBeginTime(LocalDateTime.parse(this.beginTime,df));

        onsale.setEndTime(LocalDateTime.parse(this.endTime,df));
        onsale.setQuantity(this.quantity);
        onsale.setType(OnSale.Type.getTypeByCode(type));
        onsale.setState(OnSale.State.DRAFT);

        return onsale;
    };
}
