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
        //???????????? onsale??????
        ReturnObject<PageInfo<SimpleSaleInfoVO>> onSaleInfoDTO = CreateObject.createOnSaleInfoDTO(1L);
        ReturnObject<PageInfo<SimpleSaleInfoVO>> onSaleInfoDTO1 = CreateObject.createOnSaleInfoDTO(-1L);
        Mockito.when(goodsService.getOnSalesByProductId(1L, 1, 10)).thenReturn(onSaleInfoDTO);
        Mockito.when(goodsService.getOnSalesByProductId(-1L, 1, 10)).thenReturn(onSaleInfoDTO1);
        //????????????shop??????
        ReturnObject<ShopInfoVO> shopInfoDTO = CreateObject.createShopInfoDTO(1L);
        ReturnObject<ShopInfoVO> shopInfoDTO2 = CreateObject.createShopInfoDTO(2L);
        ReturnObject<ShopInfoVO> shopInfoDTO1 = CreateObject.createShopInfoDTO(-1L);
        Mockito.when(shopService.getShop(1L)).thenReturn(shopInfoDTO);
        Mockito.when(shopService.getShop(2L)).thenReturn(shopInfoDTO2);
        Mockito.when(shopService.getShop(11L)).thenReturn(shopInfoDTO1);
        Mockito.when(shopService.getShop(-1L)).thenReturn(shopInfoDTO1);

        //redis??????
        Mockito.when(redisUtil.get("shareactivivybyid_10")).thenReturn(null);
        Mockito.when(redisUtil.get("shareactivivybyid_1")).thenReturn("{\"id\":1,\"shopId\":1,\"shopName\":\"????????????\",\"name\":\"????????????1\",\"beginTime\":[2021,11,11,15,1,23],\"endTime\":[2022,2,19,15,1,23],\"state\":1,\"createdBy\":1,\"createName\":\"admin\",\"modifiedBy\":null,\"modiName\":null,\"gmtCreate\":[2021,11,11,15,1,23],\"gmtModified\":null,\"strategy\":null}");
        Mockito.when(redisUtil.get("shareactivivyid_1_shopid_10")).thenReturn(null);
        Mockito.when(redisUtil.get("shareactivivyid_1_shopid_2")).thenReturn("{\"id\":1,\"shopId\":1,\"shopName\":\"????????????\",\"name\":\"????????????1\",\"beginTime\":[2021,11,11,15,1,23],\"endTime\":[2022,2,19,15,1,23],\"state\":1,\"createdBy\":1,\"createName\":\"admin\",\"modifiedBy\":null,\"modiName\":null,\"gmtCreate\":[2021,11,11,15,1,23],\"gmtModified\":null,\"strategy\":null}");
    }

    /**
     * ?????????????????????????????????
     * Method: getShareState()
     */
    @Test
    public void testGetShareState() throws Exception {
        String responseString = mvc.perform(get("/shareactivities/states"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString = "{\"errno\":0,\"data\":[{\"code\":0,\"name\":\"??????\"},{\"code\":1,\"name\":\"??????\"},{\"code\":2,\"name\":\"??????\"}],\"errmsg\":\"??????\"}";
        JSONAssert.assertEquals(expectString, responseString, true);
    }

    /**
     * ?????????????????????????????????
     * Method: getShareByShopId(@PathVariable(name = "shopId", required = true) Long shopId, @RequestParam(name = "productId", required = false) Long productId, @RequestParam(name = "beginTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime beginTime, @RequestParam(name = "endTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime, @RequestParam(name = "state", required = false) Byte state, @RequestParam(name = "page", required = false) Integer page, @RequestParam(name = "pageSize", required = false) Integer pageSize)
     */
    @Test
    public void testGetShareByShopId() throws Exception {
        //?????????query???
        String responseString = mvc.perform(get("/shops/2/shareactivities").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString = "{\"errno\":0,\"data\":{\"total\":3,\"pages\":1,\"pageSize\":10,\"page\":1,\"list\":[{\"id\":1,\"name\":\"????????????1\"},{\"id\":7,\"name\":\"????????????7\"},{\"id\":10,\"name\":\"????????????10\"}]},\"errmsg\":\"??????\"}";
        JSONAssert.assertEquals(expectString, responseString, true);

        //page pageSize shopId????????????
        String responseString2 = mvc.perform(get("/shops/2/shareactivities?page=-1").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString2 = "{\"errno\":503,\"errmsg\":\"??????????????????????????????0\"}";
        JSONAssert.assertEquals(expectString2, responseString2, true);

        String responseString3 = mvc.perform(get("/shops/2/shareactivities?pageSize=-1").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString3 = "{\"errno\":503,\"errmsg\":\"??????????????????????????????0\"}";
        JSONAssert.assertEquals(expectString3, responseString3, true);

        String responseString4 = mvc.perform(get("/shops/-1/shareactivities").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString4 = "{\"errno\":503,\"errmsg\":\"shopId??????\"}";
        JSONAssert.assertEquals(expectString4, responseString4, true);

        String responseString5 = mvc.perform(get("/shops/2/shareactivities?productId=-1").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString5 = "{\"errno\":503,\"errmsg\":\"productId??????\"}";
        JSONAssert.assertEquals(expectString5, responseString5, true);

        //???????????????query??????????????????
        String responseString6 = mvc.perform(get("/shops/2/shareactivities?productId=1&beginTime=2021-11-11 10:10:10.000&endTime=2023-11-11 16:10:10.000&state=1&page=1&pageSize=3").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString6 = "{\"errno\":0,\"data\":{\"total\":1,\"pages\":1,\"pageSize\":3,\"page\":1,\"list\":[{\"id\":1,\"name\":\"????????????1\"}]},\"errmsg\":\"??????\"}";
        JSONAssert.assertEquals(expectString6, responseString6, true);


        String responseString7 = mvc.perform(get("/shops/1111111/shareactivities").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString7 = "{\"errno\":0,\"errmsg\":\"??????\",\"data\":{\"total\":0,\"pages\":0,\"pageSize\":10,\"page\":1,\"list\":[]}}";
        JSONAssert.assertEquals(expectString7, responseString7, true);
    }

    /**
     * ???????????????????????????
     * Method: addShareAct(@PathVariable(value = "shopId", required = true) Long shopId, @Validated @RequestBody ShareActivityVo shareActivityDTO, BindingResult bindingResult)
     */
    @Test
    @Transactional(rollbackFor = Exception.class)
    public void testAddShareAct() throws Exception {
        CustomComparator CUSTOM_COMPARATOR = new CustomComparator(JSONCompareMode.LENIENT,
                new Customization("data.id", (o1, o2) -> true));
        String requestJson = "{\"name\":\"String\",\"beginTime\":\"2021-11-11 15:01:02.000\",\"endTime\":\"2021-11-11 15:01:10.000\",\"strategy\":[{\"quantity\":10,\"percentage\":10},{\"quantity\":10,\"percentage\":10}]}";
//        //???????????????query??????????????????
        String responseString = mvc.perform(post("/shops/2/shareactivities").header("authorization", token).contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString = "{\"errno\":0,\"data\":{\"id\":138,\"shop\":{\"id\":2,\"name\":\"???????????????\"},\"name\":\"String\",\"beginTime\":\"2021-11-11 15:01:02.000\",\"endTime\":\"2021-11-11 15:01:10.000\",\"strategy\":[{\"quantity\":10,\"percentage\":10},{\"quantity\":10,\"percentage\":10}]},\"errmsg\":\"??????\"}";
        JSONAssert.assertEquals(expectString, responseString, CUSTOM_COMPARATOR);


        //???????????????null
        String requestJson1 = "{\"name\":\"\",\"beginTime\":\"2021-11-11 15:01:02.000\",\"endTime\":\"2021-11-11 15:01:10.000\",\"strategy\":[{\"quantity\":10,\"percentage\":10},{\"quantity\":10,\"percentage\":10}]}";
        String responseString1 = mvc.perform(post("/shops/2/shareactivities").header("authorization", token).contentType("application/json;charset=UTF-8").content(requestJson1))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString1 = "{\"errno\":503,\"errmsg\":\"??????????????????????????????;\"}";
        JSONAssert.assertEquals(expectString1, responseString1, true);


        //????????????
        String requestJson2 = "{\"name\":\"??????????????????\",\"beginTime\":\"2021-11-11 15:01:02.000\",\"endTime\":null,\"strategy\":[{\"quantity\":10,\"percentage\":10},{\"quantity\":10,\"percentage\":10}]}";
        String responseString2 = mvc.perform(post("/shops/2/shareactivities").header("authorization", token).contentType("application/json;charset=UTF-8").content(requestJson2))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString2 = "{\"errno\":503,\"errmsg\":\"????????????????????????;\"}";
        JSONAssert.assertEquals(expectString2, responseString2, true);


        //?????????????????????
        String requestJson4 = "{\"name\":\"??????????????????\",\"beginTime\":\"2021-11-11 15:01:02.000\",\"endTime\":\"2021-11-11 15:01:10.000\",\"strategy\":[{\"quantity\":null,\"percentage\":10},{\"quantity\":10,\"percentage\":10}]}";
        String responseString4 = mvc.perform(post("/shops/2/shareactivities").header("authorization", token).contentType("application/json;charset=UTF-8").content(requestJson4))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();
        String expectString4 = "{\"errno\":503,\"errmsg\":\"??????????????????;\"}";
        JSONAssert.assertEquals(expectString4, responseString4, true);


        String requestJson5 = "{\"name\":\"??????????????????\",\"beginTime\":\"2021-11-11 15:01:02.000\",\"endTime\":\"2021-11-11 15:01:10.000\",\"strategy\":[{\"quantity\":-5,\"percentage\":10},{\"quantity\":10,\"percentage\":10}]}";
        String responseString5 = mvc.perform(post("/shops/2/shareactivities").header("authorization", token).contentType("application/json;charset=UTF-8").content(requestJson5))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();
        String expectString5 = "{\"errno\":503,\"errmsg\":\"???????????????????????????0;\"}";
        JSONAssert.assertEquals(expectString5, responseString5, true);


        String requestJson6 = "{\"name\":\"??????????????????\",\"beginTime\":\"2021-11-11 15:01:02.000\",\"endTime\":\"2021-11-11 15:01:10.000\",\"strategy\":[{\"quantity\":5,\"percentage\":110},{\"quantity\":10,\"percentage\":10}]}";
        String responseString6 = mvc.perform(post("/shops/2/shareactivities").header("authorization", token).contentType("application/json;charset=UTF-8").content(requestJson6))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse().getContentAsString();
        String expectString6 = "{\"errno\":503,\"errmsg\":\"???????????????????????????0???100??????;\"}";
        JSONAssert.assertEquals(expectString6, responseString6, true);


//        //???????????????
        String requestJson7 = "{\"name\":\"??????????????????\",\"beginTime\":\"2021-11-11 15:01:02.000\",\"endTime\":\"2021-11-11 15:01:10.000\",\"strategy\":[{\"quantity\":5,\"percentage\":10},{\"quantity\":10,\"percentage\":10}]}";
        String responseString7 = mvc.perform(post("/shops/2/shareactivities").header("authorization", token).contentType("application/json;charset=UTF-8").content(requestJson7))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString7 = "{\"errno\":0,\"data\":{\"id\":139,\"shop\":{\"id\":2,\"name\":\"???????????????\"},\"name\":\"??????????????????\",\"beginTime\":\"2021-11-11 15:01:02.000\",\"endTime\":\"2021-11-11 15:01:10.000\",\"strategy\":[{\"quantity\":5,\"percentage\":10},{\"quantity\":10,\"percentage\":10}]},\"errmsg\":\"??????\"}";
        JSONAssert.assertEquals(expectString7, responseString7, CUSTOM_COMPARATOR);


        //???????????????
        String requestJson8 = "{\"name\":\"??????????????????\",\"beginTime\":\"2021-11-11 15:01:02.000\",\"endTime\":\"2021-11-11 15:01:00.000\",\"strategy\":[{\"quantity\":10,\"percentage\":10},{\"quantity\":10,\"percentage\":10}]}";
        String responseString8 = mvc.perform(post("/shops/2/shareactivities").header("authorization", token).contentType("application/json;charset=UTF-8").content(requestJson8))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString8 = "{\"errno\":503,\"errmsg\":\"????????????????????????????????????\"}";
        JSONAssert.assertEquals(expectString8, responseString8, true);

        //shopId??????
        String requestJson9 = "{\"name\":\"??????????????????\",\"beginTime\":\"2021-11-11 15:01:02.000\",\"endTime\":\"2021-11-11 15:01:10.000\",\"strategy\":[{\"quantity\":5,\"percentage\":10},{\"quantity\":10,\"percentage\":10}]}";
        String responseString9 = mvc.perform(post("/shops/11/shareactivities").header("authorization", token).contentType("application/json;charset=UTF-8").content(requestJson9))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString9 = "{\"errno\":503,\"errmsg\":\"??????????????????\"}";
        JSONAssert.assertEquals(expectString9, responseString9, true);

    }

    /**
     * ?????????????????? ????????????????????????????????????
     * Method: getShareActivity(@RequestParam(name = "shopId", required = false) Long shopId, @RequestParam(name = "productId", required = false) Long productId, @RequestParam(name = "beginTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime beginTime, @RequestParam(name = "endTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime, @RequestParam(name = "page", required = false) Integer page, @RequestParam(name = "pageSize", required = false) Integer pageSize)
     */
    @Test
    public void testGetShareActivity() throws Exception {
        //?????????query???
        String responseString = mvc.perform(get("/shareactivities").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString = "{\"errno\":0,\"data\":{\"total\":0,\"pages\":0,\"pageSize\":10,\"page\":1,\"list\":[]},\"errmsg\":\"??????\"}";
        JSONAssert.assertEquals(expectString, responseString, true);

        //???shopId
        String responseString1 = mvc.perform(get("/shareactivities?shopId=1").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString1 = "{\"errno\":0,\"data\":{\"total\":0,\"pages\":0,\"pageSize\":10,\"page\":1,\"list\":[]},\"errmsg\":\"??????\"}";
        JSONAssert.assertEquals(expectString1, responseString1, true);

        //???productId
        String responseString2 = mvc.perform(get("/shareactivities?productId=1").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString2 = "{\"errno\":0,\"data\":{\"total\":0,\"pages\":0,\"pageSize\":10,\"page\":1,\"list\":[]},\"errmsg\":\"??????\"}";
        JSONAssert.assertEquals(expectString2, responseString2, true);


        //page?????????
        String responseString4 = mvc.perform(get("/shareactivities?page=-1").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString4 = "{\"errno\":503,\"errmsg\":\"??????????????????????????????0\"}";
        JSONAssert.assertEquals(expectString4, responseString4, true);

        //pageSize?????????
        String responseString5 = mvc.perform(get("/shareactivities?pageSize=-1").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString5 = "{\"errno\":503,\"errmsg\":\"??????????????????????????????0\"}";
        JSONAssert.assertEquals(expectString5, responseString5, true);

        //?????????
        String responseString6 = mvc.perform(get("/shareactivities?shopId=1&productId=1&beginTime=2021-11-11 15:01:02.000&endTime=2021-11-11 15:01:02.000&page=1&pageSize=4").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString6 = "{\"errno\":0,\"data\":{\"total\":0,\"pages\":0,\"pageSize\":4,\"page\":1,\"list\":[]},\"errmsg\":\"??????\"}";
        JSONAssert.assertEquals(expectString6, responseString6, true);

        //shopId<0
        String responseString7 = mvc.perform(get("/shareactivities?shopId=-1&productId=1&beginTime=2021-11-11 15:01:02.000&endTime=2021-11-11 15:01:02.000&page=1&pageSize=4").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString7 = "{\"errno\":503,\"errmsg\":\"shopId??????\"}";
        JSONAssert.assertEquals(expectString7, responseString7, true);


        //productId<0
        String responseString8 = mvc.perform(get("/shareactivities?productId=-1").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString8 = "{\"errno\":503,\"errmsg\":\"productId??????\"}";
        JSONAssert.assertEquals(expectString8, responseString8, true);
    }

    /**
     * ???????????????????????? ????????????????????????????????????
     * Method: getShareActivityById(@PathVariable(value = "id", required = true) Long id)
     */
    @Test
    public void testGetShareActivityById() throws Exception {
        String responseString = mvc.perform(get("/shareactivities/1").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString = "{\"errno\":0,\"data\":{\"id\":1,\"shop\":{\"id\":1,\"name\":\"????????????\"},\"name\":\"????????????1\",\"beginTime\":\"2021-11-11 15:01:23.000\",\"endTime\":\"2022-02-19 15:01:23.000\",\"strategy\":null},\"errmsg\":\"??????\"}";
        JSONAssert.assertEquals(expectString, responseString, true);

        //???redis
        String responseString1 = mvc.perform(get("/shareactivities/10").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString1 = "{\"errno\":0,\"data\":{\"id\":10,\"shop\":{\"id\":2,\"name\":\"????????????\"},\"name\":\"????????????10\",\"beginTime\":\"2021-11-11 15:01:23.000\",\"endTime\":\"2022-02-19 15:01:23.000\",\"strategy\":null},\"errmsg\":\"??????\"}";
        JSONAssert.assertEquals(expectString1, responseString1, true);


        String responseString3 = mvc.perform(get("/shareactivities/111111").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString3 = "{\"errno\":504,\"errmsg\":\"???????????????id?????????\"}";
        JSONAssert.assertEquals(expectString3, responseString3, true);
    }

    /**
     * ??????????????????????????????,?????????????????????????????????
     * Method: getShareActivityByShopIdAndId(@PathVariable("shopId") Long shopId, @PathVariable("id") Long id)
     */
    @Test
    public void testGetShareActivityByShopIdAndId() throws Exception {
        String responseString = mvc.perform(get("/shops/2/shareactivities/1").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString = "{\"errno\":0,\"data\":{\"id\":1,\"shop\":{\"id\":1,\"name\":\"????????????\"},\"name\":\"????????????1\",\"beginTime\":\"2021-11-11 15:01:23.000\",\"endTime\":\"2022-02-19 15:01:23.000\",\"state\":1,\"createdBy\":{\"id\":1,\"name\":\"admin\"},\"gmtCreate\":\"2021-11-11 15:01:23.000\",\"gmtModified\":null,\"modifiedBy\":{\"id\":null,\"name\":null},\"strategy\":null},\"errmsg\":\"??????\"}";
        JSONAssert.assertEquals(expectString, responseString, true);

        //???redis
        String responseString1 = mvc.perform(get("/shops/2/shareactivities/10").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString1 = "{\"errno\":0,\"data\":{\"id\":10,\"shop\":{\"id\":2,\"name\":\"????????????\"},\"name\":\"????????????10\",\"beginTime\":\"2021-11-11 15:01:23.000\",\"endTime\":\"2022-02-19 15:01:23.000\",\"state\":1,\"createdBy\":{\"id\":1,\"name\":\"admin\"},\"gmtCreate\":\"2021-11-11 15:01:23.000\",\"gmtModified\":null,\"modifiedBy\":{\"id\":null,\"name\":null},\"strategy\":null},\"errmsg\":\"??????\"}";
        JSONAssert.assertEquals(expectString1, responseString1, true);


        String responseString3 = mvc.perform(get("/shops/2/shareactivities/11111").header("authorization", token).contentType("application/json;charset=UTF-8"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expectString3 = "{\"errno\":504,\"errmsg\":\"???????????????id?????????\"}";
        JSONAssert.assertEquals(expectString3, responseString3, true);
    }

}
