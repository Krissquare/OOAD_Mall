package cn.edu.xmu.oomall.freight.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author ziyi guo
 * @date 2021/11/10
 */
@Data
@NoArgsConstructor
public class RegionRetVo {

    @ApiModelProperty(value = "地区id")
    private Long id;

    @ApiModelProperty(value = "地区父id")
    private Long pid;

    @ApiModelProperty(value = "地区名")
    private String name;

    /**
     * 0有效/1停用/2废除
     */
    @ApiModelProperty(value = "地区状态")
    private Byte state;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime gmtCreate;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime gmtModified;

    @ApiModelProperty(value = "创建者id")
    private SimpleUserRetVo creator;

    @ApiModelProperty(value = "修改者id")
    private SimpleUserRetVo modifier;

}
