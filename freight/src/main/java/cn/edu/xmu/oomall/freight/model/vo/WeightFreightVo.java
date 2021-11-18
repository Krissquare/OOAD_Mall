package cn.edu.xmu.oomall.freight.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author ziyi guo
 * @date 2021/11/16
 */
@Data
@NoArgsConstructor
public class WeightFreightVo {

    @NotNull(message="首重不能为空")
    @ApiModelProperty(value = "首重")
    private Integer firstWeight;

    @NotNull(message="首重价格不能为空")
    @ApiModelProperty(value = "首重价格")
    private Long firstWeightFreight;

    @NotNull(message="10kg以下每单位重量价格不能为空")
    @ApiModelProperty(value = "10kg以下每单位重量价格")
    private Long tenPrice;

    @NotNull(message="50kg以下每单位重量价格不能为空")
    @ApiModelProperty(value = "50kg以下每单位重量价格")
    private Long fiftyPrice;

    @NotNull(message="100kg以下每单位重量价格不能为空")
    @ApiModelProperty(value = "100kg以下每单位重量价格")
    private Long hundredPrice;

    @NotNull(message="300kg以下每单位重量价格不能为空")
    @ApiModelProperty(value = "300kg以下每单位重量价格")
    private Long trihunPrice;

    @NotNull(message="300kg以上每单位重量价格不能为空")
    @ApiModelProperty(value = "300kg以上每单位重量价格")
    private Long abovePrice;

    @NotNull(message="抵达地区码不能为空")
    @ApiModelProperty(value = "抵达地区码")
    private Long regionId;
}
