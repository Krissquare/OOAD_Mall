package cn.edu.xmu.oomall.shop.controller;

import cn.edu.xmu.oomall.core.util.JacksonUtil;
import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.shop.model.vo.ShopAccountVo;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author  Xusheng Wang
 * @date  2021-11-11
 * @studentId 34520192201587
 */

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
public class ShopAccountControllerTest {

    @Autowired
    private MockMvc mvc;

    /**
     * @author  Xusheng Wang
     * @date  2021-11-11
     * @studentId 34520192201587
     */
    @Test
    public void addShopAccountTest() throws Exception {

        ShopAccountVo shopAccountVo= new ShopAccountVo();
        shopAccountVo.setAccount("1115");
        shopAccountVo.setType((byte) 1);
        shopAccountVo.setPriority((byte) 1);
        shopAccountVo.setName("test5");
        String requestJson= JacksonUtil.toJson(shopAccountVo);

        //测试新增记录需要移动优先级的情况
        mvc.perform(post("/shops/2/accounts").contentType("application/json;charset=UTF-8")
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(MockMvcResultHandlers.print());

        //测试新增记录不需要移动优先级的情况
        mvc.perform(post("/shops/5/accounts").contentType("application/json;charset=UTF-8")
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(MockMvcResultHandlers.print());
    }

    /**
     * @author  Xusheng Wang
     * @date  2021-11-11
     * @studentId 34520192201587
     */
    @Test
    public void getShopAccountsTest() throws Exception{

        String expectResponse="{\"code\":\"OK\",\"errmsg\":\"成功\",\"data\":[{\"id\":2,\"type\":0,\"account\":\"111220333\",\"name\":\"甜蜜之旅支付宝帐号\",\"priority\":1,\"creator\":{\"id\":1,\"name\":\"admin\"},\"gmtCreate\":\"2021-11-11 14:14:59\",\"gmtModified\":null,\"modifier\":{\"id\":null,\"name\":null}},{\"id\":3,\"type\":1,\"account\":\"3112133333\",\"name\":\"甜蜜之旅微信帐号\",\"priority\":2,\"createdBy\":{\"id\":1,\"name\":\"admin\"},\"gmtCreate\":\"2021-11-11 14:14:59\",\"gmtModified\":null,\"modifiedBy\":{\"id\":null,\"name\":null}}]}";

        String responseString=mvc.perform(get("/shops/2/accounts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(MockMvcResultHandlers.print())
                .andReturn().getResponse().getContentAsString();

        JSONAssert.assertEquals(expectResponse, responseString, false);
    }

    /**
     * @author  Xusheng Wang
     * @date  2021-11-11
     * @studentId 34520192201587
     */
    @Test
    public void deleteShopAccountTest() throws Exception{
        //测试正确删除
        String response1=mvc.perform(delete("/shops/2/accounts/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        assertEquals("{\"errno\":0,\"errmsg\":\"成功\"}",response1);

        //测试accountId和shopId无法对应的情况
        String response2=mvc.perform(delete("/shops/2/accounts/5"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.errno").value(ReturnNo.RESOURCE_ID_NOTEXIST.getCode()))
                .andReturn().getResponse().getContentAsString();
        assertEquals("{\"errno\":504,\"errmsg\":\"账户信息有误！\"}",response2);
    }
}
