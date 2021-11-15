package cn.edu.xmu.oomall.coupon.model.vo;

import cn.edu.xmu.oomall.core.model.VoObject;
import cn.edu.xmu.oomall.coupon.model.po.CouponActivityPo;
import lombok.*;

import java.time.LocalDateTime;

/**
 * @author RenJieZheng 22920192204334
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CouponActivityVoInfo implements VoObject {
    private Long id;
    private String name;
    private SimpleShopRetVo shop;
    private LocalDateTime couponTime;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private Integer quantity;
    private Byte quantityType;
    private Byte validTerm;
    private String imageUrl;
    private String strategy;
    private Byte state;
    private SimpleUserRetVo createBy;
    private SimpleUserRetVo modifiedBy;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

    public CouponActivityVoInfo(CouponActivityPo couponActivityPo) {
        this.id = couponActivityPo.getId();
        this.name = couponActivityPo.getName();
        this.beginTime = couponActivityPo.getBeginTime();
        this.endTime = couponActivityPo.getEndTime();
        this.couponTime = couponActivityPo.getCouponTime();
        this.state = couponActivityPo.getState();
        this.shop = new SimpleShopRetVo(couponActivityPo.getShopId(),couponActivityPo.getShopName());
        this.quantity = couponActivityPo.getQuantity();
        this.validTerm = couponActivityPo.getValidTerm();
        this.imageUrl = couponActivityPo.getImageUrl();
        this.strategy = couponActivityPo.getStrategy();
        this.gmtCreate = couponActivityPo.getGmtCreate();
        this.gmtModified = couponActivityPo.getGmtModified();
        this.quantityType = couponActivityPo.getQuantityType();
        this.createBy = new SimpleUserRetVo(couponActivityPo.getCreatedBy(),couponActivityPo.getCreateName());
        this.modifiedBy = new SimpleUserRetVo(couponActivityPo.getModifiedBy(),couponActivityPo.getModiName());
    }

    @Override
    public Object createVo() {
        return this;
    }

    @Override
    public Object createSimpleVo() {
        return this;
    }
}
