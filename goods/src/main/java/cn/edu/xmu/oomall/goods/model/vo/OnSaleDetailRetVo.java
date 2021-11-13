package cn.edu.xmu.oomall.goods.model.vo;

import cn.edu.xmu.oomall.goods.model.bo.OnSaleDetailInfo;
import lombok.Data;

/**
 * @author yujie lin
 * @date 2021/11/11
 */
@Data
public class OnSaleDetailRetVo {

    @Data
    class Shop{
        private Long id;
        private String name;

    }
    @Data
    class Product{
        private Long id;
        private String name;
        private String imageUrl;
    }
    @Data
    class ShareAct{
        private Long id;
        private String name;
    }
    @Data
    class CreatedBy{
        private Long id;
        private String username;
    }
    @Data
    class ModifiedBy{
        private Long id;
        private String username;
    }

    private Long id;
    private Shop shop;
    private Product product;
    private Integer price;
    private String beginTime;
    private String endTime;
    private Integer quantity;
    private Integer type;
    private ShareAct shareAct;
    private CreatedBy createdBy;
    private String gmtCreate;
    private String gmtModified;
    private ModifiedBy modifiedBy;

    OnSaleDetailRetVo(Long id, Long shopId, String shopName, Long productId, String productName, String imageUrl,
                      Integer price, String beginTime, String endTime, Integer quantity, Integer type,
                      Long shareActId, String shareActName, Long createdById, String createdByName, String gmtCreate,
                      String gmtModified, Long modifiedById, String modifiedName){
        this.id=id;
        this.shop.id=shopId;
        this.shop.name=shopName;
        this.product.id=productId;
        this.product.name=productName;
        this.product.imageUrl=imageUrl;
        this.price=price;
        this.beginTime=beginTime;
        this.endTime=endTime;
        this.quantity=quantity;
        this.type=type;
        this.shareAct.id=shareActId;
        this.shareAct.name=shareActName;
        this.createdBy.id=createdById;
        this.createdBy.username=createdByName;
        this.gmtCreate=gmtCreate;
        this.gmtModified=gmtModified;
        this.modifiedBy.id=modifiedById;
        this.modifiedBy.username=modifiedName;
    }

    public OnSaleDetailRetVo(OnSaleDetailInfo info) {
        this.id=info.getId();;
        this.shop=new Shop();
        this.shop.id=info.getShopId();
        this.shop.name=info.getShopName();
        this.product=new Product();
        this.shareAct=new ShareAct();
        this.createdBy=new CreatedBy();
        this.modifiedBy=new ModifiedBy();
        this.product.id=info.getProductId();
        this.product.name=info.getProductName();
        this.product.imageUrl=info.getProductImageUrl();
        this.price=info.getPrice();
        this.beginTime=info.getBeginTime();
        this.endTime=info.getEndTime();
        this.quantity=info.getQuantity();
        this.type=info.getType();
        this.shareAct.id=info.getShareActId();
        this.shareAct.name=info.getShareActName();
        this.createdBy.id=info.getCreatedById();
        this.createdBy.username=info.getCreatedByUserName();
        this.gmtCreate=info.getGmtCreate();
        this.gmtModified=info.getGmtModified();
        this.modifiedBy.id=info.getModifiedById();
        this.modifiedBy.username=info.getModifiedByUserName();
    }
}
