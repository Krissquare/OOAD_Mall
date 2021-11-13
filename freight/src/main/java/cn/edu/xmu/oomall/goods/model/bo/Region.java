package cn.edu.xmu.oomall.goods.model.bo;

import cn.edu.xmu.oomall.core.model.VoObject;
import cn.edu.xmu.oomall.goods.model.po.RegionPo;
import cn.edu.xmu.oomall.goods.model.vo.RegionRetVo;

import java.time.LocalDateTime;

/**
 * @author ziyi guo
 * @date 2021/11/10
 */
public class Region implements VoObject {

    private RegionPo regionPo;

    public Region() {
        this.regionPo = new RegionPo();
    }

    public Region(RegionPo regionPo) {
        this.regionPo = regionPo;
    }

    @Override
    public RegionRetVo createVo(){
        return new RegionRetVo(this);
    }

    @Override
    public RegionRetVo createSimpleVo(){
        return new RegionRetVo(this);
    }

    public RegionPo gotRegionPo(){
        return this.regionPo;
    }


    public Long getId() {
        return regionPo.getId();
    }

    public void setId(Long id) {
        regionPo.setId(id);
    }

    public Long getPid() {
        return regionPo.getPid();
    }

    public void setPid(Long pid) {
        regionPo.setPid(pid);
    }

    public String getName() {
        return regionPo.getName();
    }

    public void setName(String name) {
        regionPo.setName(name);
    }

    public Byte getState() {
        return regionPo.getState();
    }

    public void setState(Byte state) {
        regionPo.setState(state);
    }

    public Long getCreatedBy() {
        return regionPo.getCreatedBy();
    }

    public void setCreatedBy(Long creatorId) {
        regionPo.setCreatedBy(creatorId);
    }

    public String getCreateName() {
        return regionPo.getCreateName();
    }

    public void setCreateName(String createName) {regionPo.setCreateName(createName);}

    public LocalDateTime getGmtCreate() {
        return regionPo.getGmtCreate();
    }

    public void setGmtCreate(LocalDateTime gmtCreate) {
        regionPo.setGmtCreate(gmtCreate);
    }

    public Long getModifiedBy() {
        return regionPo.getModifiedBy();
    }

    public void setModifiedBy(Long modifierId) {
        regionPo.setModifiedBy(modifierId);
    }

    public String getModiName() {
        return regionPo.getModiName();
    }

    public void setModiName(String createName) {regionPo.setModiName(createName);}

    public LocalDateTime getGmtModified() {
        return regionPo.getGmtModified();
    }

    public void setGmtModified(LocalDateTime gmtModified) {
        regionPo.setGmtModified(gmtModified);
    }

}
