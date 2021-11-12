package cn.edu.xmu.oomall.freight;

import cn.edu.xmu.oomall.core.util.JacksonUtil;
import cn.edu.xmu.oomall.goods.model.vo.RegionVo;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author ziyi guo
 * @date 2021/11/11
 */
@SpringBootTest(classes = DemoApplication.class)
@AutoConfigureMockMvc
@Transactional
public class RegionControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void getParentRegionTest() throws Exception {
        String responseString = this.mvc.perform(get("/freight/region/4191/ancestor"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":0,\"data\":[{\"id\":1,\"pid\":0,\"name\":\"中国\",\"state\":0,\"gmtCreate\":\"2020-12-15T13:29:49\",\"gmtModified\":null,\"createdBy\":{\"id\":1,\"userName\":\"admin\"},\"modifiedBy\":{\"id\":null,\"userName\":null}},{\"id\":14,\"pid\":1,\"name\":\"福建省\",\"state\":0,\"gmtCreate\":\"2020-12-15T13:29:49\",\"gmtModified\":null,\"createdBy\":{\"id\":1,\"userName\":\"admin\"},\"modifiedBy\":{\"id\":null,\"userName\":null}},{\"id\":151,\"pid\":14,\"name\":\"厦门市\",\"state\":0,\"gmtCreate\":\"2020-12-15T13:29:49\",\"gmtModified\":null,\"createdBy\":{\"id\":1,\"userName\":\"admin\"},\"modifiedBy\":{\"id\":null,\"userName\":null}}],\"errmsg\":\"成功\"}";

        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }

    @Test   //non-redis
    public void getParentRegionTest1() throws Exception {
        String responseString = this.mvc.perform(get("/freight/region/2/ancestor"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":0,\"data\":[{\"id\":1,\"pid\":0,\"name\":\"中国\",\"state\":0,\"gmtCreate\":\"2020-12-15T13:29:49\",\"gmtModified\":null,\"createdBy\":{\"id\":1,\"userName\":\"admin\"},\"modifiedBy\":{\"id\":null,\"userName\":null}}],\"errmsg\":\"成功\"}";

        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }

    @Test   //non-redis
    public void getParentRegionTest2() throws Exception {
        String responseString = this.mvc.perform(get("/freight/region/1/ancestor"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":504,\"errmsg\":\"操作的资源id不存在\"}";

        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }

    @Test
    public void addRegionTest() throws Exception {
        RegionVo r =  new RegionVo();
        r.setName("test");

        String goodJson = JacksonUtil.toJson(r);

        String responseString = this.mvc.perform(post("/freight/shops/0/regions/4191/subregions").contentType("application/json;charset=UTF-8").content(goodJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";

        JSONAssert.assertEquals(expectedResponse, responseString, true);

    }

    @Test
    public void addRegionTest1() throws Exception {
        String responseString = this.mvc.perform(put("/freight/shops/0/regions/1/suspend"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";

        JSONAssert.assertEquals(expectedResponse, responseString, true);

        responseString = this.mvc.perform(delete("/freight/shops/0/regions/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";

        JSONAssert.assertEquals(expectedResponse, responseString, false);

        RegionVo r =  new RegionVo();
        r.setName("test");

        String goodJson = JacksonUtil.toJson(r);

        responseString = this.mvc.perform(post("/freight/shops/0/regions/1/subregions").contentType("application/json;charset=UTF-8").content(goodJson))
                .andExpect(status().isNotAcceptable())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        expectedResponse = "{\"errno\":995,\"errmsg\":\"地区已废弃\"}";

        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }

    @Test
    public void modifyRegionTest() throws Exception {
        RegionVo r =  new RegionVo();
        r.setName("test");

        String goodJson = JacksonUtil.toJson(r);

        String responseString = this.mvc.perform(put("/freight/shops/0/regions/4191").contentType("application/json;charset=UTF-8").content(goodJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";

        JSONAssert.assertEquals(expectedResponse, responseString, true);

    }

    @Test   //标识此方法为测试方法
    public void abandonRegionTest() throws Exception {
        String responseString = this.mvc.perform(put("/freight/shops/0/regions/2/suspend"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";

        JSONAssert.assertEquals(expectedResponse, responseString, true);

        responseString = this.mvc.perform(delete("/freight/shops/0/regions/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";

        JSONAssert.assertEquals(expectedResponse, responseString, false);
    }

    @Test   //标识此方法为测试方法
    public void abandonRegionTest1() throws Exception {
        String responseString = this.mvc.perform(delete("/freight/shops/0/regions/3"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":507,\"errmsg\":\"当前状态禁止此操作\"}";

        JSONAssert.assertEquals(expectedResponse, responseString, false);
    }

    @Test   //标识此方法为测试方法
    public void suspendRegionTest() throws Exception {
        String responseString = this.mvc.perform(put("/freight/shops/0/regions/4/suspend"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";

        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }

    @Test   //标识此方法为测试方法
    public void suspendRegionTest1() throws Exception {
        String responseString = this.mvc.perform(put("/freight/shops/0/regions/5/suspend"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";

        JSONAssert.assertEquals(expectedResponse, responseString, true);

        responseString = this.mvc.perform(delete("/freight/shops/0/regions/5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";

        JSONAssert.assertEquals(expectedResponse, responseString, false);

        responseString = this.mvc.perform(put("/freight/shops/0/regions/5/suspend"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        expectedResponse = "{\"errno\":507,\"errmsg\":\"当前状态禁止此操作\"}";

        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }

    @Test   //标识此方法为测试方法
    public void resumeRegionTest() throws Exception {
        String responseString = this.mvc.perform(put("/freight/shops/0/regions/6/resume"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";

        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }

    @Test   //标识此方法为测试方法
    public void resumeRegionTest1() throws Exception {
        String responseString = this.mvc.perform(put("/freight/shops/0/regions/7/suspend"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";

        JSONAssert.assertEquals(expectedResponse, responseString, true);

        responseString = this.mvc.perform(delete("/freight/shops/0/regions/7"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";

        JSONAssert.assertEquals(expectedResponse, responseString, false);

        responseString = this.mvc.perform(put("/freight/shops/0/regions/7/resume"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        expectedResponse = "{\"errno\":507,\"errmsg\":\"当前状态禁止此操作\"}";

        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }

}
