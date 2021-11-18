package cn.edu.xmu.oomall.activity.controller;

import cn.edu.xmu.oomall.activity.ActivityApplication;
import cn.edu.xmu.oomall.activity.microservice.GoodsService;
import cn.edu.xmu.oomall.activity.microservice.ShopService;
import cn.edu.xmu.oomall.activity.microservice.vo.SimpleSaleInfoVO;
import cn.edu.xmu.oomall.activity.microservice.vo.ShopInfoVO;
import cn.edu.xmu.oomall.activity.util.CreateObject;
import cn.edu.xmu.oomall.core.util.RedisUtil;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import com.github.pagehelper.PageInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
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
 * @date 11/13/2021
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ActivityApplication.class)
@AutoConfigureMockMvc
@Rollback(value = true)
public class ShareActivityControllerTest {

    private static String token = "0";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private GoodsService goodsService;

    @MockBean
    private ShopService shopService;

    @MockBean
    private RedisUtil redisUtil;

    @Before
    public void init() throws Exception {
        //生成一个 onsale对象
        ReturnObject<PageInfo<SimpleSaleInfoVO>> onSaleInfoDTO = CreateObject.createOnSaleInfoDTO(1L);
        ReturnObject<PageInfo<SimpleSaleInfoVO>> onSaleInfoDTO1 = CreateObject.createOnSaleInfoDTO(-1L);
        Mockito.when(goodsService.getOnSalesByProductId(1L, 1, 10)).thenReturn(onSaleInfoDTO);
        Mockito.when(goodsService.getOnSalesByProductId(-1L, 1, 10)).thenReturn(onSaleInfoDTO1);
        //生成一个shop对象
        ReturnObject<ShopInfoVO> shopInfoDTO = CreateObject.createShopInfoDTO(1L);
        ReturnObject<ShopInfoVO> shopInfoDTO2 = CreateObject.createShopInfoDTO(2L);
        ReturnObject<ShopInfoVO> shopInfoDTO1 = CreateObject.createShopInfoDTO(-1L);
        Mockito.when(shopService.getShop(1L)).thenReturn(shopInfoDTO);
        Mockito.when(shopService.getShop(2L)).thenReturn(shopInfoDTO2);
        Mockito.when(shopService.getShop(11L)).thenReturn(shopInfoDTO1);
        Mockito.when(shopService.getShop(-1L)).thenReturn(shopInfoDTO1);

        //redis配置
        Mockito.when(redisUtil.get("shareactivivybyid_10")).thenReturn(null);
        Mockito.when(redisUtil.get("shareactivivybyid_1")).thenReturn("{\"id\":1,\"shopId\":1,\"shopName\":\"甜蜜之旅\",\"name\":\"分享活动1\",\"beginTime\":[2021,11,11,15,1,23],\"endTime\":[2022,2,19,15,1,23],\"state\":1,\"createdBy\":1,\"createName\":\"admin\",\"modifiedBy\":null,\"modiName\":null,\"gmtCreate\":[2021,11,11,15,1,23],\"gmtModified\":null,\"strategy\":null}");
        Mockito.when(redisUtil.get("shareactivivyid_1_shopid_10")).thenReturn(null);
        Mockito.when(redisUtil.get("shareactivivyid_1_shopid_2")).thenReturn("{\"id\":1,\"shopId\":1,\"shopName\":\"甜蜜之旅\",\"name\":\"分享活动1\",\"beginTime\":[2021,11,11,15,1,23],\"endTime\":[2022,2,19,15,1,23],\"state\":1,\"createdBy\":1,\"createName\":\"admin\",\"modifiedBy\":null,\"modiName\":null,\"gmtCreate\":[2021,11,11,15,1,23],\"gmtModified\":null,\"strategy\":null}");
    }

    /**
     * 获得分享活动的所有状态
     * Method: getShareState()
     */
    @Test
    public void testGetShareState() throws Exception {
        String responseString = mvc.perform(get("/shareactivities/states"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString = "{\"errno\":0,\"data\":[{\"code\":0,\"name\":\"草稿\"},{\"code\":1,\"name\":\"下线\"},{\"code\":2,\"name\":\"上线\"}],\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectString, responseString, true);
    }

    /**
     * 显示所有状态的分享活动
     * Method: getShareByShopId(@PathVariable(name = "shopId", required = true) Long shopId, @RequestParam(name = "productId", required = false) Long productId, @RequestParam(name = "beginTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime beginTime, @RequestParam(name = "endTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime, @RequestParam(name = "state", required = false) Byte state, @RequestParam(name = "page", required = false) Integer page, @RequestParam(name = "pageSize", required = false) Integer pageSize)
     */
    @Test
    public void testGetShareByShopId() throws Exception {
        //不添加query时
        String responseString = mvc.perform(get("/shops/2/shareactivities").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString = "{\"errno\":0,\"data\":{\"total\":3,\"pages\":1,\"pageSize\":10,\"page\":1,\"list\":[{\"id\":1,\"name\":\"分享活动1\"},{\"id\":7,\"name\":\"分享活动7\"},{\"id\":10,\"name\":\"分享活动10\"}]},\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectString, responseString, true);

        //page pageSize shopId不合规时
        String responseString2 = mvc.perform(get("/shops/2/shareactivities?page=-1").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString2 = "{\"errno\":503,\"errmsg\":\"页数和页数大小应大于0\"}";
        JSONAssert.assertEquals(expectString2, responseString2, true);

        String responseString3 = mvc.perform(get("/shops/2/shareactivities?pageSize=-1").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString3 = "{\"errno\":503,\"errmsg\":\"页数和页数大小应大于0\"}";
        JSONAssert.assertEquals(expectString3, responseString3, true);

        String responseString4 = mvc.perform(get("/shops/-1/shareactivities").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString4 = "{\"errno\":503,\"errmsg\":\"shopId错误\"}";
        JSONAssert.assertEquals(expectString4, responseString4, true);

        String responseString5 = mvc.perform(get("/shops/2/shareactivities?productId=-1").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString5 = "{\"errno\":503,\"errmsg\":\"productId错误\"}";
        JSONAssert.assertEquals(expectString5, responseString5, true);

        //有添加所有query都有时且合规
        String responseString6 = mvc.perform(get("/shops/2/shareactivities?productId=1&beginTime=2021-11-11 10:10:10.000&endTime=2023-11-11 16:10:10.000&state=1&page=1&pageSize=3").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString6 = "{\"errno\":0,\"data\":{\"total\":1,\"pages\":1,\"pageSize\":3,\"page\":1,\"list\":[{\"id\":1,\"name\":\"分享活动1\"}]},\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectString6, responseString6, true);


        String responseString7 = mvc.perform(get("/shops/1111111/shareactivities").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString7 = "{\"errno\":0,\"errmsg\":\"成功\",\"data\":{\"total\":0,\"pages\":0,\"pageSize\":10,\"page\":1,\"list\":[]}}";
        JSONAssert.assertEquals(expectString7, responseString7, true);
    }

    /**
     * 管理员新增分享活动
     * Method: addShareAct(@PathVariable(value = "shopId", required = true) Long shopId, @Validated @RequestBody ShareActivityVo shareActivityDTO, BindingResult bindingResult)
     */
    @Test
    @Transactional(rollbackFor = Exception.class)
    public void testAddShareAct() throws Exception {
        CustomComparator CUSTOM_COMPARATOR = new CustomComparator(JSONCompareMode.LENIENT,
                new Customization("data.id", (o1, o2) -> true));
        String requestJson = "{\"name\":\"String\",\"beginTime\":\"2021-11-11 15:01:02.000\",\"endTime\":\"2021-11-11 15:01:10.000\",\"strategy\":[{\"quantity\":10,\"percentage\":10},{\"quantity\":10,\"percentage\":10}]}";
//        //有添加所有query都有时且合规
        String responseString = mvc.perform(post("/shops/2/shareactivities").header("authorization", token).contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString = "{\"errno\":0,\"data\":{\"id\":138,\"shop\":{\"id\":2,\"name\":\"良耳的商铺\"},\"name\":\"String\",\"beginTime\":\"2021-11-11 15:01:02.000\",\"endTime\":\"2021-11-11 15:01:10.000\",\"strategy\":[{\"quantity\":10,\"percentage\":10},{\"quantity\":10,\"percentage\":10}]},\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectString, responseString, CUSTOM_COMPARATOR);


        //姓名为空或null
        String requestJson1 = "{\"name\":\"\",\"beginTime\":\"2021-11-11 15:01:02.000\",\"endTime\":\"2021-11-11 15:01:10.000\",\"strategy\":[{\"quantity\":10,\"percentage\":10},{\"quantity\":10,\"percentage\":10}]}";
        String responseString1 = mvc.perform(post("/shops/2/shareactivities").header("authorization", token).contentType("application/json;charset=UTF-8").content(requestJson1))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString1 = "{\"errno\":503,\"errmsg\":\"预售活动名称不能为空;\"}";
        JSONAssert.assertEquals(expectString1, responseString1, true);


        //时间为空
        String requestJson2 = "{\"name\":\"我是一个活动\",\"beginTime\":\"2021-11-11 15:01:02.000\",\"endTime\":null,\"strategy\":[{\"quantity\":10,\"percentage\":10},{\"quantity\":10,\"percentage\":10}]}";
        String responseString2 = mvc.perform(post("/shops/2/shareactivities").header("authorization", token).contentType("application/json;charset=UTF-8").content(requestJson2))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString2 = "{\"errno\":503,\"errmsg\":\"结束时间不能为空;\"}";
        JSONAssert.assertEquals(expectString2, responseString2, true);


        //活动条件不合规
        String requestJson4 = "{\"name\":\"我是一个活动\",\"beginTime\":\"2021-11-11 15:01:02.000\",\"endTime\":\"2021-11-11 15:01:10.000\",\"strategy\":[{\"quantity\":null,\"percentage\":10},{\"quantity\":10,\"percentage\":10}]}";
        String responseString4 = mvc.perform(post("/shops/2/shareactivities").header("authorization", token).contentType("application/json;charset=UTF-8").content(requestJson4))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();
        String expectString4 = "{\"errno\":503,\"errmsg\":\"数量不能为空;\"}";
        JSONAssert.assertEquals(expectString4, responseString4, true);


        String requestJson5 = "{\"name\":\"我是一个活动\",\"beginTime\":\"2021-11-11 15:01:02.000\",\"endTime\":\"2021-11-11 15:01:10.000\",\"strategy\":[{\"quantity\":-5,\"percentage\":10},{\"quantity\":10,\"percentage\":10}]}";
        String responseString5 = mvc.perform(post("/shops/2/shareactivities").header("authorization", token).contentType("application/json;charset=UTF-8").content(requestJson5))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();
        String expectString5 = "{\"errno\":503,\"errmsg\":\"规则的数量不能小于0;\"}";
        JSONAssert.assertEquals(expectString5, responseString5, true);


        String requestJson6 = "{\"name\":\"我是一个活动\",\"beginTime\":\"2021-11-11 15:01:02.000\",\"endTime\":\"2021-11-11 15:01:10.000\",\"strategy\":[{\"quantity\":5,\"percentage\":110},{\"quantity\":10,\"percentage\":10}]}";
        String responseString6 = mvc.perform(post("/shops/2/shareactivities").header("authorization", token).contentType("application/json;charset=UTF-8").content(requestJson6))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();
        String expectString6 = "{\"errno\":503,\"errmsg\":\"规则的百分比需要在0和100之间;\"}";
        JSONAssert.assertEquals(expectString6, responseString6, true);


//        //所有都合规
        String requestJson7 = "{\"name\":\"我是一个活动\",\"beginTime\":\"2021-11-11 15:01:02.000\",\"endTime\":\"2021-11-11 15:01:10.000\",\"strategy\":[{\"quantity\":5,\"percentage\":10},{\"quantity\":10,\"percentage\":10}]}";
        String responseString7 = mvc.perform(post("/shops/2/shareactivities").header("authorization", token).contentType("application/json;charset=UTF-8").content(requestJson7))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString7 = "{\"errno\":0,\"data\":{\"id\":139,\"shop\":{\"id\":2,\"name\":\"良耳的商铺\"},\"name\":\"我是一个活动\",\"beginTime\":\"2021-11-11 15:01:02.000\",\"endTime\":\"2021-11-11 15:01:10.000\",\"strategy\":[{\"quantity\":5,\"percentage\":10},{\"quantity\":10,\"percentage\":10}]},\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectString7, responseString7, CUSTOM_COMPARATOR);


        //时间不合规
        String requestJson8 = "{\"name\":\"我是一个活动\",\"beginTime\":\"2021-11-11 15:01:02.000\",\"endTime\":\"2021-11-11 15:01:00.000\",\"strategy\":[{\"quantity\":10,\"percentage\":10},{\"quantity\":10,\"percentage\":10}]}";
        String responseString8 = mvc.perform(post("/shops/2/shareactivities").header("authorization", token).contentType("application/json;charset=UTF-8").content(requestJson8))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString8 = "{\"errno\":503,\"errmsg\":\"开始时间不得早于结束时间\"}";
        JSONAssert.assertEquals(expectString8, responseString8, true);

        //shopId没有
        String requestJson9 = "{\"name\":\"我是一个活动\",\"beginTime\":\"2021-11-11 15:01:02.000\",\"endTime\":\"2021-11-11 15:01:10.000\",\"strategy\":[{\"quantity\":5,\"percentage\":10},{\"quantity\":10,\"percentage\":10}]}";
        String responseString9 = mvc.perform(post("/shops/11/shareactivities").header("authorization", token).contentType("application/json;charset=UTF-8").content(requestJson9))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString9 = "{\"errno\":503,\"errmsg\":\"不存在该商铺\"}";
        JSONAssert.assertEquals(expectString9, responseString9, true);

    }

    /**
     * 查询分享活动 只显示上线状态的分享活动
     * Method: getShareActivity(@RequestParam(name = "shopId", required = false) Long shopId, @RequestParam(name = "productId", required = false) Long productId, @RequestParam(name = "beginTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime beginTime, @RequestParam(name = "endTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime, @RequestParam(name = "page", required = false) Integer page, @RequestParam(name = "pageSize", required = false) Integer pageSize)
     */
    @Test
    public void testGetShareActivity() throws Exception {
        //不添加query时
        String responseString = mvc.perform(get("/shareactivities").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString = "{\"errno\":0,\"data\":{\"total\":0,\"pages\":0,\"pageSize\":10,\"page\":1,\"list\":[]},\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectString, responseString, true);

        //有shopId
        String responseString1 = mvc.perform(get("/shareactivities?shopId=1").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString1 = "{\"errno\":0,\"data\":{\"total\":0,\"pages\":0,\"pageSize\":10,\"page\":1,\"list\":[]},\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectString1, responseString1, true);

        //有productId
        String responseString2 = mvc.perform(get("/shareactivities?productId=1").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString2 = "{\"errno\":0,\"data\":{\"total\":0,\"pages\":0,\"pageSize\":10,\"page\":1,\"list\":[]},\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectString2, responseString2, true);


        //page不合规
        String responseString4 = mvc.perform(get("/shareactivities?page=-1").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString4 = "{\"errno\":503,\"errmsg\":\"页数和页数大小应大于0\"}";
        JSONAssert.assertEquals(expectString4, responseString4, true);

        //pageSize不合规
        String responseString5 = mvc.perform(get("/shareactivities?pageSize=-1").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString5 = "{\"errno\":503,\"errmsg\":\"页数和页数大小应大于0\"}";
        JSONAssert.assertEquals(expectString5, responseString5, true);

        //都合规
        String responseString6 = mvc.perform(get("/shareactivities?shopId=1&productId=1&beginTime=2021-11-11 15:01:02.000&endTime=2021-11-11 15:01:02.000&page=1&pageSize=4").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString6 = "{\"errno\":0,\"data\":{\"total\":0,\"pages\":0,\"pageSize\":4,\"page\":1,\"list\":[]},\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectString6, responseString6, true);

        //shopId<0
        String responseString7 = mvc.perform(get("/shareactivities?shopId=-1&productId=1&beginTime=2021-11-11 15:01:02.000&endTime=2021-11-11 15:01:02.000&page=1&pageSize=4").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString7 = "{\"errno\":503,\"errmsg\":\"shopId错误\"}";
        JSONAssert.assertEquals(expectString7, responseString7, true);


        //productId<0
        String responseString8 = mvc.perform(get("/shareactivities?productId=-1").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString8 = "{\"errno\":503,\"errmsg\":\"productId错误\"}";
        JSONAssert.assertEquals(expectString8, responseString8, true);
    }

    /**
     * 查看分享活动详情 只显示上线状态的分享活动
     * Method: getShareActivityById(@PathVariable(value = "id", required = true) Long id)
     */
    @Test
    public void testGetShareActivityById() throws Exception {
        String responseString = mvc.perform(get("/shareactivities/1").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString = "{\"errno\":0,\"data\":{\"id\":1,\"shop\":{\"id\":1,\"name\":\"甜蜜之旅\"},\"name\":\"分享活动1\",\"beginTime\":\"2021-11-11 15:01:23.000\",\"endTime\":\"2022-02-19 15:01:23.000\",\"strategy\":null},\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectString, responseString, true);

        //没redis
        String responseString1 = mvc.perform(get("/shareactivities/10").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString1 = "{\"errno\":0,\"data\":{\"id\":10,\"shop\":{\"id\":2,\"name\":\"甜蜜之旅\"},\"name\":\"分享活动10\",\"beginTime\":\"2021-11-11 15:01:23.000\",\"endTime\":\"2022-02-19 15:01:23.000\",\"strategy\":null},\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectString1, responseString1, true);


        String responseString3 = mvc.perform(get("/shareactivities/111111").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString3 = "{\"errno\":504,\"errmsg\":\"操作的资源id不存在\"}";
        JSONAssert.assertEquals(expectString3, responseString3, true);
    }

    /**
     * 查看特定分享活动详情,显示所有状态的分享活动
     * Method: getShareActivityByShopIdAndId(@PathVariable("shopId") Long shopId, @PathVariable("id") Long id)
     */
    @Test
    public void testGetShareActivityByShopIdAndId() throws Exception {
        String responseString = mvc.perform(get("/shops/2/shareactivities/1").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString = "{\"errno\":0,\"data\":{\"id\":1,\"shop\":{\"id\":1,\"name\":\"甜蜜之旅\"},\"name\":\"分享活动1\",\"beginTime\":\"2021-11-11 15:01:23.000\",\"endTime\":\"2022-02-19 15:01:23.000\",\"state\":1,\"createdBy\":{\"id\":1,\"name\":\"admin\"},\"gmtCreate\":\"2021-11-11 15:01:23.000\",\"gmtModified\":null,\"modifiedBy\":{\"id\":null,\"name\":null},\"strategy\":null},\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectString, responseString, true);

        //没redis
        String responseString1 = mvc.perform(get("/shops/2/shareactivities/10").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString1 = "{\"errno\":0,\"data\":{\"id\":10,\"shop\":{\"id\":2,\"name\":\"甜蜜之旅\"},\"name\":\"分享活动10\",\"beginTime\":\"2021-11-11 15:01:23.000\",\"endTime\":\"2022-02-19 15:01:23.000\",\"state\":1,\"createdBy\":{\"id\":1,\"name\":\"admin\"},\"gmtCreate\":\"2021-11-11 15:01:23.000\",\"gmtModified\":null,\"modifiedBy\":{\"id\":null,\"name\":null},\"strategy\":null},\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectString1, responseString1, true);


        String responseString3 = mvc.perform(get("/shops/2/shareactivities/11111").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString3 = "{\"errno\":504,\"errmsg\":\"操作的资源id不存在\"}";
        JSONAssert.assertEquals(expectString3, responseString3, true);
    }

}
