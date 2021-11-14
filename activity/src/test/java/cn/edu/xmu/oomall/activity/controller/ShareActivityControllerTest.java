package cn.edu.xmu.oomall.activity.controller;

import cn.edu.xmu.oomall.activity.ActivityApplication;
import cn.edu.xmu.oomall.activity.openfeign.GoodsApi;
import cn.edu.xmu.oomall.activity.openfeign.ShopApi;
import cn.edu.xmu.oomall.activity.openfeign.vo.goods.SimpleSaleInfoDTO;
import cn.edu.xmu.oomall.activity.openfeign.vo.shop.ShopInfoDTO;
import cn.edu.xmu.oomall.activity.util.CreateObject;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import com.github.pagehelper.PageInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * ShareActivityController Tester.
 *
 * @author xiuchen lang 22920192204222
 * @since: 11/13/2021
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ActivityApplication.class)
@AutoConfigureMockMvc
public class ShareActivityControllerTest {

    private static String token = "0";

    @Autowired
    private MockMvc mvc;

    @MockBean(name = "cn.edu.xmu.oomall.activity.openfeign.GoodsApi")
    private GoodsApi goodsApi;

    @MockBean(name = "cn.edu.xmu.oomall.activity.openfeign.ShopApi")
    private ShopApi shopApi;

    @Before
    public void createObject() throws Exception {
        //生成一个 onsale对象
        ReturnObject<PageInfo<SimpleSaleInfoDTO>> onSaleInfoDTO = CreateObject.createOnSaleInfoDTO(1L);
        ReturnObject<PageInfo<SimpleSaleInfoDTO>> onSaleInfoDTO1 = CreateObject.createOnSaleInfoDTO(-1L);
        Mockito.when(goodsApi.getOnSalesByProductId(1L, 1, 10)).thenReturn(onSaleInfoDTO);
        Mockito.when(goodsApi.getOnSalesByProductId(-1L, 1, 10)).thenReturn(onSaleInfoDTO1);

        //生成一个shop对象
        ReturnObject<ShopInfoDTO> shopInfoDTO = CreateObject.createShopInfoDTO(1L);
        ReturnObject<ShopInfoDTO> shopInfoDTO1 = CreateObject.createShopInfoDTO(-1L);
        Mockito.when(shopApi.getShop(1L)).thenReturn(shopInfoDTO);
        Mockito.when(shopApi.getShop(-1L)).thenReturn(shopInfoDTO1);
    }

    /**
     * 获得分享活动的所有状态
     * Method: getShareState()
     */
    @Test
    @Transactional
    public void testGetShareState() throws Exception {
        String responseString = mvc.perform(get("/shareactivities/states"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
    }

    /**
     * 显示所有状态的分享活动
     * Method: getShareByShopId(@PathVariable(name = "shopId", required = true) Long shopId, @RequestParam(name = "productId", required = false) Long productId, @RequestParam(name = "beginTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime beginTime, @RequestParam(name = "endTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime, @RequestParam(name = "state", required = false) Byte state, @RequestParam(name = "page", required = false) Integer page, @RequestParam(name = "pageSize", required = false) Integer pageSize)
     */
    @Test
    @Transactional
    public void testGetShareByShopId() throws Exception {
        //不添加query时
        String responseString = mvc.perform(get("/shops/1/shareactivities").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        //page pageSize shopId不合规时
        String responseString2 = mvc.perform(get("/shops/1/shareactivities?page=-1").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String responseString3 = mvc.perform(get("/shops/1/shareactivities?pageSize=-1").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String responseString4 = mvc.perform(get("/shops/-1/shareactivities").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        //有添加所有query都有时且合规
        String responseString5 = mvc.perform(get("/shops/1/shareactivities?productId=1&beginTime=2021-11-11 10:10:10&endTime=2021-11-11 10:10:10&state=1&page=1&pageSize=3").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
    }

    /**
     * 管理员新增分享活动
     * Method: addShareAct(@PathVariable(value = "shopId", required = true) Long shopId, @Validated @RequestBody ShareActivityDTO shareActivityDTO, BindingResult bindingResult)
     */
    @Test
    @Transactional
    public void testAddShareAct() throws Exception {
        String requestJson = "{\"name\":\"String\",\"beginTime\":\"2021-11-11 15:01:02\",\"endTime\":\"2021-11-11 15:01:10\",\"strategy\":[{\"quantity\":10,\"percentage\":10},{\"quantity\":10,\"percentage\":10}]}\n";
        //有添加所有query都有时且合规
        String responseString = mvc.perform(post("/shops/1/shareactivities").header("authorization", token).contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        //姓名为空或null
        String requestJson1 = "{\"name\":\"\",\"beginTime\":\"2021-11-11 15:01:02\",\"endTime\":\"2021-11-11 15:01:10\",\"strategy\":[{\"quantity\":10,\"percentage\":10},{\"quantity\":10,\"percentage\":10}]}";
        String responseString1 = mvc.perform(post("/shops/1/shareactivities").header("authorization", token).contentType("application/json;charset=UTF-8").content(requestJson1))
                .andExpect(status().is(400))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        //时间为空
        String requestJson2 = "{\"name\":\"我是一个活动\",\"beginTime\":\"2021-11-11 15:01:02\",\"endTime\":null,\"strategy\":[{\"quantity\":10,\"percentage\":10},{\"quantity\":10,\"percentage\":10}]}";
        String responseString2 = mvc.perform(post("/shops/1/shareactivities").header("authorization", token).contentType("application/json;charset=UTF-8").content(requestJson2))
                .andExpect(status().is(400))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        //时间不合规
        String requestJson3 = "{\"name\":\"我是一个活动\",\"beginTime\":\"2021-11-11 15:01:02\",\"endTime\":\"2021-11-11\",\"strategy\":[{\"quantity\":10,\"percentage\":10},{\"quantity\":10,\"percentage\":10}]}";
        String responseString3 = mvc.perform(post("/shops/1/shareactivities").header("authorization", token).contentType("application/json;charset=UTF-8").content(requestJson3))
                .andExpect(status().is(400))
                .andReturn().getResponse().getContentAsString();

        //活动条件不合规
        String requestJson4 = "{\"name\":\"我是一个活动\",\"beginTime\":\"2021-11-11 15:01:02\",\"endTime\":\"2021-11-11 15:01:10\",\"strategy\":[{\"quantity\":null,\"percentage\":10},{\"quantity\":10,\"percentage\":10}]}";
        String responseString4 = mvc.perform(post("/shops/1/shareactivities").header("authorization", token).contentType("application/json;charset=UTF-8").content(requestJson4))
                .andExpect(status().is(400))
                .andReturn().getResponse().getContentAsString();

        String requestJson5 = "{\"name\":\"我是一个活动\",\"beginTime\":\"2021-11-11 15:01:02\",\"endTime\":\"2021-11-11 15:01:10\",\"strategy\":[{\"quantity\":-5,\"percentage\":10},{\"quantity\":10,\"percentage\":10}]}";
        String responseString5 = mvc.perform(post("/shops/1/shareactivities").header("authorization", token).contentType("application/json;charset=UTF-8").content(requestJson5))
                .andExpect(status().is(400))
                .andReturn().getResponse().getContentAsString();

        String requestJson6 = "{\"name\":\"我是一个活动\",\"beginTime\":\"2021-11-11 15:01:02\",\"endTime\":\"2021-11-11 15:01:10\",\"strategy\":[{\"quantity\":5,\"percentage\":110},{\"quantity\":10,\"percentage\":10}]}";
        String responseString6 = mvc.perform(post("/shops/1/shareactivities").header("authorization", token).contentType("application/json;charset=UTF-8").content(requestJson6))
                .andExpect(status().is(400))
                .andReturn().getResponse().getContentAsString();

        //所有都合规
        String requestJson7 = "{\"name\":\"我是一个活动\",\"beginTime\":\"2021-11-11 15:01:02\",\"endTime\":\"2021-11-11 15:01:10\",\"strategy\":[{\"quantity\":5,\"percentage\":10},{\"quantity\":10,\"percentage\":10}]}";
        String responseString7 = mvc.perform(post("/shops/1/shareactivities").header("authorization", token).contentType("application/json;charset=UTF-8").content(requestJson7))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        //时间不合规
        String requestJson8 = "{\"name\":\"我是一个活动\",\"beginTime\":\"2021-11-11 15:01:02\",\"endTime\":\"2021-11-11 15:01:00\",\"strategy\":[{\"quantity\":10,\"percentage\":10},{\"quantity\":10,\"percentage\":10}]}";
        String responseString8 = mvc.perform(post("/shops/1/shareactivities").header("authorization", token).contentType("application/json;charset=UTF-8").content(requestJson8))
                .andExpect(status().is(200))
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        //shopId没有
        String requestJson9 = "{\"name\":\"我是一个活动\",\"beginTime\":\"2021-11-11 15:01:02\",\"endTime\":\"2021-11-11 15:01:10\",\"strategy\":[{\"quantity\":5,\"percentage\":10},{\"quantity\":10,\"percentage\":10}]}";
        String responseString9 = mvc.perform(post("/shops/11/shareactivities").header("authorization", token).contentType("application/json;charset=UTF-8").content(requestJson9))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
    }

    /**
     * 查询分享活动 只显示上线状态的分享活动
     * Method: getShareActivity(@RequestParam(name = "shopId", required = false) Long shopId, @RequestParam(name = "productId", required = false) Long productId, @RequestParam(name = "beginTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime beginTime, @RequestParam(name = "endTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime, @RequestParam(name = "page", required = false) Integer page, @RequestParam(name = "pageSize", required = false) Integer pageSize)
     */
    @Test
    @Transactional
    public void testGetShareActivity() throws Exception {
        //不添加query时
        String responseString = mvc.perform(get("/shareactivities").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        //有shopId
        String responseString1 = mvc.perform(get("/shareactivities?shopId=1").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        //有productId
        String responseString2 = mvc.perform(get("/shareactivities?productId=1").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        //time不合规
        String responseString3 = mvc.perform(get("/shareactivities?beginTime=2020-2-6").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().is(400))
                .andReturn().getResponse().getContentAsString();
        //page不合规
        String responseString4 = mvc.perform(get("/shareactivities?page=-1").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        //pageSize不合规
        String responseString5 = mvc.perform(get("/shareactivities?pageSize=-1").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        //都合规
        String responseString6 = mvc.perform(get("/shareactivities?shopId=1&productId=1&beginTime=2021-11-11 15:01:02&endTime=2021-11-11 15:01:02&page=1&pageSize=4").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        //shopId<0
        String responseString7 = mvc.perform(get("/shareactivities?shopId=-1&productId=1&beginTime=2021-11-11 15:01:02&endTime=2021-11-11 15:01:02&page=1&pageSize=4").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
    }

    /**
     * 查看分享活动详情 只显示上线状态的分享活动
     * Method: getShareActivityById(@PathVariable(value = "id", required = true) Long id)
     */
    @Test
    @Transactional
    public void testGetShareActivityById() throws Exception {
        String responseString = mvc.perform(get("/shareactivities/1").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
    }

    /**
     * 查看特定分享活动详情,显示所有状态的分享活动
     * Method: getShareActivityByShopIdAndId(@PathVariable("shopId") Long shopId, @PathVariable("id") Long id)
     */
    @Test
    @Transactional
    public void testGetShareActivityByShopIdAndId() throws Exception {
        String responseString = mvc.perform(get("/shops/1/shareactivities/1").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
    }

}
