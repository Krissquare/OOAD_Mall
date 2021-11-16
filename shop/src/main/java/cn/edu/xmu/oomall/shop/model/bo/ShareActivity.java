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

    @Override
    public Object createVo() {
        return this;
    }

    @Override
    public Object createSimpleVo() {
        return this;
    }
}
