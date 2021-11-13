package cn.edu.xmu.oomall.core.util.bo;

import cn.edu.xmu.oomall.core.model.VoObject;
import cn.edu.xmu.oomall.core.util.po.CategoryPo;
import cn.edu.xmu.oomall.core.util.vo.CategoryRetVo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 商品分类Bo
 * pid为0表示一级类，大于0表示二级类，为-1表示单独类
 * @author Zhiliang Li 22920192204235
 * @date 2021/11/12
 */
@Data
@NoArgsConstructor
public class Category implements VoObject, Serializable {
    private Long id;
    private String name;
    private Integer commissionRatio;
    private Long pid;
    private Long createdBy;
    private String createName;
    private LocalDateTime gmtCreate;
    private Long modifiedBy;
    private LocalDateTime gmtModified;
    private String modiName;

    public Category(CategoryPo po){
        this.id = po.getId();
        this.pid = po.getPid();
        this.name = po.getName();
        this.commissionRatio=po.getCommissionRatio();
        this.createdBy=po.getCreatedBy();
        this.createName=po.getCreateName();
        this.gmtCreate=po.getGmtCreate();
        this.modifiedBy=po.getModifiedBy();
        this.gmtModified=po.getGmtModified();
        this.modiName=po.getModiName();
    }
    @Override
    public Object createVo() {
        return new CategoryRetVo(this);
    }
    @Override
    public Object createSimpleVo() {
        return new CategoryRetVo(this);
    }

    public CategoryPo createCategoryPo() {
        CategoryPo po = new CategoryPo();
        po.setId(this.id);
        po.setName(this.name);
        po.setPid(this.pid);
        po.setModiName(this.modiName);
        po.setModifiedBy(this.modifiedBy);
        po.setGmtModified(this.gmtModified);
        po.setCommissionRatio(this.commissionRatio);
        po.setGmtCreate(this.gmtCreate);
        po.setCreateName(this.createName);
        po.setCreatedBy(this.createdBy);
        return po;
    }
}

