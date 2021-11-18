package cn.edu.xmu.oomall.activity.model.bo;

/**
 * @author xiuchen lang 22920192204222
 * @date 2021/11/12 14:54
 */
public enum ShareActivityStatesBo {
    /**
     * DRAFT活动在草稿状态
     * OFFLINE活动在下线状态
     * ONLINE活动在上线状态
     */
    DRAFT((byte)0,"草稿"),
    OFFLINE((byte)1,"下线"),
    ONLINE((byte)2, "上线");

    private Byte code;
    private String value;
    ShareActivityStatesBo(Byte code, String value) {
        this.code=code;
        this.value=value;
    }


    public Byte getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
