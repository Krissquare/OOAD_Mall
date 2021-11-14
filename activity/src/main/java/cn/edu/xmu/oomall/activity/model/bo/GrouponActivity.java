package cn.edu.xmu.oomall.activity.model.bo;

import cn.edu.xmu.oomall.activity.model.po.GroupOnActivityPo;
import cn.edu.xmu.oomall.activity.model.vo.GrouponUpdateSimpleVo;
import cn.edu.xmu.oomall.core.model.VoObject;
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
        GroupOnActivityPo groupOnActivityPo = new GroupOnActivityPo();
        groupOnActivityPo.setId(id);
        groupOnActivityPo.setName(name);
        groupOnActivityPo.setShopId(shopId);
        groupOnActivityPo.setShopName(shopName);
        groupOnActivityPo.setStrategy(strategy);
        groupOnActivityPo.setBeginTime(beginTime);
        groupOnActivityPo.setEndTime(endTime);
        groupOnActivityPo.setState(state);
        groupOnActivityPo.setCreatedBy(createdBy);
        groupOnActivityPo.setCreateName(createName);
        groupOnActivityPo.setModifiedBy(modifiedBy);
        groupOnActivityPo.setModiName(modiName);
        groupOnActivityPo.setGmtCreate(gmtCreate);
        groupOnActivityPo.setGmtModified(gmtModified);
        return groupOnActivityPo;
    }

    public void generateFromPo(GroupOnActivityPo groupOnActivityPo){

    }

}
