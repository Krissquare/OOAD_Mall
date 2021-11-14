package cn.edu.xmu.oomall.shop.service;

import cn.edu.xmu.oomall.core.util.Common;
import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.shop.dao.CategoryDao;
import cn.edu.xmu.oomall.shop.model.bo.Category;
import cn.edu.xmu.oomall.shop.model.po.CategoryPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 商品分类Service
 *
 * @author Zhiliang Li 22920192204235
 * @date 2021/11/14
 */
@Service
public class CategoryService {
    @Autowired
    private CategoryDao categoryDao;

    /**
     * 通过id查找子分类
     * 若为二级分类返回空数组
     *
     * @param id
     * @return ReturnObject
     */
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    public ReturnObject getSubCategories(Long id) {
        if (categoryDao.getCategoryById(id).getData() == null && id > 0) {
            return new ReturnObject<>(ReturnNo.RESOURCE_ID_NOTEXIST);
        }

        return categoryDao.getSubCategories(id);
    }

    /**
     * 创建新分类
     * id=0为一级分类，>0为二级分类（考虑父类别为二级分类或单独分类的异常情况）
     * id<0在controller层拦截
     *
     * @param id
     * @return ReturnObject
     */
    @Transactional(rollbackFor = Exception.class)
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
        CategoryPo categoryPo = (CategoryPo) Common.cloneVo(category, CategoryPo.class);
        Common.setPoCreatedFields(categoryPo, createId, createName);
        categoryPo.setPid(id.longValue());
        ReturnObject ret = categoryDao.insertCategory(categoryPo);
        return ret;
    }

    /**
     * 更新分类
     *
     * @param id,category,modifyId,modiName
     * @return ReturnObject
     */
    @Transactional(rollbackFor = Exception.class)
    public ReturnObject changeCategory(Long id, Category category, Long modifyId, String modiName) {
        if (categoryDao.getCategoryById(id).getData() == null) {
            return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST);
        }
        if (categoryDao.hasSameName(category.getName())) {
            return new ReturnObject<>(ReturnNo.GOODS_CATEGORY_SAME);
        }
        CategoryPo po = (CategoryPo) Common.cloneVo(category, CategoryPo.class);
        Common.setPoModifiedFields(po, modifyId, modiName);
        po.setId(id.longValue());

        ReturnObject ret = categoryDao.updateCategory(po);
        return ret;
    }

    /**
     * 删除分类
     *
     * @param id
     * @return ReturnObject
     */
    @Transactional(rollbackFor = Exception.class)
    public ReturnObject deleteCategoryById(Long id) {
        /** 若有子类别，将子类别设为单独分类（pid=-1）**/
        var sub = categoryDao.getSubCategories(id);
        if (sub.getCode().equals(ReturnNo.OK)) {
            for (Category category : sub.getData()) {
                category.setPid(-1L);
                CategoryPo categoryPo = (CategoryPo) Common.cloneVo(category, CategoryPo.class);
                categoryDao.updateCategory(categoryPo);
            }
        }
        ReturnObject ret = categoryDao.deleteCategoryById(id.longValue());
        return ret;
    }
}
