
package cn.edu.xmu.oomall.shop.controller;

import cn.edu.xmu.oomall.core.util.Common;
import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.shop.model.bo.Shop;
import cn.edu.xmu.oomall.shop.model.vo.ShopConclusionVo;
import cn.edu.xmu.oomall.shop.model.vo.ShopVo;
import cn.edu.xmu.oomall.shop.service.ShopService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Api(value = "店铺", tags = "shop")
@RestController /*Restful的Controller对象*/
@RequestMapping(value = "/", produces = "application/json;charset=UTF-8")
@Component
public class ShopController {
    @Autowired
    private HttpServletResponse httpServletResponse;

    @Autowired
    private ShopService shopService;

    @ApiOperation(value = "获得店铺的所有状态")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "成功") })
    @GetMapping(value = "/shops/states")
    public Object getshopState()
    {
        ReturnObject<List> returnObject=shopService.getShopStates();
        return Common.decorateReturnObject(returnObject);
    }


    @ApiOperation(value = "店家申请店铺")
    @ApiImplicitParam(name = "authorization", value = "shopToken", required = true, dataType = "String", paramType = "header")
    @ApiResponses(value = {
            @ApiResponse(code = 969, message = "用户已经有店铺"),
            @ApiResponse(code = 200, message = "成功") })
    @PostMapping(value = "/shops")
//    @Audit
    public Object addShop(@Validated @RequestBody ShopVo shopvo, BindingResult bindingResult,Long shopid, Long loginUser,String loginUsername){
    //todo:
        loginUser=Long.valueOf(111);
        loginUsername="hhhhh";
        shopid=Long.valueOf(-1);

        Object obj = Common.processFieldErrors(bindingResult,httpServletResponse);
        if (null != obj) {
            return obj;
        }

        if(shopid == -1)
        {

            var ret = shopService.newShop(shopvo,loginUser,loginUsername);
            if(ret.getCode().equals(ReturnNo.OK))httpServletResponse.setStatus(HttpStatus.OK.value());
            return Common.decorateReturnObject(ret);

        }
        else
            return Common.decorateReturnObject(new ReturnObject(ReturnNo.SHOP_USER_HASSHOP, "您已经拥有店铺，无法重新申请"));

    }


    @ApiOperation(value = "店家修改店铺信息", nickname = "modifyShop", notes = "", tags={ "shop", })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "shopToken", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "id", value = "商店id", required = true, dataType = "Long", paramType = "path")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "成功"),
            @ApiResponse(code = 507, message = "该店铺无法修改")})
    @PutMapping(value = "/shops/{id}")
    public Object modifyShop(@Validated @RequestBody ShopVo shopVo,BindingResult bindingResult,@PathVariable Long id,Long loginUser,String loginUsername){
        //todo:
        loginUser=Long.valueOf(111);
        loginUsername="hhhhh";
        Object obj = Common.processFieldErrors(bindingResult,httpServletResponse);
        if (null != obj) {
            return obj;
        }
        else
        {
            ReturnObject ret=shopService.updateShop(id,shopVo, loginUser,loginUsername);
            return Common.decorateReturnObject(ret);
        }
    }


    @ApiOperation(value = "管理员或店家关闭店铺", nickname = "deleteShop", notes = "如果店铺从未上线则物理删除",  tags={ "shop", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "成功") ,
            @ApiResponse(code = 180, message = "该店铺无法被执行关闭操作")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "shopToken", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "id", value = "商店id", required = true, dataType = "Long", paramType = "path")
    })
    @DeleteMapping(value = "/shops/{id}")
    public Object deleteShop(@ApiParam(value = "shop ID",required=true) @PathVariable("id") Long id,Long loginUser,String loginUsername){
        //todo:
        loginUser=Long.valueOf(111);
        loginUsername="hhhhh";

        var shop = shopService.getShopByShopId(id).getData();
        if(shop.getState() == Shop.State.OFFLINE.getCode().byteValue())
        {

            ReturnObject ret=shopService.deleteShopById(id, loginUser,loginUsername);
            return Common.decorateReturnObject(ret);
        }
        else
        {
            return Common.decorateReturnObject(new ReturnObject(ReturnNo.STATENOTALLOW));
        }
    }


    @ApiOperation(value = "平台管理员审核店铺信息", nickname = "shopsShopIdNewshopsIdAuditPut", notes = "",  tags={ "shop", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "成功") ,
            @ApiResponse(code = 150, message = "该店铺不是待审核状态")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "adminToken", required = true, dataType = "String", paramType = "header")
    })
    @PutMapping(value = "/shops/{shopId}/newshops/{id}/audit")
    public Object auditShop(@PathVariable("shopId") Long shopId,@ApiParam(value = "新店 ID",required=true) @PathVariable("id") Long id,@ApiParam(value = "" ,required=true )   @RequestBody ShopConclusionVo conclusion,Long loginUser,String loginUsername){
        //todo:
        loginUser=Long.valueOf(111);
        loginUsername="hhhhh";
        var shop = shopService.getShopByShopId(id).getData();
        if(shop.getState() == Shop.State.EXAME.getCode().byteValue())
        {
            ReturnObject ret=shopService.passShop(id,conclusion,loginUser,loginUsername);
            return Common.decorateReturnObject(ret);
        }
        else
        {
            return Common.decorateReturnObject(new ReturnObject(ReturnNo. STATENOTALLOW));
        }
    }


    @ApiOperation(value = "管理员上线店铺", nickname = "shopsIdOnshelvesPut", notes = "", tags={ "shop", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "成功"),
            @ApiResponse(code = 160, message = "该店铺无法上线")
    })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "adminToken", required = true, dataType = "String", paramType = "header")
    })
    @PutMapping(value = "/shops/{id}/onshelves")
//    @Audit
    public Object shopsIdOnshelvesPut(@PathVariable("id") long id,Long loginUser,String loginUsername){
        //todo:
        loginUser=Long.valueOf(111);
        loginUsername="hhhhh";

        ReturnObject ret= shopService.onShelfShop(id, loginUser,loginUsername);
        return Common.decorateReturnObject(ret);
    }


    @ApiOperation(value = "管理员下线店铺", nickname = "shopsIdOffshelvesPut", notes = "", tags={ "shop", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "成功"),
            @ApiResponse(code = 170, message = "该店铺无法下线")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization", value = "adminToken", required = true, dataType = "String", paramType = "header")
    })
    @PutMapping(value = "/shops/{id}/offshelves")
//    @Audit
    public Object shopsIdOffshelvesPut(@PathVariable("id") long id,Long loginUser,String loginUsername){
        //todo:
        loginUser=Long.valueOf(111);
        loginUsername="hhhhh";

        ReturnObject ret= shopService.offShelfShop(id,loginUser,loginUsername);
        return Common.decorateReturnObject(ret);
    }

}
