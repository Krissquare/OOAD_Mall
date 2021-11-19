package cn.edu.xmu.oomall.activity.controller;

import cn.edu.xmu.oomall.activity.constant.Constants;
import cn.edu.xmu.oomall.activity.constant.GroupOnState;
import cn.edu.xmu.oomall.activity.model.vo.FullGroupOnActivityVo;
import cn.edu.xmu.oomall.activity.model.vo.GroupOnActivityPostVo;
import cn.edu.xmu.oomall.activity.model.vo.GroupOnActivityVo;
import cn.edu.xmu.oomall.activity.service.GroupOnService;
import cn.edu.xmu.oomall.core.util.Common;
import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.LocalDateTime;

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
        return Common.decorateReturnObject(groupOnService.getGroupOnStates());
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
                                             @RequestParam(required = false) @DateTimeFormat(pattern = Constants.DATE_TIME_FORMAT) LocalDateTime beginTime,
                                             @RequestParam(required = false) @DateTimeFormat(pattern = Constants.DATE_TIME_FORMAT) LocalDateTime endTime,
                                             @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize) {
        return Common.decorateReturnObject(groupOnService.getGroupOnActivities(productId, shopId, beginTime, endTime, GroupOnState.ONLINE, page, pageSize));
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
        var ret = groupOnService.getGroupOnActivity(id, GroupOnState.ONLINE, null);
        if (ret.getCode().equals(ReturnNo.OK)) {
            ret = new ReturnObject(Common.cloneVo(ret.getData(), GroupOnActivityVo.class));
        }
        return Common.decorateReturnObject(ret);
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
                                             @RequestParam(required = false) LocalDateTime beginTime, @RequestParam(required = false) LocalDateTime endTime,
                                             @RequestParam(required = false) GroupOnState state, @RequestParam(defaultValue = "1") Integer page,
                                             @RequestParam(defaultValue = "10") Integer pageSize) {
        return Common.decorateReturnObject(groupOnService.getGroupOnActivities(productId, shopId, beginTime, endTime, state, page, pageSize));
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
    public Object addGroupOnActivity(@PathVariable("shopId") Long shopId, @Valid @RequestBody GroupOnActivityPostVo body,
                                     BindingResult bindingResult, Long loginUserId, String loginUserName) {
        loginUserId = 1L;
        loginUserName = "admin";
        var fieldErrors = Common.processFieldErrors(bindingResult, httpServletResponse);
        if (fieldErrors != null) {
            return fieldErrors;
        }
        ReturnObject ret;
        if (!body.getBeginTime().isBefore(body.getEndTime())) {
            ret = new ReturnObject(ReturnNo.LATE_BEGINTIME);
        } else {
            ret = groupOnService.addActivity(shopId, body, loginUserId, loginUserName);
        }
        return Common.decorateReturnObject(ret);
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
        var ret = groupOnService.getGroupOnActivity(id, null, shopId);
        if (ret.getCode().equals(ReturnNo.OK)) {
            ret = new ReturnObject(Common.cloneVo(ret.getData(), FullGroupOnActivityVo.class));
        }
        return Common.decorateReturnObject(ret);
    }

}
