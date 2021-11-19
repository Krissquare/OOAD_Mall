package cn.edu.xmu.oomall.activity.model.vo;

import cn.edu.xmu.oomall.activity.model.po.AdvanceSalePo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class AdvanceSaleVo implements Serializable {
    @ApiModelProperty(value = "商店id")
    private Long shopId;
    @ApiModelProperty(value = "商店名")
    private String shopName;
    @ApiModelProperty(value = "活动名")
    private String name;
    @ApiModelProperty(value = "支付首款时间")
    private LocalDateTime payTime;
    @ApiModelProperty(value = "首款金额")
    private Long advancePayPrice;
    @ApiModelProperty(value = "创建者id")
    private Long createdBy;
    @ApiModelProperty(value = "创建者姓名")
    private String createName;
    @ApiModelProperty(value = "修改者id")
    private Long modifiedBy;
    @ApiModelProperty(value = "修改者id")
    private String modiName;
    @ApiModelProperty(value = "活动状态")
    private Byte state;

    public AdvanceSaleVo() {
    }
}
