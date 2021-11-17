package cn.edu.xmu.oomall.freight.controller;

import cn.edu.xmu.oomall.core.util.Common;
import cn.edu.xmu.oomall.core.util.ResponseUtil;
import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.freight.model.vo.WeightFreightRetVo;
import cn.edu.xmu.oomall.freight.model.vo.WeightFreightVo;
import cn.edu.xmu.oomall.freight.service.WeightFreightService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import static cn.edu.xmu.oomall.core.util.Common.processFieldErrors;

/**
 * @author ziyi guo
 * @date 2021/11/16
 */
@Api(value = "重量运费模板API", tags = "重量运费模板API")
@RestController
@RequestMapping(value = "/freight", produces = "application/json;charset=UTF-8")
public class WeightFreightController {

    @Autowired
    private WeightFreightService weightFreightService;

    @Autowired
    private HttpServletResponse httpServletResponse;

    @ApiOperation(value = "管理员定义重量模板明细",  produces="application/json;charset=UTF-8")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value ="用户token" ,required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Integer", name = "shopId", value ="商铺id" ,required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Integer", name = "id", value ="运费模板id" ,required = true),
            @ApiImplicitParam(paramType = "body", dataType = "Object", name = "body", value ="运费模板资料" ,required = true)
    })
    @ApiResponses({
            @ApiResponse(code=0,message = "成功"),
            @ApiResponse(code=500,message = "服务器内部错误"),
            @ApiResponse(code=504,message = "操作的资源id不存在"),
            @ApiResponse(code=997,message = "该运费模板中该地区已经定义"),
            @ApiResponse(code=996,message = "该运费模板类型与内容不符")
    })
    @PostMapping("/shops/{shopId}/freightmodels/{id}/weightItems")
    public Object addWeightItems(@Validated @RequestBody WeightFreightVo weightFreightVo, @PathVariable("shopId") Integer shopId, @PathVariable("id") Long id, BindingResult bindingResult, Long userId, String userName) {

        if(shopId!=0){
            return new ResponseEntity(ResponseUtil.fail(ReturnNo.RESOURCE_ID_OUTSCOPE, "非管理员无权操作"), HttpStatus.FORBIDDEN);
        }
        userId=Long.valueOf(1);
        userName="admin";

        Object object = processFieldErrors(bindingResult, httpServletResponse);
        if (object != null){
            return object;
        }

        ReturnObject returnObject = weightFreightService.addWeightItems(weightFreightVo, id, userId, userName);

        returnObject = new ReturnObject(Common.cloneVo(returnObject, WeightFreightRetVo.class));

        return Common.decorateReturnObject(returnObject);
    }



    @ApiOperation(value = "店家或管理员查询某个重量运费模板的明细",  produces="application/json;charset=UTF-8")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value ="用户token" ,required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Integer", name = "shopId", value ="商铺id" ,required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Integer", name = "id", value ="运费模板id" ,required = true),
            @ApiImplicitParam(name = "page", value = "页数", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "页大小", required = false, dataType = "Integer", paramType = "query")
    })
    @ApiResponses({
            @ApiResponse(code=0,message = "成功"),
            @ApiResponse(code=500,message = "服务器内部错误"),
            @ApiResponse(code=504,message = "操作的资源id不存在")
    })
    @GetMapping("/shops/{shopId}/freightmodels/{id}/weightItems")
    public Object getWeightItems(@PathVariable("shopId") Integer shopId, @PathVariable("id") Long id, @RequestParam(name = "page", required = false, defaultValue = "1") Integer page, @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize) {

        ReturnObject returnObject = weightFreightService.getWeightItems(id, page, pageSize);

        returnObject = Common.getPageRetVo(returnObject,WeightFreightRetVo.class);

        return Common.decorateReturnObject(returnObject);
    }



    @ApiOperation(value = "店家或管理员修改重量运费模板明细",  produces="application/json;charset=UTF-8")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value ="用户token" ,required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Integer", name = "shopId", value ="商铺id" ,required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Integer", name = "id", value ="运费模板id" ,required = true),
            @ApiImplicitParam(paramType = "body", dataType = "Object", name = "body", value ="运费模板资料" ,required = true)
    })
    @ApiResponses({
            @ApiResponse(code=0,message = "成功"),
            @ApiResponse(code=500,message = "服务器内部错误"),
            @ApiResponse(code=504,message = "操作的资源id不存在"),
            @ApiResponse(code=999,message = "运费模板中该地区已经定义")
    })
    @PutMapping("/shops/{shopId}/weightItems/{id}")
    public Object updateWeightItems(@Validated @RequestBody WeightFreightVo weightFreightVo, @PathVariable("shopId") Integer shopId, @PathVariable("id") Long id, Long userId, String userName) {

        if(shopId!=0){
            return new ResponseEntity(ResponseUtil.fail(ReturnNo.RESOURCE_ID_OUTSCOPE, "非管理员无权操作"), HttpStatus.FORBIDDEN);
        }
        userId=Long.valueOf(1);
        userName="admin";

        ReturnObject returnObject = weightFreightService.updateWeightItems(weightFreightVo, id, userId, userName);

        return Common.decorateReturnObject(returnObject);
    }



    @ApiOperation(value = "店家或管理员删掉重量运费模板明细",  produces="application/json;charset=UTF-8")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value ="用户token" ,required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Integer", name = "shopId", value ="商铺id" ,required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Integer", name = "id", value ="运费模板id" ,required = true)
    })
    @ApiResponses({
            @ApiResponse(code=0,message = "成功"),
            @ApiResponse(code=500,message = "服务器内部错误"),
            @ApiResponse(code=504,message = "操作的资源id不存在"),
    })
    @DeleteMapping("/shops/{shopId}/weightItems/{id}")
    public Object deleteWeightItems(@PathVariable("shopId") Integer shopId, @PathVariable("id") Long id) {

        if(shopId!=0){
            return new ResponseEntity(ResponseUtil.fail(ReturnNo.RESOURCE_ID_OUTSCOPE, "非管理员无权操作"), HttpStatus.FORBIDDEN);
        }

        ReturnObject returnObject = weightFreightService.deleteWeightItems(id);

        return Common.decorateReturnObject(returnObject);
    }

}
