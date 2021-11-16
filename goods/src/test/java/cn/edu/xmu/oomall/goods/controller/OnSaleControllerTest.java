package cn.edu.xmu.oomall.goods.controller;


import cn.edu.xmu.oomall.core.util.RedisUtil;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.goods.GoodsApplication;

import cn.edu.xmu.oomall.goods.model.vo.ModifyOnSaleVo;
import cn.edu.xmu.oomall.goods.model.vo.NewOnSaleVo;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
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
    private RedisUtil redisUtil;


    @Test
    public void testCreateOnsale() throws Exception {
//TODO: Test goes here...

        Mockito.when(redisUtil.get("p_"+2532L)).thenReturn(null);
        Mockito.when(redisUtil.get("p_"+2549L)).thenReturn(null);

        // 正常=》
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
        Mockito.when(redisUtil.get("o_"+2L)).thenReturn(null);
        Mockito.when(redisUtil.get("o_"+1L)).thenReturn(null);
        Mockito.when(redisUtil.get("o_"+30L)).thenReturn(null);

        //        正常
        String res = this.mvc.perform(put("/shops/9/onsales/30/online").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn()
                .getResponse().getContentAsString();
        String expect="{\"errno\": 0,\"errmsg\": \"成功\"}";
        JSONAssert.assertEquals(expect, res,true);


//        不存在价格浮动
        res = this.mvc.perform(put("/shops/3/onsales/66666/online").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()).andReturn().getResponse().getContentAsString();
        expect="{\"errno\": 504,\"errmsg\": \"不存在该价格浮动\"}";
        JSONAssert.assertEquals(expect, res,true);


        //只能处理秒杀、普通
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

        Mockito.when(redisUtil.get("o_"+2L)).thenReturn(null);
        Mockito.when(redisUtil.get("o_"+1L)).thenReturn(null);
        Mockito.when(redisUtil.get("o_"+30L)).thenReturn(null);

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


        //只能处理秒杀、普通
        res = this.mvc.perform(put("/shops/3/onsales/2/offline").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isForbidden()).andReturn().getResponse().getContentAsString();
        expect="{\"errno\": 505,\"errmsg\": \"只能处理普通和秒杀类型\"}";
        JSONAssert.assertEquals(expect, res,true);

        //上线态才能下线
        res = this.mvc.perform(put("/shops/9/onsales/30/offline").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        expect="{\"errno\": 507,\"errmsg\": \"非上线态无法下线\"}";
        JSONAssert.assertEquals(expect, res,true);

    }





    @Test
    public void testOnlineOnSaleGroPre() throws Exception {
//TODO: Test goes here...

        //        正常
        String res = this.mvc.perform(put("/internal/activities/1/onsales/online").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        String expect="{\"errno\": 0,\"errmsg\": \"成功\"}";
        JSONAssert.assertEquals(expect, res,true);

    }

    /**
     * Method: offlineOnSale(@PathVariable Long shopId, @PathVariable Long id, Long loginUserId, String loginUserName)
     */
    @Test
    public void testOfflineOnSaleGP() throws Exception {

        //        正常
        String res = this.mvc.perform(put("/internal/activities/1/onsales/offline").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        String expect="{\"errno\": 0,\"errmsg\": \"成功\"}";
        JSONAssert.assertEquals(expect, res,true);

    }







    @Test
    public void testCreateAllOnSale() throws Exception {


        Mockito.when(redisUtil.get("p_"+2532L)).thenReturn(null);
        Mockito.when(redisUtil.get("p_"+2549L)).thenReturn(null);

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
    public void testDeleteNorSec() throws Exception{
        String res = this.mvc.perform(delete("/shops/9/onsales/30").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn()
                .getResponse().getContentAsString();
        String expect="{\"errno\": 0,\"errmsg\": \"成功\"}";
        JSONAssert.assertEquals(expect, res,true);

        //不存在价格浮动
        res = this.mvc.perform(delete("/shops/9/onsales/66666").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNotFound()).andReturn()
                .getResponse().getContentAsString();
        expect="{\"errno\": 504,\"errmsg\": \"不存在该价格浮动\"}";
        JSONAssert.assertEquals(expect, res,true);

        //限定普通或秒杀
        res = this.mvc.perform(delete("/shops/4/onsales/3").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isForbidden()).andReturn()
                .getResponse().getContentAsString();
        expect="{\"errno\": 505,\"errmsg\": \"只能处理普通和秒杀类型\"}";
        JSONAssert.assertEquals(expect, res,true);

        //草稿态才能删除
        res = this.mvc.perform(delete("/shops/10/onsales/1").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        expect="{\"errno\": 507,\"errmsg\": \"非草稿态无法删除\"}";
        JSONAssert.assertEquals(expect, res,true);



    }

    @Test
    public void testDeleteAct() throws Exception{
        String res = this.mvc.perform(delete("/internal/activities/3/onsales").contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn()
                .getResponse().getContentAsString();
        String expect="{\"errno\": 0,\"errmsg\": \"成功\"}";
        JSONAssert.assertEquals(expect, res,true);


    }

    @Test
    public void testUpdate() throws Exception{

        // 正常=》
        JSONObject input = new JSONObject();
        input.put("price", 1000L);
        input.put("beginTime", "2022-10-11 15:20:30");
        input.put("endTime", "2022-10-12 16:20:30");
        input.put("quantity",10);
        String s = input.toJSONString();
        String res = this.mvc.perform(put("/internal/onsales/30")
                .contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String expect;

//        开始时间晚于结束时间
        input = new JSONObject();
        input.put("price", 1000L);
        input.put("beginTime", "2028-03-11 15:30:30");
        input.put("endTime", "2028-02-12 16:20:30");
        input.put("quantity",10);
        s = input.toJSONString();
        res = this.mvc.perform(put("/internal/onsales/29")
                .contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk()).andReturn()
                .getResponse().getContentAsString();
        expect="{\"errno\": 947,\"errmsg\": \"开始时间晚于结束时间。\"}";
        JSONAssert.assertEquals(expect, res,true);



        //        不存在价格浮动
        input = new JSONObject();
        input.put("price", 1000L);
        input.put("beginTime", "2028-03-11 15:30:30");
        input.put("endTime", "2028-04-12 16:20:30");
        input.put("quantity",10);
        s = input.toJSONString();
        res = this.mvc.perform(put("/internal/onsales/66666")
                .contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isNotFound()).andReturn()
                .getResponse().getContentAsString();
        expect="{\"errno\":504 ,\"errmsg\": \"不存在该价格浮动\"}";
        JSONAssert.assertEquals(expect, res,true);



    }

    @Test
    public void testUpdateNorSec() throws Exception{

        // 正常=》
        JSONObject input = new JSONObject();
        input.put("price", 1000L);
        input.put("beginTime", "2022-10-11 15:20:30");
        input.put("endTime", "2022-10-12 16:20:30");
        input.put("quantity",10);
        String s = input.toJSONString();
        String res = this.mvc.perform(put("/shops/9/onsales/30")
                .contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        String expect;

//        开始时间晚于结束时间
        input = new JSONObject();
        input.put("price", 1000L);
        input.put("beginTime", "2028-03-11 15:30:30");
        input.put("endTime", "2028-02-12 16:20:30");
        input.put("quantity",10);
        s = input.toJSONString();
        res = this.mvc.perform(put("/shops/10/onsales/1")
                .contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isOk()).andReturn()
                .getResponse().getContentAsString();
        expect="{\"errno\": 947,\"errmsg\": \"开始时间晚于结束时间。\"}";
        JSONAssert.assertEquals(expect, res,true);


//        限定普通秒杀
        input = new JSONObject();
        input.put("price", 1000L);
        input.put("beginTime", "2028-03-11 15:30:30");
        input.put("endTime", "2028-04-12 16:20:30");
        input.put("quantity",10);
        s = input.toJSONString();
        res = this.mvc.perform(put("/shops/3/onsales/2")
                .contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isForbidden()).andReturn()
                .getResponse().getContentAsString();
        expect="{\"errno\": 505,\"errmsg\": \"限定处理普通或秒杀。\"}";
        JSONAssert.assertEquals(expect, res,true);

        //        不存在价格浮动
        input = new JSONObject();
        input.put("price", 1000L);
        input.put("beginTime", "2028-03-11 15:30:30");
        input.put("endTime", "2028-04-12 16:20:30");
        input.put("quantity",10);
        s = input.toJSONString();
        res = this.mvc.perform(put("/shops/3/onsales/22266")
                .contentType(MediaType.APPLICATION_JSON).content(s)).andExpect(status().isNotFound()).andReturn()
                .getResponse().getContentAsString();
        expect="{\"errno\":504 ,\"errmsg\": \"不存在该价格浮动\"}";
        JSONAssert.assertEquals(expect, res,true);



    }






}
