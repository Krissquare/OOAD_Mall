package cn.edu.xmu.oomall.goods.controller;


import cn.edu.xmu.oomall.core.util.RedisUtil;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.goods.GoodsApplication;
import cn.edu.xmu.oomall.goods.microservice.ShareService;
import cn.edu.xmu.oomall.goods.microservice.ShopService;
import cn.edu.xmu.oomall.goods.microservice.bo.ActInfo;
import cn.edu.xmu.oomall.goods.microservice.bo.ShopInfo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * OnsaleController Tester.
 *
 * @author <Authors name>
 * @version 1.0
 * @since <pre>11月 11, 2021</pre>
 */


@AutoConfigureMockMvc
@Transactional
@SpringBootTest(classes = GoodsApplication.class)
public class OnSaleControllerTest {

    @Autowired
    protected WebApplicationContext wac;
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ShareService shareService;

    @MockBean
    private ShopService shopService;

    @MockBean
    private RedisUtil redisUtil;



    /**
     * Method: searchOnSale(@PathVariable Long shopId, @PathVariable Long id, @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue="1") Integer page)
     */
    @Test
    public void testSearchOnSale() throws Exception {

        Mockito.when(redisUtil.get("p_"+2549L)).thenReturn(null);

        String res = this.mvc.perform(get("/shops/0/products/2549/onsales").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        System.out.println(res);
        String expect="{\"errno\":0,\"data\":{\"page\":1,\"pageSize\":1,\"total\":1,\"pages\":1,\"list\":[{\"id\":1000,\"price\":7429,\"beginTime\":\"2021-11-11T14:38:20\",\"endTime\":\"2022-02-19T14:38:20\",\"quantity\":38}]},\"errmsg\":\"成功\"}\n";
        JSONAssert.assertEquals(expect, res,true);

    }

    /**
     * Method: getOnSaleDetail(@PathVariable Long shopId, @PathVariable Long id)
     */
    @Test
    public void testGetOnSaleDetail() throws Exception {


        ShopInfo info = new ShopInfo(2L,"甜蜜之旅");
        ShopInfo info2=new ShopInfo(4L,"努力向前");
        ShopInfo info1 = new ShopInfo(null,null);

        ActInfo actInfo =new ActInfo(null,null);
        ActInfo actInfo1 =new ActInfo(1L,null);


        Mockito.when(shopService.getInfo(2L)).thenReturn(new ReturnObject(info));
        Mockito.when(shopService.getInfo(4L)).thenReturn(new ReturnObject(info2));
        Mockito.when(shopService.getInfo(null)).thenReturn(new ReturnObject(info1));
        Mockito.when(shareService.getInfo(null)).thenReturn(new ReturnObject(actInfo));

        Mockito.when(redisUtil.get("o_"+996L)).thenReturn(null);
        Mockito.when(redisUtil.get("o_"+3L)).thenReturn(null);


        String res = this.mvc.perform(get("/shops/2/onsales/996").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        String expect="{\"errno\":0,\"data\":{\"id\":996,\"shop\":{\"id\":2,\"name\":\"甜蜜之旅\"},\"product\":{\"id\":2545,\"name\":\"蒙牛金装1段\",\"imageUrl\":null},\"price\":7451,\"beginTime\":\"2021-11-11T14:38:20\",\"endTime\":\"2022-02-19T14:38:20\",\"quantity\":23,\"type\":0,\"shareAct\":{\"id\":null,\"name\":null},\"createdBy\":{\"id\":1,\"username\":\"admin\"},\"gmtCreate\":\"2021-11-11T14:38:20\",\"gmtModified\":\"null\",\"modifiedBy\":{\"id\":null,\"username\":null}},\"errmsg\":\"成功\"}\n";
        JSONAssert.assertEquals(expect, res,true);
        // 非普通或秒杀
        res = this.mvc.perform(get("/shops/4/onsales/3").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isForbidden()).andReturn().getResponse().getContentAsString();
        expect="{\"errno\": 505,\"errmsg\": \"只能处理普通或秒杀类型\"}";
        JSONAssert.assertEquals(expect, res,true);
    }


    @Test
    public void testSearchOnAct() throws Exception{

        //        正常
        String res = this.mvc.perform(get("/internal/activities/6/onsales")
        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        System.out.println(res);
        String expect="{\"errno\":0,\"data\":{\"page\":1,\"pageSize\":2,\"total\":2,\"pages\":1,\"list\":[{\"id\":2,\"price\":473,\"beginTime\":\"2021-11-11T14:38:20\",\"endTime\":\"2022-02-19T14:38:20\",\"quantity\":97},{\"id\":68,\"price\":73338,\"beginTime\":\"2021-11-11T14:38:20\",\"endTime\":\"2022-02-19T14:38:20\",\"quantity\":53}]},\"errmsg\":\"成功\"}\n";
        JSONAssert.assertEquals(expect, res,true);
    }


    @Test
    public void testSearchOnSaleShare() throws Exception{
        //  正常
       String res = this.mvc.perform(get("/internal/shareactivities/1/onsales")
        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        String expect="{\"errno\":0,\"data\":{\"page\":1,\"pageSize\":1,\"total\":1,\"pages\":1,\"list\":[{\"id\":9,\"price\":6985,\"beginTime\":\"2021-11-11T14:38:20\",\"endTime\":\"2022-02-19T14:38:20\",\"quantity\":78}]},\"errmsg\":\"成功\"}\n";
        JSONAssert.assertEquals(expect, res,true);
    }

    @Test
    public void testSearchOnSaleById() throws Exception{

        ShopInfo info = new ShopInfo(2L,"甜蜜之旅");
        ActInfo actInfo =new ActInfo(null,null);

        Mockito.when(shopService.getInfo(2L)).thenReturn(new ReturnObject(info));
        Mockito.when(shareService.getInfo(null)).thenReturn(new ReturnObject(actInfo));
        Mockito.when(redisUtil.get("o_"+996L)).thenReturn(null);


        String res = this.mvc.perform(get("/internal/onsales/996").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        String expect="{\"errno\":0,\"data\":{\"id\":996,\"shop\":{\"id\":2,\"name\":\"甜蜜之旅\"},\"product\":{\"id\":2545,\"name\":\"蒙牛金装1段\",\"imageUrl\":null},\"price\":7451,\"beginTime\":\"2021-11-11T14:38:20\",\"endTime\":\"2022-02-19T14:38:20\",\"quantity\":23,\"type\":0,\"shareAct\":{\"id\":null,\"name\":null},\"createdBy\":{\"id\":1,\"username\":\"admin\"},\"gmtCreate\":\"2021-11-11T14:38:20\",\"gmtModified\":\"null\",\"modifiedBy\":{\"id\":null,\"username\":null}},\"errmsg\":\"成功\"}\n";
        JSONAssert.assertEquals(expect, res,true);
    }


    @Test
    public void testSearchOnSaleAll() throws Exception {

        Mockito.when(redisUtil.get("p_"+2549L)).thenReturn(null);

        //        正常
        String res = this.mvc.perform(get("/internal/products/2549/onsales").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn()
                .getResponse().getContentAsString();
        String expect="{\"errno\":0,\"data\":{\"page\":1,\"pageSize\":1,\"total\":1,\"pages\":1,\"list\":[{\"id\":1000,\"price\":7429,\"beginTime\":\"2021-11-11T14:38:20\",\"endTime\":\"2022-02-19T14:38:20\",\"quantity\":38}]},\"errmsg\":\"成功\"}\n";
        JSONAssert.assertEquals(expect, res,true);
    }







}
