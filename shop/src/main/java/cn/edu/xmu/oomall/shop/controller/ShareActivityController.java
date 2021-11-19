package cn.edu.xmu.oomall.shop.controller;

import cn.edu.xmu.oomall.core.util.Common;
import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.shop.model.vo.ShareActivityVo;
import cn.edu.xmu.oomall.shop.service.ShareActivityService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @author BingShuai Liu
 * @studentId 22920192204245
 * @date 2021/11/13/15:37
 */
@Api(value = "分享活动", tags = "shareactivity")
@RestController
@RequestMapping(value = "/", produces = "application/json;charset=UTF-8")
@Component
public class ShareActivityController {

    @Autowired
    ShareActivityService shareActivityService;

    @ApiOperation(value = "在已有销售上增加分享")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization ", value = "token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "shopId", value = "店铺id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "id", value = "在售商品id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "sid", value = "分享活动id", required = true, dataType = "Integer", paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 507, message = "信息签名不正确"),
            @ApiResponse(code = 504, message = "资源不存在"),
            @ApiResponse(code = 500, message = "服务器内部错误")
    })
    @PostMapping("/shops/{shopId}/onSale/{id}/shareActivities/{sid}")
    public Object addShareActivityOnOnSale(@PathVariable("shopId") Long shopId, @PathVariable("id") Long id, @PathVariable("sid") Long sid,Long loginUser,String loginUsername){
        loginUser= 123L;
        loginUsername="pika";
        ReturnObject onSale = shareActivityService.addShareActivityOnOnSale(id,sid,loginUser,loginUsername);
        return Common.decorateReturnObject(onSale);
    }

    @ApiOperation(value = "取消已有销售上的分享")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization ", value = "token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "shopId", value = "店铺id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "id", value = "在售商品id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "sid", value = "分享活动id", required = true, dataType = "Integer", paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 504, message = "资源不存在"),
            @ApiResponse(code = 500, message = "服务器内部错误")
    })
    @DeleteMapping("/shops/{shopId}/onSale/{id}/shareActivities/{sid}")
    public Object deleteShareActivityOnOnSale(@PathVariable("shopId") Long shopId, @PathVariable("id") Long id, @PathVariable("sid") Long sid,Long loginUser,String loginUsername){
        loginUser= 123L;
        loginUsername="pika";
        ReturnObject returnObject = shareActivityService.deleteShareActivityOnOnSale(id,sid,loginUser,loginUsername);
        return Common.decorateReturnObject(returnObject);
    }

    @ApiOperation(value = "修改平台分享活动的内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization ", value = "token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "shopId", value = "店铺id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "id", value = "分享活动id", required = true, dataType = "Integer", paramType = "path"),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 500, message = "服务器内部错误"),
            @ApiResponse(code = 504, message = "资源不存在"),
            @ApiResponse(code = 507, message = "信息签名不正确"),
            @ApiResponse(code = 947, message = "开始时间不能晚于结束时间")
    })
    @PutMapping("/shops/{shopId}/shareactivities/{id}")
    public Object modifyShareActivity(@PathVariable("shopId") Long shopId, @PathVariable("id") Long id, @RequestBody ShareActivityVo shareActivityVo,Long loginUser,String loginUsername){
        loginUser= 123L;
        loginUsername="pika";
        if(shareActivityVo.getBeginTime().compareTo(shareActivityVo.getEndTime())>0){
            return Common.decorateReturnObject(new ReturnObject(ReturnNo.LATE_BEGINTIME));
        }
        ReturnObject returnObject =shareActivityService.modifyShareActivity(id, shareActivityVo,loginUser,loginUsername);
        return Common.decorateReturnObject(returnObject);
    }

    @ApiOperation(value = "删除草稿状态的分享活动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization ", value = "token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "shopId", value = "店铺id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "id", value = "分享活动id", required = true, dataType = "Integer", paramType = "path"),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 500, message = "服务器内部错误"),
            @ApiResponse(code = 504, message = "资源不存在"),
            @ApiResponse(code = 507, message = "信息签名不正确"),
    })
    @DeleteMapping("/shops/{shopId}/shareactivities/{id}")
    public Object deleteShareActivity(@PathVariable("shopId") Long shopId, @PathVariable("id") Long id,Long loginUser,String loginUsername){
        loginUser= 123L;
        loginUsername="pika";
        ReturnObject returnObject =shareActivityService.deleteShareActivity(id,loginUser,loginUsername);
        return Common.decorateReturnObject(returnObject);
    }

    @ApiOperation(value = "上线分享活动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization ", value = "token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "shopId", value = "店铺id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "id", value = "分享活动id", required = true, dataType = "Integer", paramType = "path"),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 500, message = "服务器内部错误"),
            @ApiResponse(code = 504, message = "资源不存在"),
            @ApiResponse(code = 507, message = "信息签名不正确"),
    })
    @PutMapping("/shops/{shopId}/shareactivities/{id}/online")
    public Object onlineShareActivity(@PathVariable("shopId") Long shopId, @PathVariable("id") Long id,Long loginUser,String loginUsername){
        loginUser= 123L;
        loginUsername="pika";
        ReturnObject returnObject=shareActivityService.onlineShareActivity(id,loginUser,loginUsername);
        return Common.decorateReturnObject(returnObject);
    }

    @ApiOperation(value = "下线分享活动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization ", value = "token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "shopId", value = "店铺id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "id", value = "分享活动id", required = true, dataType = "Integer", paramType = "path"),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 500, message = "服务器内部错误"),
            @ApiResponse(code = 504, message = "资源不存在"),
            @ApiResponse(code = 507, message = "信息签名不正确"),
    })
    @PutMapping("/shops/{shopId}/shareactivities/{id}/offline")
    public Object offlineShareActivity(@PathVariable("shopId") Long shopId, @PathVariable("id") Long id,Long loginUser,String loginUsername){
        loginUser= 123L;
        loginUsername="pika";
        ReturnObject returnObject=shareActivityService.offlineShareActivity(id,loginUser,loginUsername);
        return Common.decorateReturnObject(returnObject);
    }

}
