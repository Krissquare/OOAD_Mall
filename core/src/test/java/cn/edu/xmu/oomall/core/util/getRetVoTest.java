package cn.edu.xmu.oomall.core.util;

import cn.edu.xmu.oomall.core.util.bo.Category;
import cn.edu.xmu.oomall.core.util.vo.CategoryRetVo;
import com.github.pagehelper.PageInfo;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author ziyi guo
 * @date 2021/11/15
 */
public class getRetVoTest {

    @Test
    public void getRetObjectTest() {
        Category categoryBo=new Category();
        categoryBo.setId(1L);
        categoryBo.setCommissionRatio(1);
        categoryBo.setCreatedBy(2L);
        categoryBo.setCreateName("CreateName");
        categoryBo.setModifiedBy(3L);
        categoryBo.setModiName("ModiName");
        LocalDateTime gmtCreate=LocalDateTime.now().minusDays(1);
        LocalDateTime gmtModified=LocalDateTime.now();
        categoryBo.setGmtCreate(gmtCreate);
        categoryBo.setGmtModified(gmtModified);
        categoryBo.setPid(2L);
        categoryBo.setName("name");

        ReturnObject retObjBo1 = new ReturnObject(categoryBo);
        ReturnObject retObjBo2 = new ReturnObject();
        ReturnObject retObjBo3 = new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST);

        ReturnObject retObjVo1 = Common.getRetObject(retObjBo1);
        ReturnObject retObjVo2 = Common.getRetObject(retObjBo2);
        ReturnObject retObjVo3 = Common.getRetObject(retObjBo3);

        assertEquals(retObjVo1.getCode(),ReturnNo.OK);
        assertEquals(retObjVo1.getErrmsg(),"成功");
        CategoryRetVo categoryRetVo = (CategoryRetVo) retObjVo1.getData();
        assertEquals(categoryRetVo.getId(),1L);
        assertEquals(categoryRetVo.getName(),"name");
        assertEquals(categoryRetVo.getCreatedBy().getId(),2L);
        assertEquals(categoryRetVo.getCreatedBy().getName(),"CreateName");
        assertEquals(categoryRetVo.getModifiedBy().getId(),3L);
        assertEquals(categoryRetVo.getModifiedBy().getName(),"ModiName");
        assertEquals(categoryRetVo.getCommissionRate(),1);
        assertEquals(categoryRetVo.getGmtCreate(),gmtCreate);
        assertEquals(categoryRetVo.getGmtModified(),gmtModified);

        assertEquals(retObjVo2.getCode(),ReturnNo.OK);
        assertEquals(retObjVo2.getErrmsg(),"成功");
        assertEquals(retObjVo2.getData(),null);

        assertEquals(retObjVo3.getCode(),ReturnNo.RESOURCE_ID_NOTEXIST);
        assertEquals(retObjVo3.getErrmsg(),"操作的资源id不存在");
        assertEquals(retObjVo3.getData(),null);
    }

    @Test
    public void getListRetObjectTest() {
        Category categoryBo=new Category();
        categoryBo.setId(1L);
        categoryBo.setCommissionRatio(1);
        categoryBo.setCreatedBy(2L);
        categoryBo.setCreateName("CreateName");
        categoryBo.setModifiedBy(3L);
        categoryBo.setModiName("ModiName");
        LocalDateTime gmtCreate=LocalDateTime.now().minusDays(1);
        LocalDateTime gmtModified=LocalDateTime.now();
        categoryBo.setGmtCreate(gmtCreate);
        categoryBo.setGmtModified(gmtModified);
        categoryBo.setPid(2L);
        categoryBo.setName("name");
        List<Category> boList = new ArrayList<>();
        boList.add(categoryBo);

        ReturnObject retObjBo1 = new ReturnObject(boList);
        ReturnObject retObjBo2 = new ReturnObject();
        ReturnObject retObjBo3 = new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST);

        ReturnObject retObjVo1 = Common.getListRetObject(retObjBo1);
        ReturnObject retObjVo2 = Common.getListRetObject(retObjBo2);
        ReturnObject retObjVo3 = Common.getListRetObject(retObjBo3);

        assertEquals(retObjVo1.getCode(),ReturnNo.OK);
        assertEquals(retObjVo1.getErrmsg(),"成功");
        List<CategoryRetVo> voList = (List<CategoryRetVo>) retObjVo1.getData();
        CategoryRetVo categoryRetVo = voList.get(0);
        assertEquals(categoryRetVo.getId(),1L);
        assertEquals(categoryRetVo.getName(),"name");
        assertEquals(categoryRetVo.getCreatedBy().getId(),2L);
        assertEquals(categoryRetVo.getCreatedBy().getName(),"CreateName");
        assertEquals(categoryRetVo.getModifiedBy().getId(),3L);
        assertEquals(categoryRetVo.getModifiedBy().getName(),"ModiName");
        assertEquals(categoryRetVo.getCommissionRate(),1);
        assertEquals(categoryRetVo.getGmtCreate(),gmtCreate);
        assertEquals(categoryRetVo.getGmtModified(),gmtModified);

        assertEquals(retObjVo2.getCode(),ReturnNo.OK);
        assertEquals(retObjVo2.getErrmsg(),"成功");
        assertEquals(retObjVo2.getData(),null);

        assertEquals(retObjVo3.getCode(),ReturnNo.RESOURCE_ID_NOTEXIST);
        assertEquals(retObjVo3.getErrmsg(),"操作的资源id不存在");
        assertEquals(retObjVo3.getData(),null);
    }

    @Test
    public void getListRetVoTest() {
        Category categoryBo=new Category();
        categoryBo.setId(1L);
        categoryBo.setCommissionRatio(1);
        categoryBo.setCreatedBy(2L);
        categoryBo.setCreateName("CreateName");
        categoryBo.setModifiedBy(3L);
        categoryBo.setModiName("ModiName");
        LocalDateTime gmtCreate=LocalDateTime.now().minusDays(1);
        LocalDateTime gmtModified=LocalDateTime.now();
        categoryBo.setGmtCreate(gmtCreate);
        categoryBo.setGmtModified(gmtModified);
        categoryBo.setPid(2L);
        categoryBo.setName("name");
        List<Category> boList = new ArrayList<>();
        boList.add(categoryBo);

        ReturnObject retObjBo1 = new ReturnObject(boList);
        ReturnObject retObjBo2 = new ReturnObject();
        ReturnObject retObjBo3 = new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST);

        ReturnObject retObjVo1 = Common.getListRetVo(retObjBo1, CategoryRetVo.class);
        ReturnObject retObjVo2 = Common.getListRetVo(retObjBo2, CategoryRetVo.class);
        ReturnObject retObjVo3 = Common.getListRetVo(retObjBo3, CategoryRetVo.class);

        assertEquals(retObjVo1.getCode(),ReturnNo.OK);
        assertEquals(retObjVo1.getErrmsg(),"成功");
        List<CategoryRetVo> voList = (List<CategoryRetVo>) retObjVo1.getData();
        CategoryRetVo categoryRetVo = voList.get(0);
        assertEquals(categoryRetVo.getId(),1L);
        assertEquals(categoryRetVo.getName(),"name");
        assertEquals(categoryRetVo.getCreatedBy().getId(),2L);
        assertEquals(categoryRetVo.getCreatedBy().getName(),"CreateName");
        assertEquals(categoryRetVo.getModifiedBy().getId(),3L);
        assertEquals(categoryRetVo.getModifiedBy().getName(),"ModiName");
        assertEquals(categoryRetVo.getCommissionRate(),null);
        assertEquals(categoryRetVo.getGmtCreate(),gmtCreate);
        assertEquals(categoryRetVo.getGmtModified(),gmtModified);

        assertEquals(retObjVo2.getCode(),ReturnNo.OK);
        assertEquals(retObjVo2.getErrmsg(),"成功");
        assertEquals(retObjVo2.getData(),null);

        assertEquals(retObjVo3.getCode(),ReturnNo.RESOURCE_ID_NOTEXIST);
        assertEquals(retObjVo3.getErrmsg(),"操作的资源id不存在");
        assertEquals(retObjVo3.getData(),null);
    }

    @Test
    public void getPageRetObjectTest() {
        Category categoryBo=new Category();
        categoryBo.setId(1L);
        categoryBo.setCommissionRatio(1);
        categoryBo.setCreatedBy(2L);
        categoryBo.setCreateName("CreateName");
        categoryBo.setModifiedBy(3L);
        categoryBo.setModiName("ModiName");
        LocalDateTime gmtCreate=LocalDateTime.now().minusDays(1);
        LocalDateTime gmtModified=LocalDateTime.now();
        categoryBo.setGmtCreate(gmtCreate);
        categoryBo.setGmtModified(gmtModified);
        categoryBo.setPid(2L);
        categoryBo.setName("name");
        List<Category> boList = new ArrayList<>();
        boList.add(categoryBo);
        PageInfo pageInfo = new PageInfo(boList);

        ReturnObject retObjBo1 = new ReturnObject(pageInfo);
        ReturnObject retObjBo2 = new ReturnObject();
        ReturnObject retObjBo3 = new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST);

        ReturnObject retObjVo1 = Common.getPageRetObject(retObjBo1);
        ReturnObject retObjVo2 = Common.getPageRetObject(retObjBo2);
        ReturnObject retObjVo3 = Common.getPageRetObject(retObjBo3);

        assertEquals(retObjVo1.getCode(),ReturnNo.OK);
        assertEquals(retObjVo1.getErrmsg(),"成功");
        Map<String, Object> map = (Map<String, Object>) retObjVo1.getData();
        List<CategoryRetVo> voList = (List<CategoryRetVo>) map.get("list");
        CategoryRetVo categoryRetVo = voList.get(0);
        assertEquals(categoryRetVo.getId(),1L);
        assertEquals(categoryRetVo.getName(),"name");
        assertEquals(categoryRetVo.getCreatedBy().getId(),2L);
        assertEquals(categoryRetVo.getCreatedBy().getName(),"CreateName");
        assertEquals(categoryRetVo.getModifiedBy().getId(),3L);
        assertEquals(categoryRetVo.getModifiedBy().getName(),"ModiName");
        assertEquals(categoryRetVo.getCommissionRate(),1);
        assertEquals(categoryRetVo.getGmtCreate(),gmtCreate);
        assertEquals(categoryRetVo.getGmtModified(),gmtModified);
        assertEquals(map.get("total"),Long.valueOf(1));
        assertEquals(map.get("page"),1);
        assertEquals(map.get("pageSize"),1);
        assertEquals(map.get("pages"),1);

        assertEquals(retObjVo2.getCode(),ReturnNo.OK);
        assertEquals(retObjVo2.getErrmsg(),"成功");
        assertEquals(retObjVo2.getData(),null);

        assertEquals(retObjVo3.getCode(),ReturnNo.RESOURCE_ID_NOTEXIST);
        assertEquals(retObjVo3.getErrmsg(),"操作的资源id不存在");
        assertEquals(retObjVo3.getData(),null);
    }

    @Test
    public void getPageRetVoTest() {
        Category categoryBo=new Category();
        categoryBo.setId(1L);
        categoryBo.setCommissionRatio(1);
        categoryBo.setCreatedBy(2L);
        categoryBo.setCreateName("CreateName");
        categoryBo.setModifiedBy(3L);
        categoryBo.setModiName("ModiName");
        LocalDateTime gmtCreate=LocalDateTime.now().minusDays(1);
        LocalDateTime gmtModified=LocalDateTime.now();
        categoryBo.setGmtCreate(gmtCreate);
        categoryBo.setGmtModified(gmtModified);
        categoryBo.setPid(2L);
        categoryBo.setName("name");
        List<Category> boList = new ArrayList<>();
        boList.add(categoryBo);
        PageInfo pageInfo = new PageInfo(boList);

        ReturnObject retObjBo1 = new ReturnObject(pageInfo);
        ReturnObject retObjBo2 = new ReturnObject();
        ReturnObject retObjBo3 = new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST);

        ReturnObject retObjVo1 = Common.getPageRetVo(retObjBo1, CategoryRetVo.class);
        ReturnObject retObjVo2 = Common.getPageRetVo(retObjBo2, CategoryRetVo.class);
        ReturnObject retObjVo3 = Common.getPageRetVo(retObjBo3, CategoryRetVo.class);

        assertEquals(retObjVo1.getCode(),ReturnNo.OK);
        assertEquals(retObjVo1.getErrmsg(),"成功");
        Map<String, Object> map = (Map<String, Object>) retObjVo1.getData();
        List<CategoryRetVo> voList = (List<CategoryRetVo>) map.get("list");
        CategoryRetVo categoryRetVo = voList.get(0);
        assertEquals(categoryRetVo.getId(),1L);
        assertEquals(categoryRetVo.getName(),"name");
        assertEquals(categoryRetVo.getCreatedBy().getId(),2L);
        assertEquals(categoryRetVo.getCreatedBy().getName(),"CreateName");
        assertEquals(categoryRetVo.getModifiedBy().getId(),3L);
        assertEquals(categoryRetVo.getModifiedBy().getName(),"ModiName");
        assertEquals(categoryRetVo.getCommissionRate(),null);
        assertEquals(categoryRetVo.getGmtCreate(),gmtCreate);
        assertEquals(categoryRetVo.getGmtModified(),gmtModified);
        assertEquals(map.get("total"),Long.valueOf(1));
        assertEquals(map.get("page"),1);
        assertEquals(map.get("pageSize"),1);
        assertEquals(map.get("pages"),1);

        assertEquals(retObjVo2.getCode(),ReturnNo.OK);
        assertEquals(retObjVo2.getErrmsg(),"成功");
        assertEquals(retObjVo2.getData(),null);

        assertEquals(retObjVo3.getCode(),ReturnNo.RESOURCE_ID_NOTEXIST);
        assertEquals(retObjVo3.getErrmsg(),"操作的资源id不存在");
        assertEquals(retObjVo3.getData(),null);
    }

    @Test
    public void getNullRetObjTest(){
        ReturnObject retObjBo1 = new ReturnObject();
        ReturnObject retObjBo2 = new ReturnObject(ReturnNo.RESOURCE_ID_NOTEXIST);

        ReturnObject retObjVo1 = Common.getNullRetObj(retObjBo1);
        ReturnObject retObjVo2 = Common.getNullRetObj(retObjBo2);

        assertEquals(retObjVo1.getCode(),ReturnNo.OK);
        assertEquals(retObjVo1.getErrmsg(),"成功");
        assertEquals(retObjVo1.getData(),null);
        assertEquals(retObjVo2.getCode(),ReturnNo.RESOURCE_ID_NOTEXIST);
        assertEquals(retObjVo2.getErrmsg(),"操作的资源id不存在");
        assertEquals(retObjVo2.getData(),null);
    }

}
