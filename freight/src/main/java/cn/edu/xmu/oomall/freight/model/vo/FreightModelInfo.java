package cn.edu.xmu.oomall.freight.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 *
 * @author RenJieZheng 22920192204334
 * @date 2021/11/16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FreightModelInfo {
    @Length(max = 100,message = "name字符串最大长度为100")
    private String name;
    @Min(value = 0,message = "unit最小值为0")
    private Integer unit;
    @Min(value = 0,message = "type最小值为0")
    @Max(value = 1,message = "type最大值为1")
    private Byte type;
}
