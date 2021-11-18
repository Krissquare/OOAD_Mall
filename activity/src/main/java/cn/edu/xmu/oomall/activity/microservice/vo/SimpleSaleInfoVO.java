package cn.edu.xmu.oomall.activity.microservice.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 管理员查询所有商品的价格浮动（2021-1-3） 返回的列表
 *
 * @author xiuchen lang 22920192204222
 * @date 2021/11/13 22:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleSaleInfoVO {
    Long id;
    Integer price;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime beginTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime endTime;
    Integer quantity;
    Long activityId;
    Byte type;
    Long shareActId;

}
