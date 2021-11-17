package cn.edu.xmu.oomall.freight.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xiuchen lang 229219224222
 * @date 221/11/16 2138
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeightFreightVO {
    private Integer firstWeight;
    private Integer firstWeightFreight;
    private Integer tenPrice;
    private Integer fiftyPrice;
    private Integer hundredPrice;
    private Integer trihunPrice;
    private Integer abovePrice;
    private Integer regionId;
}
