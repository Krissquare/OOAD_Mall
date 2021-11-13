package cn.edu.xmu.oomall.activity.microservice.vo;

import cn.edu.xmu.oomall.activity.model.vo.SimpleAdminUserVo;
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
public class OnSaleVo {
    Long id;
    SimpleShopVo shop;
    Object product;
    Long price;
    String beginTime;
    String endTime;
    Long quantity;
    Integer type; // 0为无活动，1为秒杀，2为团购，3为预售
    Long activityId;
    Object shareAct;
    SimpleAdminUserVo createdBy;
    String gmtCreate;
    String gmtModified;
    SimpleAdminUserVo modifiedBy;
}
