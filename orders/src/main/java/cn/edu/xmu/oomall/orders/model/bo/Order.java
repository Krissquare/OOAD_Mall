package cn.edu.xmu.oomall.orders.model.bo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author RenJieZheng 22920192204334
 * @date 2021/11/16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    public enum State {
        /**
         * 代付款
         */
        TO_BE_PAID(1, "待付款"),
        /**
         * 待收货
         */
        TO_BE_RECEIVED(2, "待收货"),

        /**
         * 已完成
         */
        COMPLETED(3, "已完成"),
        /**
         * 已取消
         */
        CANCELED(4, "已取消"),
        /**
         * 新订单
         */
        NEW(11, "新订单"),
        /**
         * 待支付尾款
         */
        BALANCE_TO_BE_PAID(12, "待支付尾款"),
        /**
         * 付款完成
         */
        PAID(21, "付款完成"),
        /**
         * 待成团
         */
        GROUPON_THRESHOLD_TO_BE_REACH(22, "待成团"),
        /**
         *
         * 未成团
         */
        GROUPON_THRESHOLD_NOT_REACH(23, "未成团"),
        /**
         * 已发货
         */
        DELIVERED(24, "已发货");

        private static final Map<Integer, State> stateMap;
        static { //由类加载机制，静态块初始加载对应的枚举属性到map中，而不用每次取属性时，遍历一次所有枚举值
            stateMap = new HashMap();
            for (State enum1 : values()) {
                stateMap.put(enum1.code, enum1);
            }
        }
        private int code;
        private String description;

        State(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public static State getTypeByCode(Integer code) {
            return stateMap.get(code);
        }

        public Integer getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }
    }

    private Long id;
    private Long customerId;
    private Long shopId;
    private String orderSn;
    private Long pid;
    private String consignee;
    private Long regionId;
    private String address;
    private String mobile;
    private String message;
    private Byte orderType;
    private Long presaleId;
    private Long grouponId;
    private Long freightPrice;
    private Long discountPrice;
    private Long originPrice;
    private LocalDateTime confirmTime;
    private String shipmentSn;
    private Byte state;
    private Byte substate;
    private Byte beDeleted;
    private Long point;
    private String createName;
    private Long createBy;
    private Long modifiedBy;
    private String modiName;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
}
