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

    private final Integer code;
    private final String name;

    public static GroupOnState valueOf(Byte code) {
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
