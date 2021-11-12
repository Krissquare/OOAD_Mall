package cn.edu.xmu.oomall.shop.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * SimpleUserRetVo
 *
 * @author Zhiliang Li 22920192204235
 * @date 2021/11/12
 */
@Data
public class SimpleUserRetVo {
    @ApiModelProperty(value = "用户id")
    private Long id;
    @ApiModelProperty(value = "用户名")
    private String name;

    public SimpleUserRetVo(){
    }
}
