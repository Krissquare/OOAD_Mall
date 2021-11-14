package cn.edu.xmu.oomall.activity.controller;

import cn.edu.xmu.oomall.activity.model.vo.*;
import cn.edu.xmu.oomall.activity.microservice.GoodsService;
import cn.edu.xmu.oomall.activity.microservice.ShopService;
import cn.edu.xmu.oomall.activity.microservice.vo.OnSaleVo;
import cn.edu.xmu.oomall.activity.microservice.vo.SimpleOnSaleVo;
import cn.edu.xmu.oomall.activity.microservice.vo.SimpleShopVo;
import cn.edu.xmu.oomall.core.util.JacksonUtil;
import cn.edu.xmu.oomall.core.util.ResponseUtil;
//import org.junit.Assert;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GroupOnActivityControllerTest {
    private static String adminToken = "0";
    private static String shopToken = "0";

    @Autowired
    private MockMvc mvc;

    @MockBean(name = "cn.edu.xmu.oomall.activity.microservice.ShopService")
    private ShopService shopService;

    @MockBean(name = "cn.edu.xmu.oomall.activity.microservice.GoodsService")
    private GoodsService goodsService;

    /**
     * 获得所有团购活动状态
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void getGroupOnStatesTest() throws Exception {
        this.mvc.perform(get("/groupons/states"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().json("{\"errno\":0,\"data\":[{\"code\":0,\"name\":\"草稿\"},{\"code\":1,\"name\":\"上线\"},{\"code\":2,\"name\":\"下线\"}],\"errmsg\":\"成功\"}"));
    }


    /**
     * 获得所有上线态的团购活动（正常流程）
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void getOnlineGroupOnActivitiesTest1() throws Exception {
        Mockito.when(goodsService.getOnsSlesOfProduct(1578L, 1, 10)).thenReturn(new ReturnObject<>(new PageInfoVo<>(Collections.singletonList(new SimpleOnSaleVo(29L, 17931L, "2021-11-11 14:38:20", "2022-02-19 14:38:20", 39L)), 1L, 1, 10, 1)));
        Mockito.when(goodsService.getOnSale(29L)).thenReturn(new ReturnObject<>(new OnSaleVo(29L, null, null, 17931L, "2021-11-11 14:38:20", "2022-02-19 14:38:20", 39L, 2, 3L, null, null, "2021-11-11 14:38:20", null, null)));

        this.mvc.perform(get("/groupons")
                .queryParam("shopId", "3")
                .queryParam("productId", "1578")
                .queryParam("beginTime", "2021-11-11T00:00:00.000")
                .queryParam("endTime", "2023-11-11T00:00:00.000"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().json("{\"errno\":0,\"data\":{\"list\":[{\"id\":3,\"name\":\"团购活动3\"}],\"total\":1,\"page\":1,\"pageSize\":10,\"pages\":1},\"errmsg\":\"成功\"}"));
    }

    /**
     * 获得上线态团购活动详情（正常流程）
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void getOnlineGroupOnActivityTest1() throws Exception {
        this.mvc.perform(get("/groupons/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().json("{\"errno\":0,\"data\":{\"id\":1,\"name\":\"团购活动1\",\"shopId\":3,\"strategy\":null,\"beginTime\":\"2021-11-11T14:58:24.000\",\"endTime\":\"2022-02-19T14:58:24.000\"},\"errmsg\":\"成功\"}"));
    }

    /**
     * 管理员查询商铺的所有状态团购活动（正常流程）
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void getGroupOnActivitiesInShopTest1() throws Exception {
        this.mvc.perform(get("/shops/2/groupons"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().json("{\"errno\":0,\"data\":{\"list\":[{\"id\":5,\"name\":\"团购活动5\"},{\"id\":7,\"name\":\"团购活动7\"}],\"total\":2,\"page\":1,\"pageSize\":10,\"pages\":1},\"errmsg\":\"成功\"}"));
    }

    /**
     * 管理员新增团购活动（正常流程）
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void addGroupOnActivityTest1() throws Exception {
        Mockito.when(shopService.getShopInfo(1L)).thenReturn(new ReturnObject<>(new SimpleShopVo(1L, "OOMALL自营商铺")));
        var responseString = this.mvc.perform(post("/shops/1/groupons")
                .contentType("application/json;charset=UTF-8")
                .content("{\"name\":\"测试\",\"beginTime\":\"2021-11-11T00:00:00.000\",\"endTime\":\"2021-11-13T00:00:00.000\",\"strategy\":[{\"quantity\":10,\"percentage\":500}]}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        var id = JacksonUtil.parseObject(responseString, "data", GroupOnActivityVo.class).getId();
        this.mvc.perform(get("/shops/1/groupons/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andDo(MockMvcResultHandlers.print());
    }


    /**
     * 管理员查看特定团购活动详情（正常流程）
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void getGroupOnActivityInShopTest1() throws Exception {
        this.mvc.perform(get("/shops/1/groupons/16"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().json("{\"errno\":0,\"data\":{\"id\":16,\"name\":\"测试\",\"shopId\":1,\"strategy\":[{\"quantity\":10,\"percentage\":500}],\"beginTime\":\"2021-11-11T00:00:00.000\",\"endTime\":\"2021-11-13T00:00:00.000\",\"createdBy\":{\"id\":1,\"name\":\"admin\"},\"gmtCreate\":\"2021-11-12T16:35:18.000\",\"gmtModified\":null,\"modifiedBy\":{\"id\":null,\"name\":null},\"state\":0},\"errmsg\":\"成功\"}"));
    }

    /**
     * 日期格式不正确，得到bad request
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void invalidDateTimeFormatTest() throws Exception {
        this.mvc.perform(get("/groupons")
                .queryParam("shopId", "1")
                .queryParam("beginTime", "2021-11-11T00:00:00")
                .queryParam("endTime", "2023-11-11T00:00:00"))
                .andExpect(status().isBadRequest());

        this.mvc.perform(get("/shops/2/groupons")
                .queryParam("beginTime", "2021-11-11T00:00:00"))
                .andExpect(status().isBadRequest());

        Mockito.when(shopService.getShopInfo(1L)).thenReturn(new ReturnObject<>(new SimpleShopVo(1L, "OOMALL自营商铺")));
        this.mvc.perform(post("/shops/1/groupons")
                .contentType("application/json;charset=UTF-8")
                .content("{\"name\":\"测试\",\"beginTime\":\"2021-11-11T00:00:00\",\"endTime\":\"2021-11-13T00:00:00.000\",\"strategy\":[{\"quantity\":10,\"percentage\":500}]}"))
                .andExpect(status().isBadRequest());
    }

    /**
     * 获得上线态团购活动详情（ID未找到）
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void getOnlineGroupOnActivityTest2() throws Exception {
        this.mvc.perform(get("/groupons/0"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().json("{\"errno\":504,\"errmsg\":\"未找到指定ID对应的团购活动\"}"));
    }

    /**
     * 获得上线态团购活动详情（未上线）
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void getOnlineGroupOnActivityTest3() throws Exception {
        Mockito.when(shopService.getShopInfo(1L)).thenReturn(new ReturnObject<>(new SimpleShopVo(1L, "OOMALL自营商铺")));
        var responseString = this.mvc.perform(post("/shops/1/groupons")
                .contentType("application/json;charset=UTF-8")
                .content("{\"name\":\"测试\",\"beginTime\":\"2021-11-11T00:00:00.000\",\"endTime\":\"2021-11-13T00:00:00.000\",\"strategy\":[{\"quantity\":10,\"percentage\":500}]}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        var id = JacksonUtil.parseObject(responseString, "data", GroupOnActivityVo.class).getId();
        this.mvc.perform(get("/groupons/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().json("{\"errno\":507,\"errmsg\":\"团购活动未上线\"}"));
    }

    /**
     * 管理员查看特定团购活动详情（ID未找到）
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void getGroupOnActivityInShopTest2() throws Exception {
        this.mvc.perform(get("/shops/1/groupons/0"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().json("{\"errno\":504,\"errmsg\":\"未找到指定ID对应的团购活动\"}"));
    }


    /**
     * 管理员查看特定团购活动详情（未在该店铺中）
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void getGroupOnActivityInShopTest3() throws Exception {
        this.mvc.perform(get("/shops/1/groupons/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().json("{\"errno\":504,\"errmsg\":\"指定店铺中不存在指定ID对应的团购活动\"}"));
    }

    /**
     * 管理员新增团购活动（body格式不合法）
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void addGroupOnActivityTest2() throws Exception {
        Mockito.when(shopService.getShopInfo(1L)).thenReturn(new ReturnObject<>(new SimpleShopVo(1L, "OOMALL自营商铺")));
        this.mvc.perform(post("/shops/1/groupons")
                .contentType("application/json;charset=UTF-8")
                .content("{\"name\":\"\",\"beginTime\":\"2021-11-11T00:00:00.000\",\"endTime\":\"2021-11-13T00:00:00.000\",\"strategy\":[{\"quantity\":10,\"percentage\":500}]}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().json("{\"errno\":503,\"errmsg\":\"length must be between 1 and 128;\"}"));
        this.mvc.perform(post("/shops/1/groupons")
                .contentType("application/json;charset=UTF-8")
                .content("{\"name\":\"测试\",\"beginTime\":\"2021-11-11T00:00:00.000\",\"endTime\":\"2021-11-13T00:00:00.000\",\"strategy\":[{\"quantity\":-10,\"percentage\":500}]}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().json("{\"errno\":503,\"errmsg\":\"must be greater than or equal to -1;\"}"));
        this.mvc.perform(post("/shops/1/groupons")
                .contentType("application/json;charset=UTF-8")
                .content("{\"name\":\"测试\",\"beginTime\":\"2021-11-11T00:00:00.000\",\"endTime\":\"2021-11-13T00:00:00.000\",\"strategy\":[{\"quantity\":10,\"percentage\":500000}]}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().json("{\"errno\":503,\"errmsg\":\"must be less than or equal to 1000;\"}"));
    }

    /**
     * 管理员新增团购活动（开始日期晚于结束日期）
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void addGroupOnActivityTest3() throws Exception {
        Mockito.when(shopService.getShopInfo(1L)).thenReturn(new ReturnObject<>(new SimpleShopVo(1L, "OOMALL自营商铺")));
        this.mvc.perform(post("/shops/1/groupons")
                .contentType("application/json;charset=UTF-8")
                .content("{\"name\":\"测试\",\"beginTime\":\"2022-11-11T00:00:00.000\",\"endTime\":\"2021-11-13T00:00:00.000\",\"strategy\":[{\"quantity\":10,\"percentage\":500}]}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().json("{\"errno\":947,\"errmsg\":\"开始时间不能晚于结束时间\"}"));
    }

}
