package cn.edu.xmu.oomall.goods.model.vo;

import cn.edu.xmu.oomall.goods.model.bo.OnSale;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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



}
