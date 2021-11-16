package cn.edu.xmu.oomall.shop.model.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 *
 * @author BingShuai Liu
 * @studentId 22920192204245
 * @date 2021/11/13/18:13
 */
@Data
@ApiModel(value = "分享活动策略视图")
@AllArgsConstructor
public class ShareActivityStrategyVo {
    private Long quantity;
    private Long percentage;
}
