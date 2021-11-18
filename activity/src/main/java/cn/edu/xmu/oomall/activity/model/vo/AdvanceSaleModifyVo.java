package cn.edu.xmu.oomall.activity.model.vo;

import cn.edu.xmu.oomall.activity.model.po.AdvanceSalePo;
import cn.edu.xmu.oomall.activity.util.DateTimeUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;


@Data
public class AdvanceSaleModifyVo {

    @ApiModelProperty(value = "活动名")
    private String name;
    @JsonFormat(pattern = "YY-MM-dd hh:mm:ss")
    @ApiModelProperty(value = "开始时间")
    private LocalDateTime beginTime;
    @JsonFormat(pattern = "YY-MM-dd hh:mm:ss")
    @ApiModelProperty(value = "结束时间")
    private LocalDateTime endTime;
    @ApiModelProperty(value = "支付首款时间")
    private LocalDateTime payTime;
    @ApiModelProperty(value = "首款金额")
    private Long advancePayPrice;
    @ApiModelProperty(value = "数量")
    private Integer quantity;
    @ApiModelProperty(value = "价格")
    private Long price;

    public AdvanceSaleModifyVo() {
    }
}
