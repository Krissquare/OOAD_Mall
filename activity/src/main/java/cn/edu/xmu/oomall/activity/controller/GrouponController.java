package cn.edu.xmu.oomall.activity.controller;

import cn.edu.xmu.oomall.activity.model.bo.ShopAdministrator;
import cn.edu.xmu.oomall.activity.microservice.vo.GrouponUpdateSimpleVo;
import cn.edu.xmu.oomall.activity.model.vo.NewGrouponProductOnSaleVo;
import cn.edu.xmu.oomall.activity.service.GrouponService;
import cn.edu.xmu.oomall.core.util.Common;
import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Api(tags = "管理员上下线团购活动")
@RestController
public class GrouponController {

    @Autowired
    GrouponService grouponService;

    /**
     * 管理员上线团购活动
     * 下线态才可操作，否则出507错误
     * 若OnSale的开始时间早于当前时间，修改OnSale的开始时间为当前时间
     * 修改OnSale的状态为上线
     * @param shopId 商铺id
     * @param id 团购活动id
     * @return 结果状态
     *      507: 禁止操作
     *      default: 成功
     */
    @ApiOperation(value="管理员上线团购活动", produces="application/json;charset=UTF-8")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType="header",dataType="String",name="authorization",value="用户token",required=true),
        @ApiImplicitParam(paramType="path",dataType="Integer",name="shopId",value="商铺id",required=true),
        @ApiImplicitParam(paramType="path",dataType="Integer",name="id",value="团购活动id",required=true)
    })
    @PutMapping(value = "/shops/{shopId}/groupons/{id}/online", produces = "application/json;charset=UTF-8")
    public Object grouponUpLineByManager(@PathVariable("shopId")Integer shopId,
                                         @PathVariable("id")Integer id){
        ShopAdministrator admin = new ShopAdministrator((long)233,"xxx");

        ReturnObject result = grouponService.onlineGrouponActivity(id,shopId,admin);
        return Common.decorateReturnObject(result);
    }

    /**
     * 管理员下线团购活动
     * 上线态才可操作，否则出507错误
     * 修改OnSale的状态为下线，结束时间为当前时间
     * @param shopId 商铺id
     * @param id 团购活动id
     * @return 结果状态
     *      507: 禁止操作
     *      default: 成功
     */
    @ApiOperation(value="管理员下线团购活动", produces="application/json;charset=UTF-8")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="header",dataType="String",name="authorization",value="用户token",required=true),
            @ApiImplicitParam(paramType="path",dataType="Integer",name="shopId",value="商铺id",required=true),
            @ApiImplicitParam(paramType="path",dataType="Integer",name="id",value="团购活动id",required=true)
    })
    @PutMapping(value = "/shops/{shopId}/groupons/{id}/offline", produces = "application/json;charset=UTF-8")
    public Object grouponOfflineByManager(@PathVariable("shopId")Integer shopId,
                                          @PathVariable("id")Integer id){
        ShopAdministrator admin = new ShopAdministrator((long)666,"yyy");

        return Common.decorateReturnObject(grouponService.offlineGrouponActivity(id,shopId,admin));
    }

    /**
     * 管理员修改团购活动
     * 团购为草稿态时才可操作，否则出507错误
     * 同步修改团购的OnSale开始时间和结束时间，若与1上线的OnSale时间冲突，出902错误
    * */
    @ApiOperation(value="管理员修改团购活动", produces="application/json;charset=UTF-8")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="header",dataType="String",name="authorization",value="用户token",required=true),
            @ApiImplicitParam(paramType="path",dataType="Integer",name="shopId",value="商铺id",required=true),
            @ApiImplicitParam(paramType="path",dataType="Integer",name="id",value="团购活动id",required=true),
            @ApiImplicitParam(paramType="body",dataType="Object",name="actUpd",value="变更内容",required = true)
    })
    @PutMapping(value = "/shops/{shopId}/groupons/{id}", produces = "application/json;charset=UTF-8")
    public Object grouponUpdatedByManager(@PathVariable("shopId")Integer shopId,
                                          @PathVariable("id")Integer id,
                                          @RequestBody GrouponUpdateSimpleVo actUpd){
        ShopAdministrator admin = new ShopAdministrator((long)233,"xxx");
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        if (LocalDateTime.parse(actUpd.getBeginTime(),df).isAfter(LocalDateTime.parse(actUpd.getEndTime(),df))){
            return Common.decorateReturnObject(new ReturnObject(ReturnNo.ACT_LATE_BEGINTIME));
        }
        return Common.decorateReturnObject(grouponService.updateGrouponActivity(id,shopId,actUpd,admin));
    }

    /**
     * 管理员物理删除团购活动
     * 草稿态才能操作，否则出507错误
     * 同步物理删除关联的OnSale
     * */
    @ApiOperation(value="管理员物理删除团购活动", produces="application/json;charset=UTF-8")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="header",dataType="String",name="authorization",value="用户token",required=true),
            @ApiImplicitParam(paramType="path",dataType="Integer",name="shopId",value="商铺id",required=true),
            @ApiImplicitParam(paramType="path",dataType="Integer",name="id",value="团购活动id",required=true)
    })
    @DeleteMapping(value="/shops/{shopId}/groupons/{id}",produces="application/json;charset=UTF-8")
    public Object grouponDeletedByManager(@PathVariable("shopId")Integer shopId,
                                          @PathVariable("id")Integer id){
        ShopAdministrator admin = new ShopAdministrator((long)233,"xxx");

        return Common.decorateReturnObject(grouponService.deleteGrouponActivity(id,shopId));
    }

    /**
     * 管理员新增参与团购的商品销售
     * 团购为草稿态时才可操作，否则出507错误
     * 若团购的开始时间晚于当前时间，则OnSale的开始时间为团购的开始时间，否则OnSale的开始时间为当前时间
     * 结束时间与团购时间一致
     * 新增的OnSale为0草稿态，若与1上线的OnSale时间冲突，出902错误
     * */
    @ApiOperation(value="管理员新增参与团购的商品销售", produces="application/json;charset=UTF-8")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="header",dataType="String",name="authorization",value="用户token",required=true),
            @ApiImplicitParam(paramType="path",dataType="Integer",name="shopId",value="商铺id",required=true),
            @ApiImplicitParam(paramType="path",dataType="Integer",name="id",value="团购活动id",required=true),
            @ApiImplicitParam(paramType="path",dataType="Integer",name="pid",value = "货品id",required = true)
    })
    @PutMapping(value = "/shops/{shopId}/products/{pid}/groupons/{id}/onsale",produces = "application/json;charset=UTF-8")
    public Object grouponAddedByManager(@PathVariable("shopId")Integer shopId,
                                        @PathVariable("pid")Integer pid,
                                        @PathVariable("id")Integer id){
        ShopAdministrator admin = new ShopAdministrator((long)233,"xxx");

        NewGrouponProductOnSaleVo retVO = new NewGrouponProductOnSaleVo();
        return Common.decorateReturnObject(grouponService.addGrouponProduct(id,shopId,pid,admin,retVO));
    }


}
