package cn.edu.xmu.oomall.shop.model.vo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class ShopAllRetVo {
    private Long page;
    private Long pageSize;
    private Long total;
    private Long pages;
    private List<Object> list=new ArrayList<>();
}
