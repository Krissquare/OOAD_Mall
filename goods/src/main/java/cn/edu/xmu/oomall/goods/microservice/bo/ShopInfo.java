package cn.edu.xmu.oomall.goods.microservice.bo;

import lombok.Data;

/**
 * @author YuJie 22920192204242
 * @date 2021/11/14
 */
@Data
public class ShopInfo {
    Long id;
    String name;


    public ShopInfo(Long id, String name) {
        this.id=id;
        this.name=name;
    }
}
