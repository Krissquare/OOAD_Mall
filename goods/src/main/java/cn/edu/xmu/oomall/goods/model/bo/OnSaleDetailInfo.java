package cn.edu.xmu.oomall.goods.model.bo;

import cn.edu.xmu.oomall.core.model.VoObject;
import cn.edu.xmu.oomall.goods.model.vo.OnSaleDetailRetVo;
import lombok.Data;

/**
 * @author yujie lin
 * @date 2021/11/11
 */
@Data
public class OnSaleDetailInfo implements VoObject {



    private Long id;
    private Long shopId;
    private String shopName;

    private Long productId;
    private String productName;
    private String productImageUrl;

    private Integer price;
    private String beginTime;
    private String endTime;
    private Integer quantity;
    private Integer type;

    private Long shareActId;
    private String shareActName;

    private Long createdById;
    private String createdByUserName;

    private String gmtCreate;
    private String gmtModified;

    private Long modifiedById;
    private String modifiedByUserName;



    public OnSaleDetailInfo(String shopName, ProductBaseInfo pInfo, String shareActName, OnSale onsale){
        this.id=onsale.getId();
        this.shopId=onsale.getShopId();
        this.shopName=shopName;
        this.productId=onsale.getProductId();
        this.productName=pInfo.getProductName();
        this.productImageUrl=pInfo.getImageUrl();
        this.price= Math.toIntExact(onsale.getPrice());
        this.beginTime=String.valueOf(onsale.getBeginTime());
        this.endTime=String.valueOf(onsale.getEndTime());
        this.quantity=onsale.getQuantity();
        this.type=onsale.getType().getCode();
        this.shareActId=onsale.getShareActId();
        this.shareActName=shareActName;
        this.createdById=onsale.getCreatedBy();
        this.createdByUserName=onsale.getCreateName();
        this.gmtCreate=String.valueOf(onsale.getGmtCreate());
        this.gmtModified=String.valueOf(onsale.getGmtModified());
        this.modifiedById=onsale.getModifiedBy();
        this.modifiedByUserName=onsale.getModiName();

    }

    @Override
    public Object createVo() {

        return new OnSaleDetailRetVo(this);
    }

    @Override
    public Object createSimpleVo() {
        return null;
    }
}
