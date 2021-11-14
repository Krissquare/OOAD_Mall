import cn.edu.xmu.oomall.activity.ActivityApplication;
import cn.edu.xmu.oomall.activity.model.vo.GrouponUpdateSimpleVo;
import cn.edu.xmu.oomall.activity.model.vo.OnsaleSimpleVo;
import cn.edu.xmu.oomall.activity.openfeign.OnsalesApi;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ActivityApplication.class)
@AutoConfigureMockMvc
@Transactional
public class GrouponControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean(name = "cn.edu.xmu.oomall.activity.openfeign.OnsalesApi")
    private OnsalesApi onsalesApiMock;

    ///////////////上线///////////////
    @Test public void grouponUpLineByManagerTest1() throws Exception{
        String responseString
                =this.mockMvc.perform(put("/shops/3/groupons/3/online"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse,responseString,true);
    }
    @Test public void grouponUpLineByManagerTest2() throws Exception{
        String responseString
                =this.mockMvc.perform(put("/shops/3/groupons/1/online"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\": 507, \"errmsg\": \"已经是上线状态！\"}";
        JSONAssert.assertEquals(expectedResponse,responseString,true);
    }
    @Test public void grouponUpLineByManagerTest3() throws Exception{
        String responseString
                =this.mockMvc.perform(put("/shops/3/groupons/4/online"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\": 505, \"errmsg\": \"该店铺没有这个活动！\"}";
        JSONAssert.assertEquals(expectedResponse,responseString,true);
    }
    @Test public void grouponUpLineByManagerTest4() throws Exception{
        String responseString
                =this.mockMvc.perform(put("/shops/3/groupons/999/online"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\": 924, \"errmsg\": \"不存在的团购活动！\"}";
        JSONAssert.assertEquals(expectedResponse,responseString,true);
    }
    ///////////////下线///////////////
    @Test public void grouponOfflineByManagerTest1() throws Exception{
        String responseString
                =this.mockMvc.perform(put("/shops/1/groupons/6/offline"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        Mockito.when(onsalesApiMock.setGrouponOnsalesToOffline(6)).thenReturn(null);

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse,responseString,true);
    }
    @Test public void grouponOfflineByManagerTest2() throws Exception{
        String responseString
                =this.mockMvc.perform(put("/shops/1/groupons/4/offline"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\": 507, \"errmsg\": \"已经是下线状态！\"}";
        JSONAssert.assertEquals(expectedResponse,responseString,true);
    }
    @Test public void grouponOfflineByManagerTest3() throws Exception{
        String responseString
                =this.mockMvc.perform(put("/shops/1/groupons/1/offline"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\": 505, \"errmsg\": \"该店铺没有这个活动！\"}";
        JSONAssert.assertEquals(expectedResponse,responseString,true);
    }
    @Test public void grouponOfflineByManagerTest4() throws Exception{
        String responseString
                =this.mockMvc.perform(put("/shops/1/groupons/999/offline"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\": 924, \"errmsg\": \"不存在的团购活动！\"}";
        JSONAssert.assertEquals(expectedResponse,responseString,true);
    }
    @Test public void grouponOfflineByManagerTest5() throws Exception{
        String responseString
                =this.mockMvc.perform(put("/shops/4/groupons/16/offline"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\": 507, \"errmsg\": \"信息签名不正确\"}";
        JSONAssert.assertEquals(expectedResponse,responseString,true);
    }
    ///////////////物理删除///////////////
    @Test public void delTest1() throws Exception{
        String responseString
                =this.mockMvc.perform(delete("/shops/1/groupons/12"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse,responseString,true);
    }
    @Test public void delTest2() throws Exception{
        String responseString
                =this.mockMvc.perform(delete("/shops/1/groupons/999"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":507,\"errmsg\":\"当前状态禁止此操作\"}";
        JSONAssert.assertEquals(expectedResponse,responseString,true);
    }
    @Test public void delTest3() throws Exception{
        String responseString
                =this.mockMvc.perform(delete("/shops/1/groupons/7"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":507,\"errmsg\":\"当前状态禁止此操作\"}";
        JSONAssert.assertEquals(expectedResponse,responseString,true);
    }
    @Test public void delTest4() throws Exception{
        String responseString
                =this.mockMvc.perform(delete("/shops/1/groupons/15"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":507,\"errmsg\":\"当前状态禁止此操作\"}";
        JSONAssert.assertEquals(expectedResponse,responseString,true);
    }
    ///////////////修改///////////////
    @Test public void updTest1() throws Exception{
        HashMap<String,Object> retSuccess = new HashMap<>();
        retSuccess.put("errno",0);
        GrouponUpdateSimpleVo updVo
                = new GrouponUpdateSimpleVo("test-strategy","2021-11-11 22:22:22","2021-11-22 22:22:22");

        String body = "{\n" +
                "    \"strategy\": \"test-strategy\",\n" +
                "    \"beginTime\": \"2021-11-11 22:22:22\",\n" +
                "    \"endTime\": \"2021-11-22 22:22:22\"\n" +
                "}";

        Mockito.when(onsalesApiMock.updateGrouponOnsale(17,updVo)).thenReturn(retSuccess);
        String responseString
                =this.mockMvc.perform(put("/shops/5/groupons/17")
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":0,\"errmsg\":\"成功\"}";
        JSONAssert.assertEquals(expectedResponse,responseString,true);
    }
    @Test public void updTest2() throws Exception{
        HashMap<String,Object> retConflict = new HashMap<>();
        retConflict.put("errno",902);
        GrouponUpdateSimpleVo updVo
                = new GrouponUpdateSimpleVo("test-strategy","2021-11-11 22:22:22","2021-11-22 22:22:22");

        String body = "{\n" +
                "    \"strategy\": \"test-strategy\",\n" +
                "    \"beginTime\": \"2021-11-11 22:22:22\",\n" +
                "    \"endTime\": \"2021-11-22 22:22:22\"\n" +
                "}";

        Mockito.when(onsalesApiMock.updateGrouponOnsale(17,updVo)).thenReturn(retConflict);
        String responseString
                =this.mockMvc.perform(put("/shops/5/groupons/17")
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":902,\"errmsg\":\"商品销售时间冲突\"}";
        JSONAssert.assertEquals(expectedResponse,responseString,true);
    }
    @Test public void updTest3() throws Exception{
        String body = "{\n" +
                "    \"strategy\": \"test-strategy\",\n" +
                "    \"beginTime\": \"2021-11-11 22:22:22\",\n" +
                "    \"endTime\": \"2021-11-22 22:22:22\"\n" +
                "}";

        String responseString
                =this.mockMvc.perform(put("/shops/5/groupons/999")
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":924,\"errmsg\":\"不存在的团购活动！\"}";
        JSONAssert.assertEquals(expectedResponse,responseString,true);
    }
    @Test public void updTest4() throws Exception{
        String body = "{\n" +
                "    \"strategy\": \"test-strategy\",\n" +
                "    \"beginTime\": \"2021-11-11 22:22:22\",\n" +
                "    \"endTime\": \"2021-11-22 22:22:22\"\n" +
                "}";

        String responseString
                =this.mockMvc.perform(put("/shops/5/groupons/10")
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":505,\"errmsg\":\"该店铺没有这个活动！\"}";
        JSONAssert.assertEquals(expectedResponse,responseString,true);
    }
    @Test public void updTest5() throws Exception{
        String body = "{\n" +
                "    \"strategy\": \"test-strategy\",\n" +
                "    \"beginTime\": \"2021-11-11 22:22:22\",\n" +
                "    \"endTime\": \"2021-11-22 22:22:22\"\n" +
                "}";

        String responseString
                =this.mockMvc.perform(put("/shops/3/groupons/2")
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();

        String expectedResponse = "{\"errno\":507,\"errmsg\":\"当前状态禁止此操作\"}";
        JSONAssert.assertEquals(expectedResponse,responseString,true);
    }
    ///////////////新增团购商品///////////////
    @Test public void addTest1() throws Exception{
        OnsaleSimpleVo onsaleSimpleVo = new OnsaleSimpleVo();
        Mockito.when(onsalesApiMock.addOneGrouponOnsale(6,18,11)).thenReturn(new ReturnObject<OnsaleSimpleVo>(onsaleSimpleVo));
        String responseString
                =this.mockMvc.perform(put("/shops/6/products/11/groupons/18/onsale"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
    }
    @Test public void addTest2() throws Exception{
        OnsaleSimpleVo onsaleSimpleVo = new OnsaleSimpleVo();
        Mockito.when(onsalesApiMock.addOneGrouponOnsale(1,15,11)).thenReturn(new ReturnObject<OnsaleSimpleVo>(onsaleSimpleVo));
        String responseString
                =this.mockMvc.perform(put("/shops/1/products/11/groupons/15/onsale"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andReturn().getResponse().getContentAsString();
    }

}
