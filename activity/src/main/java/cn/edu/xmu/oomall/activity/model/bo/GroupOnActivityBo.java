package cn.edu.xmu.oomall.activity.model.bo;

import cn.edu.xmu.oomall.activity.enums.GroupOnState;
import cn.edu.xmu.oomall.activity.model.po.GroupOnActivityPo;
import cn.edu.xmu.oomall.activity.model.vo.*;
import cn.edu.xmu.oomall.core.model.VoObject;
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
public class GroupOnActivityBo implements VoObject {
    private Long id;
    private String name;
    private Long shopId;
    private List<GroupOnStrategyVo> strategy;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private SimpleAdminUserVo createdBy;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private SimpleAdminUserVo modifiedBy;
    private GroupOnState state;

    public GroupOnActivityBo(GroupOnActivityPo po){
        id = po.getId();
        name = po.getName();
        shopId = po.getShopId();
        strategy = JacksonUtil.parseObjectList(po.getStrategy(), GroupOnStrategyVo.class);
        beginTime = po.getBeginTime();
        endTime = po.getEndTime();
        createdBy = new SimpleAdminUserVo(po.getCreatedBy(), po.getCreateName());
        gmtCreate = po.getGmtCreate();
        gmtModified = po.getGmtModified();
        state = GroupOnState.fromCode(po.getState().intValue());
    }

    public GroupOnActivityPo createPo() {
        var po = new GroupOnActivityPo();
        po.setName(name);
        po.setShopId(shopId);
        po.setStrategy(JacksonUtil.toJson(strategy));
        po.setBeginTime(beginTime);
        po.setEndTime(endTime);
        po.setState(state.getCode().byteValue());
        return po;
    }

    public FullGroupOnActivityVo createFullVo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String beginStr = formatter.format(beginTime);
        String endStr = formatter.format(endTime);
        String gmtCreateStr = formatter.format(gmtCreate);
        String gmtModifiedStr = null;
        if (gmtModified != null) {
            gmtModifiedStr = formatter.format(gmtModified);
        }
        return new FullGroupOnActivityVo(id, name, shopId, strategy, beginStr, endStr, createdBy, gmtCreateStr, gmtModifiedStr, modifiedBy, state.getCode());
    }

    @Override
    public GroupOnActivityVo createVo() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String begin = formatter.format(beginTime);
        String end = formatter.format(endTime);
        return new GroupOnActivityVo(id, name, shopId, strategy, begin, end);
    }

    @Override
    public SimpleGroupOnActivityVo createSimpleVo() {
        return new SimpleGroupOnActivityVo(id, name);
    }
}
