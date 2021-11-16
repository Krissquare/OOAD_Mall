package cn.edu.xmu.oomall.activity.model.vo;

import cn.edu.xmu.oomall.core.model.VoObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author xiuchen lang 22920192204222
 * @date 2021/11/12 17:38
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StrategyVo implements VoObject, Serializable {
    @Min(value = 0,message = "规则的数量不能小于0")
    @NotNull(message = "数量不能为空")
    private Long quantity;
    @Range(min = 0,max = 100,message = "规则的百分比需要在0和100之间")
    @NotNull(message = "百分比不能为空")
    private Long percentage;
    @Override
    public Object createVo() {
        return null;
    }

    @Override
    public Object createSimpleVo() {
        return null;
    }
}
