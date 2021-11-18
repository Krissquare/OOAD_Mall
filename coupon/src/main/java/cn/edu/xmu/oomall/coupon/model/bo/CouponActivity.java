package cn.edu.xmu.oomall.coupon.model.bo;

import lombok.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author RenJieZheng 22920192204334
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CouponActivity {
    public enum State {
        /**
         * 草稿
         */
        DRAFT(0,"草稿"),
        /**
         * 上线
         */
        ONLINE(1,"上线"),
        /**
         * 下线
         */
        OFFLINE(2, "下线");

        private static final Map<Integer, State> STATE_MAP;
        static { //由类加载机制，静态块初始加载对应的枚举属性到map中，而不用每次取属性时，遍历一次所有枚举值
            STATE_MAP = new HashMap();
            for (State enum1 : values()) {
                STATE_MAP.put(enum1.code, enum1);
            }
        }
        private int code;
        private String description;

        State(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public static State getTypeByCode(Integer code) {
            return STATE_MAP.get(code);
        }

        public Integer getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }
    }

    private Long id;
    private String name;
    private Long shopId;
    private String shopName;
    private LocalDateTime couponTime;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private Integer quantity;
    private Byte quantityType;
    private Byte validTerm;
    private String imageUrl;
    private String strategy;
    private Byte state;
    private Long createdBy;
    private String createName;
    private Long modifiedBy;
    private String modiName;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;


}
