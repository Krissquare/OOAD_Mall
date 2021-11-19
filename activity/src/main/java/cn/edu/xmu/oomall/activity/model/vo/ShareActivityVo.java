package cn.edu.xmu.oomall.activity.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author xiuchen lang 22920192204222
 * @date 2021/11/12 17:36
 */
@Data
@ToString
public class ShareActivityVo {
    @NotBlank(message="预售活动名称不能为空")
    private String name;

    @NotNull(message="开始时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    private LocalDateTime beginTime;

    @NotNull(message="结束时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    private LocalDateTime endTime;

    @Valid
    private List<StrategyVo> strategy;
}
