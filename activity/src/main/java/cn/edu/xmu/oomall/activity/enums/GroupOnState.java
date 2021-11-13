package cn.edu.xmu.oomall.activity.enums;

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

    public static GroupOnState fromCode(Integer code) {
        switch (code) {
            case 0:
                return DRAFT;
            case 1:
                return ONLINE;
            case 2:
                return OFFLINE;
            default:
                return null;
        }
    }
}
