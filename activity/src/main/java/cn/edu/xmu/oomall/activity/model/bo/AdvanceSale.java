package cn.edu.xmu.oomall.activity.model.bo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
@Data
public class AdvanceSale implements Serializable {


    private Long shopId;
    private String shopName;
    private String name;
    private LocalDateTime payTime;
    private Long advancePayPrice;
    private Long createdBy;
    private String createName;
    private Long modifiedBy;
    private String modiName;
    private Byte state;
}
