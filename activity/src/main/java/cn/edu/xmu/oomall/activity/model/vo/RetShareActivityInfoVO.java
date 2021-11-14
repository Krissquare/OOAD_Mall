package cn.edu.xmu.oomall.activity.model.vo;

import cn.edu.xmu.oomall.activity.model.po.ShareActivityPo;
import cn.edu.xmu.oomall.core.model.VoObject;
import cn.edu.xmu.oomall.core.util.JacksonUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiuchen lang 22920192204222
 * @date 2021/11/12 18:28
 */
@Data
@ToString
@NoArgsConstructor
@ApiModel(value = "分享活动详情")
public class RetShareActivityInfoVO implements VoObject, Serializable {
    private Long id;
    private ShopVO shop;
    private String name;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime beginTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime endTime;
    private List<StrategyVO> strategy;

    public RetShareActivityInfoVO(ShareActivityPo shareActivityPo){
        this.shop=new ShopVO();
        this.strategy= new ArrayList<>();
        this.id=shareActivityPo.getId();
        this.shop.setId(shareActivityPo.getShopId());
        this.shop.setName(shareActivityPo.getShopName());
        this.name= shareActivityPo.getName();
        this.beginTime= shareActivityPo.getBeginTime();
        this.endTime=shareActivityPo.getEndTime();
        String strategy = shareActivityPo.getStrategy();
        if (strategy!=null){
            List<StrategyVO> strategyVos = (List<StrategyVO>) JacksonUtil.toObj(strategy,new ArrayList<StrategyVO>().getClass());
            this.strategy=strategyVos;
        }
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
