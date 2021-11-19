package cn.edu.xmu.oomall.activity.microservice.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Gao Yanfeng
 * @date 2021/11/13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleOnSaleVo {
    Long id;
    Long price;
    String beginTime;
    String endTime;
    Long quantity;
}
