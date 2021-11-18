package cn.edu.xmu.oomall.core.util;

/**
 * 返回的错误码
 * @author Ming Qiu
 */
public enum ReturnNo {
    //不加说明的状态码为200或201（POST）
    OK(0,"成功"),
    /***************************************************
     *    系统级错误
     **************************************************/
    //状态码 500
    INTERNAL_SERVER_ERR(500,"服务器内部错误"),

    //所有需要登录才能访问的API都可能会返回以下错误
    //状态码 401
    AUTH_INVALID_JWT(501,"JWT不合法"),
    AUTH_JWT_EXPIRED(502,"JWT过期"),

    //以下错误码提示可以自行修改
    //--------------------------------------------
    //状态码 400
    FIELD_NOTVALID(503,"字段不合法"),
    RESOURCE_FALSIFY(511, "信息签名不正确"),
    IMG_FORMAT_ERROR(508,"图片格式不正确"),
    IMG_SIZE_EXCEED(509,"图片大小超限"),
    PARAMETER_MISSED(510, "缺少必要参数"),
    ACT_LATE_BEGINTIME(947, "开始时间不能晚于结束时间"),
    ACT_LATE_PAYTIME(948, "尾款支付时间晚于活动结束时间"),
    ACT_EARLY_PAYTIME(949, "尾款支付时间早于活动开始时间"),
    COUPON_LATE_COUPONTIME(950,"优惠卷领卷时间晚于活动开始时间"),


    //所有路径带id的API都可能返回此错误
    //状态码 404
    RESOURCE_ID_NOTEXIST(504,"操作的资源id不存在"),

    //状态码 403
    RESOURCE_ID_OUTSCOPE(505,"操作的资源id不是自己的对象"),
    FILE_NO_WRITE_PERMISSION(506,"目录文件夹没有写入的权限"),

    //状态码 200
    STATENOTALLOW(507,"当前状态禁止此操作"),
    /***************************************************
     *    其他模块错误码
     **************************************************/
    ADDRESS_OUTLIMIT(601,"达到地址簿上限"),

    SHAREACT_CONFLICT(605,"分享活动时段冲突"),
    ORDERITEM_NOTSHARED(606,"订单明细无分享记录"),
    ADVERTISEMENT_STATENOTALLOW(608,"广告状态禁止"),


    /***************************************************
     *    订单模块错误码
     **************************************************/
    ORDER_STATENOTALLOW(801,"订单状态禁止"),
    REFUND_MORE(804,"退款金额超过支付金额"),
    REGION_NOT_REACH(805,"该地区不可达"),



    /***************************************************
     *    商品模块错误码
     **************************************************/
    GOODS_STOCK_NOTENOUGH(900,"商品规格库存不够"),
    GOODS_CATEGORY_SAME(901, "类目名称已存在"),
    GOODS_PRICE_CONFLICT(902,"商品销售时间冲突"),
    GOODS_CATEGORY_NOTALLOW(903, "不允许加入到一级分类"),


    COUPON_NOTBEGIN(909,"未到优惠卷领取时间"),
    COUPON_FINISH(910,"优惠卷领罄"),
    COUPON_END(911,"优惠卷活动终止"),
    STATE_NOCHANGE(920,"状态未改变"),
    CATEALTER_INVALID(921,"对SPU类别操作无效"),
    ACTIVITY_NOTFOUND(924,"无符合条件的优惠活动"),
    SHOP_NOTOPERABLE(925,"不可对该商铺进行操作"),
    COMMENT_EXISTED(941,"该订单条目已评论"),

    SHOP_HASDEPOSIT(966,"店铺仍有保证金未结算"),
    SHOP_CATEGORY_NOTPERMIT(967, "不允许增加新的下级分类"),
    SHOP_NOT_RECON(968,"店铺尚有支付未清算完毕"),
    SHOP_USER_HASSHOP(969,"用户已经有店铺"),
    COMMENT_USER_NOORDER(970,"用户没有购买此商品"),

    FREIGHT_REGIONOBSOLETE(995,"地区已废弃"),
    FREIGHT_WRONGTYPE(996,"该运费模板类型与内容不符"),
    FREIGHT_REGIONEXIST(997,"该运费模板中该地区已经定义"),
    FREIGHT_NOTDELETED(998,"存在上架销售商品，不能删除运费模板"),
    FREIGHT_REGIONSAME(999,"运费模板中该地区已经定义");

    private int code;
    private String message;
    ReturnNo(int code, String message){
        this.code = code;
        this.message = message;
    }

    public static ReturnNo getByCode(int code1) {
        ReturnNo[] all=ReturnNo.values();
        for (ReturnNo returnNo :all) {
            if (returnNo.code==code1) {
                return returnNo;
            }
        }
        return null;
    }
    public int getCode() {
        return code;
    }

    public String getMessage(){
        return message;
    }

}
