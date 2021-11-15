package cn.edu.xmu.oomall.activity.model.vo;

import cn.edu.xmu.oomall.core.model.VoObject;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author xiuchen lang 22920192204222
 * @date 2021/11/12 20:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "特定分享活动详情")
public class RetShareActivitySpecificInfoVo implements VoObject, Serializable {
    Long id;
    ShopVo shop;
    String name;
    LocalDateTime beginTime;
    LocalDateTime endTime;
    Byte state;
    SimpleUserRetVo createdBy;
    LocalDateTime gmtCreate;
    LocalDateTime gmtModified;
    SimpleUserRetVo modifiedBy;
    List<StrategyVo> strategy;

    @Override
    public Object createVo() {
        return this;
    }

    @Override
    public Object createSimpleVo() {
        return this;
    }
}
