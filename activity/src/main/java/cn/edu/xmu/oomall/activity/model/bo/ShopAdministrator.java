package cn.edu.xmu.oomall.activity.model.bo;

import lombok.Data;

@Data
public class ShopAdministrator {
    Long id;
    String name;

    public ShopAdministrator(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
