package cn.edu.xmu.oomall.freight.model.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author ziyi guo
 * @date 2021/11/17
 */
@Data
@NoArgsConstructor
public class FreightModel implements Serializable {
    private Long id;
    private String name;
    private Byte defaultModel;
    private Byte type;
    private Integer unit;
    private Long createdBy;
    private String createName;
    private Long modifiedBy;
    private String modiName;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
}
