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
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * @Author: 蒋欣雨
 * @Sn: 22920192204219
 */
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
    @Transactional
    public void getAllState() throws Exception {
        String responseString = this.mvc.perform(get("/shops/states"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
    }

    /**
     * 新建店铺（正常流程）
     * @throws Exception
     */
    @Test
    @Transactional
    public void applyShop() throws Exception {
        String requestJson = "{\"name\": \"我的商铺\"}";
        String responseString = this.mvc.perform(post("/shops").header("authorization", shopToken).contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
    }

    /**
     * 新建店铺（不传名称）
     * @throws Exception
     */
    @Test
    @Transactional
    public void applyShop_null() throws Exception {
        String requestJson = "{\"name\": \"\"}";
        String responseString = this.mvc.perform(post("/shops").header("authorization", shopToken).contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
    }

    /**
     * 新建店铺（名称是空格）
     * @throws Exception
     */
    @Test
    @Transactional
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
    @Transactional
    public void auditShop() throws Exception {
        String requestJson = "{\"conclusion\": true}";
        String responseString = this.mvc.perform(put("/shops/0/newshops/1/audit").header("authorization", adminToken).contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
    }
    @Test
    @Transactional
    public void auditShop_false() throws Exception {
        String requestJson = "{\"conclusion\": false}";
        String responseString = this.mvc.perform(put("/shops/0/newshops/1/audit").header("authorization", adminToken).contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
    }

    /**
     * 修改店铺
     * @throws Exception
     */
    @Test
    @Transactional
    public void modifyShop() throws Exception {
        String requestJson = "{\"name\": \"修改后的名称\"}";
        String responseString = this.mvc.perform(put("/shops/1").header("authorization", shopToken).contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
    }
    @Test
    @Transactional
    public void modifyShop_null() throws Exception {
        String requestJson = "{\"name\": \"\"}";
        String responseString = this.mvc.perform(put("/shops/1").header("authorization", shopToken).contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
    }

    /**
     * 修改店铺(试图改id)
     * @throws Exception
     */
    @Test
    @Transactional
    public void modifyShop_ID() throws Exception {
        String requestJson = "{\"name\": \"修改后的店铺名称\",\"id\":\"123\"}";
        String responseString = this.mvc.perform(put("/shops/1").header("authorization", shopToken).contentType("application/json;charset=UTF-8").content(requestJson))
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
    @Transactional
    public void onAndOffshelfShop() throws Exception {
        String requestJson = "{\"conclusion\": true}";
        String responseString_audit = this.mvc.perform(put("/shops/0/newshops/1/audit").header("authorization", adminToken).contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String responseString_onself = this.mvc.perform(put("/shops/1/online").header("authorization", adminToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String responseString_offself = this.mvc.perform(put("/shops/1/offline").header("authorization", adminToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
    }



    /**
     * 关闭店铺，已清算完毕已打款
     * @throws Exception
     */
    @Test
    @Transactional
    public void deleteShop() throws Exception {
        String requestJson = "{\"conclusion\": true}";
        String responseString_audit = this.mvc.perform(put("/shops/0/newshops/1/audit").header("authorization", adminToken).contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        Mockito.when(payApi.isSettled(Long.valueOf(1))).thenReturn(true);
        Mockito.when(payApi.paybackDeposit(Long.valueOf(1))).thenReturn(true);
        String responseString_delete = this.mvc.perform(delete("/shops/1").header("authorization", adminToken,shopToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
    }
    /**
     * 关闭店铺,已清算但状态不对
     * @throws Exception
     */
    @Test
    @Transactional
    public void deleteShop_online() throws Exception {
        String requestJson = "{\"conclusion\": true}";
        String responseString_audit = this.mvc.perform(put("/shops/0/newshops/1/audit").header("authorization", adminToken).contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String responseString_onself = this.mvc.perform(put("/shops/1/online").header("authorization", adminToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        Mockito.when(payApi.isSettled(Long.valueOf(1))).thenReturn(true);
        Mockito.when(payApi.paybackDeposit(Long.valueOf(1))).thenReturn(true);
        String responseString = this.mvc.perform(delete("/shops/1").header("authorization", adminToken,shopToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
    }

    /**
     * 关闭店铺,未清算
     * @throws Exception
     */
    @Test
    @Transactional
    public void deleteShop_isNotSettled() throws Exception {
        String requestJson = "{\"conclusion\": true}";
        String responseString_audit = this.mvc.perform(put("/shops/0/newshops/1/audit").header("authorization", adminToken).contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        Mockito.when(payApi.isSettled(Long.valueOf(1))).thenReturn(false);
        Mockito.when(payApi.paybackDeposit(Long.valueOf(1))).thenReturn(true);
        String responseString = this.mvc.perform(delete("/shops/1").header("authorization", adminToken,shopToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        System.out.println(responseString);
    }

    /**
     * 关闭店铺,未打款
     * @throws Exception
     */
    @Test
    @Transactional
    public void deleteShop_isNotPay() throws Exception {
        String requestJson = "{\"conclusion\": true}";
        String responseString_audit = this.mvc.perform(put("/shops/0/newshops/1/audit").header("authorization", adminToken).contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        Mockito.when(payApi.isSettled(Long.valueOf(1))).thenReturn(true);
        Mockito.when(payApi.paybackDeposit(Long.valueOf(1))).thenReturn(false);
        String responseString = this.mvc.perform(delete("/shops/1").header("authorization", adminToken,shopToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    @Transactional
    public void modifyShopForbid() throws Exception {
        String requestJson = "{\"conclusion\": true}";
        String responseString_audit = this.mvc.perform(put("/shops/0/newshops/1/audit").header("authorization", adminToken).contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        Mockito.when(payApi.isSettled(Long.valueOf(1))).thenReturn(true);
        Mockito.when(payApi.paybackDeposit(Long.valueOf(1))).thenReturn(true);
        String responseString_delete = this.mvc.perform(delete("/shops/1").header("authorization", adminToken,shopToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String requestJson2 = "{\"name\": \"修改后\"}";
        String responseString = this.mvc.perform(put("/shops/1").header("authorization", shopToken).contentType("application/json;charset=UTF-8").content(requestJson2))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

    }


    /**
     * 审核店铺
     * @throws Exception
     */
    @Test
    @Transactional
    public void auditShop_forbid() throws Exception {
        String requestJson = "{\"conclusion\": true}";
        String responseString_audit = this.mvc.perform(put("/shops/0/newshops/1/audit").header("authorization", adminToken).contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        Mockito.when(payApi.isSettled(Long.valueOf(1))).thenReturn(true);
        Mockito.when(payApi.paybackDeposit(Long.valueOf(1))).thenReturn(true);
        String responseString_delete = this.mvc.perform(delete("/shops/1").header("authorization", adminToken,shopToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String requestJson2 = "{\"conclusion\": true}";
        String responseString = this.mvc.perform(put("/shops/0/newshops/1/audit").header("authorization", adminToken).contentType("application/json;charset=UTF-8").content(requestJson2))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
//        System.out.println(responseString);
    }
//

    @Test
    @Transactional
    public void onshelfShop_forbid() throws Exception {
        String requestJson = "{\"conclusion\": true}";
        String responseString_audit = this.mvc.perform(put("/shops/0/newshops/1/audit").header("authorization", adminToken).contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        Mockito.when(payApi.isSettled(Long.valueOf(1))).thenReturn(true);
        Mockito.when(payApi.paybackDeposit(Long.valueOf(1))).thenReturn(true);
        String responseString_delete = this.mvc.perform(delete("/shops/1").header("authorization", adminToken,shopToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String responseString = this.mvc.perform(put("/shops/1/online").header("authorization", adminToken))
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
    @Transactional
    public void offshelfShop_forbid() throws Exception {
        String requestJson = "{\"conclusion\": true}";
        String responseString_audit = this.mvc.perform(put("/shops/0/newshops/1/audit").header("authorization", adminToken).contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        Mockito.when(payApi.isSettled(Long.valueOf(1))).thenReturn(true);
        Mockito.when(payApi.paybackDeposit(Long.valueOf(1))).thenReturn(true);
        String responseString_delete = this.mvc.perform(delete("/shops/1").header("authorization", adminToken,shopToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String responseString = this.mvc.perform(put("/shops/1/offline").header("authorization", adminToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        System.out.println(responseString);
    }


}
