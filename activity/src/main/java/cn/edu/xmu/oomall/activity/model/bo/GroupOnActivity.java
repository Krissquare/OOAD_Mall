package cn.edu.xmu.oomall.activity.model.bo;

import cn.edu.xmu.oomall.activity.constant.GroupOnState;
import cn.edu.xmu.oomall.activity.model.vo.GroupOnStrategyVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Gao Yanfeng
 * @date 2021/11/11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupOnActivity implements Serializable {

    private Long id;
    private String name;
    private Long shopId;
    private String shopName;
    private List<GroupOnStrategyVo> strategy;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private Long createdBy;
    private String createName;
    private Long modifiedBy;
    private String modiName;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private Byte state;

    public GroupOnState getState() {
        return GroupOnState.valueOf(state);
    }

    public void setState(GroupOnState state) {
        this.state = state.getCode().byteValue();
    }
}
