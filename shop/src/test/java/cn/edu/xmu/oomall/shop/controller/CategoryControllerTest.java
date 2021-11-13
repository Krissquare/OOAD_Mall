package cn.edu.xmu.oomall.shop.controller;

import cn.edu.xmu.oomall.shop.ShopApplication;
import org.junit.jupiter.api.Test;
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
 * @date 2021/11/12
 */
@SpringBootTest(classes = ShopApplication.class)
@AutoConfigureMockMvc
@Transactional
public class CategoryControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void addCategory() throws Exception {
        // 命名重复
        String requestJson = "{\"name\": \"女装男装\"}";
        String responseString = this.mvc.perform(post("/shops/0/categories/0/subcategories").contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        assertEquals("", responseString);


        // 找不到
        requestJson = "{\"name\": \"女装男装\"}";
        responseString = this.mvc.perform(post("/shops/0/categories/500/subcategories").contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        assertEquals("", responseString);

        // 成功插入，二级目录
        requestJson = "{\"name\": \"童装a\"}";
        responseString = this.mvc.perform(post("/shops/0/categories/1/subcategories").contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        assertEquals("", responseString);

        // 成功插入，一级目录
        requestJson = "{\"name\": \"军械\"}";
        responseString = this.mvc.perform(post("/shops/0/categories/0/subcategories").contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        assertEquals("", responseString);

        // 不能插入成为三级目录
        requestJson = "{\"name\": \"手机游戏\"}";
        responseString = this.mvc.perform(post("/shops/0/categories/277/subcategories").contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        assertEquals("", responseString);

        // 传参错误
        requestJson = "{\"name\": \"\"}";
        responseString = this.mvc.perform(post("/shops/0/categories/277/subcategories").contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        assertEquals("", responseString);

        // 服务器错误
        requestJson = "{\"name\":\"conditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionitionconditionconditionitionconditionconditionitionconditionconditionitionconditionconditionitionconditionconditionitionconditionconditionitionconditionconditionitionconditionconditionitionconditionconditionitionconditionconditionitionconditioncondition\"}";
        responseString = this.mvc.perform(post("/shops/0/categories/0/subcategories").contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        assertEquals("", responseString);

        // id小于0
        requestJson = "{\"name\": \"t1\"}";
        responseString = this.mvc.perform(post("/shops/0/categories/-1/subcategories").contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        assertEquals("", responseString);
    }

    @Test
    public void getCategory() throws Exception {
        String responseString;

        // 有子分类
        responseString = this.mvc.perform(get("/categories/1/subcategories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        assertEquals("", responseString);

        // 找不到
        responseString = this.mvc.perform(get("/categories/500/subcategories"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        assertEquals("", responseString);

        // 无子分类
        responseString = this.mvc.perform(get("/categories/277/subcategories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        assertEquals("", responseString);

        // 尝试查所有一级分类
        responseString = this.mvc.perform(get("/categories/0/subcategories"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        assertEquals("", responseString);

        // 查所有单独分类
        responseString = this.mvc.perform(get("/orphoncategories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        assertEquals("", responseString);
    }

    @Test
    public void modifyCategory() throws Exception {
        // 可以修改
        String requestJson = "{\"name\": \"test\"}";
        String responseString = this.mvc.perform(put("/shops/0/categories/313").contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        assertEquals("", responseString);

        // 重名
        requestJson = "{\"name\": \"童装\"}";
        responseString = this.mvc.perform(put("/shops/0/categories/313").contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        assertEquals("", responseString);

        // 找不到资源
        responseString = this.mvc.perform(put("/shops/0/categories/500").contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        assertEquals("", responseString);

        // 传参错误
        requestJson = "{\"name\": \"\"}";
        responseString = this.mvc.perform(put("/shops/0/categories/313").contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        assertEquals("", responseString);

        // 服务器错误
        requestJson = "{\"name\":\"conditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditionconditioncondition\"}";
        responseString = this.mvc.perform(put("/shops/0/categories/313").contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        assertEquals("", responseString);

    }

    @Test
    public void deleteCategory() throws Exception {
        // 删有子类别的
        String responseString = this.mvc.perform(delete("/shops/0/categories/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        assertEquals("", responseString);

        // 删单独的
        responseString = this.mvc.perform(delete("/shops/0/categories/277"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        assertEquals("", responseString);

        // 找不到
        responseString = this.mvc.perform(delete("/shops/0/categories/500"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        assertEquals("", responseString);

        // 删为0或-1的
        responseString = this.mvc.perform(delete("/shops/0/categories/0"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        assertEquals("", responseString);

    }
}
