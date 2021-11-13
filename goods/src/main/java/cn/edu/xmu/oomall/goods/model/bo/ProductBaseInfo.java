package cn.edu.xmu.oomall.goods.model.bo;

import cn.edu.xmu.oomall.goods.model.po.ProductPo;
import lombok.Data;

/**
 * @author yujie lin
 * @date 2021/11/11
 */
@Data
public class ProductBaseInfo {
    private Long id;
    private String productName;
    private String imageUrl;

    public ProductBaseInfo(ProductPo productPo){
        this.id=productPo.getId();
        this.productName=productPo.getName();
        this.imageUrl=productPo.getImageUrl();
    }
}
