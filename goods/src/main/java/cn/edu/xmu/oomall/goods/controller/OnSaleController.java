package cn.edu.xmu.oomall.goods.controller;


import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.goods.model.bo.OnSale;
import cn.edu.xmu.oomall.goods.model.vo.ModifyOnSaleVo;
import cn.edu.xmu.oomall.goods.model.vo.NewOnSaleRetVo;
import cn.edu.xmu.oomall.goods.model.vo.NewOnSaleVo;
import cn.edu.xmu.oomall.goods.service.OnsaleService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import static cn.edu.xmu.oomall.core.util.Common.*;

/**
 * @author yujie lin 22920192204242
 * @date 2021/11/10
 */
@Api(value = "货品销售情况", tags = "goods")
@RestController
@RequestMapping(value = "/", produces = "application/json;charset=UTF-8")
public class OnSaleController {

    private final Logger logger = LoggerFactory.getLogger(OnSaleController.class);

    @Autowired
    private OnsaleService onsaleService;

    @Autowired
    private HttpServletResponse httpServletResponse;

    @ApiOperation(value = "管理员新增商品价格和数量（普通和秒杀）")
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 902, message = "商品销售时间冲突"),
            @ApiResponse(code = 947, message = "开始时间不能晚于结束时间"),
            @ApiResponse(code = 505, message = "限定秒杀或普通"),
    })
    @PostMapping("shops/{shopId}/products/{id}/onsales")
    public Object createNewOnSaleNormalSeckill(@PathVariable Long shopId, @PathVariable Long id, @Validated @RequestBody NewOnSaleVo newOnSaleVo,
                                               Long loginUserId, String loginUserName, BindingResult bindingResult) {

        Object returnObject = processFieldErrors(bindingResult, httpServletResponse);
        if (null != returnObject) {
            return returnObject;
        }

        loginUserId = 1L;
        loginUserName = "yujie";


        // 判断是否秒杀或普通
        if (!newOnSaleVo.getType().equals(OnSale.Type.NOACTIVITY.getCode())
                && !newOnSaleVo.getType().equals(OnSale.Type.SECKILL.getCode())) {
            ReturnObject<Object> returnObject2 = new ReturnObject<>(ReturnNo.RESOURCE_ID_OUTSCOPE, "限定处理普通或秒杀。");
            return decorateReturnObject(returnObject2);
        }

        // 判断开始时间是否比结束时间晚
        if (newOnSaleVo.getBeginTime().isAfter(newOnSaleVo.getEndTime())) {
            return decorateReturnObject(new ReturnObject<>(ReturnNo.LATE_BEGINTIME, "开始时间晚于结束时间。"));
        }

        ReturnObject returnObject1 = onsaleService.createOnSale(shopId, id, newOnSaleVo, loginUserId, loginUserName);

        if (returnObject1.getCode() != ReturnNo.OK) {
            return decorateReturnObject(returnObject1);
        }

        httpServletResponse.setStatus(HttpStatus.CREATED.value());

        return decorateReturnObject(getRetVo(returnObject1,NewOnSaleRetVo.class));


    }

    @ApiOperation(value = "管理员上线商品价格浮动，限定普通秒杀")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization ", value = "token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "shopId", value = "店铺id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "id", value = "浮动价格id", required = true, dataType = "Integer", paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 505, message = "限定只能处理普通和秒杀"),
            @ApiResponse(code = 507, message = "只有草稿态才能上线"),
    })
    @PutMapping("shops/{shopId}/onsales/{id}/online")
    public Object onlineOnSaleNormalSeckill(@PathVariable Long shopId, @PathVariable Long id, Long loginUserId, String loginUserName) {
        loginUserId = 1L;
        loginUserName = "yujie";

        ReturnObject returnObject1 = onsaleService.onlineOrOfflineOnSale(shopId, id, loginUserId, loginUserName, OnSale.State.ONLINE);
        return decorateReturnObject(returnObject1);
    }

    @ApiOperation(value = "管理员下线商品价格浮动，限定普通秒杀")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization ", value = "token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "shopId", value = "店铺id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "id", value = "浮动价格id", required = true, dataType = "Integer", paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 505, message = "限定只能处理普通和秒杀"),
            @ApiResponse(code = 507, message = "只有上线态才能下线"),
    })
    @PutMapping("shops/{shopId}/onsales/{id}/offline")
    public Object offlineOnSaleNormalSeckill(@PathVariable Long shopId, @PathVariable Long id, Long loginUserId, String loginUserName) {
        loginUserId = 1L;
        loginUserName = "yujie";

        ReturnObject returnObject1 = onsaleService.onlineOrOfflineOnSale(shopId, id, loginUserId, loginUserName, OnSale.State.OFFLINE);
        return decorateReturnObject(returnObject1);
    }


    @ApiOperation(value = "管理员上线团购和预售活动的商品价格浮动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization ", value = "token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "id", value = "活动id", required = true, dataType = "Integer", paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 505, message = "限定只能处理团购和预售"),
    })
    @PutMapping("internal/activities/{id}/onsales/online")
    public Object onlineOnSaleGroupPre(@PathVariable Long id, Long loginUserId, String loginUserName) {
        loginUserId = 1L;
        loginUserName = "yujie";

        ReturnObject returnObject1 = onsaleService.onlineOrOfflineOnSaleGroupPre(id, loginUserId, loginUserName, OnSale.State.DRAFT, OnSale.State.ONLINE);
        return decorateReturnObject(returnObject1);
    }

    @ApiOperation(value = "管理员下线团购和预售活动的商品价格浮动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization ", value = "token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "id", value = "活动id", required = true, dataType = "Integer", paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 505, message = "限定只能处理团购和预售"),
    })
    @PutMapping("internal/activities/{id}/onsales/offline")
    public Object offlineOnSaleGroupPre(@PathVariable Long id, Long loginUserId, String loginUserName) {
        loginUserId = 1L;
        loginUserName = "yujie";

        ReturnObject returnObject1 = onsaleService.onlineOrOfflineOnSaleGroupPre(id, loginUserId, loginUserName, OnSale.State.ONLINE, OnSale.State.OFFLINE);
        return decorateReturnObject(returnObject1);
    }

    @ApiOperation(value = "管理员新增商品价格和数量")
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 902, message = "商品销售时间冲突"),
            @ApiResponse(code = 947, message = "开始时间不能晚于结束时间"),
    })
    @PostMapping("internal/products/{id}/onsales")
    public Object createNewOnSale(@PathVariable Long id, @Validated @RequestBody NewOnSaleVo newOnSaleVo,
                                  Long loginUserId, String loginUserName, BindingResult bindingResult) {
        loginUserId = 1L;
        loginUserName = "yujie";

        Object returnObject = processFieldErrors(bindingResult, httpServletResponse);
        if (null != returnObject) {
            return returnObject;
        }


        //        判断开始时间是否比结束时间晚
        if (newOnSaleVo.getBeginTime().isAfter(newOnSaleVo.getEndTime())) {
            return decorateReturnObject(new ReturnObject<>(ReturnNo.LATE_BEGINTIME, "开始时间晚于结束时间。"));
        }

        ReturnObject returnObject1 = onsaleService.createOnSaleWithoutShopId(id, newOnSaleVo, loginUserId, loginUserName);
        if (returnObject1.getCode() != ReturnNo.OK) {
            return decorateReturnObject(returnObject1);
        }
        httpServletResponse.setStatus(HttpStatus.CREATED.value());

        return decorateReturnObject(getRetVo(returnObject1,NewOnSaleRetVo.class));
    }


    @ApiOperation(value = "物理删除草稿态的普通和秒杀")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization ", value = "token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "shopId", value = "商铺id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "id", value = "销售id", required = true, dataType = "Integer", paramType = "path"),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 505, message = "限定只能处理普通和秒杀"),
            @ApiResponse(code = 507, message = "只能删除草稿态"),
    })
    @DeleteMapping("shops/{shopId}/onsales/{id}")
    public Object deleteOnSaleNorSec(@PathVariable Long shopId, @PathVariable Long id, Long loginUserId, String loginUserName) {
        loginUserId = 1L;
        loginUserName = "yujie";

        ReturnObject returnObject1 = onsaleService.deleteOnSaleNorSec(shopId, id);
        return decorateReturnObject(returnObject1);
    }

    @ApiOperation(value = "物理删除草稿态的团购和预售")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization ", value = "token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "id", value = "销售id", required = true, dataType = "Integer", paramType = "path"),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 505, message = "限定只能处理团购和预售"),
            @ApiResponse(code = 507, message = "只能删除草稿态"),
    })
    @DeleteMapping("internal/activities/{id}/onsales")
    public Object deleteOnSaleGroPre(@PathVariable Long id, Long loginUserId, String loginUserName) {
        loginUserId = 1L;
        loginUserName = "yujie";

        ReturnObject returnObject1 = onsaleService.deleteOnSaleGroPre(id);
        return decorateReturnObject(returnObject1);
    }


    @ApiOperation(value = "修改任意类型商品价格和数量")
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 507, message = "只有草稿态和下线态才能修改"),
    })
    @PutMapping("internal/onsales/{id}")
    public Object modifyOnSale(@PathVariable Long id, @RequestBody ModifyOnSaleVo onSale, Long loginUserId, String loginUserName) {
        loginUserId = 1L;
        loginUserName = "yujie";


        // 判断开始时间是否比结束时间晚
        if (onSale.getBeginTime().isAfter(onSale.getEndTime())) {
            return decorateReturnObject(new ReturnObject<>(ReturnNo.LATE_BEGINTIME, "开始时间晚于结束时间。"));
        }

        OnSale bo = (OnSale)cloneVo(onSale,OnSale.class);
        bo.setId(id);
        ReturnObject returnObject1 = onsaleService.updateOnSale(bo, loginUserId, loginUserName);
        return decorateReturnObject(returnObject1);
    }


    @ApiOperation(value = "修改普通和秒杀价格和数量")
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 507, message = "只有草稿态和下线态才能修改"),
    })
    @PutMapping("shops/{shopId}/onsales/{id}")
    public Object modifyOnSaleNorSec(@PathVariable Long shopId, @PathVariable Long id, @RequestBody ModifyOnSaleVo onSale, Long loginUserId, String loginUserName) {
        loginUserId = 1L;
        loginUserName = "yujie";

        // 判断开始时间是否比结束时间晚
        if (onSale.getBeginTime().isAfter(onSale.getEndTime())) {
            return decorateReturnObject(new ReturnObject<>(ReturnNo.LATE_BEGINTIME, "开始时间晚于结束时间。"));
        }

        OnSale bo = (OnSale)cloneVo(onSale,OnSale.class);
        bo.setId(id);

        ReturnObject returnObject1 = onsaleService.updateOnSaleNorSec(bo, shopId, loginUserId, loginUserName);
        return decorateReturnObject(returnObject1);
    }


}
