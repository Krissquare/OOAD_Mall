package cn.edu.xmu.oomall.activity.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Gao Yanfeng
 * @date 2021/11/12
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FullGroupOnActivityVo {
    private Long id;
    private String name;
    private Long shopId;
    private List<GroupOnStrategyVo> strategy;
    private String beginTime;
    private String endTime;
    private SimpleAdminUserVo createdBy;
    private String gmtCreate;
    private String gmtModified;
    private SimpleAdminUserVo modifiedBy;
    private Integer state;
}
