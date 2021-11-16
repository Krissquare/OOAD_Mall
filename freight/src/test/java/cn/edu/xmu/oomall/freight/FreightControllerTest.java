package cn.edu.xmu.oomall.freight;


import cn.edu.xmu.oomall.core.util.JacksonUtil;
import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.freight.microservice.ProductService;
import cn.edu.xmu.oomall.freight.model.vo.FreightModelInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;

@RunWith(SpringJUnit4ClassRunner.class) //指定测试用例的运行器 这里是指定了Junit4
@SpringBootTest(classes = FreightApplication.class)
@Transactional      //防止脏数据
@AutoConfigureMockMvc      //自动初始化MockMvc
public class FreightControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean(name = "cn.edu.xmu.oomall.freight.microservice.ProductService")
    private ProductService productService;


    @Autowired
    ResourceLoader resourceLoader;

    /**
     * 管理员定义运费模板
     * @throws Exception 异常信息
     */
    @Test
    public void addFreightModel()throws Exception{
        //以下是正常情况返回的
        FreightModelInfo freightModelInfo = new FreightModelInfo("test",100,(byte)0);
        String json = JacksonUtil.toJson(freightModelInfo);
        String responseString;
        responseString = this.mockMvc.perform(MockMvcRequestBuilders.post("/shops/0/freightmodels")
                .contentType("application/json;charset=UTF-8").content(json))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        String expectedString = "{\n" +
                "\"errno\": 0,\n" +
                "\"data\":{\n" +
                "\"name\": \"test\",\n" +
                "\"defaultModel\": 0,\n" +
                "\"type\": 0,\n" +
                "\"unit\": 100,\n" +
                "\"createBy\":{\n" +
                "\"id\": 1,\n" +
                "\"name\": \"test\"\n" +
                "},\n" +
                "\"modifiedBy\":{\n" +
                "\"id\": null,\n" +
                "\"name\": null\n" +
                "},\n" +
                "\"gmtModified\": null\n" +
                "},\n" +
                "\"errmsg\": \"成功\"\n" +
                "}";
        JSONAssert.assertEquals(expectedString,responseString,false);

        //非管理员返回错误
        String responseString1;
        responseString1 = this.mockMvc.perform(MockMvcRequestBuilders.post("/shops/1/freightmodels")
                .contentType("application/json;charset=UTF-8").content(json))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        String expectedString1 = "{\n" +
                "\"errno\": 503,\n" +
                "\"errmsg\": \"字段不合法\"\n" +
                "}";
        JSONAssert.assertEquals(expectedString1,responseString1,false);
    }

    /**
     * 获得商品的运费模板
     * @throws Exception 异常信息
     */
    @Test
    public void showFreightModel()throws Exception{
        //以下是正常情况返回的
        String responseString;
        responseString = this.mockMvc.perform(MockMvcRequestBuilders.get("/shops/0/freightmodels")
                .param("name","freight model/100g").param("page","1").param("pageSize","10")
                .contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        String expectedString = "{\n" +
                "\"code\": \"OK\",\n" +
                "\"errmsg\": \"成功\",\n" +
                "\"data\":{\n" +
                "\"total\": 1,\n" +
                "\"pages\": 1,\n" +
                "\"pageSize\": 1,\n" +
                "\"page\": 1,\n" +
                "\"list\":[\n" +
                "{\n" +
                "\"id\": 1,\n" +
                "\"name\": \"freight model/100g\",\n" +
                "\"defaultModel\": 0,\n" +
                "\"type\": 0,\n" +
                "\"unit\": 100,\n" +
                "\"createBy\":{\n" +
                "\"id\": 1,\n" +
                "\"name\": \"admin\"\n" +
                "},\n" +
                "\"modifiedBy\":{\n" +
                "\"id\": null,\n" +
                "\"name\": null\n" +
                "}\n" +
                "}\n" +
                "]\n" +
                "}\n" +
                "}";
        JSONAssert.assertEquals(expectedString,responseString,false);

        //非管理员返回错误
        String responseString1;
        responseString1 = this.mockMvc.perform(MockMvcRequestBuilders.get("/shops/1/freightmodels")
                .contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        String expectedString1 = "{\n" +
                "\"errno\": 503,\n" +
                "\"errmsg\": \"字段不合法\"\n" +
                "}";
        JSONAssert.assertEquals(expectedString1,responseString1,false);
    }

    /**
     * 获得商品的运费模板 带货品
     * @throws Exception 异常信息
     */
    @Test
    public void showFreightModelWithProductId()throws Exception{
        //以下是正常情况返回的
        Mockito.when(productService.getFreightIdByProductId(1L)).thenReturn( new ReturnObject<>(1L));
        String responseString;
        responseString = this.mockMvc.perform(MockMvcRequestBuilders.get("/shops/0/products/1/freightmodels")
                .param("name","freight model/100g").param("page","1").param("pageSize","10")
                .contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        String expectedString = "{\n" +
                "\"code\": \"OK\",\n" +
                "\"errmsg\": \"成功\",\n" +
                "\"data\":{\n" +
                "\"total\": 0,\n" +
                "\"pages\": 0,\n" +
                "\"pageSize\": 0,\n" +
                "\"page\": 1,\n" +
                "\"list\":[\n" +
                "]\n" +
                "}\n" +
                "}";
        JSONAssert.assertEquals(expectedString,responseString,false);

        //非管理员返回错误
        String responseString1;
        responseString1 = this.mockMvc.perform(MockMvcRequestBuilders.get("/shops/1/products/1/freightmodels")
                .contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        String expectedString1 = "{\n" +
                "\"errno\": 503,\n" +
                "\"errmsg\": \"字段不合法\"\n" +
                "}";
        JSONAssert.assertEquals(expectedString1,responseString1,false);
    }

    /**
     * 管理员克隆运费模板
     * @throws Exception 异常信息
     */
    @Test
    public void cloneFreightModel()throws Exception{
        //以下是正常情况返回的
        String responseString;
        responseString = this.mockMvc.perform(MockMvcRequestBuilders.post("/shops/0/freightmodels/1/clone")
                .contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        String expectedString = "{\n" +
                "\"errno\": 0,\n" +
                "\"data\":{\n" +
                "\"name\": \"freight model/100g\",\n" +
                "\"defaultModel\": 0,\n" +
                "\"type\": 0,\n" +
                "\"unit\": 100,\n" +
                "\"createBy\":{\n" +
                "\"id\": 1,\n" +
                "\"name\": \"test\"\n" +
                "},\n" +
                "\"modifiedBy\":{\n" +
                "\"id\": null,\n" +
                "\"name\": null\n" +
                "}\n" +
                "},\n" +
                "\"errmsg\": \"成功\"\n" +
                "}";
        JSONAssert.assertEquals(expectedString,responseString,false);

        //非管理员返回错误
        String responseString1;
        responseString1 = this.mockMvc.perform(MockMvcRequestBuilders.post("/shops/1/freightmodels/1/clone")
                .contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        String expectedString1 = "{\n" +
                "\"errno\": 503,\n" +
                "\"errmsg\": \"字段不合法\"\n" +
                "}";
        JSONAssert.assertEquals(expectedString1,responseString1,false);

        //操作的资源id不存在
        String responseString2;
        responseString2 = this.mockMvc.perform(MockMvcRequestBuilders.post("/shops/0/freightmodels/111/clone")
                .contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        String expectedString2 = "{\n" +
                "\"errno\": 504,\n" +
                "\"errmsg\": \"操作的资源id不存在\"\n" +
                "}";
        JSONAssert.assertEquals(expectedString2,responseString2,false);
    }

    /**
     * 获得运费模板详情
     * @throws Exception 异常信息
     */
    @Test
    public void showFreightModelById()throws Exception{
        //以下是正常情况返回的
        Mockito.when(productService.getFreightIdByProductId(1L)).thenReturn( new ReturnObject<>(1L));
        String responseString;
        responseString = this.mockMvc.perform(MockMvcRequestBuilders.get("/shops/0/freightmodels/1")
                .contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        String expectedString = "{\n" +
                "\"errno\": 0,\n" +
                "\"data\":{\n" +
                "\"id\": 1,\n" +
                "\"name\": \"freight model/100g\",\n" +
                "\"defaultModel\": 0,\n" +
                "\"type\": 0,\n" +
                "\"unit\": 100,\n" +
                "\"createBy\":{\n" +
                "\"id\": 1,\n" +
                "\"name\": \"admin\"\n" +
                "},\n" +
                "\"modifiedBy\":{\n" +
                "\"id\": null,\n" +
                "\"name\": null\n" +
                "}\n" +
                "},\n" +
                "\"errmsg\": \"成功\"\n" +
                "}";
        JSONAssert.assertEquals(expectedString,responseString,false);

        //非管理员返回错误
        String responseString1;
        responseString1 = this.mockMvc.perform(MockMvcRequestBuilders.get("/shops/1/freightmodels/1")
                .contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        String expectedString1 = "{\n" +
                "\"errno\": 503,\n" +
                "\"errmsg\": \"字段不合法\"\n" +
                "}";
        JSONAssert.assertEquals(expectedString1,responseString1,false);

        //操作的资源id不存在
        String responseString2;
        responseString2 = this.mockMvc.perform(MockMvcRequestBuilders.get("/shops/0/freightmodels/111")
                .contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        String expectedString2 = "{\n" +
                "\"errno\": 504,\n" +
                "\"errmsg\": \"操作的资源id不存在\"\n" +
                "}";
        JSONAssert.assertEquals(expectedString2,responseString2,false);
    }

    /**
     * 管理员修改运费模板
     * @throws Exception 异常信息
     */
    @Test
    public void updateFreightModel()throws Exception{
        //以下是正常情况返回的
        FreightModelInfo freightModelInfo = new FreightModelInfo("test",100,(byte)0);
        String json = JacksonUtil.toJson(freightModelInfo);
        String responseString;
        responseString = this.mockMvc.perform(MockMvcRequestBuilders.put("/shops/0/freightmodels/1")
                .contentType("application/json;charset=UTF-8").content(json))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        String expectedString = "{\n" +
                "\"errno\": 0,\n" +
                "\"errmsg\": \"成功\"\n" +
                "}";
        JSONAssert.assertEquals(expectedString,responseString,false);

        //非管理员返回错误
        String responseString1;
        responseString1 = this.mockMvc.perform(MockMvcRequestBuilders.put("/shops/1/freightmodels/1")
                .contentType("application/json;charset=UTF-8").content(json))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        String expectedString1 = "{\n" +
                "\"errno\": 503,\n" +
                "\"errmsg\": \"字段不合法\"\n" +
                "}";
        JSONAssert.assertEquals(expectedString1,responseString1,false);

        //操作的资源id不存在
        String responseString2;
        responseString2 = this.mockMvc.perform(MockMvcRequestBuilders.put("/shops/0/freightmodels/1111")
                .contentType("application/json;charset=UTF-8").content(json))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        String expectedString2 = "{\n" +
                "\"errno\": 504,\n" +
                "\"errmsg\": \"操作的资源id不存在\"\n" +
                "}";
        JSONAssert.assertEquals(expectedString2,responseString2,false);
    }

    /**
     * 管理员删除运费模板
     * @throws Exception 异常信息
     */
    @Test
    public void deleteFreightModel()throws Exception{
        //以下是正常情况返回的
        Mockito.when(productService.isToBeDelete(1L)).thenReturn( new ReturnObject<>(true));
        //以下是正常情况返回的
        Mockito.when(productService.deleteRelationshipWithFreightModel(1L)).thenReturn( new ReturnObject<>(ReturnNo.OK));
        //以下是正常情况返回的
        String responseString;
        responseString = this.mockMvc.perform(MockMvcRequestBuilders.delete("/shops/0/freightmodels/1")
                .contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        String expectedString = "{\n" +
                "\"errno\": 0,\n" +
                "\"errmsg\": \"成功\"\n" +
                "}";
        JSONAssert.assertEquals(expectedString,responseString,false);

        //非管理员返回错误
        String responseString1;
        responseString1 = this.mockMvc.perform(MockMvcRequestBuilders.delete("/shops/1/freightmodels/1")
                .contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        String expectedString1 = "{\n" +
                "\"errno\": 503,\n" +
                "\"errmsg\": \"字段不合法\"\n" +
                "}";
        JSONAssert.assertEquals(expectedString1,responseString1,false);

    }

    /**
     * 店家或管理员为商铺定义默认运费模板。会将原有的默认运费模板取消
     * @throws Exception 异常信息
     */
    @Test
    public void updateDefaultModel()throws Exception{
        //以下是正常情况返回的;
        String responseString;
        responseString = this.mockMvc.perform(MockMvcRequestBuilders.post("/shops/0/freightmodels/1/default")
                .contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        String expectedString = "{\n" +
                "\"errno\": 0,\n" +
                "\"errmsg\": \"成功\"\n" +
                "}";
        JSONAssert.assertEquals(expectedString,responseString,false);

        //非管理员返回错误
        String responseString1;
        responseString1 = this.mockMvc.perform(MockMvcRequestBuilders.post("/shops/1/freightmodels/1/default")
                .contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        String expectedString1 = "{\n" +
                "\"errno\": 503,\n" +
                "\"errmsg\": \"字段不合法\"\n" +
                "}";
        JSONAssert.assertEquals(expectedString1,responseString1,false);

        //操作的资源id不存在
        String responseString2;
        responseString2 = this.mockMvc.perform(MockMvcRequestBuilders.post("/shops/0/freightmodels/1111/default")
                .contentType("application/json;charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        String expectedString2 = "{\n" +
                "\"errno\": 504,\n" +
                "\"errmsg\": \"操作的资源id不存在\"\n" +
                "}";
        JSONAssert.assertEquals(expectedString2,responseString2,false);
    }



}
