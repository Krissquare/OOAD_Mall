package cn.edu.xmu.oomall.goods.model.vo;

import cn.edu.xmu.oomall.goods.model.bo.Region;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ziyi guo
 * @date 2021/11/10
 */
@Data
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
    private SimpleUserRetVo createdBy;

    @ApiModelProperty(value = "修改者id")
    private SimpleUserRetVo modifiedBy;


    public RegionRetVo(Region region) {
        this.id=region.getId();
        this.pid= region.getPid();
        this.name= region.getName();
        this.state= region.getState();
        this.gmtCreate=region.getGmtCreate();
        this.gmtModified=region.getGmtModified();
        this.createdBy=new SimpleUserRetVo();
        this.createdBy.setId(region.getCreatedBy());
        this.createdBy.setUserName(region.getCreateName());
        this.modifiedBy=new SimpleUserRetVo();
        this.modifiedBy.setId(region.getModifiedBy());
        this.modifiedBy.setUserName(region.getModiName());
    }
}
