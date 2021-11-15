package cn.edu.xmu.oomall.freight.controller;

import cn.edu.xmu.oomall.core.util.Common;
import cn.edu.xmu.oomall.core.util.ResponseUtil;
import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.freight.service.RegionService;
import cn.edu.xmu.oomall.freight.model.vo.RegionRetVo;
import cn.edu.xmu.oomall.freight.model.vo.RegionVo;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static cn.edu.xmu.oomall.core.util.Common.*;


/**
 * RegionController
 * @author ziyi guo
 * @date 2021/11/10
 */
@Api(value = "地区API", tags = "地区API")
@RestController
@RequestMapping(value = "/freight", produces = "application/json;charset=UTF-8")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @Autowired
    private HttpServletResponse httpServletResponse;

    @ApiOperation(value = "查询某个地区的所有上级地区",  produces="application/json;charset=UTF-8")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value ="用户token" ,required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Integer", name = "id", value ="该地区id" ,required = true)
    })
    @ApiResponses({
            @ApiResponse(code=0,message = "成功"),
            @ApiResponse(code=500,message = "服务器内部错误"),
            @ApiResponse(code=504,message = "操作的资源id不存在")
    })
    @GetMapping("/region/{id}/ancestor")
    public Object getParentRegion(@PathVariable("id") Long id) {

        ReturnObject returnObject = regionService.getParentRegion(id);

        returnObject = Common.getListRetVo(returnObject,RegionRetVo.class);

        return Common.decorateReturnObject(returnObject);
    }


    @ApiOperation(value = "管理员在地区下新增子地区",  produces="application/json;charset=UTF-8")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value ="用户token" ,required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Integer", name = "did", value ="商铺id" ,required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Integer", name = "id", value ="该地区id" ,required = true),
            @ApiImplicitParam(paramType = "body", dataType = "Object", name = "body", value ="地区信息" ,required = true)
    })
    @ApiResponses({
            @ApiResponse(code=0,message = "成功"),
            @ApiResponse(code=500,message = "服务器内部错误"),
            @ApiResponse(code=504,message = "操作的资源id不存在"),
            @ApiResponse(code=995,message = "地区已废弃")
    })
    @PostMapping("/shops/{did}/regions/{id}/subregions")
    public Object addRegion(@Validated @RequestBody RegionVo regionVo, @PathVariable("did") Integer did, @PathVariable("id") Long id, Long userId, String userName, BindingResult bindingResult){

        if(did!=0){
            return new ResponseEntity(ResponseUtil.fail(ReturnNo.RESOURCE_ID_OUTSCOPE, "非管理员无权操作"), HttpStatus.FORBIDDEN);
        }
        userId=Long.valueOf(1);
        userName="admin";

        Object object = processFieldErrors(bindingResult, httpServletResponse);
        if (object != null){
            return object;
        }

        ReturnObject returnObject = regionService.createRegion(regionVo,id,userId,userName);

        return Common.decorateReturnObject(returnObject);
    }


    @ApiOperation(value = "管理员查询在地区下的子地区",  produces="application/json;charset=UTF-8")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value ="用户token" ,required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Integer", name = "did", value ="商铺id" ,required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Integer", name = "id", value ="该地区id" ,required = true)
    })
    @ApiResponses({
            @ApiResponse(code=0,message = "成功"),
            @ApiResponse(code=500,message = "服务器内部错误"),
            @ApiResponse(code=504,message = "操作的资源id不存在")
    })
    @GetMapping("/shops/{did}/regions/{id}/subregions")
    public Object adminGetChildRegion(@PathVariable("did") Integer did, @PathVariable("id") Long id) {

        if(did!=0){
            return new ResponseEntity(ResponseUtil.fail(ReturnNo.RESOURCE_ID_OUTSCOPE, "非管理员无权操作"), HttpStatus.FORBIDDEN);
        }

        ReturnObject returnObject = regionService.getChildRegion(id, Long.valueOf(did));

        return Common.decorateReturnObject(returnObject);
    }


    @ApiOperation(value = "查询在地区下的子地区",  produces="application/json;charset=UTF-8")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "path", dataType = "Integer", name = "id", value ="该地区id" ,required = true)
    })
    @ApiResponses({
            @ApiResponse(code=0,message = "成功"),
            @ApiResponse(code=500,message = "服务器内部错误"),
            @ApiResponse(code=504,message = "操作的资源id不存在"),
            @ApiResponse(code=995,message = "地区已废弃")
    })
    @GetMapping("/regions/{id}/subregions")
    public Object getChildRegion(@PathVariable("id") Long id) {

        ReturnObject returnObject = regionService.getChildRegion(id, 1L);

        return Common.decorateReturnObject(returnObject);
    }


    @ApiOperation(value = "管理员修改某个地区",  produces="application/json;charset=UTF-8")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value ="用户token" ,required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Integer", name = "did", value ="商铺id" ,required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Integer", name = "id", value ="该地区id" ,required = true),
            @ApiImplicitParam(paramType = "body", dataType = "Object", name = "body", value ="地区信息" ,required = true)
    })
    @ApiResponses({
            @ApiResponse(code=0,message = "成功"),
            @ApiResponse(code=500,message = "服务器内部错误"),
            @ApiResponse(code=504,message = "操作的资源id不存在")
    })
    @PutMapping("/shops/{did}/regions/{id}")
    public Object modifyRegion(@Validated @RequestBody RegionVo regionVo, @PathVariable("did") Integer did, @PathVariable("id") Long id, Long userId, String userName, BindingResult bindingResult){

        if(did!=0){
            return new ResponseEntity(ResponseUtil.fail(ReturnNo.RESOURCE_ID_OUTSCOPE, "非管理员无权操作"), HttpStatus.FORBIDDEN);
        }
        userId=Long.valueOf(1);
        userName="admin";

        Object object = processFieldErrors(bindingResult, httpServletResponse);
        if (null != object){
            return object;
        }

        ReturnObject returnObject = regionService.modifyRegion(regionVo, id, userId, userName);

        return Common.decorateReturnObject(returnObject);
    }


    @ApiOperation(value = "管理员废弃某个地区",  produces="application/json;charset=UTF-8")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value ="用户token" ,required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Integer", name = "did", value ="商铺id" ,required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Integer", name = "id", value ="该地区id" ,required = true)
    })
    @ApiResponses({
            @ApiResponse(code=0,message = "成功"),
            @ApiResponse(code=500,message = "服务器内部错误"),
            @ApiResponse(code=504,message = "操作的资源id不存在"),
            @ApiResponse(code=507,message = "当前状态禁止此操作")
    })
    @DeleteMapping("/shops/{did}/regions/{id}")
    public Object abandonRegion(@PathVariable("did") Integer did, @PathVariable("id") Long id, Long userId, String userName) {

        if(did!=0){
            return new ResponseEntity(ResponseUtil.fail(ReturnNo.RESOURCE_ID_OUTSCOPE, "非管理员无权操作"), HttpStatus.FORBIDDEN);
        }
        userId=Long.valueOf(1);
        userName="admin";

        ReturnObject returnObject = regionService.abandonRegion(id, userId, userName);

        return Common.decorateReturnObject(returnObject);
    }


    @ApiOperation(value = "管理员停用某个地区",  produces="application/json;charset=UTF-8")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value ="用户token" ,required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Integer", name = "did", value ="商铺id" ,required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Integer", name = "id", value ="该地区id" ,required = true)
    })
    @ApiResponses({
            @ApiResponse(code=0,message = "成功"),
            @ApiResponse(code=500,message = "服务器内部错误"),
            @ApiResponse(code=504,message = "操作的资源id不存在"),
            @ApiResponse(code=507,message = "当前状态禁止此操作")
    })
    @PutMapping("/shops/{did}/regions/{id}/suspend")
    public Object suspendRegion(@PathVariable("did") Integer did, @PathVariable("id") Long id, Long userId, String userName) {

        if(did!=0){
            return new ResponseEntity(ResponseUtil.fail(ReturnNo.RESOURCE_ID_OUTSCOPE, "非管理员无权操作"), HttpStatus.FORBIDDEN);
        }
        userId=Long.valueOf(1);
        userName="admin";

        ReturnObject returnObject = regionService.suspendRegion(id, userId, userName);

        return Common.decorateReturnObject(returnObject);
    }


    @ApiOperation(value = "管理员恢复某个地区",  produces="application/json;charset=UTF-8")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value ="用户token" ,required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Integer", name = "did", value ="商铺id" ,required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Integer", name = "id", value ="该地区id" ,required = true)
    })
    @ApiResponses({
            @ApiResponse(code=0,message = "成功"),
            @ApiResponse(code=500,message = "服务器内部错误"),
            @ApiResponse(code=504,message = "操作的资源id不存在"),
            @ApiResponse(code=507,message = "当前状态禁止此操作")
    })
    @PutMapping("/shops/{did}/regions/{id}/resume")
    public Object resumeRegion(@PathVariable("did") Integer did, @PathVariable("id") Long id, Long userId, String userName) {

        if(did!=0){
            return new ResponseEntity(ResponseUtil.fail(ReturnNo.RESOURCE_ID_OUTSCOPE, "非管理员无权操作"), HttpStatus.FORBIDDEN);
        }
        userId=Long.valueOf(1);
        userName="admin";

        ReturnObject returnObject = regionService.resumeRegion(id, userId, userName);

        return Common.decorateReturnObject(returnObject);
    }
}
