package cn.edu.xmu.oomall.activity.model.vo;

import cn.edu.xmu.oomall.core.model.VoObject;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author xiuchen lang 22920192204222
 * @date 2021/11/12 15:04
 */
@Data
@AllArgsConstructor
@ApiModel(value = "特定分享活动详情")
public class RetStatesVo implements VoObject {
    private Byte code;
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
