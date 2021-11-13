package cn.edu.xmu.oomall.core.util.vo;

import cn.edu.xmu.oomall.core.util.bo.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 商品分类RetVo
 *
 * @author Zhiliang Li 22920192204235
 * @date 2021/11/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRetVo {
    private Long id;
    private Integer commissionRate;
    private String name;
    private SimpleUserRetVo createdBy;
    private SimpleUserRetVo modifiedBy;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    public CategoryRetVo(Category category) {
        this.id = category.getId();
        this.name = category.getName();
        this.commissionRate= category.getCommissionRatio();
        this.createdBy=new SimpleUserRetVo();
        this.createdBy.setName(category.getCreateName());
        this.createdBy.setId(category.getCreatedBy());
        this.modifiedBy=new SimpleUserRetVo();
        this.modifiedBy.setName(category.getModiName());
        this.modifiedBy.setId(category.getModifiedBy());
        this.gmtCreate= category.getGmtCreate();
        this.gmtModified=category.getGmtModified();
    }
}
