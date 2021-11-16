package cn.edu.xmu.oomall.activity.model.vo;

import cn.edu.xmu.oomall.activity.model.po.ShareActivityPo;
import cn.edu.xmu.oomall.core.model.VoObject;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;

/**
 * @author xiuchen lang 22920192204222
 * @date 2021/11/12 15:53
 */
@Data
@ApiModel(value = "分享活动列表")
public class RetShareActivityListVo implements VoObject, Serializable {
    Long id;
    String name;

    @Override
    public Object createVo() {
        return this;
    }

    @Override
    public Object createSimpleVo() {
        return this;
    }
}
