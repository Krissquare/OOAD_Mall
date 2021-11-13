package cn.edu.xmu.oomall.activity;

import cn.edu.xmu.oomall.activity.model.vo.SimpleShopVo;
import cn.edu.xmu.oomall.activity.openfeign.ShopApi;
import cn.edu.xmu.oomall.core.util.ResponseUtil;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GroupOnActivityTest {
    private static String adminToken = "0";
    private static String shopToken = "0";

    @Autowired
    private MockMvc mvc;

    @MockBean(name = "cn.edu.xmu.oomall.activity.openfeign.ShopApi")
    private ShopApi shopApi;

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
     * 获得所有上线态的团购活动（正常流程，通过shopId和时间查询）
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void getOnlineGroupOnActivitiesTest1() throws Exception {
        this.mvc.perform(get("/groupons")
                .queryParam("shopId", "1")
                .queryParam("beginTime", "2021-11-11 00:00:00")
                .queryParam("endTime", "2023-11-11 00:00:00"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(content().json("{\"errno\":0,\"data\":{\"list\":[{\"id\":4,\"name\":\"团购活动4\"},{\"id\":6,\"name\":\"团购活动6\"},{\"id\":9,\"name\":\"团购活动9\"}],\"total\":3,\"page\":1,\"pageSize\":10,\"pages\":1},\"errmsg\":\"成功\"}"));
    }


    /**
     * 获得所有上线态的团购活动（日期格式不正确）
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void getOnlineGroupOnActivitiesTest2() throws Exception {
        this.mvc.perform(get("/groupons")
                .queryParam("shopId", "1")
                .queryParam("beginTime", "2021-11-11T00:00:00")
                .queryParam("endTime", "2023-11-11T00:00:00"))
                .andExpect(status().is(400))
                .andExpect(content().contentType("application/json;charset=UTF-8"));
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
                .andExpect(content().json("{\"errno\":0,\"data\":{\"id\":1,\"name\":\"团购活动1\",\"shopId\":3,\"strategy\":null,\"beginTime\":\"2021-11-11 14:58:24\",\"endTime\":\"2022-02-19 14:58:24\"},\"errmsg\":\"成功\"}"));
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
        Mockito.when(shopApi.getShopInfo(1L)).thenReturn(ResponseUtil.ok(new SimpleShopVo(1L, "OOMALL自营商铺")));
        this.mvc.perform(post("/shops/1/groupons")
                .contentType("application/json;charset=UTF-8")
                .content("{\"name\":\"测试\",\"beginTime\":\"2021-11-11 00:00:00\",\"endTime\":\"2021-11-13 00:00:00\",\"strategy\":[{\"quantity\":10,\"percentage\":500}]}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

    }


}
