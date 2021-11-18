package cn.edu.xmu.oomall.goods.model.bo;

import cn.edu.xmu.oomall.core.model.VoObject;
import cn.edu.xmu.oomall.goods.model.po.OnSalePo;
import cn.edu.xmu.oomall.goods.model.vo.NewOnSaleRetVo;
import com.alibaba.druid.support.spring.stat.annotation.Stat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static cn.edu.xmu.oomall.core.util.Common.cloneVo;

@Data
public class OnSale  implements  VoObject, Serializable {

    private Long id;
    private Long shopId;
    private Long productId;
    private Long price;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private Integer quantity;
    private Byte type;
    private Long activityId;
    private Long shareActId;
    private Byte state;
    private Long creatorId;
    private String creatorName;
    private Long modifierId;
    private String modifierName;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;



    public Type getType() {
        return Type.getTypeByCode(Integer.valueOf(type));
    }


    public void setType(Type type) {
        this.type=(type.getCode().byteValue());
    }



    public State getState() {
        return State.getStatusByCode(Integer.valueOf(state));
    }


    public void setState(State state) {
        Integer code=state.getCode();
        Byte b=code.byteValue();
        this.state=b;
    }

    @Override
    public NewOnSaleRetVo createVo() {
        return (NewOnSaleRetVo)cloneVo(this,NewOnSaleRetVo.class);
    }

    @Override
    public Object createSimpleVo() {
        return null;
    }


    public enum Type {
        NOACTIVITY(0, "无活动"),
        SECKILL(1, "秒杀"),
        GROUPON(2, "团购"),
        PRESALE(3, "预售");


        private static final Map<Integer, Type> TYPE_MAP;

        static { 
            TYPE_MAP = new HashMap();
            for (Type enum1 : values()) {
                TYPE_MAP.put(enum1.code, enum1);
            }
        }

        private int code;
        private String description;

        Type(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public static Type getTypeByCode(Integer code) {
            return TYPE_MAP.get(code);
        }

        public Integer getCode() {
            return code;
        }

        public String getDescription() {
            return description;
        }

    }


    public enum State {
        DRAFT(0, "草稿"),
        ONLINE(1, "上线"),
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
            this.code=code;
            this.description=description;
        }

        public static State getStatusByCode(Integer code){
            return STATE_MAP.get(code);
        }

        public Integer getCode(){
            return code;
        }

        public String getDescription() {return description;}

    }


}