package cn.edu.xmu.oomall.goods.controller;


import cn.edu.xmu.oomall.goods.GoodsApplication;
import cn.edu.xmu.oomall.goods.model.vo.NewOnSaleVo;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

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

    /**
     * Method: createOnsale(@Validated @PathVariable Long shopId, @PathVariable Long id, @Validated @RequestBody OnsaleVo onsaleVO, Long loginUserId, String loginUserName, BindingResult bindingResult)
     * post:
     * {
     * "price": 0,
     * "beginTime": "string",
     * "endTime": "string",
     * "quantity": 0,
     * "type": "string"
     * }
     * <p>
     * response:
     * {
     * "errno": 0,
     * "errmsg": "成功",
     * "data": {
     * "id": 0,
     * "price": 0,
     * "beginTime": "string",
     * "endTime": "string",
     * "quantity": 0
     * }
     * }
     */
    @Test
    public void testCreateOnsale() throws Exception {
//TODO: Test goes here...
        // 正常=》
        NewOnSaleVo vo=new NewOnSaleVo();
        vo.setType(0);
        JSONObject input = new JSONObject();
        input.put("price", 1000L);
        input.put("beginTime", "2022-10-11 15:20:30");
        input.put("endTime", "2022-10-12 16:20:30");
        input.put("quantity",10);
        input.put("type", 0);
        String s = input.toJSONString();
        String res = this.mvc.perform(post("/shops/3/products/2532/onsales")
                .contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        // 商品销售时间冲突=》
        input = new JSONObject();
        input.put("price", 1000L);
        input.put("beginTime", "2021-11-12 09:30:30");
        input.put("endTime", "2022-10-12 09:40:30");
        input.put("quantity",10);
        input.put("type", 0);
        s = input.toJSONString();
        res = this.mvc.perform(post("/shops/2/products/2549/onsales")
                .contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk()).andReturn()
                .getResponse().getContentAsString();

        String expect="{\"errno\": 902,\"errmsg\": \"商品销售时间冲突。\"}";
        JSONAssert.assertEquals(expect, res,true);


//        开始时间晚于结束时间
        input = new JSONObject();
        input.put("price", 1000L);
        input.put("beginTime", "2028-03-11 15:30:30");
        input.put("endTime", "2028-02-12 16:20:30");
        input.put("quantity",10);
        input.put("type", 0);
        s = input.toJSONString();
        res = this.mvc.perform(post("/shops/2/products/2549/onsales")
                .contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk()).andReturn()
                .getResponse().getContentAsString();
        expect="{\"errno\": 947,\"errmsg\": \"开始时间晚于结束时间。\"}";
        JSONAssert.assertEquals(expect, res,true);



//        非普通或秒杀
        input = new JSONObject();
        input.put("price", 1000L);
        input.put("beginTime", "2028-03-11 15:30:30");
        input.put("endTime", "2029-02-12 16:20:30");
        input.put("quantity",10);
        input.put("type", 3);
        s = input.toJSONString();
        res = this.mvc.perform(post("/shops/2/products/2549/onsales")
                .contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isForbidden()).andReturn()
                .getResponse().getContentAsString();
        expect="{\"errno\": 505,\"errmsg\": \"限定处理普通或秒杀。\"}";
        JSONAssert.assertEquals(expect, res,true);


        //        货品不存在
        input = new JSONObject();
        input.put("price", 1000L);
        input.put("beginTime", "2028-03-11 15:30:30");
        input.put("endTime", "2029-02-12 16:20:30");
        input.put("quantity",10);
        input.put("type", 0);
        s = input.toJSONString();
        res = this.mvc.perform(post("/shops/2/products/999999/onsales")
                .contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isNotFound()).andReturn()
                .getResponse().getContentAsString();
        expect="{\"errno\": 504,\"errmsg\": \"货品id不存在。\"}";
        JSONAssert.assertEquals(expect, res,true);


        //货品非该商家
        input = new JSONObject();
        input.put("price", 1000L);
        input.put("beginTime", "2028-03-11 15:30:30");
        input.put("endTime", "2029-02-12 16:20:30");
        input.put("quantity",10);
        input.put("type", 0);
        s = input.toJSONString();
        res = this.mvc.perform(post("/shops/2/products/2532/onsales")
                .contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isForbidden()).andReturn()
                .getResponse().getContentAsString();
        expect="{\"errno\": 505,\"errmsg\": \"该货品不属于该商铺。\"}";
        JSONAssert.assertEquals(expect, res,true);

    }

    /**
     * Method: onlineOnSale(@PathVariable Long shopId, @PathVariable Long id, Long loginUserId, String loginUserName)
     */
    @Test
    public void testOnlineOnSale() throws Exception {
//TODO: Test goes here...

        //        正常
        String res = this.mvc.perform(put("/shops/2/onsales/3913/online").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn()
                .getResponse().getContentAsString();
        String expect="{\"errno\": 0,\"errmsg\": \"成功\"}";
        JSONAssert.assertEquals(expect, res,true);


//        不存在价格浮动
        res = this.mvc.perform(put("/shops/3/onsales/66666/online").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()).andReturn().getResponse().getContentAsString();
        expect="{\"errno\": 504,\"errmsg\": \"不存在该价格浮动\"}";
        JSONAssert.assertEquals(expect, res,true);


        //只能处理秒杀、普通订单
        res = this.mvc.perform(put("/shops/3/onsales/2/online").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isForbidden()).andReturn().getResponse().getContentAsString();
        expect="{\"errno\": 505,\"errmsg\": \"只能处理普通和秒杀类型\"}";
        JSONAssert.assertEquals(expect, res,true);


        //草稿态才能上线
        res = this.mvc.perform(put("/shops/10/onsales/1/online").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        expect="{\"errno\": 507,\"errmsg\": \"非草稿态无法上线\"}";
        JSONAssert.assertEquals(expect, res,true);


    }

    /**
     * Method: offlineOnSale(@PathVariable Long shopId, @PathVariable Long id, Long loginUserId, String loginUserName)
     */
    @Test
    public void testOfflineOnSale() throws Exception {
//TODO: Test goes here...
        //        正常
        String res = this.mvc.perform(put("/shops/10/onsales/1/offline").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

        String expect="{\"errno\": 0,\"errmsg\": \"成功\"}";
        JSONAssert.assertEquals(expect, res,true);



        //        不存在价格浮动
        res = this.mvc.perform(put("/shops/3/onsales/66666/offline").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()).andReturn().getResponse().getContentAsString();
        expect="{\"errno\": 504,\"errmsg\": \"不存在该价格浮动\"}";
        JSONAssert.assertEquals(expect, res,true);


        //只能处理秒杀、普通订单
        res = this.mvc.perform(put("/shops/3/onsales/2/offline").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isForbidden()).andReturn().getResponse().getContentAsString();
        expect="{\"errno\": 505,\"errmsg\": \"只能处理普通和秒杀类型\"}";
        JSONAssert.assertEquals(expect, res,true);

        //上线态才能下线
        res = this.mvc.perform(put("/shops/2/onsales/3913/offline").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        expect="{\"errno\": 507,\"errmsg\": \"非上线态无法下线\"}";
        JSONAssert.assertEquals(expect, res,true);

    }

    /**
     * Method: searchOnSale(@PathVariable Long shopId, @PathVariable Long id, @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue="1") Integer page)
     */
    @Test
    public void testSearchOnSale() throws Exception {

        String res = this.mvc.perform(get("/shops/0/products/2549/onsales").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        System.out.println(res);
        String expect="{\"errno\":0,\"data\":{\"page\":1,\"pageSize\":3,\"total\":3,\"pages\":1,\"list\":[{\"id\":1000,\"price\":7429,\"beginTime\":\"2021-11-11T14:38:20\",\"endTime\":\"2022-02-19T14:38:20\",\"quantity\":38},{\"id\":3913,\"price\":6666,\"beginTime\":\"2021-11-12T08:39:31\",\"endTime\":\"2021-11-12T09:39:39\",\"quantity\":20},{\"id\":3940,\"price\":2233,\"beginTime\":\"2021-08-17T20:20:30\",\"endTime\":\"2021-08-17T21:20:20\",\"quantity\":100}]},\"errmsg\":\"成功\"}\n";
        JSONAssert.assertEquals(expect, res,true);

    }

    /**
     * Method: getOnSaleDetail(@PathVariable Long shopId, @PathVariable Long id)
     */
    @Test
    public void testGetOnSaleDetail() throws Exception {


        String res = this.mvc.perform(get("/shops/0/onsales/996").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        String expect="{\"errno\":0,\"data\":{\"id\":996,\"shop\":{\"id\":2,\"name\":\"mock-shop-name\"},\"product\":{\"id\":2545,\"name\":\"蒙牛金装1段\",\"imageUrl\":null},\"price\":7451,\"beginTime\":\"2021-11-11T14:38:20\",\"endTime\":\"2022-02-19T14:38:20\",\"quantity\":23,\"type\":0,\"shareAct\":{\"id\":null,\"name\":\"mock-act-name\"},\"createdBy\":{\"id\":1,\"username\":\"admin\"},\"gmtCreate\":\"2021-11-11T14:38:20\",\"gmtModified\":\"null\",\"modifiedBy\":{\"id\":null,\"username\":null}},\"errmsg\":\"成功\"}\n";
        JSONAssert.assertEquals(expect, res,true);
        // 非普通或秒杀
        res = this.mvc.perform(get("/shops/0/onsales/3").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isForbidden()).andReturn().getResponse().getContentAsString();
        expect="{\"errno\": 505,\"errmsg\": \"只能处理普通或秒杀类型\"}";
        JSONAssert.assertEquals(expect, res,true);

    }


    @Test
    public void testOnlineOnSaleGroPre() throws Exception {
//TODO: Test goes here...

        //        正常
        String res = this.mvc.perform(put("/internal/onsales/3945/online").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        String expect="{\"errno\": 0,\"errmsg\": \"成功\"}";
        JSONAssert.assertEquals(expect, res,true);


//        不存在价格浮动
        res = this.mvc.perform(put("/internal/onsales/66666/online").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()).andReturn().getResponse().getContentAsString();
        expect="{\"errno\": 504,\"errmsg\": \"不存在该价格浮动\"}";
        JSONAssert.assertEquals(expect, res,true);

        //只能处理团购预售
        res = this.mvc.perform(put("/internal/onsales/3913/online").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isForbidden()).andReturn().getResponse().getContentAsString();
        expect="{\"errno\": 505,\"errmsg\": \"只能处理团购和预售类型\"}";
        JSONAssert.assertEquals(expect, res,true);

        //草稿态才能上线
        res = this.mvc.perform(put("/internal/onsales/68/online").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        expect="{\"errno\": 507,\"errmsg\": \"非草稿态无法上线\"}";
        JSONAssert.assertEquals(expect, res,true);


    }

    /**
     * Method: offlineOnSale(@PathVariable Long shopId, @PathVariable Long id, Long loginUserId, String loginUserName)
     */
    @Test
    public void testOfflineOnSaleGP() throws Exception {
        //        正常
        String res = this.mvc.perform(put("/internal/onsales/68/offline").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        String expect="{\"errno\": 0,\"errmsg\": \"成功\"}";
        JSONAssert.assertEquals(expect, res,true);


        //        不存在价格浮动
        res = this.mvc.perform(put("/internal/onsales/66666/offline").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()).andReturn().getResponse().getContentAsString();
        expect="{\"errno\": 504,\"errmsg\": \"不存在该价格浮动\"}";
        JSONAssert.assertEquals(expect, res,true);


        //只能处理团购预售订单
        res = this.mvc.perform(put("/internal/onsales/3913/offline").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isForbidden()).andReturn().getResponse().getContentAsString();
        expect="{\"errno\": 505,\"errmsg\": \"只能处理团购和预售类型\"}";
        JSONAssert.assertEquals(expect, res,true);

        //上线态才能下线
        res = this.mvc.perform(put("/internal/onsales/3946/offline").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        expect="{\"errno\": 507,\"errmsg\": \"非上线态无法下线\"}";
        JSONAssert.assertEquals(expect, res,true);

    }

    @Test
    public void testSearchOnSaleGro() throws Exception{
        //        正常
        String res = this.mvc.perform(get("/internal/grouponactivities/6/onsales")
        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        System.out.println(res);
        String expect="{\"errno\":0,\"data\":{\"page\":1,\"pageSize\":4,\"total\":4,\"pages\":1,\"list\":[{\"id\":2,\"price\":473,\"beginTime\":\"2021-11-11T14:38:20\",\"endTime\":\"2022-02-19T14:38:20\",\"quantity\":97},{\"id\":68,\"price\":73338,\"beginTime\":\"2021-11-11T14:38:20\",\"endTime\":\"2022-02-19T14:38:20\",\"quantity\":53},{\"id\":3945,\"price\":4444,\"beginTime\":\"2023-04-21T23:38:27\",\"endTime\":\"2026-05-27T23:38:32\",\"quantity\":44},{\"id\":3946,\"price\":4444,\"beginTime\":\"2023-04-21T23:38:27\",\"endTime\":\"2026-05-27T23:38:32\",\"quantity\":44}]},\"errmsg\":\"成功\"}\n";
        JSONAssert.assertEquals(expect, res,true);


    }

    @Test
    public void testSearchOnSaleAdv() throws Exception{
        //        正常
        String res = this.mvc.perform(get("/internal/advacnesaleactivities/1/onsales")
        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        System.out.println(res);
        String expect="{\"errno\":0,\"data\":{\"page\":1,\"pageSize\":4,\"total\":4,\"pages\":1,\"list\":[{\"id\":3,\"price\":12650,\"beginTime\":\"2021-11-11T14:38:20\",\"endTime\":\"2022-02-19T14:38:20\",\"quantity\":26},{\"id\":40,\"price\":46784,\"beginTime\":\"2021-11-11T14:38:20\",\"endTime\":\"2022-02-19T14:38:20\",\"quantity\":84},{\"id\":47,\"price\":10459,\"beginTime\":\"2021-11-11T14:38:20\",\"endTime\":\"2022-02-19T14:38:20\",\"quantity\":94},{\"id\":50,\"price\":17246,\"beginTime\":\"2021-11-11T14:38:20\",\"endTime\":\"2022-02-19T14:38:20\",\"quantity\":54}]},\"errmsg\":\"成功\"}\n";
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
        //        正常
        String res = this.mvc.perform(get("/internal/onsales/1")
        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        String expect="{\"errno\":0,\"data\":{\"id\":1,\"shop\":{\"id\":10,\"name\":\"mock-shop-name\"},\"product\":{\"id\":1550,\"name\":\"欢乐家久宝桃罐头\",\"imageUrl\":null},\"price\":53295,\"beginTime\":\"2021-11-11T14:38:20\",\"endTime\":\"2022-02-19T14:38:20\",\"quantity\":26,\"type\":0,\"shareAct\":{\"id\":null,\"name\":\"mock-act-name\"},\"createdBy\":{\"id\":1,\"username\":\"admin\"},\"gmtCreate\":\"2021-11-11T14:38:20\",\"gmtModified\":\"null\",\"modifiedBy\":{\"id\":null,\"username\":null}},\"errmsg\":\"成功\"}\n";
        JSONAssert.assertEquals(expect, res,true);
    }

    @Test
    public void testCreateAllOnSale() throws Exception {
        // 正常=》
        NewOnSaleVo vo=new NewOnSaleVo();
        vo.setType(0);
        JSONObject input = new JSONObject();
        input.put("price", 1000L);
        input.put("beginTime", "2022-10-11 15:20:30");
        input.put("endTime", "2022-10-12 16:20:30");
        input.put("quantity",10);
        input.put("type", 0);
        String s = input.toJSONString();
        String res = this.mvc.perform(post("/internal/products/2532/onsales")
                .contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isCreated()).andReturn()
                .getResponse().getContentAsString();



        // 商品销售时间冲突=》
        input = new JSONObject();
        input.put("price", 1000L);
        input.put("beginTime", "2021-11-12 09:30:30");
        input.put("endTime", "2022-10-12 09:40:30");
        input.put("quantity",10);
        input.put("type", 0);
        s = input.toJSONString();
        res = this.mvc.perform(post("/internal/products/2549/onsales")
                .contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk()).andReturn()
                .getResponse().getContentAsString();
        String expect="{\"errno\": 902,\"errmsg\": \"商品销售时间冲突。\"}";
        JSONAssert.assertEquals(expect, res,true);


//        开始时间晚于结束时间
        input = new JSONObject();
        input.put("price", 1000L);
        input.put("beginTime", "2028-03-11 15:30:30");
        input.put("endTime", "2028-02-12 16:20:30");
        input.put("quantity",10);
        input.put("type", 0);
        s = input.toJSONString();
        res = this.mvc.perform(post("/internal/products/2549/onsales")
                .contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk()).andReturn()
                .getResponse().getContentAsString();

        expect="{\"errno\": 947,\"errmsg\": \"开始时间晚于结束时间。\"}";
        JSONAssert.assertEquals(expect, res,true);

        //        货品不存在
        input = new JSONObject();
        input.put("price", 1000L);
        input.put("beginTime", "2028-03-11 15:30:30");
        input.put("endTime", "2029-02-12 16:20:30");
        input.put("quantity",10);
        input.put("type", 0);
        s = input.toJSONString();
        res = this.mvc.perform(post("/internal/products/999999/onsales")
                .contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isNotFound()).andReturn()
                .getResponse().getContentAsString();
        expect="{\"errno\": 504,\"errmsg\": \"货品id不存在。\"}";
        JSONAssert.assertEquals(expect, res,true);


    }




    @Test
    public void testSearchOnSaleAll() throws Exception {

        //        正常
        String res = this.mvc.perform(get("/internal/products/2549/onsales").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn()
                .getResponse().getContentAsString();
        String expect="{\"errno\":0,\"data\":{\"page\":1,\"pageSize\":5,\"total\":5,\"pages\":1,\"list\":[{\"id\":1000,\"price\":7429,\"beginTime\":\"2021-11-11T14:38:20\",\"endTime\":\"2022-02-19T14:38:20\",\"quantity\":38},{\"id\":3913,\"price\":6666,\"beginTime\":\"2021-11-12T08:39:31\",\"endTime\":\"2021-11-12T09:39:39\",\"quantity\":20},{\"id\":3940,\"price\":2233,\"beginTime\":\"2021-08-17T20:20:30\",\"endTime\":\"2021-08-17T21:20:20\",\"quantity\":100},{\"id\":3945,\"price\":4444,\"beginTime\":\"2023-04-21T23:38:27\",\"endTime\":\"2026-05-27T23:38:32\",\"quantity\":44},{\"id\":3946,\"price\":4444,\"beginTime\":\"2023-04-21T23:38:27\",\"endTime\":\"2026-05-27T23:38:32\",\"quantity\":44}]},\"errmsg\":\"成功\"}\n";
        JSONAssert.assertEquals(expect, res,true);
    }






}
