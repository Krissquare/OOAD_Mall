package cn.edu.xmu.oomall.freight.controller;

import cn.edu.xmu.oomall.core.util.JacksonUtil;
import cn.edu.xmu.oomall.freight.FreightApplication;
import cn.edu.xmu.oomall.freight.model.vo.WeightFreightVo;
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

/**
 * @author ziyi guo
 * @date 2021/11/17
 */
@SpringBootTest(classes = FreightApplication.class)
@AutoConfigureMockMvc
@Transactional
public class WeightFreightControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @Transactional
    public void addWeightItemsTest1() throws Exception {
        WeightFreightVo vo = new WeightFreightVo();
        vo.setFirstWeight(500);
        vo.setFirstWeightFreight(1000L);
        vo.setTenPrice(100L);
        vo.setFiftyPrice(50L);
        vo.setHundredPrice(10L);
        vo.setTrihunPrice(5L);
        vo.setAbovePrice(0L);
        vo.setRegionId(14L);

        String responseString = this.mvc.perform(post("/freight/shops/0/freightmodels/1/weightItems").contentType("application/json;charset=UTF-8").content(JacksonUtil.toJson(vo)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectedResponse = "{\"errno\":0,\"data\":{\"freightModelId\":1,\"firstWeight\":500,\"firstWeightFreight\":1000,\"tenPrice\":100,\"fiftyPrice\":50,\"hundredPrice\":10,\"trihunPrice\":5,\"abovePrice\":0,\"regionId\":14,\"gmtModified\":null,\"createdBy\":{\"id\":1,\"name\":\"admin\"},\"modifiedBy\":{\"id\":null,\"name\":null}},\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, false);
    }

    @Test
    @Transactional
    public void addWeightItemsTest2() throws Exception {
        WeightFreightVo vo = new WeightFreightVo();
        vo.setFirstWeight(500);
        vo.setFirstWeightFreight(1000L);
        vo.setTenPrice(100L);
        vo.setFiftyPrice(50L);
        vo.setHundredPrice(10L);
        vo.setTrihunPrice(5L);
        vo.setAbovePrice(0L);
        vo.setRegionId(151L);

        String responseString = this.mvc.perform(post("/freight/shops/0/freightmodels/1/weightItems").contentType("application/json;charset=UTF-8").content(JacksonUtil.toJson(vo)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectedResponse = "{\"errno\":997,\"errmsg\":\"该运费模板中该地区已经定义\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }

    @Test
    @Transactional
    public void addWeightItemsTest3() throws Exception {
        WeightFreightVo vo = new WeightFreightVo();
        vo.setFirstWeight(500);
        vo.setFirstWeightFreight(1000L);
        vo.setTenPrice(100L);
        vo.setFiftyPrice(50L);
        vo.setHundredPrice(10L);
        vo.setTrihunPrice(5L);
        vo.setAbovePrice(0L);
        vo.setRegionId(14L);

        String responseString = this.mvc.perform(post("/freight/shops/0/freightmodels/0/weightItems").contentType("application/json;charset=UTF-8").content(JacksonUtil.toJson(vo)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectedResponse = "{\"errno\":504,\"errmsg\":\"操作的资源id不存在\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }

    @Test
    @Transactional
    public void addWeightItemsTest4() throws Exception {
        WeightFreightVo vo = new WeightFreightVo();
        vo.setFirstWeight(500);
        vo.setFirstWeightFreight(1000L);
        vo.setTenPrice(100L);
        vo.setFiftyPrice(50L);
        vo.setHundredPrice(10L);
        vo.setTrihunPrice(5L);
        vo.setAbovePrice(0L);
        vo.setRegionId(14L);

        String responseString = this.mvc.perform(post("/freight/shops/0/freightmodels/2/weightItems").contentType("application/json;charset=UTF-8").content(JacksonUtil.toJson(vo)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":996,\"errmsg\":\"该运费模板类型与内容不符\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }

    @Test
    @Transactional
    public void addWeightItemsTest5() throws Exception {
        WeightFreightVo vo = new WeightFreightVo();
        vo.setFirstWeight(500);
        vo.setFirstWeightFreight(1000L);
        vo.setTenPrice(100L);
        vo.setFiftyPrice(50L);
        vo.setHundredPrice(10L);
        vo.setTrihunPrice(5L);
        vo.setAbovePrice(0L);
        vo.setRegionId(14L);

        String responseString = this.mvc.perform(post("/freight/shops/1/freightmodels/2/weightItems").contentType("application/json;charset=UTF-8").content(JacksonUtil.toJson(vo)))
                .andExpect(status().isForbidden())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":505,\"errmsg\":\"非管理员无权操作\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }

    @Test
    @Transactional
    public void getWeightItemsTest1() throws Exception {
        String responseString = this.mvc.perform(get("/freight/shops/0/freightmodels/1/weightItems"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectedResponse = "{\"errno\":0,\"data\":{\"total\":2,\"pages\":1,\"pageSize\":2,\"page\":1,\"list\":[{\"id\":1,\"freightModelId\":1,\"firstWeight\":500,\"firstWeightFreight\":1000,\"tenPrice\":100,\"fiftyPrice\":50,\"hundredPrice\":10,\"trihunPrice\":5,\"abovePrice\":0,\"regionId\":151,\"gmtCreate\":\"2021-11-11 14:18:26.000\",\"gmtModified\":null,\"createdBy\":{\"id\":1,\"name\":\"admin\"},\"modifiedBy\":{\"id\":null,\"name\":null}},{\"id\":2,\"freightModelId\":1,\"firstWeight\":500,\"firstWeightFreight\":1000,\"tenPrice\":100,\"fiftyPrice\":50,\"hundredPrice\":10,\"trihunPrice\":5,\"abovePrice\":0,\"regionId\":152,\"gmtCreate\":\"2021-11-17 09:18:26.000\",\"gmtModified\":null,\"createdBy\":{\"id\":1,\"name\":\"admin\"},\"modifiedBy\":{\"id\":null,\"name\":null}}]},\"errmsg\":\"成功\"}" ;
        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }

    @Test
    @Transactional
    public void getWeightItemsTest2() throws Exception {
        String responseString = this.mvc.perform(get("/freight/shops/0/freightmodels/0/weightItems"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectedResponse = "{\"errno\":504,\"errmsg\":\"操作的资源id不存在\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }

    @Test
    @Transactional
    public void updateWeightItemsTest1() throws Exception {
        WeightFreightVo vo = new WeightFreightVo();
        vo.setFirstWeight(400);
        vo.setFirstWeightFreight(1000L);
        vo.setTenPrice(100L);
        vo.setFiftyPrice(50L);
        vo.setHundredPrice(10L);
        vo.setTrihunPrice(5L);
        vo.setAbovePrice(0L);
        vo.setRegionId(151L);

        String responseString = this.mvc.perform(put("/freight/shops/0/weightItems/1").contentType("application/json;charset=UTF-8").content(JacksonUtil.toJson(vo)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }

    @Test
    @Transactional
    public void updateWeightItemsTest2() throws Exception {
        WeightFreightVo vo = new WeightFreightVo();
        vo.setFirstWeight(400);
        vo.setFirstWeightFreight(1000L);
        vo.setTenPrice(100L);
        vo.setFiftyPrice(50L);
        vo.setHundredPrice(10L);
        vo.setTrihunPrice(5L);
        vo.setAbovePrice(0L);
        vo.setRegionId(151L);

        String responseString = this.mvc.perform(put("/freight/shops/0/weightItems/0").contentType("application/json;charset=UTF-8").content(JacksonUtil.toJson(vo)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectedResponse = "{\"errno\":504,\"errmsg\":\"操作的资源id不存在\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }

    @Test
    @Transactional
    public void updateWeightItemsTest3() throws Exception {
        WeightFreightVo vo = new WeightFreightVo();
        vo.setFirstWeight(500);
        vo.setFirstWeightFreight(1000L);
        vo.setTenPrice(100L);
        vo.setFiftyPrice(50L);
        vo.setHundredPrice(10L);
        vo.setTrihunPrice(5L);
        vo.setAbovePrice(0L);
        vo.setRegionId(152L);

        String responseString = this.mvc.perform(put("/freight/shops/0/weightItems/1").contentType("application/json;charset=UTF-8").content(JacksonUtil.toJson(vo)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectedResponse = "{\"errno\":999,\"errmsg\":\"运费模板中该地区已经定义\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }

    @Test
    @Transactional
    public void updateWeightItemsTest4() throws Exception {
        WeightFreightVo vo = new WeightFreightVo();
        vo.setFirstWeight(500);
        vo.setFirstWeightFreight(1000L);
        vo.setTenPrice(100L);
        vo.setFiftyPrice(50L);
        vo.setHundredPrice(10L);
        vo.setTrihunPrice(5L);
        vo.setAbovePrice(0L);
        vo.setRegionId(152L);

        String responseString = this.mvc.perform(put("/freight/shops/1/weightItems/1").contentType("application/json;charset=UTF-8").content(JacksonUtil.toJson(vo)))
                .andExpect(status().isForbidden())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectedResponse = "{\"errno\":505,\"errmsg\":\"非管理员无权操作\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }

    @Test
    @Transactional
    public void deleteWeightItemsTest1() throws Exception {
        String responseString = this.mvc.perform(delete("/freight/shops/0/weightItems/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }

    @Test
    @Transactional
    public void deleteWeightItemsTest2() throws Exception {
        String responseString = this.mvc.perform(delete("/freight/shops/0/weightItems/0"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectedResponse = "{\"errno\":504,\"errmsg\":\"操作的资源id不存在\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }

    @Test
    @Transactional
    public void deleteWeightItemsTest3() throws Exception {
        String responseString = this.mvc.perform(delete("/freight/shops/1/weightItems/0"))
                .andExpect(status().isForbidden())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectedResponse = "{\"errno\":505,\"errmsg\":\"非管理员无权操作\"}";
        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }
}
