package cn.edu.xmu.oomall.shop.model.vo;

import cn.edu.xmu.oomall.shop.model.bo.Category;
import lombok.Data;

@Data
public class CategoryRetVo {
    private Long id;
    private Integer commissionRate;
    private String name;
    private SimpleUserRetVo createdBy;
    private SimpleUserRetVo modifiedBy;
    private String gmtCreate;
    private String gmtModified;
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
        if(category.getGmtCreate()!=null)
            this.gmtCreate= category.getGmtCreate().toString();
        if(category.getGmtModified()!=null)
            this.gmtModified=category.getGmtModified().toString();
    }
}
