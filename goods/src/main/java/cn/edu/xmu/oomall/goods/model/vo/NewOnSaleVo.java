package cn.edu.xmu.oomall.goods.model.vo;

import cn.edu.xmu.oomall.goods.model.bo.OnSale;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss.SSS" )
    private LocalDateTime beginTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss.SSS" )
    private LocalDateTime endTime;

    @Min(1)
    private Integer quantity;

    private Integer type;

    public NewOnSaleVo(){}


}
