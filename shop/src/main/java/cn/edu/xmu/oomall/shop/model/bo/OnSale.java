package cn.edu.xmu.oomall.shop.model.bo;

import lombok.*;

import java.time.LocalDateTime;

/**
 * 在售商品Bo
 *
 * @author BingShuai Liu
 * @studentId 22920192204245
 * @date 2021/11/13/15:55
 */
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OnSale {
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
    private Long productId;
    private Long price;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private Integer quantity;
    private Byte type;
    private Long activityId;
    private Long shareActId;
    private Byte state;
    private Long createdBy;
    private String createName;
    private Long modifiedBy;
    private String modiName;
    private LocalDateTime gmtCreate;
    private LocalDateTime gmtModified;
}
