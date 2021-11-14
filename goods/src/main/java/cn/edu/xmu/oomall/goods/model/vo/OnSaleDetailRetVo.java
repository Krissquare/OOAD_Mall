package cn.edu.xmu.oomall.goods.model.vo;

import cn.edu.xmu.oomall.goods.model.bo.OnSale;
import cn.edu.xmu.oomall.goods.model.bo.ProductBaseInfo;
import lombok.Data;

import static cn.edu.xmu.oomall.core.util.Common.cloneVo;

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

    public OnSaleDetailRetVo(String shopName, ProductBaseInfo pInfo, String shareActName, OnSale onsale){
        this.id=onsale.getId();

        this.shop=new Shop();
        this.shop.setId(onsale.getShopId());
        this.shop.setName(shopName);

        this.product=new Product();
        this.product.setId(pInfo.getId());
        this.product.setName(pInfo.getName());
        this.product.setImageUrl(pInfo.getImageUrl());

        this.price= Math.toIntExact(onsale.getPrice());
        this.beginTime=String.valueOf(onsale.getBeginTime());
        this.endTime=String.valueOf(onsale.getEndTime());
        this.quantity=onsale.getQuantity();
        this.type=onsale.getType().getCode();

        this.shareAct=new ShareAct();
        this.shareAct.setId(onsale.getShareActId());
        this.shareAct.setName(shareActName);

        this.createdBy=new CreatedBy();
        this.createdBy.setId(onsale.getCreatedBy());
        this.createdBy.setUsername(onsale.getCreateName());
        this.gmtCreate=String.valueOf(onsale.getGmtCreate());
        this.gmtModified=String.valueOf(onsale.getGmtModified());
        this.modifiedBy=new ModifiedBy();
        this.modifiedBy.setId(onsale.getModifiedBy());
        this.modifiedBy.setUsername(onsale.getModiName());

    }

}
