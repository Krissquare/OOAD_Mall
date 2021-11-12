package cn.edu.xmu.oomall.goods.model.vo;

import cn.edu.xmu.oomall.goods.model.bo.Region;
import cn.edu.xmu.oomall.goods.model.bo.SimpleAdminUser;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ziyi guo
 * @date 2021/11/10
 */
@Data
public class RegionRetVo {

    private Long id;

    private Long pid;

    private String name;

    /**
     * 0有效/1停用/2废除
     */
    private Byte state;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModified;

    private SimpleAdminUser createdBy;

    private SimpleAdminUser modifiedBy;


    public RegionRetVo(Region region) {
        this.id=region.getId();
        this.pid= region.getPid();
        this.name= region.getName();
        this.state= region.getState();
        this.gmtCreate=region.getGmtCreate();
        this.gmtModified=region.getGmtModified();
        this.createdBy=new SimpleAdminUser(region.getCreatedBy(),region.getCreateName());
        this.modifiedBy=new SimpleAdminUser(region.getModifiedBy(),region.getModiName());
    }
}
