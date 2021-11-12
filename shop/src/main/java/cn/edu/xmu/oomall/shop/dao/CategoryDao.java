package cn.edu.xmu.oomall.shop.dao;

import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.shop.mapper.CategoryPoMapper;
import cn.edu.xmu.oomall.shop.model.bo.Category;
import cn.edu.xmu.oomall.shop.model.po.CategoryPo;
import cn.edu.xmu.oomall.shop.model.po.CategoryPoExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryDao {
    private  static  final Logger logger = LoggerFactory.getLogger(CategoryDao.class);

    @Autowired
    private CategoryPoMapper categoryPoMapper;

    public ReturnObject getCategoryById(Long id){
        try{
            CategoryPo categoryPo=categoryPoMapper.selectByPrimaryKey(id);
            if(categoryPo==null){
                return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST);
            }
            Category category=new Category(categoryPo);
            return new ReturnObject(category);
        }catch (Exception e){
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR);
        }
    }

    public ReturnObject<List<Category>> getSubCategories(Long id){
        CategoryPoExample example=new CategoryPoExample();
        CategoryPoExample.Criteria criteria=example.createCriteria();
        criteria.andPidEqualTo(id);
        try {
            List<CategoryPo> categoryPos = categoryPoMapper.selectByExample(example);
            List<Category> categories=new ArrayList<>();

            for(CategoryPo categoryPo:categoryPos){
                Category c=new Category(categoryPo);
                categories.add(c);
            }
            ReturnObject<List<Category>> ret=new ReturnObject<>(categories);
            return ret;
        }catch (Exception e){
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR);
        }
    }

    public boolean hasSameName(String name){
        CategoryPoExample example = new CategoryPoExample();
        CategoryPoExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(name);

        var ret = categoryPoMapper.selectByExample(example);
        return !ret.isEmpty();
    }

    public ReturnObject insertCategory(CategoryPo categoryPo) {
        try{
            int ret=categoryPoMapper.insertSelective(categoryPo);
            if (ret == 0) {
                logger.debug("insertSku: insert failed: " + categoryPo.getId());
                return new ReturnObject(ReturnNo.FIELD_NOTVALID);
            }
            Category category=new Category(categoryPo);
            return new ReturnObject(category);
        }catch (Exception e){
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR);
        }
    }

    public ReturnObject updateCategory(CategoryPo po) {
        try{
            categoryPoMapper.updateByPrimaryKeySelective(po);
            return new ReturnObject(ReturnNo.OK);

        }catch (Exception e){
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR);
        }

    }

    public ReturnObject deleteCategoryById(Long id){
        try{
            int ret;
            ret= categoryPoMapper.deleteByPrimaryKey(id);
            if(ret==0){
                return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST);
            }else{
                return new ReturnObject(ReturnNo.OK);
            }
        }catch (Exception e){
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR);
        }
    }
}
