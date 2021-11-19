package cn.edu.xmu.oomall.activity.controller;

import cn.edu.xmu.oomall.activity.microsservice.GoodsService;
import cn.edu.xmu.oomall.activity.model.vo.AdvanceSaleModifyVo;
import cn.edu.xmu.oomall.activity.service.AdvanceSaleService;
import cn.edu.xmu.oomall.core.util.Common;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Gxc 22920192204194
 */
@RestController
@Slf4j
public class AdvanceSaleController {
    @Autowired
    AdvanceSaleService advanceSaleService;

    @Autowired
    GoodsService goodsService;
    @Autowired
    private HttpServletResponse httpServletResponse;
    /**
     * @author Gxc 22920192204194
     */
    @ApiOperation(value = "商铺管理员上线预售活动")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "token", value = "用户的token", required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Long", name = "shopId", value = "商铺id", required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Long", name = "id", value = "预售活动id", required = true)
    })
    @PutMapping("/shops/{shopId}/advancesales/{id}/online")
    public Object onlineAdvancesale(Long adminId,String adminName, @PathVariable("shopId")Long shopId, @PathVariable("id")Long advancesaleId) throws MicrosServiceConnectException {
        adminId=1L;adminName="店铺管理员";
        ReturnObject returnObject = null;
        returnObject= advanceSaleService.onlineAdvancesale(adminId,adminName,shopId,advancesaleId);
        return Common.decorateReturnObject(returnObject);
    }

    @ApiOperation(value = "商铺管理员下线预售活动")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "token", value = "用户的token", required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Long", name = "shopId", value = "商铺id", required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Long", name = "id", value = "预售活动id", required = true)
    })
    @PutMapping("/shops/{shopId}/advancesales/{id}/offline")
    public Object offlineAdvancesale(Long adminId,String adminName, @PathVariable("shopId")Long shopId, @PathVariable("id")Long advancesaleId) throws MicrosServiceConnectException {
        adminId=1L;adminName="店铺管理员";
        ReturnObject returnObject = null;
        returnObject= advanceSaleService.offlineAdvancesale(adminId,adminName,shopId,advancesaleId);
        return Common.decorateReturnObject(returnObject);
    }

    @ApiOperation(value = "商铺管理员修改预售活动")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "token", value = "用户的token", required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Long", name = "shopId", value = "商铺id", required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Long", name = "id", value = "预售活动id", required = true),
            @ApiImplicitParam(paramType = "body", dataType = "AdvanceSaleModifyVo", name = "advancesalemodifyvo", value = "修改内容", required = true)
    })
    @PutMapping("/shops/{shopId}/advancesales/{id}")
    public Object modifyAdvancesale(Long adminId, String adminName, @PathVariable("shopId")Long shopId, @PathVariable("id")Long advancesaleId,
                                    @RequestBody AdvanceSaleModifyVo advanceSaleModifyVo){
        adminId=1L;adminName="店铺管理员";
        ReturnObject returnObject=null;
        returnObject= advanceSaleService.modifyAdvancesale(adminId,shopId,adminName,advancesaleId,advanceSaleModifyVo);
        return Common.decorateReturnObject(returnObject);
    }

    @ApiOperation(value = "商铺管理员删除预售活动")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "header", dataType = "String", name = "token", value = "用户的token", required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Long", name = "shopId", value = "商铺id", required = true),
            @ApiImplicitParam(paramType = "path", dataType = "Long", name = "id", value = "预售活动id", required = true)
    })
    @DeleteMapping("/shops/{shopId}/advancesales/{id}")
    public Object deleteAdvancesale(Long adminId,String adminName, @PathVariable("shopId")Long shopId, @PathVariable("id")Long advancesaleId) throws MicrosServiceConnectException {
        adminId=1L;adminName="店铺管理员";
        ReturnObject returnObject = null;
        returnObject= advanceSaleService.deleteAdvancesale(adminId,shopId,advancesaleId);
        return Common.decorateReturnObject(returnObject);
    }
}
