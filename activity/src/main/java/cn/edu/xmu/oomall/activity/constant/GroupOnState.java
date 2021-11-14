package cn.edu.xmu.oomall.activity.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Gao Yanfeng
 * @date 2021/11/11
 */
@AllArgsConstructor
@Getter
public enum GroupOnState {
    DRAFT(0, "草稿"),
    ONLINE(1, "上线"),
    OFFLINE(2, "下线"),
    ;

    private Integer code;
    private String name;

}
