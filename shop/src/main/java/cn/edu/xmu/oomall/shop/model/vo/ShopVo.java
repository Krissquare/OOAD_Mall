package cn.edu.xmu.oomall.shop.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@ApiModel(value = "Shop视图")
public class ShopVo {
    @NotBlank(message = "商铺名不能为空")
    @ApiModelProperty(value = "shop名称")
    private String name;

    @ApiModelProperty(value = "shop状态")
    private Byte state;
}
