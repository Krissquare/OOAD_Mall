package cn.edu.xmu.oomall.goods.model.vo;

import cn.edu.xmu.oomall.goods.model.bo.Region;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotBlank;

/**
 * @author ziyi guo
 * @date 2021/11/10
 */
@Data
public class RegionVo {

    private static Logger logger = LoggerFactory.getLogger(RegionVo.class);

    @NotBlank(message="地区名称不能为空")
    private String name;

    public Region createRegion(){
        Region region = new Region();
        region.setName(this.name);
        return region;
    }
}

