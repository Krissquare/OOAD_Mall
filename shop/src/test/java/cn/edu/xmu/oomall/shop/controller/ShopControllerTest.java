package cn.edu.xmu.oomall.shop.controller;

import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.shop.microservice.PaymentService;
import cn.edu.xmu.oomall.shop.microservice.ReconciliationService;
import cn.edu.xmu.oomall.shop.microservice.vo.RefundDepositVo;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
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
    private static String adminToken = "0";
    private static String shopToken = "0";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private PaymentService paymentService;

    @MockBean
    private ReconciliationService reconciliationService;

    /**
     * 获取店铺所有状态
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void getAllState() throws Exception {
        String responseString = this.mvc.perform(get("/shops/states"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expected = "{\"errno\":0,\"data\":[{\"code\":0,\"name\":\"未审核\"},{\"code\":1,\"name\":\"下线\"},{\"code\":2,\"name\":\"上线\"},{\"code\":3,\"name\":\"关闭\"}],\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expected, responseString, true);
    }
    /**
     * 获取店铺信息
     * @throws Exception
     */
    @Test
    @Transactional
    public void getSimpleShopById() throws Exception {
        String responseString = this.mvc.perform(get("/shops/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expected = " {\"errno\":0,\"data\":{\"id\":1,\"name\":\"修改后\"},\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expected, responseString, true);

    }
    /**
     * 获取所有店铺信息
     * @throws Exception
     */
    @Test
    @Transactional
    public void getAllShop() throws Exception {
        String responseString = this.mvc.perform(get("/shops/0/shops"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expected="{\"errno\":0,\"data\":{\"total\":3,\"pages\":1,\"pageSize\":3,\"page\":1,\"list\":[{\"id\":1,\"name\":\"修改后\",\"deposit\":5000000,\"depositThreshold\":1000000,\"state\":0,\"createdBy\":{\"id\":1,\"name\":\"admin\"},\"modifiedBy\":{\"id\":111,\"name\":\"hhhhh\"},\"gmtCreated\":null,\"gmtModified\":null},{\"id\":2,\"name\":\"修改后的店铺名称\",\"deposit\":5000000,\"depositThreshold\":1000000,\"state\":0,\"createdBy\":{\"id\":1,\"name\":\"admin\"},\"modifiedBy\":{\"id\":111,\"name\":\"hhhhh\"},\"gmtCreated\":null,\"gmtModified\":null},{\"id\":3,\"name\":\"向往时刻\",\"deposit\":5000000,\"depositThreshold\":1000000,\"state\":0,\"createdBy\":{\"id\":1,\"name\":\"admin\"},\"modifiedBy\":{\"id\":111,\"name\":\"hhhhh\"},\"gmtCreated\":null,\"gmtModified\":null}]},\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expected, responseString, true);
    }
    /**
     * 获取所有店铺信息,id不为0
     * @throws Exception
     */
    @Test
    @Transactional
    public void getAllShopIdErro() throws Exception {
        String responseString = this.mvc.perform(get("/shops/1/shops"))
                .andExpect(status().isForbidden())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expected=" {\"errno\":505,\"errmsg\":\"操作的资源id不是自己的对象\"}";
        JSONAssert.assertEquals(expected, responseString, true);
    }

    /**
     * 新建店铺（正常流程）
     *
     * @throws Exception
     */
    @Test
    @Transactional
    public void applyShop() throws Exception {
        String requestJson = "{\"name\": \"我的商铺\"}";
        String responseString = this.mvc.perform(post("/shops").header("authorization", shopToken).contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse()
                .getContentAsString();
        String expected = "{\"errno\":0,\"data\":{\"name\":\"我的商铺\",\"deposit\":0,\"depositThreshold\":null,\"state\":0},\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expected, responseString, false);
    }

    /**
     * 新建店铺（不传名称）
     *
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

        String expected = "{\"errno\":503,\"errmsg\":\"商铺名不能为空;\"}";
        JSONAssert.assertEquals(expected, responseString, true);
    }

    /**
     * 新建店铺（名称是空格）
     *
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

        String expected = "{\"errno\":503,\"errmsg\":\"商铺名不能为空;\"}";
        JSONAssert.assertEquals(expected, responseString, true);
    }

    /**
     * 审核店铺
     *
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

        String expected = "{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expected, responseString, true);
    }

    @Test
    @Transactional
    public void auditShop_false() throws Exception {
        String requestJson = "{\"conclusion\": false}";
        String responseString = this.mvc.perform(put("/shops/0/newshops/1/audit").header("authorization", adminToken).contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expected = "{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expected, responseString, true);
    }

    /**
     * 修改店铺
     *
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

        String expected = "{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expected, responseString, true);
    }

    @Test
    @Transactional
    public void modifyShop_null() throws Exception {
        String requestJson = "{\"name\": \"\"}";
        String responseString = this.mvc.perform(put("/shops/1").header("authorization", shopToken).contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expected = "{\"errno\":503,\"errmsg\":\"商铺名不能为空;\"}";
        JSONAssert.assertEquals(expected, responseString, true);
    }

    /**
     * 修改店铺(试图改id)
     *
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

        String expected = "{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expected, responseString, true);
    }

    /**
     * 上架店铺
     *
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

        String expected = "{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expected, responseString_audit, true);

        String responseString_onself = this.mvc.perform(put("/shops/1/online").header("authorization", adminToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        expected = "{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expected, responseString_onself, true);

        String responseString_offself = this.mvc.perform(put("/shops/1/offline").header("authorization", adminToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        expected = "{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expected, responseString_offself, true);
    }


    /**
     * 关闭店铺，已清算完毕已打款
     *
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

        String expected = "{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expected, responseString_audit, true);

        RefundDepositVo refundDepositVo = new RefundDepositVo();
        refundDepositVo.setAccount("11111111");
        refundDepositVo.setType(Byte.valueOf((byte) 0));
        refundDepositVo.setName("测试");

        Mockito.when(reconciliationService.isClean(Long.valueOf(1))).thenReturn(new ReturnObject(true));
        Mockito.when(paymentService.refund(refundDepositVo)).thenReturn(new ReturnObject());
        String responseString_delete = this.mvc.perform(delete("/shops/1").header("authorization", adminToken, shopToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        expected = "{\"errno\":0,\"data\":true,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expected, responseString_delete, true);
    }

    /**
     * 关闭店铺,已清算但状态不对
     *
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
        String expected = "{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expected, responseString_audit, true);

        String responseString_onself = this.mvc.perform(put("/shops/1/online").header("authorization", adminToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        expected = "{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expected, responseString_onself, true);

        RefundDepositVo refundDepositVo = new RefundDepositVo();
        refundDepositVo.setAccount("11111111");
        refundDepositVo.setType(Byte.valueOf((byte) 0));
        refundDepositVo.setName("测试");
        Mockito.when(reconciliationService.isClean(Long.valueOf(1))).thenReturn(new ReturnObject(true));
        Mockito.when(paymentService.refund(refundDepositVo)).thenReturn(new ReturnObject());
        String responseString = this.mvc.perform(delete("/shops/1").header("authorization", adminToken, shopToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        expected = "{\"errno\":507,\"errmsg\":\"当前状态禁止此操作\"}";
        JSONAssert.assertEquals(expected, responseString, true);
    }

    /**
     * 关闭店铺,未清算
     *
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

        RefundDepositVo refundDepositVo = new RefundDepositVo();
        refundDepositVo.setAccount("11111111");
        refundDepositVo.setType(Byte.valueOf((byte) 0));
        refundDepositVo.setName("测试");
        Mockito.when(reconciliationService.isClean(Long.valueOf(1))).thenReturn(new ReturnObject(false));
        Mockito.when(paymentService.refund(refundDepositVo)).thenReturn(new ReturnObject());
        String responseString = this.mvc.perform(delete("/shops/1").header("authorization", adminToken, shopToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expected = "{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expected, responseString_audit, true);
    }

    /**
     * 关闭店铺,未打款
     *
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
        String expected = "{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expected, responseString_audit, true);

        RefundDepositVo refundDepositVo = new RefundDepositVo();
        refundDepositVo.setAccount("11111111");
        refundDepositVo.setType(Byte.valueOf((byte) 0));
        refundDepositVo.setName("测试");
        Mockito.when(reconciliationService.isClean(Long.valueOf(1))).thenReturn(new ReturnObject(false));
        ReturnObject returnObject = new ReturnObject(ReturnNo.AUTH_INVALID_JWT, "错了");
        Mockito.when(paymentService.refund(refundDepositVo)).thenReturn(returnObject);
        String responseString = this.mvc.perform(delete("/shops/1").header("authorization", adminToken, shopToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        expected = "{\"errno\":0,\"data\":false,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expected, responseString, true);
    }

    @Test
    @Transactional
    public void modifyShopForbid() throws Exception {
        String requestJson = "{\"conclusion\": true}";
        String responseString_audit = this.mvc.perform(put("/shops/0/newshops/1/audit").header("authorization", adminToken).contentType("application/json;charset=UTF-8").content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        String expected = "{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expected, responseString_audit, true);
        RefundDepositVo refundDepositVo = new RefundDepositVo();
        refundDepositVo.setAccount("11111111");
        refundDepositVo.setType(Byte.valueOf((byte) 0));
        refundDepositVo.setName("测试");
        Mockito.when(reconciliationService.isClean(Long.valueOf(1))).thenReturn(new ReturnObject(false));
        ReturnObject returnObject = new ReturnObject();
        Mockito.when(paymentService.refund(refundDepositVo)).thenReturn(returnObject);
        String responseString_delete = this.mvc.perform(delete("/shops/1").header("authorization", adminToken, shopToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        expected = "{\"errno\":0,\"data\":false,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expected, responseString_delete, true);

        String requestJson2 = "{\"name\": \"修改后\"}";
        String responseString = this.mvc.perform(put("/shops/1").header("authorization", shopToken).contentType("application/json;charset=UTF-8").content(requestJson2))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        expected = "{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expected, responseString, true);
    }


    /**
     * 审核店铺
     *
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
        String expected = "{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expected, responseString_audit, true);

        RefundDepositVo refundDepositVo = new RefundDepositVo();
        refundDepositVo.setAccount("11111111");
        refundDepositVo.setType(Byte.valueOf((byte) 0));
        refundDepositVo.setName("测试");
        Mockito.when(reconciliationService.isClean(Long.valueOf(1))).thenReturn(new ReturnObject(false));
        ReturnObject returnObject = new ReturnObject();
        Mockito.when(paymentService.refund(refundDepositVo)).thenReturn(returnObject);
        String responseString_delete = this.mvc.perform(delete("/shops/1").header("authorization", adminToken, shopToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        expected = "{\"errno\":0,\"data\":false,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expected, responseString_delete, true);


        String requestJson2 = "{\"conclusion\": true}";
        String responseString = this.mvc.perform(put("/shops/0/newshops/1/audit").header("authorization", adminToken).contentType("application/json;charset=UTF-8").content(requestJson2))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        expected = "{\"errno\":507,\"errmsg\":\"当前状态禁止此操作\"}";
        JSONAssert.assertEquals(expected, responseString, true);
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
        String expected = "{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expected, responseString_audit, true);

        RefundDepositVo refundDepositVo = new RefundDepositVo();
        refundDepositVo.setAccount("11111111");
        refundDepositVo.setType(Byte.valueOf((byte) 0));
        refundDepositVo.setName("测试");
        Mockito.when(reconciliationService.isClean(Long.valueOf(1))).thenReturn(new ReturnObject(false));
        ReturnObject returnObject = new ReturnObject();
        Mockito.when(paymentService.refund(refundDepositVo)).thenReturn(returnObject);
        String responseString_delete = this.mvc.perform(delete("/shops/1").header("authorization", adminToken, shopToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        expected = "{\"errno\":0,\"data\":false,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expected, responseString_delete, true);

        String responseString = this.mvc.perform(put("/shops/1/online").header("authorization", adminToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        expected = "{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expected, responseString, true);
    }


    /**
     * 下架店铺
     *
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
        String expected = "{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expected, responseString_audit, true);

        RefundDepositVo refundDepositVo = new RefundDepositVo();
        refundDepositVo.setAccount("11111111");
        refundDepositVo.setType(Byte.valueOf((byte) 0));
        refundDepositVo.setName("测试");
        Mockito.when(reconciliationService.isClean(Long.valueOf(1))).thenReturn(new ReturnObject(false));
        ReturnObject returnObject = new ReturnObject();
        Mockito.when(paymentService.refund(refundDepositVo)).thenReturn(returnObject);

        String responseString_delete = this.mvc.perform(delete("/shops/1").header("authorization", adminToken, shopToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        expected = "{\"errno\":0,\"data\":false,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expected, responseString_delete, true);

        String responseString = this.mvc.perform(put("/shops/1/offline").header("authorization", adminToken))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
        System.out.println(responseString);
        expected = "{\"errno\":507,\"errmsg\":\"当前状态禁止此操作\"}";
        JSONAssert.assertEquals(expected, responseString, true);
    }


}
