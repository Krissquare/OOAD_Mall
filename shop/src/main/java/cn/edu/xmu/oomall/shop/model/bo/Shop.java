package cn.edu.xmu.oomall.shop.model.bo;

import cn.edu.xmu.oomall.core.model.VoObject;
import cn.edu.xmu.oomall.shop.model.po.ShopPo;
import cn.edu.xmu.oomall.shop.model.vo.ShopRetVo;
import cn.edu.xmu.oomall.shop.model.vo.ShopSimpleRetVo;
import cn.edu.xmu.oomall.shop.model.vo.SimpleUserRetVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shop implements VoObject {
    public enum State {
        EXAME(0, "未审核"),
        OFFLINE(1, "下线"),
        ONLINE(2, "上线"),
        FORBID(3, "关闭");

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
    private String name;
    private Long deposit;
    private Long depositThreshold;
    private Byte state;
    private SimpleUserRetVo createdBy;
    private SimpleUserRetVo modifiedBy;
    private LocalDateTime gmtCreated;
    private LocalDateTime gmtModified;


    @Override
    public Object createVo() {
        return new ShopRetVo(this);
    }

    @Override
    public Object createSimpleVo() {
        ShopSimpleRetVo ret = new ShopSimpleRetVo();
        ret.setId(this.getId());
        ret.setName(this.getName());
        return ret;
    }

    public Shop(ShopPo shopPo) {
        this.setId(shopPo.getId());
        this.setName(shopPo.getName());
        this.setState(shopPo.getState());
        this.setDeposit(shopPo.getDeposit());
        this.setDepositThreshold(shopPo.getDepositThreshold());
        this.createdBy = new SimpleUserRetVo();
        this.createdBy.setId(shopPo.getCreatedBy());
        this.createdBy.setName(shopPo.getCreateName());
        this.modifiedBy = new SimpleUserRetVo();
        this.modifiedBy.setId(shopPo.getModifiedBy());
        this.modifiedBy.setName(shopPo.getModiName());
        this.setGmtCreated(shopPo.getGmtCreate());
        this.setGmtModified(shopPo.getGmtModified());
    }

//    public ShopDTO createDTO(){
//        ShopDTO dto = new ShopDTO();
//        dto.setId(id);
//        dto.setName(name);
//        dto.setGmtCreateTime(gmtCreated);
//        dto.setGmtModiTime(gmtModified);
//        dto.setState(state);
//        return dto;
//    }

    public ShopPo createPo() {
        ShopPo shopPo = new ShopPo();
        shopPo.setId(this.getId());
        shopPo.setName(this.getName());
        shopPo.setState(this.getState());
        shopPo.setDeposit(this.getDeposit());
        shopPo.setDepositThreshold(this.getDepositThreshold());
        shopPo.setCreatedBy(this.createdBy.getId());
        shopPo.setCreateName(this.createdBy.getName());
        shopPo.setModifiedBy(this.modifiedBy.getId());
        shopPo.setModiName(this.modifiedBy.getName());
        shopPo.setGmtCreate(this.getGmtCreated());
        shopPo.setGmtModified(this.getGmtModified());
        return shopPo;
    }

}

