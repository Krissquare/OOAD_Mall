package cn.edu.xmu.oomall.shop.model.vo;

import cn.edu.xmu.oomall.shop.model.bo.Category;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 商品分类RetVo
 *
 * @author Zhiliang Li 22920192204235
 * @date 2021/11/15
 */
@Data
@NoArgsConstructor
public class CategoryRetVo {
    @ApiModelProperty(value = "分类id")
    private Long id;
    @ApiModelProperty(value = "佣金率")
    private Integer commissionRatio;
    @ApiModelProperty(value = "分类名")
    private String name;
    @ApiModelProperty(value = "创建人")
    private SimpleUserRetVo createdBy;
    @ApiModelProperty(value = "修改人")
    private SimpleUserRetVo modifiedBy;
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime gmtCreate;
    @ApiModelProperty(value = "修改时间")
    private LocalDateTime gmtModified;
    public CategoryRetVo(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.commissionRatio = category.getCommissionRatio();
        this.createdBy=new SimpleUserRetVo();
        this.createdBy.setName(category.getCreateName());
        this.createdBy.setId(category.getCreatedBy());
        this.modifiedBy=new SimpleUserRetVo();
        this.modifiedBy.setName(category.getModiName());
        this.modifiedBy.setId(category.getModifiedBy());
        this.gmtCreate=category.getGmtCreate();
        this.gmtModified=category.getGmtModified();
    }
}
