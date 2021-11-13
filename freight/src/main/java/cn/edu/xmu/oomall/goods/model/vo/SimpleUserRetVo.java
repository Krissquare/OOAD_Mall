package cn.edu.xmu.oomall.goods.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ziyi guo
 * @date 2021/11/10
 */
@Data
@NoArgsConstructor
public class SimpleUserRetVo {

    @ApiModelProperty(value = "管理员id")
    private Long id;

    @ApiModelProperty(value = "管理员名")
    private String userName;

}
