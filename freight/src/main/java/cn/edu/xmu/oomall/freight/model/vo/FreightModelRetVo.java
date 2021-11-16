package cn.edu.xmu.oomall.freight.model.vo;

import cn.edu.xmu.oomall.core.model.VoObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author RenJieZheng 22920192204334
 * @date 2021/11/17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FreightModelRetVo implements VoObject, Serializable {
    private Long id;
    private String name;
    private Byte defaultModel;
    private Byte type;
    private Integer unit;
    private SimpleUserRetVo createBy;
    private SimpleUserRetVo modifiedBy;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

    @Override
    public Object createVo() {
        return this;
    }

    @Override
    public Object createSimpleVo() {
        return this;
    }
}
