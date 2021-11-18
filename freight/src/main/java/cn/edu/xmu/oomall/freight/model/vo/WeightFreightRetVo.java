package cn.edu.xmu.oomall.freight.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author ziyi guo
 * @date 2021/11/16
 */
@Data
@NoArgsConstructor
public class WeightFreightRetVo {

    @ApiModelProperty(value = "重量运费模版id")
    private Long id;

    @ApiModelProperty(value = "运费模版id")
    private Long freightModelId;

    @ApiModelProperty(value = "首重(单位克)")
    private Integer firstWeight;

    @ApiModelProperty(value = "首重价格(单位分)")
    private Long firstWeightFreight;

    @ApiModelProperty(value = "10kg以下每单位重量价格(单位分)")
    private Long tenPrice;

    @ApiModelProperty(value = "50kg以下每单位重量价格(单位分)")
    private Long fiftyPrice;

    @ApiModelProperty(value = "100kg以下每单位重量价格(单位分)")
    private Long hundredPrice;

    @ApiModelProperty(value = "300kg以下每单位重量价格(单位分)")
    private Long trihunPrice;

    @ApiModelProperty(value = "300kg以上每单位重量价格(单位分)")
    private Long abovePrice;

    @ApiModelProperty(value = "抵达地区码")
    private Long regionId;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss.SSS" )
    private LocalDateTime gmtCreate;

    @ApiModelProperty(value = "修改时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss.SSS" )
    private LocalDateTime gmtModified;

    @ApiModelProperty(value = "创建者")
    private SimpleUserRetVo createdBy;

    @ApiModelProperty(value = "修改者")
    private SimpleUserRetVo modifiedBy;
}
