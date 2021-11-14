package cn.edu.xmu.oomall.activity.util;

public enum GrouponProductStatus {
    //成功新增
    ADD_SUCCESS,
    //活动状态错误
    ADD_FORBIDDEN_STATE,
    //上线时间冲突
    ADD_TIME_CONFLICT,
    //出现异常
    INTERNAL_FAULT
}
