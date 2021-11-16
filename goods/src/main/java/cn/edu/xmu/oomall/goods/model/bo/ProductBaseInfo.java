package cn.edu.xmu.oomall.goods.model.bo;

import lombok.Data;

/**
 * @author yujie lin
 * @date 2021/11/11
 */
@Data
public class ProductBaseInfo {
    private Long id;
    private String name;
    private String imageUrl;

    public ProductBaseInfo(){}

    public ProductBaseInfo(ProductBaseInfo p){
        id=p.getId();
        name=p.getName();
        imageUrl=p.getImageUrl();
    }

}
