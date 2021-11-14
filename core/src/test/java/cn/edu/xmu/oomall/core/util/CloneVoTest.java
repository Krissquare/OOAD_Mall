package cn.edu.xmu.oomall.core.util;



import cn.edu.xmu.oomall.core.util.bo.Category;
import cn.edu.xmu.oomall.core.util.vo.CategoryRetVo;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CloneVoTest {

    /**
     * @author xucangbai
     * @date 2021/11/13
     */
    @Test
    void test() {
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


        CategoryRetVo categoryRetVo = (CategoryRetVo) Common.cloneVo(categoryBo, CategoryRetVo.class);
        assertEquals(categoryRetVo.getId(),1L);
        assertEquals(categoryRetVo.getName(),"name");

        assertEquals(categoryRetVo.getCreatedBy().getId(),2L);
        assertEquals(categoryRetVo.getCreatedBy().getName(),"CreateName");
        assertEquals(categoryRetVo.getModifiedBy().getId(),3L);
        assertEquals(categoryRetVo.getModifiedBy().getName(),"ModiName");
        assertEquals(categoryRetVo.getCommissionRate(),null);
        assertEquals(categoryRetVo.getGmtCreate(),gmtCreate);
        assertEquals(categoryRetVo.getGmtModified(),gmtModified);
    }
}