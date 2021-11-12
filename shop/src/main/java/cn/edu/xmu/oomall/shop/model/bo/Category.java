package cn.edu.xmu.oomall.shop.model.bo;

import cn.edu.xmu.oomall.core.model.VoObject;
import cn.edu.xmu.oomall.shop.model.po.CategoryPo;
import cn.edu.xmu.oomall.shop.model.vo.CategoryRetVo;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class Category implements VoObject, Serializable {
    private Long id;
    private String name;
    private Integer commissionRatio;
    private Long pid;   //pid为0表示一级类，大于0表示二级类，为-1表示单独类
    private Long createdBy;
    private String createName;
    private LocalDateTime gmtCreate;
    private Long modifiedBy;
    private LocalDateTime gmtModified;
    private String modiName;

    public Category(){
    }
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


    public CategoryPo getCategoryPo() {
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

