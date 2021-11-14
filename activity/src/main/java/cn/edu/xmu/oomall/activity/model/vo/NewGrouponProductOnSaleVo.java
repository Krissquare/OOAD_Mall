package cn.edu.xmu.oomall.activity.model.vo;

import lombok.Data;

@Data
public class NewGrouponProductOnSaleVo {
    Long id;
    Long price;
    String beginTime;
    String endTime;
    Integer quantity;

    public NewGrouponProductOnSaleVo() {}

    public NewGrouponProductOnSaleVo(Long id, Long price, String beginTime, String endTime, Integer quantity) {
        this.id = id;
        this.price = price;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.quantity = quantity;
    }
}
