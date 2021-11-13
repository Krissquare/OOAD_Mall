package cn.edu.xmu.oomall.activity.model.bo;

import cn.edu.xmu.oomall.activity.enums.GroupOnState;
import cn.edu.xmu.oomall.activity.model.po.GroupOnActivityPo;
import cn.edu.xmu.oomall.activity.model.vo.*;
import cn.edu.xmu.oomall.core.model.VoObject;
import cn.edu.xmu.oomall.core.util.Common;
import cn.edu.xmu.oomall.core.util.JacksonUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Gao Yanfeng
 * @date 2021/11/11
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupOnActivity implements VoObject {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

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
    private GroupOnState state;

    public static GroupOnActivity fromPo(GroupOnActivityPo po) {
        var ret = (GroupOnActivity) Common.cloneVo(po, GroupOnActivity.class);
        ret.setStrategy(JacksonUtil.parseObjectList(po.getStrategy(), GroupOnStrategyVo.class));
        ret.setState(GroupOnState.fromCode(po.getState().intValue()));
        return ret;
    }

    public GroupOnActivityPo createPo() {
        var ret = (GroupOnActivityPo) Common.cloneVo(this, GroupOnActivityPo.class);
        ret.setStrategy(JacksonUtil.toJson(strategy));
        ret.setState(state.getCode().byteValue());
        return ret;
    }

    public FullGroupOnActivityVo createFullVo() {
        var ret = (FullGroupOnActivityVo) Common.cloneVo(this, FullGroupOnActivityVo.class);
        ret.setBeginTime(FORMATTER.format(beginTime));
        ret.setEndTime(FORMATTER.format(endTime));
        ret.setGmtCreate(FORMATTER.format(gmtCreate));
        ret.setGmtModified(gmtModified == null ? null : FORMATTER.format(gmtModified));
        ret.setState(state.getCode());
        return ret;

    }

    @Override
    public GroupOnActivityVo createVo() {
        var ret = (GroupOnActivityVo) Common.cloneVo(this, GroupOnActivityVo.class);
        ret.setBeginTime(FORMATTER.format(beginTime));
        ret.setEndTime(FORMATTER.format(endTime));
        return ret;
    }

    @Override
    public SimpleGroupOnActivityVo createSimpleVo() {
        return (SimpleGroupOnActivityVo) Common.cloneVo(this, SimpleGroupOnActivityVo.class);
    }
}
