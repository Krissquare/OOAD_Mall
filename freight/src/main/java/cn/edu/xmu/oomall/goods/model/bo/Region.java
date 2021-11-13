package cn.edu.xmu.oomall.goods.model.bo;

import cn.edu.xmu.oomall.core.model.VoObject;
import cn.edu.xmu.oomall.goods.model.po.RegionPo;
import cn.edu.xmu.oomall.goods.model.vo.RegionRetVo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ziyi guo
 * @date 2021/11/10
 */
@Data
@NoArgsConstructor
public class Region implements VoObject, Serializable {

    private Long id;
    private Long pid;
    private String name;
    private Byte state;
    private Long createdBy;
    private String createName;
    private Long modifiedBy;
    private String modiName;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

    @Override
    public Object createVo(){
        return new RegionRetVo(this);
    }

    @Override
    public Object createSimpleVo(){
        return new RegionRetVo(this);
    }

}
