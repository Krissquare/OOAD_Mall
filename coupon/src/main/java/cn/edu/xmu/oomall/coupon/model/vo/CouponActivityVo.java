package cn.edu.xmu.oomall.coupon.model.vo;

import cn.edu.xmu.oomall.core.model.VoObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

/**
 * @author RenJieZheng 22920192204334
 */
@Data
@AllArgsConstructor
@NoArgsConstructor

public class CouponActivityVo implements VoObject {
    @Length(max = 100,message = "name字符串最大长度为100")
    private String name;
    @Min(value = 0,message = "quantity最小值为0")
    private Integer quantity;
    @Min(value = 0,message = "quantityType最小值为0")
    @Max(value = 1,message = "quantityType最大值为1")
    private Byte quantityType;
    @Min(value = 0,message = "validTerm最小值为0")
    @Max(value = 1,message = "validTerm最大值为1")
    private Byte validTerm;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime couponTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime beginTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;
    @Length(max = 100,message = "strategy字符串最大长度为100")
    private String strategy;

    @Override
    public Object createVo() {
        return this;
    }

    @Override
    public Object createSimpleVo() {
        return this;
    }
}
