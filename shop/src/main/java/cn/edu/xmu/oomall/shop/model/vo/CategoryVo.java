package cn.edu.xmu.oomall.shop.model.vo;

import cn.edu.xmu.oomall.shop.model.bo.Category;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 商品分类Vo
 *
 * @author Zhiliang Li 22920192204235
 * @date 2021/11/12
 */
@Data
public class CategoryVo {
    @NotBlank(message = "分类名不能为空")
    @ApiModelProperty(value = "用户名")
    private String name;
    public Category createCategory() {
        Category category = new Category();
        category.setName(this.name);
        return category;
    }
}
