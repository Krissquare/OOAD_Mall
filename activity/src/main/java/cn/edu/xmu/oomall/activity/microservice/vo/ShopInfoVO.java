package cn.edu.xmu.oomall.activity.microservice.vo;

import cn.edu.xmu.oomall.core.model.VoObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 调用接口需要用的vo
 * @author xiuchen lang 22920192204222
 * @date 2021/11/13 14:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopInfoVO implements VoObject, Serializable {
    private Long id;
    private String name;
    @Override
    public Object createVo() {
        return this;
    }

    @Override
    public Object createSimpleVo() {
        return this;
    }
}
