package cn.edu.xmu.oomall.activity.controller;

import cn.edu.xmu.oomall.activity.enums.GroupOnState;
import cn.edu.xmu.oomall.activity.model.vo.GroupOnActivityPostVo;
import cn.edu.xmu.oomall.activity.model.vo.SimpleGroupOnActivityVo;
import cn.edu.xmu.oomall.activity.service.GroupOnService;
import cn.edu.xmu.oomall.core.util.*;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.format.DateTimeParseException;

/**
 * @author Gao Yanfeng
 * @date 2021/11/11
 */
@Api(value = "团购活动API")
@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class GroupOnActivityController {

    @Autowired
    private GroupOnService groupOnService;

    @Autowired
    private HttpServletResponse httpServletResponse;


    @ApiOperation(value = "获得团购活动的所有状态", produces = "application/json;charset=UTF-8")
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
    })
    @GetMapping(value = "/groupons/states")
    public Object getGroupOnStates() {
        return ResponseUtil.ok(groupOnService.getGroupOnStates());
    }


    @ApiOperation(value = "查询所有上线态团购活动", produces = "application/json;charset=UTF-8")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "用户Token",
                    required = true),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "productId", value = "货品ID"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "shopId", value = "商铺ID"),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "beginTime", value = "开始时间"),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "endTime", value = "结束时间"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "page", value = "页码"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "pageSize", value = "每页数目"),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
    })
    @GetMapping(value = "/groupons")
    public Object getOnlineGroupOnActivities(@RequestParam(required = false) Long productId, @RequestParam(required = false) Long shopId,
                                             @RequestParam(required = false) String beginTime, @RequestParam(required = false) String endTime,
                                             @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize) {
        try {
            return ResponseUtil.ok(groupOnService.getGroupOnActivities(productId, shopId, beginTime, endTime, GroupOnState.ONLINE, page, pageSize));
        } catch (DateTimeParseException e) {
            httpServletResponse.setStatus(400);
            return ResponseUtil.fail(ReturnNo.FIELD_NOTVALID, "日期格式应为yyyy-MM-dd HH:mm:ss");
        }
    }


    @ApiOperation(value = "查询上线态团购活动详情", produces = "application/json;charset=UTF-8")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "用户Token",
                    required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Integer", name = "id", value = "团购活动ID"),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
    })
    @GetMapping(value = "/groupons/{id}")
    public Object getOnlineGroupOnActivity(@PathVariable Long id) {
        var res = groupOnService.getGroupOnActivity(id);
        if (res == null) {
            httpServletResponse.setStatus(404);
            return ResponseUtil.fail(ReturnNo.RESOURCE_ID_NOTEXIST, "未找到指定ID对应的团购活动");
        } else if (res.getState() != GroupOnState.ONLINE) {
            httpServletResponse.setStatus(403);
            return ResponseUtil.fail(ReturnNo.OK, "没有权限访问未上线的团购活动");
        } else {
            return ResponseUtil.ok(res.createVo());
        }
    }


    @ApiOperation(value = "管理员查询商铺的所有状态团购活动", produces = "application/json;charset=UTF-8")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "用户Token",
                    required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Integer", name = "shopId", value = "商铺ID"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "productId", value = "货品ID"),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "beginTime", value = "开始时间"),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "endTime", value = "结束时间"),
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "state", value = "状态"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "page", value = "页码"),
            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "pageSize", value = "每页数目"),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
    })
    @GetMapping(value = "/shops/{shopId}/groupons")
    public Object getGroupOnActivitiesInShop(@PathVariable Long shopId, @RequestParam(required = false) Long productId,
                                             @RequestParam(required = false) String beginTime, @RequestParam(required = false) String endTime,
                                             @RequestParam(required = false) GroupOnState state, @RequestParam(defaultValue = "1") Integer page,
                                             @RequestParam(defaultValue = "10") Integer pageSize) {
        try {
            return ResponseUtil.ok(groupOnService.getGroupOnActivities(productId, shopId, beginTime, endTime, GroupOnState.ONLINE, page, pageSize));
        } catch (DateTimeParseException e) {
            httpServletResponse.setStatus(400);
            return ResponseUtil.fail(ReturnNo.FIELD_NOTVALID, "日期格式应为yyyy-MM-dd HH:mm:ss");
        }
    }


    @ApiOperation(value = "管理员新增团购活动", produces = "application/json;charset=UTF-8")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "用户Token", required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Integer", name = "shopId", value = "店铺ID", required = true),
            @ApiImplicitParam(paramType = "body", dataType = "GroupOnPostVo", name = "body", value = "可修改的信息", required = true),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 947, message = "开始时间不能晚于结束时间"),
    })
    @PostMapping(value = "/shops/{shopId}/groupons")
    public Object addGroupOnActivity(@PathVariable("shopId") Long shopId, @Valid @RequestBody GroupOnActivityPostVo body, BindingResult bindingResult) {
        var fieldErrors = Common.processFieldErrors(bindingResult, httpServletResponse);
        if (fieldErrors != null) {
            return fieldErrors;
        }
        try {
            var res = groupOnService.addActivity(shopId, body);
            if (res != null) {
                return ResponseUtil.ok(res.createSimpleVo());
            } else {
                return ResponseUtil.fail(ReturnNo.ACT_LATE_BEGINTIME, "开始时间不能晚于结束时间");
            }
        } catch (DateTimeParseException e) {
            httpServletResponse.setStatus(400);
            return ResponseUtil.fail(ReturnNo.FIELD_NOTVALID, "日期格式应为yyyy-MM-dd HH:mm:ss");
        }
    }

    @ApiOperation(value = "管理员查看特定团购活动详情", produces = "application/json;charset=UTF-8")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "authorization", value = "用户Token",
                    required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Integer", name = "shopId", value = "商铺ID"),
            @ApiImplicitParam(paramType = "path", dataType = "Integer", name = "id", value = "团购活动ID"),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
    })
    @GetMapping(value = "/shops/{shopId}/groupons/{id}")
    public Object getGroupOnActivityInShop(@PathVariable Long shopId, @PathVariable Long id) {
        var res = groupOnService.getGroupOnActivity(id);
        if (res == null) {
            httpServletResponse.setStatus(404);
            return ResponseUtil.fail(ReturnNo.RESOURCE_ID_NOTEXIST, "未找到指定ID对应的团购活动");
        } else if (!res.getShopId().equals(shopId)) {
            httpServletResponse.setStatus(404);
            return ResponseUtil.fail(ReturnNo.RESOURCE_ID_NOTEXIST, "指定店铺中不存在指定ID对应的团购活动");
        } else {
            return ResponseUtil.ok(res.createFullVo());
        }
    }
}
