package cn.edu.xmu.oomall.activity.model.vo;

import cn.edu.xmu.oomall.core.model.VoObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * @author Gao Yanfeng
 * @date 2021/11/11
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupOnStrategyVo{
    @Min(-1)
    Integer quantity;

    @Min(0)
    @Max(1000)
    Integer percentage;

}
