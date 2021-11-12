package cn.edu.xmu.oomall.shop;

import cn.edu.xmu.oomall.shop.openfeign.PayApi;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ShopControllerTest {
    private static String adminToken="0";
    private static String shopToken="0";

    @Autowired
    private MockMvc mvc;
    @MockBean(name = "cn.edu.xmu.oomall.shop.openfeign.PayApi")
    private PayApi payApi;
    /**
     * 获取店铺所有状态
     * @throws Exception
     */
    @Test
    @Order(1)
    public void getAllState() throws Exception {
        String responseString = this.mvc.perform(get("/shops/states"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
//        String expectedResponse = "{\"errno\":0,\"data\":[{\"code\":0,\"name\":\"未审核\"},{\"code\":1,\"name\":\"未上线\"},{\"code\":2,\"name\":\"上线\"},{\"code\":3,\"name\":\"关闭\"},{\"code\":4,\"name\":\"审核未通过\"}],\"errmsg\":\"成功\"}";

//        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }

    /**
     * 新建店铺（正常流程）
     * @throws Exception
     */
    @Test
    @Order(2)
    public void applyShop() throws Exception {
        String requestJson = "{\"name\": \"我的商铺\"}";
        String responseString = this.mvc.perform(post("/shops").header("authorization", shopToken).contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
//        String expectedResponse = "{\"errno\":0,\"data\":{\"id\":16,\"name\":\"张三商铺\"},\"errmsg\":\"成功\"}";
//        JSONAssert.assertEquals(expectedResponse, responseString, true);
    }

    /**
     * 新建店铺（重复申请）
     * @throws Exception
     */
//    @Test
//    public void applyShop_again() throws Exception {
//        String requestJson = "{\"name\": \"my_shop\"}";
//        String responseString = this.mvc.perform(post("/shops").header("authorization", shopToken).contentType("application/json;charset=UTF-8").content(requestJson))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType("application/json;charset=UTF-8"))
//                .andReturn().getResponse().getContentAsString();
//        String expectedResponse =  "{\"errno\":969,\"errmsg\":\"您已经拥有店铺，无法重新申请\"}";
//        JSONAssert.assertEquals(expectedResponse, responseString, true);
//    }

    /**
     * 新建店铺（不传名称）
     * @throws Exception
     */
    @Test
    @Order(3)
    public void applyShop_null() throws Exception {
        String requestJson = "{\"name\": \"\"}";
        String responseString = this.mvc.perform(post("/shops").header("authorization", shopToken).contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
//        System.out.println(responseString);
    }




    /**
     * 新建店铺（名称是空格）
     * @throws Exception
     */
    @Test
    @Order(4)
    public void applyShop_space() throws Exception {
        String requestJson = "{\"name\": \"  \"}";
        String responseString = this.mvc.perform(post("/shops").header("authorization", shopToken).contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
    }
    /**
     * 审核店铺
     * @throws Exception
     */
    @Test
    @Order(5)
    public void auditShop() throws Exception {
        String requestJson = "{\"conclusion\": true}";
        String responseString = this.mvc.perform(put("/shops/0/newshops/1/audit").header("authorization", adminToken).contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        System.out.println(responseString);
    }
    @Test
    @Order(6)
    public void auditShop_false() throws Exception {
        String requestJson = "{\"conclusion\": false}";
        String responseString = this.mvc.perform(put("/shops/0/newshops/2/audit").header("authorization", adminToken).contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        System.out.println(responseString);
    }

    /**
     * 修改店铺
     * @throws Exception
     */
    @Test
    @Order(7)
    public void modifyShop() throws Exception {
        String requestJson = "{\"name\": \"修改后的名称\"}";
        String responseString = this.mvc.perform(put("/shops/2").header("authorization", shopToken).contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
//        System.out.println(responseString);
    }
    @Test
    @Order(8)
    public void modifyShop_null() throws Exception {
        String requestJson = "{\"name\": \"\"}";
        String responseString = this.mvc.perform(put("/shops/2").header("authorization", shopToken).contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
//        System.out.println(responseString);
    }

    /**
     * 修改店铺(试图改id)
     * @throws Exception
     */
    @Test
    @Order(9)
    public void modifyShop_ID() throws Exception {
        String requestJson = "{\"name\": \"修改后的店铺名称\",\"id\":\"123\"}";
        String responseString = this.mvc.perform(put("/shops/2").header("authorization", shopToken).contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        System.out.println(responseString);
    }

    /**
     * 上架店铺
     * @throws Exception
     */
    @Test
    @Order(10)
    public void onshelfShop() throws Exception {
        String responseString = this.mvc.perform(put("/shops/1/onshelves").header("authorization", adminToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        System.out.println(responseString);
    }

    /**
     * 下架店铺
     * @throws Exception
     */
    @Test
    @Order(11)
    public void offshelfShop() throws Exception {
        String responseString = this.mvc.perform(put("/shops/1/offshelves").header("authorization", adminToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        System.out.println(responseString);
    }

    /**
     * 关闭店铺，已清算完毕
     * @throws Exception
     */
    @Test
    @Order(12)
    public void deleteShop() throws Exception {
        Mockito.when(payApi.isSettled(Long.valueOf(1))).thenReturn(true);
        Mockito.when(payApi.paybackDeposit(Long.valueOf(1))).thenReturn(true);
        String responseString = this.mvc.perform(delete("/shops/1").header("authorization", adminToken,shopToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        System.out.println(responseString);
    }
    /**
     * 关闭店铺,已清算但状态不对
     * @throws Exception
     */
    @Test
    @Order(13)
    public void deleteShop_online() throws Exception {
        Mockito.when(payApi.isSettled(Long.valueOf(2))).thenReturn(true);
        Mockito.when(payApi.paybackDeposit(Long.valueOf(2))).thenReturn(true);
        String responseString = this.mvc.perform(delete("/shops/2").header("authorization", adminToken,shopToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        System.out.println(responseString);
    }

    /**
     * 审核店铺
     * @throws Exception
     */
    @Test
    @Order(14)
    public void auditShop2() throws Exception {
        String requestJson = "{\"conclusion\": true}";
        String responseString = this.mvc.perform(put("/shops/0/newshops/3/audit").header("authorization", adminToken).contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        System.out.println(responseString);
    }
    /**
     * 关闭店铺,未清算
     * @throws Exception
     */
    @Test
    @Order(15)
    public void deleteShop_isNotSettled() throws Exception {
        Mockito.when(payApi.isSettled(Long.valueOf(3))).thenReturn(false);
        Mockito.when(payApi.paybackDeposit(Long.valueOf(3))).thenReturn(true);
        String responseString = this.mvc.perform(delete("/shops/3").header("authorization", adminToken,shopToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        System.out.println(responseString);
    }


    @Test
    @Order(16)
    public void modifyShopForbid() throws Exception {
        String requestJson = "{\"name\": \"修改后\"}";
        String responseString = this.mvc.perform(put("/shops/1").header("authorization", shopToken).contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
//        System.out.println(responseString);
    }


    /**
     * 审核店铺
     * @throws Exception
     */
    @Test
    @Order(17)
    public void auditShop_forbid() throws Exception {
        String requestJson = "{\"conclusion\": true}";
        String responseString = this.mvc.perform(put("/shops/0/newshops/1/audit").header("authorization", adminToken).contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
//        System.out.println(responseString);
    }
//

    @Test
    @Order(18)
    public void onshelfShop_forbid() throws Exception {
        String responseString = this.mvc.perform(put("/shops/1/onshelves").header("authorization", adminToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        System.out.println(responseString);
    }



    /**
     * 下架店铺
     * @throws Exception
     */
    @Test
    @Order(19)
    public void offshelfShop_forbid() throws Exception {
        String responseString = this.mvc.perform(put("/shops/1/offshelves").header("authorization", adminToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        System.out.println(responseString);
    }


}
