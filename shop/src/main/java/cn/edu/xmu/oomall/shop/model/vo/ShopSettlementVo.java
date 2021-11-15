package cn.edu.xmu.oomall.shop.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "商铺清算结果视图")
public class ShopSettlementVo {
    private boolean isSettled;
}
