package cn.edu.xmu.oomall.activity.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Gao Yanfeng
 * @date 2021/11/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "简单团购活动视图")
public class SimpleGroupOnActivityVo {
    private Long id;
    private String name;

}
