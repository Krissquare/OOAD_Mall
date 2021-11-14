package cn.edu.xmu.oomall.goods.model.vo;

import cn.edu.xmu.oomall.goods.model.bo.Region;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotBlank;

/**
 * 地区Vo
 *
 * @author ziyi guo
 * @date 2021/11/10
 */
@Data
@NoArgsConstructor
public class RegionVo {
    @NotBlank(message="地区名不能为空")
    @ApiModelProperty(value = "地区名")
    private String name;
}

