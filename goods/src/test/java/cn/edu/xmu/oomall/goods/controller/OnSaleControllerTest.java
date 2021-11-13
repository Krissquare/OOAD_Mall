package cn.edu.xmu.oomall.goods.controller;


import cn.edu.xmu.oomall.goods.GoodsApplication;
import cn.edu.xmu.oomall.goods.model.vo.NewOnSaleVo;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
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
        MvcResult res = this.mvc.perform(post("/shops/3/products/2532/onsales")
                .contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isCreated()).andReturn();



//        传入数量小于1
        input = new JSONObject();
        input.put("price", 1000L);
        input.put("beginTime", "2023-10-11 15:30:30");
        input.put("endTime", "2023-10-12 16:20:30");
        input.put("quantity",0);
        input.put("type", 0);
        s = input.toJSONString();
        res = this.mvc.perform(post("/shops/10/products/2544/onsales")
                .contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isBadRequest()).andReturn();




        // 商品销售时间冲突=》
        input = new JSONObject();
        input.put("price", 1000L);
        input.put("beginTime", "2021-11-12 09:30:30");
        input.put("endTime", "2022-10-12 09:40:30");
        input.put("quantity",10);
        input.put("type", 0);
        s = input.toJSONString();
        res = this.mvc.perform(post("/shops/2/products/2549/onsales")
                .contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk()).andReturn();


//        开始时间晚于结束时间
        input = new JSONObject();
        input.put("price", 1000L);
        input.put("beginTime", "2028-03-11 15:30:30");
        input.put("endTime", "2028-02-12 16:20:30");
        input.put("quantity",10);
        input.put("type", 0);
        s = input.toJSONString();
        res = this.mvc.perform(post("/shops/2/products/2549/onsales")
                .contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk()).andReturn();



//        非普通或秒杀
        input = new JSONObject();
        input.put("price", 1000L);
        input.put("beginTime", "2028-03-11 15:30:30");
        input.put("endTime", "2029-02-12 16:20:30");
        input.put("quantity",10);
        input.put("type", 3);
        s = input.toJSONString();
        res = this.mvc.perform(post("/shops/2/products/2549/onsales")
                .contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isForbidden()).andReturn();


        //        货品不存在
        input = new JSONObject();
        input.put("price", 1000L);
        input.put("beginTime", "2028-03-11 15:30:30");
        input.put("endTime", "2029-02-12 16:20:30");
        input.put("quantity",10);
        input.put("type", 0);
        s = input.toJSONString();
        res = this.mvc.perform(post("/shops/2/products/999999/onsales")
                .contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isNotFound()).andReturn();


        //货品非该商家
        input = new JSONObject();
        input.put("price", 1000L);
        input.put("beginTime", "2028-03-11 15:30:30");
        input.put("endTime", "2029-02-12 16:20:30");
        input.put("quantity",10);
        input.put("type", 0);
        s = input.toJSONString();
        res = this.mvc.perform(post("/shops/2/products/2532/onsales")
                .contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isForbidden()).andReturn();

    }

    /**
     * Method: onlineOnSale(@PathVariable Long shopId, @PathVariable Long id, Long loginUserId, String loginUserName)
     */
    @Test
    public void testOnlineOnSale() throws Exception {
//TODO: Test goes here...

        //        正常
        MvcResult res = this.mvc.perform(put("/shops/2/onsales/3913/online").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();


//        不存在价格浮动
        res = this.mvc.perform(put("/shops/3/onsales/66666/online").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()).andReturn();


        //只能处理秒杀、普通订单
        res = this.mvc.perform(put("/shops/3/onsales/2/online").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isForbidden()).andReturn();

        //草稿态才能上线
        res = this.mvc.perform(put("/shops/10/onsales/1/online").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();


    }

    /**
     * Method: offlineOnSale(@PathVariable Long shopId, @PathVariable Long id, Long loginUserId, String loginUserName)
     */
    @Test
    public void testOfflineOnSale() throws Exception {
//TODO: Test goes here...
        //        正常
        MvcResult res = this.mvc.perform(put("/shops/10/onsales/1/offline").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();



        //        不存在价格浮动
        res = this.mvc.perform(put("/shops/3/onsales/66666/offline").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()).andReturn();



        //只能处理秒杀、普通订单
        res = this.mvc.perform(put("/shops/3/onsales/2/offline").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isForbidden()).andReturn();


        //上线态才能下线
        res = this.mvc.perform(put("/shops/2/onsales/3913/offline").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

    }

    /**
     * Method: searchOnSale(@PathVariable Long shopId, @PathVariable Long id, @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue="1") Integer page)
     */
    @Test
    public void testSearchOnSale() throws Exception {

        //        正常
        MvcResult res = this.mvc.perform(get("/shops/0/products/2549/onsales").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();


        // 搜索结果不存在
        res = this.mvc.perform(get("/shops/0/products/96215/onsales").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()).andReturn();

    }

    /**
     * Method: getOnSaleDetail(@PathVariable Long shopId, @PathVariable Long id)
     */
    @Test
    public void testGetOnSaleDetail() throws Exception {
//TODO: Test goes here...
        //        正常
        MvcResult res = this.mvc.perform(get("/shops/0/onsales/996").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        // 非普通或秒杀
        res = this.mvc.perform(get("/shops/0/onsales/3").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isForbidden()).andReturn();

    }


    @Test
    public void testOnlineOnSaleGroPre() throws Exception {
//TODO: Test goes here...

        //        正常
        MvcResult res = this.mvc.perform(put("/internal/onsales/3945/online").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();


//        不存在价格浮动
        res = this.mvc.perform(put("/internal/onsales/66666/online").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()).andReturn();



        //只能处理团购预售订单
        res = this.mvc.perform(put("/internal/onsales/3913/online").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isForbidden()).andReturn();


        //草稿态才能上线
        res = this.mvc.perform(put("/internal/onsales/68/online").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();



    }

    /**
     * Method: offlineOnSale(@PathVariable Long shopId, @PathVariable Long id, Long loginUserId, String loginUserName)
     */
    @Test
    public void testOfflineOnSaleGP() throws Exception {
        //        正常
        MvcResult res = this.mvc.perform(put("/internal/onsales/68/offline").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();



        //        不存在价格浮动
        res = this.mvc.perform(put("/internal/onsales/66666/offline").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()).andReturn();



        //只能处理团购预售订单
        res = this.mvc.perform(put("/internal/onsales/3913/offline").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isForbidden()).andReturn();


        //上线态才能下线
        res = this.mvc.perform(put("/internal/onsales/3946/offline").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

    }

    @Test
    public void testSearchOnSaleGro() throws Exception{
        //        正常
        MvcResult res = this.mvc.perform(get("/internal/grouponactivities/6/onsales")
        ).andExpect(status().isOk()).andReturn();



    }

    @Test
    public void testSearchOnSaleAdv() throws Exception{
        //        正常
        MvcResult res = this.mvc.perform(get("/internal/advacnesaleactivities/1/onsales")
        ).andExpect(status().isOk()).andReturn();



    }


    @Test
    public void testSearchOnSaleShare() throws Exception{
        //  正常
        MvcResult res = this.mvc.perform(get("/internal/shareactivities/1/onsales")
        ).andExpect(status().isOk()).andReturn();


    }

    @Test
    public void testSearchOnSaleById() throws Exception{
        //        正常
        MvcResult res = this.mvc.perform(get("/internal/onsales/1")
        ).andExpect(status().isOk()).andReturn();

    }

    @Test
    public void testCreateAllOnSale() throws Exception {
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
        MvcResult res = this.mvc.perform(post("/internal/products/2532/onsales")
                .contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isCreated()).andReturn();



//        传入数量小于1
        input = new JSONObject();
        input.put("price", 1000L);
        input.put("beginTime", "2023-10-11 15:30:30");
        input.put("endTime", "2023-10-12 16:20:30");
        input.put("quantity",0);
        input.put("type", 0);
        s = input.toJSONString();
        res = this.mvc.perform(post("/internal/products/2544/onsales")
                .contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isBadRequest()).andReturn();



        // 商品销售时间冲突=》
        input = new JSONObject();
        input.put("price", 1000L);
        input.put("beginTime", "2021-11-12 09:30:30");
        input.put("endTime", "2022-10-12 09:40:30");
        input.put("quantity",10);
        input.put("type", 0);
        s = input.toJSONString();
        res = this.mvc.perform(post("/internal/products/2549/onsales")
                .contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk()).andReturn();


//        开始时间晚于结束时间
        input = new JSONObject();
        input.put("price", 1000L);
        input.put("beginTime", "2028-03-11 15:30:30");
        input.put("endTime", "2028-02-12 16:20:30");
        input.put("quantity",10);
        input.put("type", 0);
        s = input.toJSONString();
        res = this.mvc.perform(post("/internal/products/2549/onsales")
                .contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk()).andReturn();


        //        货品不存在
        input = new JSONObject();
        input.put("price", 1000L);
        input.put("beginTime", "2028-03-11 15:30:30");
        input.put("endTime", "2029-02-12 16:20:30");
        input.put("quantity",10);
        input.put("type", 0);
        s = input.toJSONString();
        res = this.mvc.perform(post("/internal/products/999999/onsales")
                .contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isNotFound()).andReturn();


    }




    @Test
    public void testSearchOnSaleAll() throws Exception {
//TODO: Test goes here...
        //        正常
        MvcResult res = this.mvc.perform(get("/internal/products/2549/onsales").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();
        System.out.println(res.getResponse().getContentAsString());

        // 搜索结果不存在
        res = this.mvc.perform(get("/internal/products/96215/onsales").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()).andReturn();
        System.out.println(res.getResponse().getContentAsString());
    }






}
