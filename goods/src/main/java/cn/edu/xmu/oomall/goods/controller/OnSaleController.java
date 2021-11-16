package cn.edu.xmu.oomall.goods.controller;


import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.goods.service.OnsaleService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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



    @ApiOperation(value = "管理员查询特定商品价格浮动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization ", value = "token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "shopId", value = "店铺id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "productId", value = "货品id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "page", value = "页码", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "pageSize", value = "每页数目", required = true, dataType = "Integer", paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
    })
    @GetMapping("shops/{shopId}/products/{id}/onsales")
    public Object searchOnSaleByProductIdNormalSeckill(@PathVariable Long shopId, @PathVariable Long id,
                                                       @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "1") Integer page, Long loginUserId, String loginUserName) {
        loginUserId = 1L;
        loginUserName = "yujie";

        ReturnObject returnObject1 = onsaleService.searchOnSaleByProductNorSec(id, page, pageSize);
        return decorateReturnObject(returnObject1);

    }

    @ApiOperation(value = "管理员查询特定价格浮动的详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization ", value = "token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "shopId", value = "店铺id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "id", value = "浮动价格id", required = true, dataType = "Integer", paramType = "path"),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
            @ApiResponse(code = 505, message = "只限定普通和秒杀"),
    })
    @GetMapping("shops/{shopId}/onsales/{id}")
    public Object getOnSaleDetailNorSec(@PathVariable Long shopId, @PathVariable Long id, Long loginUserId, String loginUserName) {
        loginUserId = 1L;
        loginUserName = "yujie";

        ReturnObject returnObject1 = onsaleService.getDetailNorSec(id,shopId);
        return decorateReturnObject(returnObject1);

    }



    @ApiOperation(value = "管理员查询所有活动的商品价格浮动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization ", value = "token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "id", value = "活动id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "state", value = "商品浮动价格状态", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "page", value = "页码", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "pageSize", value = "每页数目", required = true, dataType = "Integer", paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
    })
    @GetMapping("internal/activities/{id}/onsales")
    public Object searchOnSaleAct(@PathVariable Long id,
                                  @RequestParam(required = false) Integer state,
                                  @RequestParam(required = false) String beginTime,
                                  @RequestParam(required = false) String endTime,
                                  @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "1") Integer page, Long loginUserId, String loginUserName) {
        loginUserId = 1L;
        loginUserName = "yujie";

        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime begin=null;
        LocalDateTime end=null;
        if(beginTime != null){
            try{
                begin= LocalDateTime.parse(beginTime,df);
            }
            catch(Exception e){
                return decorateReturnObject(new ReturnObject(ReturnNo.FIELD_NOTVALID,"时间格式错误"));
            }
        }
        if(endTime!=null){
            try{
                end= LocalDateTime.parse(endTime,df);
            }
            catch(Exception e){
                return decorateReturnObject(new ReturnObject(ReturnNo.FIELD_NOTVALID,"时间格式错误"));
            }
        }
        ReturnObject returnObject1 = onsaleService.searchOnSaleByActivity(id, begin,end,page, pageSize, state);
        return decorateReturnObject(returnObject1);
    }


    @ApiOperation(value = "管理员查询所有分享活动的商品价格浮动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization ", value = "token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "id", value = "活动id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "state", value = "商品浮动价格状态", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "page", value = "页码", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "pageSize", value = "每页数目", required = true, dataType = "Integer", paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
    })
    @GetMapping("internal/shareactivities/{id}/onsales")
    public Object searchOnSaleShare(@PathVariable Long id,
                                    @RequestParam(required = false) Integer state,
                                    @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "1") Integer page, Long loginUserId, String loginUserName) {
        loginUserId = 1L;
        loginUserName = "yujie";

        ReturnObject returnObject1 = onsaleService.searchOnSaleByShare(id, page, pageSize, state);
        return decorateReturnObject(returnObject1);
    }


    @ApiOperation(value = "管理员查询特定价格浮动的详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization ", value = "token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "id", value = "浮动价格id", required = true, dataType = "Integer", paramType = "path"),
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
    })
    @GetMapping("internal/onsales/{id}")
    public Object getOnSaleDetail(@PathVariable Long id, Long loginUserId, String loginUserName) {
        loginUserId = 1L;
        loginUserName = "yujie";
        ReturnObject returnObject1 = onsaleService.getDetail(id);
        return decorateReturnObject(returnObject1);
    }


    @ApiOperation(value = "管理员查询商品的所有价格浮动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "authorization ", value = "token", required = true, dataType = "String", paramType = "header"),
            @ApiImplicitParam(name = "id", value = "货品id", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "page", value = "页码", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "pageSize", value = "每页数目", required = true, dataType = "Integer", paramType = "path")
    })
    @ApiResponses({
            @ApiResponse(code = 0, message = "成功"),
    })
    @GetMapping("internal/products/{id}/onsales")
    public Object searchOnSaleAll(@PathVariable Long id,
                                  @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "1") Integer page, Long loginUserId, String loginUserName) {
        loginUserId = 1L;
        loginUserName = "yujie";

        ReturnObject returnObject1 = onsaleService.searchOnSaleByProduct(id, page, pageSize);
        return decorateReturnObject(returnObject1);

    }

}
