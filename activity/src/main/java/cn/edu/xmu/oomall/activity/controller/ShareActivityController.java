package cn.edu.xmu.oomall.activity.controller;

import cn.edu.xmu.oomall.activity.model.bo.ShareActivityStatesBo;
import cn.edu.xmu.oomall.activity.model.vo.ShareActivityVo;
import cn.edu.xmu.oomall.activity.service.ShareActivityService;
import cn.edu.xmu.oomall.core.util.*;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

/**
 * @author xiuchen lang 22920192204222
 * @date 2021/11/12 12:52
 */
@Api(value = "分享活动API", tags = "activity")
@RestController
@RequestMapping(value = "/", produces = "application/json;charset=UTF-8")
public class ShareActivityController {

    @Autowired
    private ShareActivityService shareActivityService;

    @Autowired
    private HttpServletResponse httpServletResponse;


    /**
     * 获得分享活动的所有状态
     *
     * @return
     */
    @ApiOperation(value = "获得分享活动的所有状态")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "成功")})
    @GetMapping("/shareactivities/states")
    public Object getShareState() {
        ReturnObject returnObject = shareActivityService.getShareState();
        return Common.decorateReturnObject(returnObject);
    }

    /**
     * 显示所有状态的分享活动
     *
     * @param shopId    店铺Id
     * @param productId 货品id
     * @param beginTime 晚于此开始时间
     * @param endTime   早于此结束时间
     * @param state     分享活动状态
     * @param page      页码
     * @param pageSize  每页数目
     * @return
     */
    @ApiOperation(value = "显示所有状态的分享活动")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "authorization", value = "token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "shopId", value = "商店id", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "productId", value = "货品id", required = false, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "beginTime", value = "开始时间", required = false, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "state", value = "状态", required = false, dataType = "Byte", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "页大小", required = false, dataType = "Integer", paramType = "query")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "成功")})
    @GetMapping("/shops/{shopId}/shareactivities")
    public Object getShareByShopId(@PathVariable(name = "shopId", required = true) Long shopId,
                                   @RequestParam(name = "productId", required = false) Long productId,
                                   @RequestParam(name = "beginTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime beginTime,
                                   @RequestParam(name = "endTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime,
                                   @RequestParam(name = "state", required = false) Byte state,
                                   @RequestParam(name = "page", required = false) Integer page,
                                   @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        if (shopId <= 0) {
            return Common.decorateReturnObject(new ReturnObject(ReturnNo.FIELD_NOTVALID, "shopId错误"));
        }
        if (productId != null && productId <= 0) {
            return Common.decorateReturnObject(new ReturnObject(ReturnNo.FIELD_NOTVALID, "productId错误"));
        }
        page = (page == null) ? 1 : page;
        pageSize = (pageSize == null) ? 10 : pageSize;
        if (page <= 0 || pageSize <= 0) {
            return Common.decorateReturnObject(new ReturnObject(ReturnNo.FIELD_NOTVALID, "页数和页数大小应大于0"));
        }
        ReturnObject shareByShopId = shareActivityService.getShareByShopId(shopId, productId,
                beginTime, endTime, state, page, pageSize);
        return Common.decorateReturnObject(Common.getPageRetObject(shareByShopId));
    }

    /**
     * 管理员新增分享活动
     *
     * @param shopId          店铺id
     * @param shareActivityVo 可修改的信息
     * @param bindingResult   合法性检验结果
     * @return
     */
    @ApiOperation(value = "管理员新增分享活动")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "authorization", value = "token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "shopId", value = "商店id", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "shareActivityVo", paramType = "body", dataType = "ShareActivityVo", value = "修改内容", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "成功")})
    @PostMapping("/shops/{shopId}/shareactivities")
    public Object addShareAct(String createName, Long createId, @PathVariable(value = "shopId", required = true) Long shopId,
                              @Validated @RequestBody ShareActivityVo shareActivityVo,
                              BindingResult bindingResult) {
        createName = "lxc";
        createId = 666L;
        Object obj = Common.processFieldErrors(bindingResult, httpServletResponse);
        if (null != obj) {
            return obj;
        }
        if (shareActivityVo.getBeginTime().isAfter(shareActivityVo.getEndTime())) {
            return Common.decorateReturnObject(new ReturnObject<>(ReturnNo.FIELD_NOTVALID, "开始时间不得早于结束时间"));
        }
        ReturnObject returnObject = shareActivityService.addShareAct(createName, createId, shopId, shareActivityVo);
        return Common.decorateReturnObject(returnObject);
    }

    /**
     * 查询分享活动 只显示上线状态的分享活动
     *
     * @param shopId    店铺Id
     * @param productId 货品id
     * @param beginTime 晚于此开始时间
     * @param endTime   早于此结束时间
     * @param page      页码
     * @param pageSize  每页数目
     * @return
     */
    @ApiOperation(value = "查询分享活动 只显示上线状态的分享活动")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "authorization", value = "token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "shopId", value = "商店id", required = true, dataType = "Long", paramType = "path"),
            @ApiImplicitParam(name = "productId", value = "货品id", required = false, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "beginTime", value = "开始时间", required = false, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "page", value = "页数", required = false, dataType = "Integer", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "页大小", required = false, dataType = "Integer", paramType = "query")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "成功"),
            @ApiResponse(code = 503, message = "shopId错误"),
            @ApiResponse(code = 503, message = "页数和页数大小应大于0")
    })
    @GetMapping("/shareactivities")
    public Object getShareActivity(@RequestParam(name = "shopId", required = false) Long shopId,
                                   @RequestParam(name = "productId", required = false) Long productId,
                                   @RequestParam(name = "beginTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime beginTime,
                                   @RequestParam(name = "endTime", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime,
                                   @RequestParam(name = "page", required = false) Integer page,
                                   @RequestParam(name = "pageSize", required = false) Integer pageSize) {
        if (shopId != null && shopId <= 0) {
            return Common.decorateReturnObject(new ReturnObject(ReturnNo.FIELD_NOTVALID, "shopId错误"));
        }
        if (productId != null && productId <= 0) {
            return Common.decorateReturnObject(new ReturnObject(ReturnNo.FIELD_NOTVALID, "productId错误"));
        }
        page = (page == null) ? 1 : page;
        pageSize = (pageSize == null) ? 10 : pageSize;
        if (page <= 0 || pageSize <= 0) {
            return Common.decorateReturnObject(new ReturnObject(ReturnNo.FIELD_NOTVALID, "页数和页数大小应大于0"));
        }
        ReturnObject shareByShopId = shareActivityService.getShareByShopId(shopId, productId,
                beginTime, endTime, ShareActivityStatesBo.ONLINE.getCode(), page, pageSize);
        return Common.decorateReturnObject(Common.getPageRetObject(shareByShopId));
    }

    /**
     * 查看分享活动详情 只显示上线状态的分享活动
     *
     * @param id 分享活动Id
     * @return
     */
    @ApiOperation(value = "查询分享活动 只显示上线状态的分享活动")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "authorization", value = "token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "id", value = "分享活动Id", required = true, dataType = "Long", paramType = "query")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "成功")})
    @GetMapping("/shareactivities/{id}")
    public Object getShareActivityById(@PathVariable(value = "id", required = true) Long id) {
        return Common.decorateReturnObject(shareActivityService.getShareActivityById(id));
    }

    /**
     * 查看特定分享活动详情,显示所有状态的分享活动
     *
     * @param shopId 店铺Id
     * @param id     分享活动Id
     * @return
     */
    @ApiOperation(value = "查看特定分享活动详情,显示所有状态的分享活动")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "authorization", value = "token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "shopId", value = "商铺Id", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "id", value = "分享活动Id", required = true, dataType = "Long", paramType = "query")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "成功")})
    @GetMapping("/shops/{shopId}/shareactivities/{id}")
    public Object getShareActivityByShopIdAndId(@PathVariable("shopId") Long shopId, @PathVariable("id") Long id) {
        return Common.decorateReturnObject(shareActivityService.getShareActivityByShopIdAndId(shopId, id));
    }

}
