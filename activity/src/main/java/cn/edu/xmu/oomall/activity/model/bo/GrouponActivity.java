package cn.edu.xmu.oomall.activity.model.bo;

import cn.edu.xmu.oomall.activity.model.po.GroupOnActivityPo;
import cn.edu.xmu.oomall.activity.model.vo.GrouponUpdateSimpleVo;
import cn.edu.xmu.oomall.core.model.VoObject;
import cn.edu.xmu.oomall.core.util.Common;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GrouponActivity implements VoObject {

    Long id;
    String name;
    Long shopId;
    String shopName;
    String strategy;
    LocalDateTime beginTime;
    LocalDateTime endTime;
    Byte state;
    Long createdBy;
    String createName;
    Long modifiedBy;
    String modiName;
    LocalDateTime gmtCreate;
    LocalDateTime gmtModified;

    @Override
    public Object createVo() {
        return null;
    }

    @Override
    public Object createSimpleVo() {
        return null;
    }

    public GroupOnActivityPo getGrouponActivityPo(){
        return (GroupOnActivityPo) Common.cloneVo(this,GroupOnActivityPo.class);
    }

    public void generateFromPo(GroupOnActivityPo groupOnActivityPo){

    }

}
