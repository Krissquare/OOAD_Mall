package cn.edu.xmu.oomall.shop.service;

import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.shop.dao.CategoryDao;
import cn.edu.xmu.oomall.shop.model.bo.Category;
import cn.edu.xmu.oomall.shop.model.po.CategoryPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CategoryService {
    @Autowired
    private CategoryDao categoryDao;

    public ReturnObject getSubCategories(Long id) {
        // id不为0或-1则且找不到对应id的类别
        if (categoryDao.getCategoryById(id).getData() == null && id > 0) {
            return new ReturnObject<>(ReturnNo.RESOURCE_ID_NOTEXIST);
        }

        return categoryDao.getSubCategories(id);
    }

    public ReturnObject newCategory(Long id, Category category, Long createId, String createName) {
        Category pCategory = (Category) categoryDao.getCategoryById(id).getData();
        if (pCategory == null && id > 0) {
            return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST);
        }
        /** 防止出现3级类别的情形或将单独类别（-1）作为父类别 **/
        if (pCategory != null && pCategory.getPid() != 0L) {
            return new ReturnObject(ReturnNo.SHOP_CATEGORY_NOTPERMIT);
        }
        if (categoryDao.hasSameName(category.getName())) {
            return new ReturnObject(ReturnNo.GOODS_CATEGORY_SAME);
        }
        CategoryPo categoryPo = category.getCategoryPo();
        categoryPo.setGmtCreate(LocalDateTime.now());
        categoryPo.setCreatedBy(createId);
        categoryPo.setCreateName(createName);
        categoryPo.setPid(id.longValue());
        ReturnObject ret = categoryDao.insertCategory(categoryPo);
        return ret;
    }

    public ReturnObject changeCategory(Long id, Category category, Long modifyId, String modiName) {
        if (categoryDao.getCategoryById(id).getData() == null) {
            return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST);
        }
        if (categoryDao.hasSameName(category.getName())) {
            return new ReturnObject<>(ReturnNo.GOODS_CATEGORY_SAME);
        }
        CategoryPo po = category.getCategoryPo();
        po.setId(id.longValue());
        po.setModifiedBy(modifyId);
        po.setModiName(modiName);
        po.setGmtModified(LocalDateTime.now());
        ReturnObject ret = categoryDao.updateCategory(po);
        return ret;
    }

    public ReturnObject deleteCategoryById(Long id) {
        /** 若有子类别，将子类别设为单独分类（pid=-1）**/
        var sub = categoryDao.getSubCategories(id);
        if (sub.getCode().equals(ReturnNo.OK)) {
            for (Category category : sub.getData()) {
                category.setPid(-1L);
                CategoryPo categoryPo = category.getCategoryPo();
                categoryDao.updateCategory(categoryPo);
            }
        }
        ReturnObject ret = categoryDao.deleteCategoryById(id.longValue());
        return ret;
    }

}
