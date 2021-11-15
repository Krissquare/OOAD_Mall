package cn.edu.xmu.oomall.goods.model.vo;

import cn.edu.xmu.oomall.goods.model.bo.OnSale;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author YuJie 22920192204242
 * @date 2021/11/15
 */
@Data
public class ModifyOnSaleVo {


    private Long price;

    @NotBlank(message="开始时间不能为空")
    private String beginTime;

    @NotBlank(message="结束时间不能为空")
    private String endTime;

    @Min(1)
    private Integer quantity;

    public OnSale createOnsale(Long id){

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        OnSale onsale = new OnSale();

        onsale.setId(id);
        onsale.setPrice(this.price);

        onsale.setBeginTime(LocalDateTime.parse(this.beginTime,df));

        onsale.setEndTime(LocalDateTime.parse(this.endTime,df));
        onsale.setQuantity(this.quantity);

        return onsale;
    };

}
