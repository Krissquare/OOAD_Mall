package cn.edu.xmu.oomall.shop.model.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "商铺审核结果视图")
public class ShopConclusionVo {
    @ApiModelProperty(value = "商铺审核结果")
    private boolean conclusion;
    public boolean getConclusion(){
        return this.conclusion;
    }
}
