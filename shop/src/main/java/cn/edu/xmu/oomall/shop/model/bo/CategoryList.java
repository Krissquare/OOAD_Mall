package cn.edu.xmu.oomall.shop.model.bo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品子分类列表Bo
 * 主要用于方便redis
 *
 * @author Zhiliang Li 22920192204235
 * @date 2021/11/12
 */
@Data
public class CategoryList implements Serializable {
    private List<Category> categoryList;

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(List<Category> categoryList) {
        this.categoryList = categoryList;
    }
}
