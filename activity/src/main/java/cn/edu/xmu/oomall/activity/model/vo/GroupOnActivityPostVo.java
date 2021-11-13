package cn.edu.xmu.oomall.activity.model.vo;

import cn.edu.xmu.oomall.activity.model.bo.GroupOnActivityBo;
import cn.edu.xmu.oomall.core.model.VoObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.annotation.Nullable;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Gao Yanfeng
 * @date 2021/11/11
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupOnActivityPostVo {
    @Length(min = 1, max = 128)
    @NotBlank
    private String name;

    @NotBlank
    private String beginTime;

    @NotBlank
    private String endTime;

    private List<@Valid GroupOnStrategyVo> strategy;
}
