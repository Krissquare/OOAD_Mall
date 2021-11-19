package cn.edu.xmu.oomall.activity.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Gao Yanfeng
 * @date 2021/11/11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "状态视图")
public class StateVo {
    private Byte code;
    private String name;

}
