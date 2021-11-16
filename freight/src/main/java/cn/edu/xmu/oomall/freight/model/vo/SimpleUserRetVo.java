package cn.edu.xmu.oomall.freight.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ziyi guo
 * @date 2021/11/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimpleUserRetVo {

    @ApiModelProperty(value = "管理员id")
    private Long id;

    @ApiModelProperty(value = "管理员名")
    private String name;

}
