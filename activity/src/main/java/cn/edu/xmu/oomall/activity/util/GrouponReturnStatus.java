package cn.edu.xmu.oomall.activity.util;

/**
* GrouponService返回给Controller的团购活动状态变更情况
* */
public enum GrouponReturnStatus {
    //已改变
    CHANGED,
    //已经是目标状态
    ALREADY,
    //活动不存在
    ID_NOT_EXIT,
    //活动和商铺不匹配
    ID_SHOPID_NOT_MATCH,
    //当前状态禁止操作
    CHANGE_FORBIDDEN,
    //和上线的onSale活动时间冲突
    SALE_TIME_CONFLICT,
    //出现异常
    INTERNAL_FAULT
}
