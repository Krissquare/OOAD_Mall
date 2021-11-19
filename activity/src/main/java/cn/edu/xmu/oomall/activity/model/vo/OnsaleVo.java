package cn.edu.xmu.oomall.activity.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class OnsaleVo {
    private Long id;
    private Long price;
    @JsonFormat(pattern = "YY-MM-dd hh:mm:ss")
    private LocalDateTime beginTime;
    @JsonFormat(pattern = "YY-MM-dd hh:mm:ss")
    private LocalDateTime endTime;
    private Integer quantity;

    public OnsaleVo(Long id, Long price, LocalDateTime beginTime, LocalDateTime endTime, Integer quantity) {
        this.id = id;
        this.price = price;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.quantity = quantity;
    }

    public OnsaleVo() {
    }
}
