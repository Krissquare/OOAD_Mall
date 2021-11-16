package cn.edu.xmu.oomall.freight.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 *
 * @author RenJieZheng 22920192204334
 * @date 2021/11/17
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FreightModel {
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
