package cn.edu.xmu.oomall.activity.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "修改活动信息")
public class GrouponUpdateSimpleVo {
    String strategy;
    String beginTime;
    String endTime;

    public GrouponUpdateSimpleVo(String strategy, String beginTime, String endTime) {
        this.strategy = strategy;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }
}
