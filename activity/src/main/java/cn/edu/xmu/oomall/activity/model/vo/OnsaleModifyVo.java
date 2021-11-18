package cn.edu.xmu.oomall.activity.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class OnsaleModifyVo {
    private Long price;
    @JsonFormat(pattern = "YY-MM-dd hh:mm:ss")
    private LocalDateTime begintime;
    @JsonFormat(pattern = "YY-MM-dd hh:mm:ss")
    private LocalDateTime endtime;
    private Integer quantity;

    public OnsaleModifyVo(Long price, LocalDateTime begintime, LocalDateTime endtime, Integer quantity) {
        this.price=price;
        this.begintime = begintime;
        this.endtime = endtime;
        this.quantity = quantity;
    }

    public OnsaleModifyVo() {
    }
}
