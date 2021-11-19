package cn.edu.xmu.oomall.activity.model.bo;

import cn.edu.xmu.oomall.activity.model.vo.StrategyVo;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    private LocalDateTime beginTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    private LocalDateTime endTime;
    private Byte state;
    private Long createdBy;
    private String createName;
    private Long modifiedBy;
    private String modiName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    private LocalDateTime gmtCreate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "GMT+8")
    private LocalDateTime gmtModified;
    private List<StrategyVo> strategy;
    public ShareActivityBo(){
        strategy = new ArrayList<>();
    }
}
