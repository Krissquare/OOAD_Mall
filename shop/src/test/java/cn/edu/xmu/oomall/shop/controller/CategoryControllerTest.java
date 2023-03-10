package cn.edu.xmu.oomall.shop.controller;

import cn.edu.xmu.oomall.shop.ShopApplication;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 商品分类测试类
 *
 * @author Zhiliang Li
 * @date 2021/11/14
 */
@SpringBootTest(classes = ShopApplication.class)
@AutoConfigureMockMvc
public class CategoryControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    @Transactional
    public void addCategory() throws Exception {
        // 命名重复
        String requestJson = "{\"name\": \"女装男装\"}";
        String responseString = this.mvc.perform(post("/shops/0/categories/0/subcategories").contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        assertEquals("{\"errno\":901,\"errmsg\":\"类目名称已存在\"}", responseString);


        // 找不到
        requestJson = "{\"name\": \"女装男装\"}";
        responseString = this.mvc.perform(post("/shops/0/categories/500/subcategories").contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        assertEquals("{\"errno\":504,\"errmsg\":\"操作的资源id不存在\"}", responseString);

        // 成功插入，二级目录
        requestJson = "{\"name\": \"童装a\"}";
        responseString = this.mvc.perform(post("/shops/0/categories/1/subcategories").contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        JSONAssert.assertEquals("{\"errno\":0,\"data\":{\"commissionRatio\":null,\"name\":\"童装a\",\"modifiedBy\":{\"id\":null,\"name\":null},\"gmtModified\":null},\"errmsg\":\"成功\"}", responseString, false);

        // 成功插入，一级目录
        requestJson = "{\"name\": \"军械\"}";
        responseString = this.mvc.perform(post("/shops/0/categories/0/subcategories").contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        JSONAssert.assertEquals("{\"errno\":0,\"data\":{\"commissionRatio\":null,\"name\":\"军械\",\"modifiedBy\":{\"id\":null,\"name\":null},\"gmtModified\":null},\"errmsg\":\"成功\"}", responseString, false);

        // 不能插入成为三级目录
        requestJson = "{\"name\": \"手机游戏\"}";
        responseString = this.mvc.perform(post("/shops/0/categories/277/subcategories").contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        assertEquals("{\"errno\":967,\"errmsg\":\"不允许增加新的下级分类\"}", responseString);

        // 传参错误
        requestJson = "{\"name\": \"\"}";
        responseString = this.mvc.perform(post("/shops/0/categories/277/subcategories").contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        assertEquals("{\"errno\":503,\"errmsg\":\"分类名不能为空;\"}", responseString);

        // 服务器错误
        requestJson = "{\"name\":\"conditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionitionconditionconditionitionconditionconditionitionconditionconditionitionconditionconditionitionconditionconditionitionconditionconditionitionconditionconditionitionconditionconditionitionconditionconditionitionconditionconditionitionconditioncondition\"}";
        responseString = this.mvc.perform(post("/shops/0/categories/0/subcategories").contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        JSONAssert.assertEquals("{\"errno\":500}", responseString, false);

        // id小于0
        requestJson = "{\"name\": \"t1\"}";
        responseString = this.mvc.perform(post("/shops/0/categories/-1/subcategories").contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        assertEquals("{\"errno\":504,\"errmsg\":\"操作的资源id不存在\"}", responseString);
    }

    @Test
    @Transactional
    public void getCategory() throws Exception {
        String responseString;

        // 有子分类
        responseString = this.mvc.perform(get("/categories/1/subcategories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        JSONAssert.assertEquals("{\"errno\":0,\"data\":[{\"id\":186,\"commissionRatio\":6,\"name\":\"女式上装\",\"createdBy\":{\"id\":1,\"name\":\"admin\"},\"modifiedBy\":{\"id\":null,\"name\":null},\"gmtCreate\":\"2021-11-11T12:21:18\",\"gmtModified\":null},{\"id\":187,\"commissionRatio\":1,\"name\":\"女式裤子\",\"createdBy\":{\"id\":1,\"name\":\"admin\"},\"modifiedBy\":{\"id\":null,\"name\":null},\"gmtCreate\":\"2021-11-11T12:21:18\",\"gmtModified\":null},{\"id\":188,\"commissionRatio\":2,\"name\":\"女式裙子\",\"createdBy\":{\"id\":1,\"name\":\"admin\"},\"modifiedBy\":{\"id\":null,\"name\":null},\"gmtCreate\":\"2021-11-11T12:21:18\",\"gmtModified\":null},{\"id\":189,\"commissionRatio\":7,\"name\":\"其他女装\",\"createdBy\":{\"id\":1,\"name\":\"admin\"},\"modifiedBy\":{\"id\":null,\"name\":null},\"gmtCreate\":\"2021-11-11T12:21:18\",\"gmtModified\":null},{\"id\":190,\"commissionRatio\":12,\"name\":\"男式上装\",\"createdBy\":{\"id\":1,\"name\":\"admin\"},\"modifiedBy\":{\"id\":null,\"name\":null},\"gmtCreate\":\"2021-11-11T12:21:18\",\"gmtModified\":null},{\"id\":191,\"commissionRatio\":7,\"name\":\"男式裤子\",\"createdBy\":{\"id\":1,\"name\":\"admin\"},\"modifiedBy\":{\"id\":null,\"name\":null},\"gmtCreate\":\"2021-11-11T12:21:18\",\"gmtModified\":null},{\"id\":192,\"commissionRatio\":15,\"name\":\"其他男装\",\"createdBy\":{\"id\":1,\"name\":\"admin\"},\"modifiedBy\":{\"id\":null,\"name\":null},\"gmtCreate\":\"2021-11-11T12:21:18\",\"gmtModified\":null}],\"errmsg\":\"成功\"}", responseString, false);

        // 找不到
        responseString = this.mvc.perform(get("/categories/500/subcategories"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        assertEquals("{\"errno\":504,\"errmsg\":\"操作的资源id不存在\"}", responseString);

        // 无子分类
        responseString = this.mvc.perform(get("/categories/277/subcategories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        assertEquals("{\"errno\":0,\"data\":[],\"errmsg\":\"成功\"}", responseString);

        // 尝试查所有一级分类
        responseString = this.mvc.perform(get("/categories/0/subcategories"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        assertEquals("{\"errno\":504,\"errmsg\":\"操作的资源id不存在\"}", responseString);

        // 查所有单独分类
        responseString = this.mvc.perform(get("/orphoncategories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        assertEquals("{\"errno\":0,\"data\":[],\"errmsg\":\"成功\"}", responseString);
    }

    @Test
    @Transactional
    public void modifyCategory() throws Exception {
        // 可以修改
        String requestJson = "{\"name\": \"test\"}";
        String responseString = this.mvc.perform(put("/shops/0/categories/313").contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        assertEquals("{\"errno\":0,\"errmsg\":\"成功\"}", responseString);

        // 重名
        requestJson = "{\"name\": \"童装\"}";
        responseString = this.mvc.perform(put("/shops/0/categories/313").contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        assertEquals("{\"errno\":901,\"errmsg\":\"类目名称已存在\"}", responseString);

        // 找不到资源
        responseString = this.mvc.perform(put("/shops/0/categories/500").contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        assertEquals("{\"errno\":504,\"errmsg\":\"操作的资源id不存在\"}", responseString);

        // 传参错误
        requestJson = "{\"name\": \"\"}";
        responseString = this.mvc.perform(put("/shops/0/categories/313").contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        assertEquals("{\"errno\":503,\"errmsg\":\"分类名不能为空;\"}", responseString);

        // 服务器错误
        requestJson = "{\"name\":\"conditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditioncondition\"}";
        responseString = this.mvc.perform(put("/shops/0/categories/313").contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        JSONAssert.assertEquals("{\"errno\":500}", responseString, false);

    }

    @Test
    @Transactional
    public void deleteCategory() throws Exception {
        // 删有子类别的
        String responseString = this.mvc.perform(delete("/shops/0/categories/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        assertEquals("{\"errno\":0,\"errmsg\":\"成功\"}", responseString);

        // 删单独的
        responseString = this.mvc.perform(delete("/shops/0/categories/277"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        assertEquals("{\"errno\":0,\"errmsg\":\"成功\"}", responseString);

        // 找不到
        responseString = this.mvc.perform(delete("/shops/0/categories/500"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        assertEquals("{\"errno\":504,\"errmsg\":\"操作的资源id不存在\"}", responseString);

        // 删为0或-1的
        responseString = this.mvc.perform(delete("/shops/0/categories/0"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        assertEquals("{\"errno\":504,\"errmsg\":\"操作的资源id不存在\"}", responseString);

    }
}
