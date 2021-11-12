package cn.edu.xmu.oomall.core.util.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author cangbai xu
 * @date 2021/11/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComplexBo {
    private Integer integer;
    private String string;
    private Short aShort;
    private Double aDouble;
    private Float aFloat;
    private Character character;
    private Boolean bool;
    private LocalDateTime localDateTime;
    private List<Integer> integerList;
    private int baseInt1;
    private int baseInt2;
}

