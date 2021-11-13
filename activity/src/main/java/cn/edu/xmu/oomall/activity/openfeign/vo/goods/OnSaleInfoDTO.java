package cn.edu.xmu.oomall.activity.openfeign.vo.goods;

import cn.edu.xmu.oomall.core.model.VoObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 查询特定价格浮动的详情DTO
 *
 * @author: xiuchen lang 22920192204222
 * @Date: 2021/11/13 14:35
 */
@Data
public class OnSaleInfoDTO implements VoObject, Serializable {
    private Long id;
    private ShopInfo shop;
    private ProductInfo productInfo;
    private Integer price;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime beginTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime endTime;
    private Integer quantity;
    private Byte type;
    private Integer activityId;
    private ShareAct shareAct;
    private UserSimpInfo createdBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime gmtModified;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime gmtCreate;
    private UserSimpInfo modifiedBy;

    public OnSaleInfoDTO() {
        shop = new ShopInfo();
        productInfo = new ProductInfo();
        shareAct = new ShareAct();
        createdBy = new UserSimpInfo();
        modifiedBy = new UserSimpInfo();
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
