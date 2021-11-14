package cn.edu.xmu.oomall.activity.model.vo;

import cn.edu.xmu.oomall.activity.model.po.ShareActivityPo;
import cn.edu.xmu.oomall.core.model.VoObject;
import cn.edu.xmu.oomall.core.util.JacksonUtil;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiuchen lang 22920192204222
 * @date 2021/11/12 20:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "特定分享活动详情")
public class RetShareActivitySpecificInfoVO implements VoObject, Serializable {
    Long id;
    ShopVO shop;
    String name;
    LocalDateTime beginTime;
    LocalDateTime endTime;
    List<StrategyVO> strategy;
    Byte state;
    SimpleUserRetVo createdBy;
    LocalDateTime gmtCreate;
    LocalDateTime gmtModified;
    SimpleUserRetVo modifiedBy;

    /**
     *   TODO：这个没有用clonevo的原因是用clonevo的效果太差大部分还是要手动getset
     */
    public RetShareActivitySpecificInfoVO(ShareActivityPo shareActivityPo) {
        strategy = new ArrayList<>();
        shop=new ShopVO();
        createdBy = new SimpleUserRetVo();
        modifiedBy = new SimpleUserRetVo();
        String strategy = shareActivityPo.getStrategy();
        if (strategy != null) {
            List<StrategyVO> strategyVos = (List<StrategyVO>) JacksonUtil.toObj(strategy, new ArrayList<StrategyVO>().getClass());
            this.strategy = strategyVos;
        }
        createdBy.setId(shareActivityPo.getCreatedBy());
        createdBy.setUserName(shareActivityPo.getCreateName());
        modifiedBy.setId(shareActivityPo.getModifiedBy());
        modifiedBy.setUserName(shareActivityPo.getModiName());
        this.id=shareActivityPo.getId();
        this.shop.setId(shareActivityPo.getShopId());
        this.shop.setName(shareActivityPo.getShopName());
        this.name= shareActivityPo.getName();
        this.beginTime= shareActivityPo.getBeginTime();
        this.endTime=shareActivityPo.getEndTime();
        this.state=shareActivityPo.getState();
        this.gmtCreate=shareActivityPo.getGmtCreate();
        this.gmtModified=shareActivityPo.getGmtModified();;
    }

    @Override
    public Object createVo() {
        return this;
    }

    @Override
    public Object createSimpleVo() {
        return this;
    }
}
