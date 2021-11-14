package cn.edu.xmu.oomall.shop.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 分享活动Vo
 *
 * @author BingShuai Liu
 * @date 2021/11/11/0:36
 * @description
 */
@Data
@ApiModel(value = "分享活动视图")
public class ShareActivityVo{
    private String name;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private List<ShareActivityStrategyVo> strategy;
}
