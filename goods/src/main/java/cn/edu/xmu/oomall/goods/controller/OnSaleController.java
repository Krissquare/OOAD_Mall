package cn.edu.xmu.oomall.goods.controller;


import cn.edu.xmu.oomall.core.model.VoObject;
import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.goods.model.bo.OnSale;
import cn.edu.xmu.oomall.goods.model.vo.NewOnSaleVo;
import cn.edu.xmu.oomall.goods.service.OnsaleService;
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

@RestController
/**Restful的Controller对象*/
@RequestMapping(produces = "application/json;charset=UTF-8")
public class OnSaleController {

    private final Logger logger = LoggerFactory.getLogger(OnSaleController.class);

    @Autowired
    private OnsaleService onsaleService;

    @Autowired
    private HttpServletResponse httpServletResponse;


    @PostMapping("shops/{shopId}/products/{id}/onsales")
    public Object createNewOnSaleNormalSeckill(@PathVariable Long shopId, @PathVariable Long id, @Validated @RequestBody NewOnSaleVo newOnSaleVo,
                                               Long loginUserId, String loginUserName, BindingResult bindingResult) {

        loginUserId = 1L;
        loginUserName = "御姐";

        Object returnObject = processFieldErrors(bindingResult, httpServletResponse);
        if (null != returnObject) {
            return returnObject;
        }

        // 判断是否秒杀或普通
        if (!newOnSaleVo.getType().equals(OnSale.Type.NOACTIVITY.getCode())
                && !newOnSaleVo.getType().equals(OnSale.Type.SECKILL.getCode())) {
            ReturnObject<Object> returnObject2 = new ReturnObject<>(ReturnNo.RESOURCE_ID_OUTSCOPE, "限定处理普通或秒杀。");
            return decorateReturnObject(returnObject2);
        }

        // 判断开始时间是否比结束时间晚
        if (newOnSaleVo.getBeginTime().compareTo(newOnSaleVo.getEndTime()) >= 0) {
            return decorateReturnObject(new ReturnObject<>(ReturnNo.ACT_LATE_BEGINTIME, "开始时间晚于结束时间，新增销售失败。"));
        }

        ReturnObject returnObject1 = onsaleService.createOnSale(shopId, id, newOnSaleVo, loginUserId, loginUserName);
        if (returnObject1.getCode() != ReturnNo.OK) {
            return decorateReturnObject(returnObject1);
        }

        httpServletResponse.setStatus(HttpStatus.CREATED.value());
        return getRetObject(returnObject1);

    }


    @PutMapping("shops/{shopId}/onsales/{id}/online")
    public Object onlineOnSaleNormalSeckill(@PathVariable Long shopId, @PathVariable Long id, Long loginUserId, String loginUserName) {
        loginUserId = 1L;
        loginUserName = "yujie";

        ReturnObject returnObject1 = onsaleService.onlineOrOfflineOnSale(shopId, id, loginUserId, loginUserName, OnSale.State.ONLINE);
        return decorateReturnObject(returnObject1);
    }

    @PutMapping("shops/{shopId}/onsales/{id}/offline")
    public Object offlineOnSaleNormalSeckill(@PathVariable Long shopId, @PathVariable Long id, Long loginUserId, String loginUserName) {
        loginUserId = 1L;
        loginUserName = "yujie";

        ReturnObject returnObject1 = onsaleService.onlineOrOfflineOnSale(shopId, id, loginUserId, loginUserName, OnSale.State.OFFLINE);
        return decorateReturnObject(returnObject1);
    }

    @GetMapping("shops/{shopId}/products/{id}/onsales")
    public Object searchOnSaleByProductIdNormalSeckill(@PathVariable Long shopId, @PathVariable Long id,
                                                       @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "1") Integer page, Long loginUserId, String loginUserName) {
        loginUserId = 1L;
        loginUserName = "yujie";

        ReturnObject returnObject1 = onsaleService.searchOnSaleByProductNorSec(id, page, pageSize);
        return decorateReturnObject(returnObject1);

    }

    @GetMapping("shops/{shopId}/onsales/{id}")
    public Object getOnSaleDetailNorSec(@PathVariable Long shopId, @PathVariable Long id, Long loginUserId, String loginUserName) {
        loginUserId = 1L;
        loginUserName = "yujie";

        ReturnObject returnObject1 = onsaleService.getDetail(id, true);
        return decorateReturnObject(returnObject1);
    }

    @PutMapping("internal/onsales/{id}/online")
    public Object onlineOnSaleGroupPre(@PathVariable Long id, Long loginUserId, String loginUserName) {
        loginUserId = 1L;
        loginUserName = "yujie";

        ReturnObject returnObject1 = onsaleService.onlineOrOfflineOnSaleGroupPre(id, loginUserId, loginUserName, OnSale.State.ONLINE);
        return decorateReturnObject(returnObject1);
    }

    @PutMapping("internal/onsales/{id}/offline")
    public Object offlineOnSaleGroupPre(@PathVariable Long id, Long loginUserId, String loginUserName) {
        loginUserId = 1L;
        loginUserName = "yujie";

        ReturnObject returnObject1 = onsaleService.onlineOrOfflineOnSaleGroupPre(id, loginUserId, loginUserName, OnSale.State.OFFLINE);
        return decorateReturnObject(returnObject1);
    }


    @GetMapping("internal/grouponactivities/{id}/onsales")
    public Object searchOnSaleGro(@PathVariable Long id,
                                  @RequestParam(required = false) Integer state,
                                  @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "1") Integer page, Long loginUserId, String loginUserName) {
        loginUserId = 1L;
        loginUserName = "yujie";

        ReturnObject returnObject1 = onsaleService.searchOnSaleByActivity(id, page, pageSize, state, OnSale.Type.GROUPON);
        return decorateReturnObject(returnObject1);
    }

    @GetMapping("internal/advacnesaleactivities/{id}/onsales")
    public Object searchOnSalePre(@PathVariable Long id,
                                  @RequestParam(required = false) Integer state,
                                  @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "1") Integer page, Long loginUserId, String loginUserName) {
        loginUserId = 1L;
        loginUserName = "yujie";

        ReturnObject returnObject1 = onsaleService.searchOnSaleByActivity(id, page, pageSize, state, OnSale.Type.PRESALE);
        return decorateReturnObject(returnObject1);
    }


    @GetMapping("internal/shareactivities/{id}/onsales")
    public Object searchOnSaleShare(@PathVariable Long id,
                                    @RequestParam(required = false) Integer state,
                                    @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "1") Integer page, Long loginUserId, String loginUserName) {
        loginUserId = 1L;
        loginUserName = "yujie";

        ReturnObject returnObject1 = onsaleService.searchOnSaleByShare(id, page, pageSize, state);
        return decorateReturnObject(returnObject1);
    }


    @GetMapping("internal/onsales/{id}")
    public Object getOnSaleDetail(@PathVariable Long id, Long loginUserId, String loginUserName) {
        loginUserId = 1L;
        loginUserName = "yujie";

        ReturnObject returnObject1 = onsaleService.getDetail(id, false);
        return decorateReturnObject(returnObject1);
    }


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
        if (newOnSaleVo.getBeginTime().compareTo(newOnSaleVo.getEndTime()) >= 0) {
            return new ReturnObject(ReturnNo.ACT_LATE_BEGINTIME, "开始时间晚于结束时间，新增销售失败。");
        }

        ReturnObject<VoObject> returnObject1 = onsaleService.createOnSaleWithoutShopId(id, newOnSaleVo, loginUserId, loginUserName);
        if (returnObject1.getCode() != ReturnNo.OK) {
            ReturnObject<Object> returnObject2 = new ReturnObject<>(returnObject1.getCode(), returnObject1.getErrmsg());
            return getNullRetObj(returnObject2, httpServletResponse);
        }
        httpServletResponse.setStatus(HttpStatus.CREATED.value());
        return getRetObject(returnObject1);
    }

    @GetMapping("internal/products/{id}/onsales")
    public Object searchOnSaleAll(@PathVariable Long id,
                                  @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "1") Integer page, Long loginUserId, String loginUserName) {
        loginUserId = 1L;
        loginUserName = "yujie";

        ReturnObject<VoObject> returnObject1 = onsaleService.searchOnSaleByProduct(id, page, pageSize);
        return decorateReturnObject(returnObject1);
    }

}
