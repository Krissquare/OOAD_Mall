package cn.edu.xmu.oomall.orders.model.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 *
 * @author RenJieZheng 22920192204334
 * @date 2021/11/16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    private Long id;
    private Long orderId;
    private Long subOrderId;
    private Long productId;
    private Long onsaleId;
    private Integer column12;
    private Long quantity;
    private Long createBy;
    private String createName;
    private Long modifiedBy;
    private String modiName;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private Long price;
    private Long discountPrice;
    private Long point;
    private String name;
    private Long couponActivityId;
    private Long couponId;
}
