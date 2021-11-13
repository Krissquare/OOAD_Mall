package cn.edu.xmu.oomall.shop.model.vo;

import cn.edu.xmu.oomall.core.model.VoObject;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class ShopAllRetVo {
    private Long page;
    private Long pageSize;
    private Long total;
    private Long pages;
    private List<VoObject> list=new ArrayList<>();
}
