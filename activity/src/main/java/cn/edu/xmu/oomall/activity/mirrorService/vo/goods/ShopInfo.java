package cn.edu.xmu.oomall.activity.mirrorService.vo.goods;

import cn.edu.xmu.oomall.core.model.VoObject;
import lombok.Data;

import java.io.Serializable;

/**
 * 调用接口需要用的vo
 * @author xiuchen lang 22920192204222
 * @date 2021/11/13 14:24
 */
@Data
public class ShopInfo implements VoObject, Serializable {
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
