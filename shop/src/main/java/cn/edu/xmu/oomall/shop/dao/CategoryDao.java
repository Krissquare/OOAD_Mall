package cn.edu.xmu.oomall.shop.dao;

import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.shop.mapper.CategoryPoMapper;
import cn.edu.xmu.oomall.shop.model.bo.Category;
import cn.edu.xmu.oomall.shop.model.bo.CategoryList;
import cn.edu.xmu.oomall.shop.model.po.CategoryPo;
import cn.edu.xmu.oomall.shop.model.po.CategoryPoExample;
import cn.edu.xmu.oomall.core.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品分类Dao层
 *
 * @author Zhiliang Li 22920192204235
 * @date 2021/11/12
 */
@Repository
public class CategoryDao {

    @Autowired
    private CategoryPoMapper categoryPoMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Value("${oomall.shop.category.expiretime}")
    private long categoryTimeout;

    /**
     * 通过id查找分类
     *
     * @param id
     * @return ReturnObject
     */
    public ReturnObject getCategoryById(Long id){
        // 通过redis找到
        String key="c_"+id;
        Category category=(Category)redisUtil.get(key);
        try{
            if(null != category){
                return new ReturnObject(category);
            }
            // 没找到去数据库取
            CategoryPo categoryPo=categoryPoMapper.selectByPrimaryKey(id);
            // 数据库未找到返回异常
            if(categoryPo == null){
                return new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST);
            }

            // 数据库找到就插入redis
            category=new Category(categoryPo);
            redisUtil.set(key,category,categoryTimeout);

            return new ReturnObject(category);
        }catch (Exception e){
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR);
        }
    }

    /**
     * 通过redis查找
     * redis中不仅存父对子类List，还要存子对父
     * 方便更新/删除操作对父类的删除
     *
     * @param id
     * @return ReturnObject<List<Category>>
     */
    public ReturnObject<List<Category>> getSubCategories(Long id){
        CategoryPoExample example=new CategoryPoExample();
        CategoryPoExample.Criteria criteria=example.createCriteria();
        criteria.andPidEqualTo(id);
        try {
            // 先从redis中取
            String key="subList_"+id;
            String subKey;
            CategoryList categoryList;
            List<CategoryPo> categoryPos;

            categoryList=(CategoryList) redisUtil.get(key);
            // 若找到直接返回
            if(null != categoryList){
                return new ReturnObject<>(categoryList.getCategoryList());
            }

            // 去数据库查询
            categoryPos= categoryPoMapper.selectByExample(example);
            List<Category> categories=new ArrayList<>();

            for(CategoryPo categoryPo:categoryPos){
                subKey="s_"+categoryPo.getId();
                redisUtil.set(subKey,id,categoryTimeout);
                Category c=new Category(categoryPo);
                categories.add(c);
            }

            categoryList=new CategoryList();
            categoryList.setCategoryList(categories);
            redisUtil.set(key,categoryList,categoryTimeout);

            ReturnObject<List<Category>> ret=new ReturnObject<>(categories);
            return ret;
        }catch (Exception e){
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR);
        }
    }

    /**
     * 判断数据库中是否有相同name
     *
     * @param name
     * @return true,false
     */
    public boolean hasSameName(String name){
        CategoryPoExample example = new CategoryPoExample();
        CategoryPoExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(name);

        var ret = categoryPoMapper.selectByExample(example);
        return !ret.isEmpty();
    }

    /**
     * 新插入分类
     *
     * @param categoryPo
     * @return ReturnObject
     */
    public ReturnObject insertCategory(CategoryPo categoryPo) {
        try{
            int ret=categoryPoMapper.insertSelective(categoryPo);
            if (ret == 0) {
                return new ReturnObject(ReturnNo.FIELD_NOTVALID);
            }
            Category category=new Category(categoryPo);
            return new ReturnObject(category);
        }catch (Exception e){
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR);
        }
    }

    /**
     * 更新分类name字段
     * 我们需要删除redis中包括它父类别（有可能存在）、自身的所有相关字段
     *
     * @param po
     * @return ReturnObject
     */
    public ReturnObject updateCategory(CategoryPo po) {
        try{
            deleteRedisForDeleteOrUpdate(po.getId());

            categoryPoMapper.updateByPrimaryKeySelective(po);
            return new ReturnObject(ReturnNo.OK);

        }catch (Exception e){
            return new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR);
        }
    }

    /**
     * 删除分类
     * 我们需要删除redis中包括它父类别（有可能存在）、自身的所有相关字段
     *
     * @param id
     * @return ReturnObject
     */
    public ReturnObject deleteCategoryById(Long id){
        try{
            deleteRedisForDeleteOrUpdate(id);

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

    private void deleteRedisForDeleteOrUpdate(Long id){
        String subKey="s_"+id;

        // 若存有父id，去redis中删除那条记录
        if (redisUtil.get(subKey)!=null){
            redisUtil.del("subList_"+redisUtil.get(subKey));
        }
        // 删除redis中相关记录
        redisUtil.del(subKey);
        redisUtil.del("c_"+ id);
    }
}
