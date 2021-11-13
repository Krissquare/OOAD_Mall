package cn.edu.xmu.oomall.activity.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author Gao Yanfeng
 * @date 2021/11/11
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "创建团购活动时的提交信息")
public class GroupOnActivityPostVo {
    @Length(min = 1, max = 128)
    @NotNull
    private String name;

    @NotBlank
    private String beginTime;

    @NotBlank
    private String endTime;

    private List<@Valid GroupOnStrategyVo> strategy;
}
