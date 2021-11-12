package cn.edu.xmu.oomall.shop.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

/**
 * @author  Xusheng Wang
 * @date  2021-11-11
 */

@Getter
public class SimpleAdminUserVo {
    @ApiModelProperty(value = "管理员id")
    Long id;

    @ApiModelProperty(value = "管理员名称")
    String name;

    public SimpleAdminUserVo(Long id, String name) {
        this.name=name;
        this.id=id;
    }

    public SimpleAdminUserVo() {

    }
}
