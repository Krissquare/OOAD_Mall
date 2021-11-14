package cn.edu.xmu.oomall.shop.model.vo;

import cn.edu.xmu.oomall.shop.model.bo.Category;
import io.swagger.annotations.ApiModelProperty;
import cn.edu.xmu.oomall.shop.model.bo.Shop;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ShopRetVo {
    private Long id;
    private String name;
    private Long deposit;
    private Long depositThreshold;
    private Byte state;
    private SimpleUserRetVo createdBy;
    private SimpleUserRetVo modifiedBy;
    private String gmtCreated;
    private String gmtModified;
//    public ShopRetVo(Shop shop) {
//        this.id = shop.getId();
//        this.name = shop.getName();
//        this.createdBy=;
//
//        this.modifiedBy=shop.getModifiedBy();
//
//        if(shop.getGmtCreated()!=null) {
//            this.gmtCreated= shop.getGmtCreated().toString();
//        }
//        if(shop.getGmtModified()!=null) {
//            this.gmtModified=shop.getGmtModified().toString();
//        }
//    }
}
