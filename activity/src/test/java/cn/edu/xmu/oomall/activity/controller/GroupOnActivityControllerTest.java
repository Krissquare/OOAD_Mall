package cn.edu.xmu.oomall.activity.controller;

import cn.edu.xmu.oomall.activity.microservice.GoodsService;
import cn.edu.xmu.oomall.activity.microservice.ShopService;
import cn.edu.xmu.oomall.activity.microservice.vo.OnSaleVo;
import cn.edu.xmu.oomall.activity.microservice.vo.SimpleOnSaleVo;
import cn.edu.xmu.oomall.activity.microservice.vo.SimpleShopVo;
import cn.edu.xmu.oomall.activity.model.vo.GroupOnActivityVo;
import cn.edu.xmu.oomall.activity.model.vo.PageInfoVo;
import cn.edu.xmu.oomall.core.util.JacksonUtil;
import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
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

import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GroupOnActivityControllerTest {

    private static final ReturnObject getShopInfoRet1 = new ReturnObject(new SimpleShopVo(1L, "OOMALL自营商铺"));
    private static final ReturnObject getShopInfoRet2 = new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR, "外部API错误");

    private static final ReturnObject getOnsSlesOfProductRet1 = new ReturnObject(new PageInfoVo(Collections.singletonList(new SimpleOnSaleVo(29L, 17931L, "2021-11-11 14:38:20", "2022-02-19 14:38:20", 39L)), 1L, 1, 10, 1));
    private static final ReturnObject getOnsSlesOfProductRet2 = new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR, "外部API错误");

    private static final ReturnObject getOnSaleRet1 = new ReturnObject(new OnSaleVo(29L, null, null, 17931L, "2021-11-11 14:38:20", "2022-02-19 14:38:20", 39L, 2, 3L, null, null, "2021-11-11 14:38:20", null, null));
    private static final ReturnObject getOnSaleRet2 = new ReturnObject(ReturnNo.INTERNAL_SERVER_ERR, "外部API错误");

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
                .andExpect(jsonPath("$.errno").value(0))
                .andExpect(jsonPath("$.data", hasSize(3)))
                .andExpect(jsonPath("$.data[0].code").value(0))
                .andExpect(jsonPath("$.data[0].name").value("草稿"))
                .andExpect(jsonPath("$.data[1].code").value(1))
                .andExpect(jsonPath("$.data[1].name").value("上线"))
                .andExpect(jsonPath("$.data[2].code").value(2))
                .andExpect(jsonPath("$.data[2].name").value("下线"))
        ;
    }

    /**
     * 获得所有上线态的团购活动（正常流程）
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void getOnlineGroupOnActivitiesTest1() throws Exception {
        Mockito.when(goodsService.getOnsSalesOfProduct(1578L, 1, 10)).thenReturn(getOnsSlesOfProductRet1);
        Mockito.when(goodsService.getOnSale(29L)).thenReturn(getOnSaleRet1);

        this.mvc.perform(get("/groupons")
                .queryParam("shopId", "3")
                .queryParam("productId", "1578")
                .queryParam("beginTime", "2021-11-11 00:00:00.000")
                .queryParam("endTime", "2023-11-11 00:00:00.000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errno").value(0))
                .andExpect(jsonPath("$.data.page").value(1))
                .andExpect(jsonPath("$.data.pageSize").value(10))
                .andExpect(jsonPath("$.data.pages").value(1))
                .andExpect(jsonPath("$.data.total").value(1))
                .andExpect(jsonPath("$.data.list", hasSize(1)))
                .andExpect(jsonPath("$.data.list[0].id").value(3))
                .andExpect(jsonPath("$.data.list[0].name").value("团购活动3"))
        ;

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
                .andExpect(jsonPath("$.errno").value(0))
                .andExpect(jsonPath("$.data.beginTime").value("2021-11-11 14:58:24.000"))
                .andExpect(jsonPath("$.data.endTime").value("2022-02-19 14:58:24.000"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("团购活动1"))
                .andExpect(jsonPath("$.data.shopId").value(3))
                .andExpect(jsonPath("$.data.strategy").isEmpty())
        ;
    }

    /**
     * @throws Exception
     */
    @Test
    @Transactional
    public void getGroupOnActivitiesInShopTest1() throws Exception {
        this.mvc.perform(get("/shops/2/groupons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errno").value(0))
                .andExpect(jsonPath("$.data.page").value(1))
                .andExpect(jsonPath("$.data.pageSize").value(10))
                .andExpect(jsonPath("$.data.pages").value(1))
                .andExpect(jsonPath("$.data.total").value(2))
                .andExpect(jsonPath("$.data.list", hasSize(2)))
                .andExpect(jsonPath("$.data.list[0].id").value(5))
                .andExpect(jsonPath("$.data.list[0].name").value("团购活动5"))
                .andExpect(jsonPath("$.data.list[1].id").value(7))
                .andExpect(jsonPath("$.data.list[1].name").value("团购活动7"))
        ;


    }

    /**
     * 管理员新增团购活动（正常流程）
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void addGroupOnActivityTest1() throws Exception {
        Mockito.when(shopService.getShopInfo(1L)).thenReturn(getShopInfoRet1);
        this.mvc.perform(post("/shops/1/groupons")
                .contentType("application/json;charset=UTF-8")
                .content("{\"name\":\"测试\",\"beginTime\":\"2021-11-11 00:00:00.000\",\"endTime\":\"2021-11-13 00:00:00.000\",\"strategy\":[{\"quantity\":10,\"percentage\":500}]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errno").value(0));
    }


    /**
     * 管理员查看特定团购活动详情（正常流程）
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void getGroupOnActivityInShopTest1() throws Exception {
        this.mvc.perform(get("/shops/3/groupons/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errno").value(0))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("团购活动1"))
                .andExpect(jsonPath("$.data.shopId").value(3))
                .andExpect(jsonPath("$.data.strategy").isEmpty())
                .andExpect(jsonPath("$.data.beginTime").value("2021-11-11 14:58:24.000"))
                .andExpect(jsonPath("$.data.endTime").value("2022-02-19 14:58:24.000"))
                .andExpect(jsonPath("$.data.gmtCreate").value("2021-11-11 14:58:24.000"))
                .andExpect(jsonPath("$.data.gmtModified").isEmpty())
                .andExpect(jsonPath("$.data.state").value(1))
                .andExpect(jsonPath("$.data.createdBy.id").value(1))
                .andExpect(jsonPath("$.data.createdBy.name").value("admin"))
                .andExpect(jsonPath("$.data.modifiedBy.id").isEmpty())
                .andExpect(jsonPath("$.data.modifiedBy.name").isEmpty())
                ;
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
                .queryParam("beginTime", "2021-11-11 00:00:00")
                .queryParam("endTime", "2023-11-11 00:00:00"))
                .andExpect(status().isBadRequest());

        this.mvc.perform(get("/shops/2/groupons")
                .queryParam("beginTime", "2021-11-11 00:00:00"))
                .andExpect(status().isBadRequest());

        Mockito.when(shopService.getShopInfo(1L)).thenReturn(getShopInfoRet1);
        this.mvc.perform(post("/shops/1/groupons")
                .content("{\"name\":\"测试\",\"beginTime\":\"2021-11-11 00:00:00\",\"endTime\":\"2021-11-13 00:00:00.000\",\"strategy\":[{\"quantity\":10,\"percentage\":500}]}"))
                .andExpect(status().isUnsupportedMediaType());
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
                .andExpect(jsonPath("$.errno").value(504));
    }

    /**
     * 获得上线态团购活动详情（未上线）
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void getOnlineGroupOnActivityTest3() throws Exception {
        Mockito.when(shopService.getShopInfo(1L)).thenReturn(getShopInfoRet1);
        var responseString = this.mvc.perform(post("/shops/1/groupons")
                .contentType("application/json;charset=UTF-8")
                .content("{\"name\":\"测试\",\"beginTime\":\"2021-11-11 00:00:00.000\",\"endTime\":\"2021-11-13 00:00:00.000\",\"strategy\":[{\"quantity\":10,\"percentage\":500}]}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        var id = JacksonUtil.parseObject(responseString, "data", GroupOnActivityVo.class).getId();
        this.mvc.perform(get("/groupons/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.errno").value(507));
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
                .andExpect(jsonPath("$.errno").value(504));
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
                .andExpect(jsonPath("$.errno").value(504));
    }

    /**
     * 管理员新增团购活动（body格式不合法）
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void addGroupOnActivityTest2() throws Exception {
        Mockito.when(shopService.getShopInfo(1L)).thenReturn(getShopInfoRet1);
        this.mvc.perform(post("/shops/1/groupons")
                .contentType("application/json;charset=UTF-8")
                .content("{\"name\":\"\",\"beginTime\":\"2021-11-11 00:00:00.000\",\"endTime\":\"2021-11-13 00:00:00.000\",\"strategy\":[{\"quantity\":10,\"percentage\":500}]}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errno").value(503));
        this.mvc.perform(post("/shops/1/groupons")
                .contentType("application/json;charset=UTF-8")
                .content("{\"name\":\"测试\",\"beginTime\":\"2021-11-11 00:00:00.000\",\"endTime\":\"2021-11-13 00:00:00.000\",\"strategy\":[{\"quantity\":-10,\"percentage\":500}]}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errno").value(503));
        this.mvc.perform(post("/shops/1/groupons")
                .contentType("application/json;charset=UTF-8")
                .content("{\"name\":\"测试\",\"beginTime\":\"2021-11-11 00:00:00.000\",\"endTime\":\"2021-11-13 00:00:00.000\",\"strategy\":[{\"quantity\":10,\"percentage\":500000}]}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errno").value(503));
    }

    /**
     * 管理员新增团购活动（开始日期晚于结束日期）
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void addGroupOnActivityTest3() throws Exception {
        Mockito.when(shopService.getShopInfo(1L)).thenReturn(getShopInfoRet1);
        this.mvc.perform(post("/shops/1/groupons")
                .contentType("application/json;charset=UTF-8")
                .content("{\"name\":\"测试\",\"beginTime\":\"2022-11-11 00:00:00.000\",\"endTime\":\"2021-11-13 00:00:00.000\",\"strategy\":[{\"quantity\":10,\"percentage\":500}]}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errno").value(947));
    }

    /**
     * 管理员新增团购活动（getShopInfo出错）
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void addGroupOnActivityTest4() throws Exception {
        Mockito.when(shopService.getShopInfo(1L)).thenReturn(getShopInfoRet2);
        this.mvc.perform(post("/shops/1/groupons")
                .contentType("application/json;charset=UTF-8")
                .content("{\"name\":\"测试\",\"beginTime\":\"2021-11-11 00:00:00.000\",\"endTime\":\"2021-11-13 00:00:00.000\",\"strategy\":[{\"quantity\":10,\"percentage\":500}]}"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.errno").value(500));
    }

    /**
     * 管理员查询商铺的所有状态团购活动（getOnSalesOfProduct出错）
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void getOnlineGroupOnActivitiesTest2() throws Exception {
        Mockito.when(goodsService.getOnsSalesOfProduct(1578L, 1, 10)).thenReturn(getOnsSlesOfProductRet2);
        Mockito.when(goodsService.getOnSale(29L)).thenReturn(getOnSaleRet1);

        this.mvc.perform(get("/groupons")
                .queryParam("shopId", "3")
                .queryParam("productId", "1578")
                .queryParam("beginTime", "2021-11-11 00:00:00.000")
                .queryParam("endTime", "2023-11-11 00:00:00.000"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.errno").value(500));
    }

    /**
     * 管理员查询商铺的所有状态团购活动（getOnSalesOfProduct出错）
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void getOnlineGroupOnActivitiesTest3() throws Exception {
        Mockito.when(goodsService.getOnsSalesOfProduct(1578L, 1, 10)).thenReturn(getOnsSlesOfProductRet1);
        Mockito.when(goodsService.getOnSale(29L)).thenReturn(getOnSaleRet2);

        this.mvc.perform(get("/groupons")
                .queryParam("shopId", "3")
                .queryParam("productId", "1578")
                .queryParam("beginTime", "2021-11-11 00:00:00.000")
                .queryParam("endTime", "2023-11-11 00:00:00.000"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.errno").value(500));
    }

}
