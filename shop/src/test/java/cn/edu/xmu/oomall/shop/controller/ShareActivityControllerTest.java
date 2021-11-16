package cn.edu.xmu.oomall.shop.controller;

import cn.edu.xmu.oomall.core.util.JacksonUtil;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.shop.microservice.OnSaleService;
import cn.edu.xmu.oomall.shop.model.bo.OnSale;
import cn.edu.xmu.oomall.shop.model.vo.ShareActivityStrategyVo;
import cn.edu.xmu.oomall.shop.model.vo.ShareActivityVo;
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

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created with IntelliJ IDEA.
 *
 * @author BingShuai Liu
 * @studentId 22920192204245
 * @date 2021/11/14/15:05
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ShareActivityControllerTest {
    private static String adminToken = "0";
    private static String shopToken = "0";

    @Autowired
    private MockMvc mvc;
    @MockBean
    private OnSaleService onSaleService;

    @Test
    @Transactional
    public void addShareActivityOnOnSale_OnSaleOfflineState() throws Exception{
        OnSale onSale = new OnSale();
        onSale.setId(1L);
        onSale.setState((byte) 2);
        Mockito.when(onSaleService.getOnSaleById(1L)).thenReturn(new ReturnObject<>(onSale));
        Mockito.when(onSaleService.updateAddOnSaleShareActId(1L,1L)).thenReturn(new ReturnObject<>(Boolean.TRUE));
        String responseString=this.mvc.perform(post("/shops/1/onSale/1/shareActivities/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        String expectedString= "{\n" +
                "\t\"errno\": 507,\n" +
                "\t\"errmsg\": \"当前状态禁止此操作\"\n" +
                "}";
        JSONAssert.assertEquals(expectedString,responseString,false);
    }
    @Test
    @Transactional
    public void addShareActivityOnOnSale_ShareActivityOfflineState() throws Exception{
        OnSale onSale = new OnSale();
        onSale.setId(1L);
        onSale.setState((byte) 1);
        Mockito.when(onSaleService.getOnSaleById(1L)).thenReturn(new ReturnObject<>(onSale));
        Mockito.when(onSaleService.updateAddOnSaleShareActId(1L,2L)).thenReturn(new ReturnObject<>(Boolean.TRUE));
        String responseString=this.mvc.perform(post("/shops/1/onSale/1/shareActivities/2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        String expectedString= "{\n" +
                "\t\"errno\": 507,\n" +
                "\t\"errmsg\": \"当前状态禁止此操作\"\n" +
                "}";
        JSONAssert.assertEquals(expectedString,responseString,false);
    }
    @Test
    @Transactional
    public void addShareActivityOnOnSale_ShareActivityIdNotFound() throws Exception{
        OnSale onSale = new OnSale();
        onSale.setId(1L);
        onSale.setState((byte) 1);
        Mockito.when(onSaleService.getOnSaleById(1L)).thenReturn(new ReturnObject<>(onSale));
        Mockito.when(onSaleService.updateAddOnSaleShareActId(1L,-1L)).thenReturn(new ReturnObject<>(Boolean.TRUE));
        String responseString=this.mvc.perform(post("/shops/1/onSale/1/shareActivities/-1"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        String expectedString= "{\n" +
                "\t\"errno\": 504,\n" +
                "\t\"errmsg\": \"操作的资源id不存在\"\n" +
                "}";
        JSONAssert.assertEquals(expectedString,responseString,false);
    }
    @Test
    @Transactional
    public void addShareActivityOnOnSale_ShareActivityOnlineState() throws Exception{
        OnSale onSale = new OnSale();
        onSale.setId(1L);
        onSale.setState((byte) 1);
        Mockito.when(onSaleService.getOnSaleById(1L)).thenReturn(new ReturnObject<>(onSale));
        Mockito.when(onSaleService.updateAddOnSaleShareActId(1L,1L)).thenReturn(new ReturnObject<>(Boolean.TRUE));
        String responseString=this.mvc.perform(post("/shops/1/onSale/1/shareActivities/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        String expectedString= "{\n" +
                "\t\"errno\": 0,\n" +
                "\t\"errmsg\": \"成功\"\n" +
                "}";
        JSONAssert.assertEquals(expectedString,responseString,false);
    }
    @Test
    @Transactional
    public void addShareActivityOnOnSale_ShareActivityDraftState() throws Exception{
        OnSale onSale = new OnSale();
        onSale.setId(1L);
        onSale.setState((byte) 1);
        Mockito.when(onSaleService.getOnSaleById(1L)).thenReturn(new ReturnObject<>(onSale));
        Mockito.when(onSaleService.updateAddOnSaleShareActId(1L,4L)).thenReturn(new ReturnObject<>(Boolean.TRUE));
        String responseString=this.mvc.perform(post("/shops/1/onSale/1/shareActivities/4"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        String expectedString= "{\n" +
                "\t\"errno\": 0,\n" +
                "\t\"errmsg\": \"成功\"\n" +
                "}";
        JSONAssert.assertEquals(expectedString,responseString,false);
    }
    @Test
    @Transactional
    public void deleteShareActivityOnOnSale_ShareActivityIdNotFound() throws Exception{
        OnSale onSale = new OnSale();
        onSale.setId(1L);
        onSale.setState((byte) 1);
        Mockito.when(onSaleService.getOnSaleById(1L)).thenReturn(new ReturnObject<>(onSale));
        Mockito.when(onSaleService.updateDeleteOnSaleShareActId(1L,-1L)).thenReturn(new ReturnObject<>(Boolean.FALSE));
        String responseString=this.mvc.perform(delete("/shops/1/onSale/1/shareActivities/-1"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        String expectedString= "{\n" +
                "\t\"errno\": 504,\n" +
                "\t\"errmsg\": \"操作的资源id不存在\"\n" +
                "}";
        JSONAssert.assertEquals(expectedString,responseString,false);
    }
    @Test
    @Transactional
    public void deleteShareActivityOnOnSale_Success() throws Exception{
        OnSale onSale = new OnSale();
        onSale.setId(1L);
        onSale.setState((byte) 1);
        Mockito.when(onSaleService.getOnSaleById(1L)).thenReturn(new ReturnObject<>(onSale));
        Mockito.when(onSaleService.updateAddOnSaleShareActId(1L,4L)).thenReturn(new ReturnObject<>(Boolean.TRUE));
        String responseString=this.mvc.perform(delete("/shops/1/onSale/1/shareActivities/4"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        String expectedString= "{\n" +
                "\t\"errno\": 0,\n" +
                "\t\"errmsg\": \"成功\"\n" +
                "}";
        JSONAssert.assertEquals(expectedString,responseString,false);
    }
    @Test
    @Transactional
    public void modifyShareActivity_NotDraftState() throws Exception{
        ShareActivityStrategyVo shareActivityStrategyVo=new ShareActivityStrategyVo(10L,10L);
        List<ShareActivityStrategyVo> list = new ArrayList<>();
        list.add(shareActivityStrategyVo);
        ShareActivityVo shareActivityVo = new ShareActivityVo();
        shareActivityVo.setName("分享活动5");
        shareActivityVo.setBeginTime(LocalDateTime.parse("2021-11-11T18:30:30"));
        shareActivityVo.setEndTime(LocalDateTime.parse("2021-11-11T19:30:40"));
        shareActivityVo.setStrategy(list);
        String json= JacksonUtil.toJson(shareActivityVo);
        String responseString=this.mvc.perform(put("/shops/1/shareactivities/1")
                        .contentType("application/json;charset=UTF-8").content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        String expectedString="{\n" +
                "\t\"errno\": 507,\n" +
                "\t\"errmsg\": \"当前状态禁止此操作\"\n" +
                "}";
        JSONAssert.assertEquals(expectedString,responseString,false);
    }
    @Test
    @Transactional
    public void modifyShareActivity_BeginTimeAfterEndTime() throws Exception{
        ShareActivityStrategyVo shareActivityStrategyVo=new ShareActivityStrategyVo(10L,10L);
        List<ShareActivityStrategyVo> list = new ArrayList<>();
        list.add(shareActivityStrategyVo);
        ShareActivityVo shareActivityVo = new ShareActivityVo();
        shareActivityVo.setName("测试活动");
        shareActivityVo.setBeginTime(LocalDateTime.parse("2022-11-11T18:30:30"));
        shareActivityVo.setEndTime(LocalDateTime.parse("2021-11-11T19:30:40"));
        shareActivityVo.setStrategy(list);
        String json= JacksonUtil.toJson(shareActivityVo);
        String responseString=this.mvc.perform(put("/shops/1/shareactivities/4")
                        .contentType("application/json;charset=UTF-8").content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        String expectedString="{\n" +
                "\t\"errno\": 947,\n" +
                "\t\"errmsg\": \"开始时间不能晚于结束时间\"\n" +
                "}";
        JSONAssert.assertEquals(expectedString,responseString,false);
    }
    @Test
    @Transactional
    public void modifyShareActivity_ShareActivityIdNotFound() throws Exception{
        ShareActivityStrategyVo shareActivityStrategyVo=new ShareActivityStrategyVo(10L,10L);
        List<ShareActivityStrategyVo> list = new ArrayList<>();
        list.add(shareActivityStrategyVo);
        ShareActivityVo shareActivityVo = new ShareActivityVo();
        shareActivityVo.setName("测试活动");
        shareActivityVo.setBeginTime(LocalDateTime.parse("2021-11-11T18:30:30"));
        shareActivityVo.setEndTime(LocalDateTime.parse("2021-11-11T19:30:40"));
        shareActivityVo.setStrategy(list);
        String json= JacksonUtil.toJson(shareActivityVo);
        String responseString=this.mvc.perform(put("/shops/1/shareactivities/-1")
                        .contentType("application/json;charset=UTF-8").content(json))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        String expectedString= "{\n" +
                "\t\"errno\": 504,\n" +
                "\t\"errmsg\": \"操作的资源id不存在\"\n" +
                "}";
        JSONAssert.assertEquals(expectedString,responseString,false);
    }
    @Test
    @Transactional
    public void modifyShareActivity_Success() throws Exception{
        ShareActivityStrategyVo shareActivityStrategyVo=new ShareActivityStrategyVo(10L,10L);
        List<ShareActivityStrategyVo> list = new ArrayList<>();
        list.add(shareActivityStrategyVo);
        ShareActivityVo shareActivityVo = new ShareActivityVo();
        shareActivityVo.setName("测试活动");
        shareActivityVo.setBeginTime(LocalDateTime.parse("2021-11-11T18:30:30"));
        shareActivityVo.setEndTime(LocalDateTime.parse("2021-11-11T19:30:40"));
        shareActivityVo.setStrategy(list);
        String json= JacksonUtil.toJson(shareActivityVo);
        String responseString=this.mvc.perform(put("/shops/1/shareactivities/4")
                        .contentType("application/json;charset=UTF-8").content(json))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        String expectedString= "{\n" +
                "\t\"errno\": 0,\n" +
                "\t\"errmsg\": \"成功\"\n" +
                "}";
        JSONAssert.assertEquals(expectedString,responseString,false);
    }
    @Test
    @Transactional
    public void deleteShareActivity_ShareActivityIdNotFound() throws Exception{
        String responseString=this.mvc.perform(delete("/shops/1/shareactivities/-1"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        String expectedString="{\n" +
                "\t\"errno\": 504,\n" +
                "\t\"errmsg\": \"操作的资源id不存在\"\n" +
                "}";
        JSONAssert.assertEquals(expectedString,responseString,false);
    }
    @Test
    @Transactional
    public void deleteShareActivity_NotDraftState() throws Exception{
        String responseString=this.mvc.perform(delete("/shops/1/shareactivities/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        String expectedString = "{\n" +
                "\t\"errno\": 507,\n" +
                "\t\"errmsg\": \"当前状态禁止此操作\"\n" +
                "}";
        JSONAssert.assertEquals(expectedString,responseString,false);
    }
    @Test
    @Transactional
    public void deleteShareActivity_Success() throws Exception{
        String responseString=this.mvc.perform(delete("/shops/1/shareactivities/4"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        String expectedString = "{\n" +
                "\t\"errno\": 0,\n" +
                "\t\"errmsg\": \"成功\"\n" +
                "}";
        JSONAssert.assertEquals(expectedString,responseString,false);
    }
    @Test
    @Transactional
    public void shareActivityOnline_NotOfflineState() throws Exception{
        String responseString=this.mvc.perform(put("/shops/1/shareactivities/1/online"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        String expectedString = "{\n" +
                "\t\"errno\": 507,\n" +
                "\t\"errmsg\": \"当前状态禁止此操作\"\n" +
                "}";
        JSONAssert.assertEquals(expectedString,responseString,false);
    }
    @Test
    @Transactional
    public void shareActivityOnline_ShareActivityIdNotFound() throws Exception{
        String responseString=this.mvc.perform(put("/shops/1/shareactivities/-1/online"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        String expectedString = "{\n" +
                "\t\"errno\": 504,\n" +
                "\t\"errmsg\": \"操作的资源id不存在\"\n" +
                "}";
        JSONAssert.assertEquals(expectedString,responseString,false);
    }
    @Test
    @Transactional
    public void shareActivityOnline_Success() throws Exception{
        String responseString=this.mvc.perform(put("/shops/1/shareactivities/2/online"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        String expectedString = "{\n" +
                "\t\"errno\": 0,\n" +
                "\t\"errmsg\": \"成功\"\n" +
                "}";
        JSONAssert.assertEquals(expectedString,responseString,false);
    }
    @Test
    @Transactional
    public void shareActivityOffline_NotOnlineState() throws Exception{
        String responseString=this.mvc.perform(put("/shops/1/shareactivities/3/offline"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        String expectedString = "{\n" +
                "\t\"errno\": 507,\n" +
                "\t\"errmsg\": \"当前状态禁止此操作\"\n" +
                "}";
        JSONAssert.assertEquals(expectedString,responseString,false);
    }
    @Test
    @Transactional
    public void shareActivityOffline_ShareActivityIdNotFound() throws Exception{
        String responseString=this.mvc.perform(put("/shops/1/shareactivities/-1/offline"))
                .andExpect(status().is4xxClientError())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        String expectedString = "{\n" +
                "\t\"errno\": 504,\n" +
                "\t\"errmsg\": \"操作的资源id不存在\"\n" +
                "}";
        JSONAssert.assertEquals(expectedString,responseString,false);
    }
    @Test
    @Transactional
    public void shareActivityOffline_Success() throws Exception{
        String responseString=this.mvc.perform(put("/shops/1/shareactivities/1/offline"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        String expectedString = "{\n" +
                "\t\"errno\": 0,\n" +
                "\t\"errmsg\": \"成功\"\n" +
                "}";
        JSONAssert.assertEquals(expectedString,responseString,false);
    }
}
