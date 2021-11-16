package cn.edu.xmu.oomall.shop.model.vo;

import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Created with IntelliJ IDEA.
 *
 * @author BingShuai Liu
 * @studentId 22920192204245
 * @date 2021/11/13/16:00
 */
@Data
@Setter
public class OnSaleRetVo {
    private Long id;
    private Long price;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private Integer quantity;

}
