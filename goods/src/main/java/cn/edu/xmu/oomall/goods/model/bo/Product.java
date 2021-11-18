package cn.edu.xmu.oomall.goods.model.bo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author YuJie 22920192204242
 * @date 2021/11/14
 */
@Data
public class Product implements Serializable {
    private Long id;


    private Long shopId;

    private String shopName;

    private Long goodsId;


    private Long categoryId;

    private Long freightId;

    private String skuSn;


    private String name;


    private Long originalPrice;


    private Long weight;


    private String imageUrl;

    private String barcode;


    private String unit;

    private String originPlace;

    private Long creatorId;

    private String creatorName;


    private Long modifierId;


    private String modifierName;

    private LocalDateTime gmtCreate;


    private LocalDateTime gmtModified;


    private Byte state;

}
