package cn.edu.xmu.oomall.goods.model.bo;

import cn.edu.xmu.oomall.core.model.VoObject;
import cn.edu.xmu.oomall.goods.model.po.OnSalePo;
import cn.edu.xmu.oomall.goods.model.vo.NewOnSaleRetVo;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class OnSale  implements VoObject{
     
    private OnSalePo onsalePo;

     
    public OnSale() {
        this.onsalePo = new OnSalePo();
    }

    public OnSale(OnSalePo onsalePo) {
        this.onsalePo = onsalePo;
    }




    public OnSalePo gotOnSalePo() {
        return this.onsalePo;
    }

    @Override
    public Object createVo() {
        return new NewOnSaleRetVo(this);
    }

    @Override
    public Object createSimpleVo() {
        return null;
    }

    public Long getId() {
        return onsalePo.getId();
    }

    public void setId(Long id) {
        onsalePo.setId(id);
    }

    public Long getShopId() {
        return onsalePo.getShopId();
    }

    public void setShopId(Long shopId) {
        onsalePo.setShopId(shopId);
    }


    public Long getProductId() {
        return onsalePo.getProductId();
    }

    public void setProductId(Long productId) {
        onsalePo.setProductId(productId);
    }


    public Long getPrice() {
        return onsalePo.getPrice();
    }


    public void setPrice(Long price) {
        onsalePo.setPrice(price);
    }

     
    public LocalDateTime getBeginTime() {
        return onsalePo.getBeginTime();
    }

     
    public void setBeginTime(LocalDateTime beginTime) {
        onsalePo.setBeginTime(beginTime);
    }

     
    public LocalDateTime getEndTime() {
        return onsalePo.getEndTime();
    }

     
    public void setEndTime(LocalDateTime endTime) {
        onsalePo.setEndTime(endTime);
    }

     
    public Integer getQuantity() {
        return onsalePo.getQuantity();
    }

     
    public void setQuantity(Integer quantity) {
        onsalePo.setQuantity(quantity);
    }

     
    public Type getType() {
        return Type.getTypeByCode(Integer.valueOf(onsalePo.getType()));
    }

     
    public void setType(Type type) {
        Integer code=type.getCode();
        Byte b=(byte)(0XFF & code);
        onsalePo.setType(b);
    }

     
    public Long getActivityId() {
        return onsalePo.getActivityId();
    }

     
    public void setActivityId(Long activityId) {
        onsalePo.setActivityId(activityId);
    }

     
    public Long getShareActId() {
        return onsalePo.getShareActId();
    }

     
    public void setShareActId(Long shareActId) {
        onsalePo.setShareActId(shareActId);
    }



    public Status getState() {
        return Status.getStatusByCode(Integer.valueOf(onsalePo.getState()));
    }


    public void setState(Status state) {
        Integer code=state.getCode();
        Byte b=(byte)(0XFF & code);
        onsalePo.setState(b);
    }

    public Long getCreatedBy(){
        return onsalePo.getCreatedBy();
    }

    public void setCreatedBy(Long createdBy){
        onsalePo.setCreatedBy(createdBy);
    }

    public String getCreateName(){
        return onsalePo.getCreateName();
    }

    public void setCreateName(String createName){
        onsalePo.setCreateName(createName);
    }


    public Long getModifiedBy(){
        return onsalePo.getModifiedBy();
    }

    public void setModifiedBy(Long modifiedBy){
        onsalePo.setModifiedBy(modifiedBy);
    }

    public String getModiName(){
        return onsalePo.getModiName();
    }

    public void setModiName(String modiName){
        onsalePo.setModiName(modiName);
    }

    public LocalDateTime getGmtCreate() {
        return onsalePo.getGmtCreate();
    }

    public void setGmtCreate(LocalDateTime llt){
        onsalePo.setGmtCreate(llt);
    }

    public LocalDateTime getGmtModified() {
        return onsalePo.getGmtModified();
    }

    public void setGmtModified(LocalDateTime llt){
        onsalePo.setGmtModified(llt);
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


    public enum Status {
        DRAFT(0, "草稿"),
        ONLINE(1, "上线"),
        OFFLINE(2, "下线");


        private static final Map<Integer, Status> STATE_MAP;

        static { //由类加载机制，静态块初始加载对应的枚举属性到map中，而不用每次取属性时，遍历一次所有枚举值
            STATE_MAP = new HashMap();
            for (Status enum1 : values()) {
                STATE_MAP.put(enum1.code, enum1);
            }
        }

        private int code;
        private String description;

        Status(int code, String description) {
            this.code=code;
            this.description=description;
        }

        public static Status getStatusByCode(Integer code){
            return STATE_MAP.get(code);
        }

        public Integer getCode(){
            return code;
        }

        public String getDescription() {return description;}

    }


}