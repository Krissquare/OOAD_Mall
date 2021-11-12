package cn.edu.xmu.oomall.freight.controller;

import cn.edu.xmu.oomall.core.model.VoObject;
import cn.edu.xmu.oomall.core.util.ResponseUtil;
import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.freight.service.RegionService;
import cn.edu.xmu.oomall.goods.model.bo.Region;
import cn.edu.xmu.oomall.goods.model.vo.RegionRetVo;
import cn.edu.xmu.oomall.goods.model.vo.RegionVo;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

import static cn.edu.xmu.oomall.core.util.Common.*;


/**
 * @author ziyi guo
 * @date 2021/11/10
 */
@RestController
@RequestMapping(value = "/freight", produces = "application/json;charset=UTF-8")
public class RegionController {

    private final Logger logger = LoggerFactory.getLogger(RegionController.class);

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
            @ApiResponse(code=0,message = "成功")
    })
    @GetMapping("/region/{id}/ancestor")
    public Object getParentRegion(@PathVariable("id") Long id) {

        ReturnObject<List<Region>> returnObject =  regionService.getParentRegionById(id);
        ReturnNo code = returnObject.getCode();
        switch (code){
            case RESOURCE_ID_NOTEXIST:
                httpServletResponse.setStatus(HttpStatus.NOT_FOUND.value());
                return ResponseUtil.fail(returnObject.getCode(), returnObject.getErrmsg());
            case OK:
                List<RegionRetVo> regionRetVos = new ArrayList<>();
                for(Region regionItem : returnObject.getData()) {
                    regionRetVos.add(regionItem.createVo());
                }
                return ResponseUtil.ok(regionRetVos);
            default:
                return ResponseUtil.fail(code);
        }
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
            @ApiResponse(code=995,message = "地区已废弃")
    })
    @PostMapping("/shops/{did}/regions/{id}/subregions")
    public Object addRegion(@Validated @RequestBody RegionVo regionVo, @PathVariable("did") Integer did, @PathVariable("id") Long id, Long userId, String userName, BindingResult bindingResult){

        userId=Long.valueOf(1);
        userName="admin";
        Object returnObject = processFieldErrors(bindingResult, httpServletResponse);
        if (null != returnObject){
            return returnObject;
        }
        ReturnObject<VoObject> returnObject1 = regionService.createRegion(regionVo,id,userId,userName);
        if(returnObject1.getCode()==ReturnNo.FREIGHT_REGIONOBSOLETE) {
            httpServletResponse.setStatus(HttpStatus.NOT_ACCEPTABLE.value());
        }
        else {
            httpServletResponse.setStatus(HttpStatus.CREATED.value());
        }
        return getRetObject(returnObject1);
    }

    @ApiOperation(value = "管理员修改某个地区",  produces="application/json;charset=UTF-8")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value ="用户token" ,required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Integer", name = "did", value ="商铺id" ,required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Integer", name = "id", value ="该地区id" ,required = true),
            @ApiImplicitParam(paramType = "body", dataType = "Object", name = "body", value ="地区信息" ,required = true)
    })
    @ApiResponses({
            @ApiResponse(code=0,message = "成功")
    })
    @PutMapping("/shops/{did}/regions/{id}")
    public Object modifyRegion(@Validated @RequestBody RegionVo regionVo, @PathVariable("did") Integer did, @PathVariable("id") Long id, Long userId, String userName, BindingResult bindingResult){

        userId=Long.valueOf(1);
        userName="admin";
        Object returnObject = processFieldErrors(bindingResult, httpServletResponse);
        if (null != returnObject){
            return returnObject;
        }
        ReturnObject<Object> returnObject1 = regionService.modifyRegion(id, regionVo, userId, userName);
        return getNullRetObj(returnObject1, httpServletResponse);
    }

    @ApiOperation(value = "管理员废弃某个地区",  produces="application/json;charset=UTF-8")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value ="用户token" ,required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Integer", name = "did", value ="商铺id" ,required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Integer", name = "id", value ="该地区id" ,required = true)
    })
    @ApiResponses({
            @ApiResponse(code=0,message = "成功"),
            @ApiResponse(code=507,message = "当前状态禁止此操作")
    })
    @DeleteMapping("/shops/{did}/regions/{id}")
    public Object abandonRegion(@PathVariable("did") Integer did, @PathVariable("id") Long id, Long userId, String userName) {

        userId=Long.valueOf(1);
        userName="admin";
        ReturnObject<Object> returnObject = regionService.abandonRegion(id, userId, userName);
        return getNullRetObj(returnObject, httpServletResponse);
    }

    @ApiOperation(value = "管理员停用某个地区",  produces="application/json;charset=UTF-8")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value ="用户token" ,required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Integer", name = "did", value ="商铺id" ,required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Integer", name = "id", value ="该地区id" ,required = true)
    })
    @ApiResponses({
            @ApiResponse(code=0,message = "成功"),
            @ApiResponse(code=507,message = "当前状态禁止此操作")
    })
    @PutMapping("/shops/{did}/regions/{id}/suspend")
    public Object suspendRegion(@PathVariable("did") Integer did, @PathVariable("id") Long id, Long userId, String userName) {

        userId=Long.valueOf(1);
        userName="admin";
        ReturnObject<Object> returnObject = regionService.suspendRegion(id, userId, userName);
        return getNullRetObj(returnObject, httpServletResponse);
    }

    @ApiOperation(value = "管理员恢复某个地区",  produces="application/json;charset=UTF-8")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value ="用户token" ,required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Integer", name = "did", value ="商铺id" ,required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Integer", name = "id", value ="该地区id" ,required = true)
    })
    @ApiResponses({
            @ApiResponse(code=0,message = "成功"),
            @ApiResponse(code=507,message = "当前状态禁止此操作")
    })
    @PutMapping("/shops/{did}/regions/{id}/resume")
    public Object resumeRegion(@PathVariable("did") Integer did, @PathVariable("id") Long id, Long userId, String userName) {

        userId=Long.valueOf(1);
        userName="admin";
        ReturnObject<Object> returnObject = regionService.resumeRegion(id, userId, userName);
        return getNullRetObj(returnObject, httpServletResponse);
    }
}
