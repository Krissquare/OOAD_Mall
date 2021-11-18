package cn.edu.xmu.oomall.goods.model.vo;

import cn.edu.xmu.oomall.goods.model.bo.OnSale;
import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING ,pattern="yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime beginTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING ,pattern="yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime endTime;

    private Integer quantity;



}
