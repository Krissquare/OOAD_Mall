package cn.edu.xmu.oomall.shop.model.bo;

import cn.edu.xmu.oomall.core.model.VoObject;
import cn.edu.xmu.oomall.shop.model.po.ShareActivityPo;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Created with IntelliJ IDEA.
 *
 * @author BingShuai Liu
 * @date 2021/11/11/21:36
 * @description
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ShareActivity implements VoObject {
    public enum State{
        Draft((byte) 0,"草稿态"),
        Online((byte)1,"上线态"),
        Offline((byte)2, "下线态");;
        private Byte code;
        private String description;
        State(Byte code, String description){
            this.code=code;
            this.description=description;
        }
        public Byte getCode() { return this.code;}
        public String getDescription(){ return this.description;}
    }
    private Long id;
    private Long shopId;
    private String shopName;
    private String name;
    private String strategy;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private Byte state;
    private Long createdBy;
    private String createName;
    private Long modifiedBy;
    private String modiName;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;

    public ShareActivityPo newShareActivityPo(){
        ShareActivityPo shareActivityPo=new ShareActivityPo();
        shareActivityPo.setId(this.id);
        shareActivityPo.setShopId(this.shopId);
        shareActivityPo.setShopName(this.shopName);
        shareActivityPo.setName(this.name);
        shareActivityPo.setStrategy(this.strategy);
        shareActivityPo.setBeginTime(this.beginTime);
        shareActivityPo.setEndTime(this.endTime);
        shareActivityPo.setState(this.state);
        shareActivityPo.setCreatedBy(this.createdBy);
        shareActivityPo.setCreateName(this.createName);
        shareActivityPo.setModifiedBy(this.modifiedBy);
        shareActivityPo.setModiName(this.modiName);
        shareActivityPo.setGmtCreate(this.gmtCreate);
        shareActivityPo.setGmtModified(this.gmtModified);
        return shareActivityPo;
    }
    @Override
    public Object createVo() {
        return null;
    }

    @Override
    public Object createSimpleVo() {
        return null;
    }
}
