package cn.edu.xmu.oomall.activity.model.bo;

import cn.edu.xmu.oomall.activity.constant.GroupOnState;
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

    public static GroupOnActivity fromPo(GroupOnActivityPo po) {
        var ret = (GroupOnActivity) Common.cloneVo(po, GroupOnActivity.class);
        ret.setStrategy(JacksonUtil.parseObjectList(po.getStrategy(), GroupOnStrategyVo.class));
        return ret;
    }

    public GroupOnActivityPo createPo() {
        var ret = (GroupOnActivityPo) Common.cloneVo(this, GroupOnActivityPo.class);
        ret.setStrategy(JacksonUtil.toJson(strategy));
        return ret;
    }

    public FullGroupOnActivityVo createFullVo() {
        var ret = (FullGroupOnActivityVo) Common.cloneVo(this, FullGroupOnActivityVo.class);
        return ret;

    }

    @Override
    public GroupOnActivityVo createVo() {
        var ret = (GroupOnActivityVo) Common.cloneVo(this, GroupOnActivityVo.class);
        System.err.println(this);
        System.err.println(ret);
        return ret;
    }

    @Override
    public SimpleGroupOnActivityVo createSimpleVo() {
        return (SimpleGroupOnActivityVo) Common.cloneVo(this, SimpleGroupOnActivityVo.class);
    }
}
