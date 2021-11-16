package cn.edu.xmu.oomall.activity.model.bo;

import cn.edu.xmu.oomall.activity.model.vo.StrategyVo;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xiuchen lang 22920192204222
 * @date 2021/11/15 19:11
 */
@Data
@AllArgsConstructor
public class ShareActivityBo {
    private Long id;
    private Long shopId;
    private String shopName;
    private String name;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private Byte state;
    private Long createdBy;
    private String createName;
    private Long modifiedBy;
    private String modiName;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
    private List<StrategyVo> strategy;
    public ShareActivityBo(){
        strategy = new ArrayList<>();
    }
}
