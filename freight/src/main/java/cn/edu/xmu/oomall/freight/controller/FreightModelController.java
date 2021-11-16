package cn.edu.xmu.oomall.freight.controller;

import cn.edu.xmu.oomall.core.util.Common;
import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.freight.model.vo.FreightModelInfo;
import cn.edu.xmu.oomall.freight.service.FreightModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *
 * @author RenJieZheng 22920192204334
 * @date 2021/11/16
 */

@RestController
@RequestMapping(value = "/",produces = "application/json;charset=UTF-8")
public class FreightModelController {

    @Autowired
    FreightModelService freightModelService;


    /**
     * 管理员定义运费模板
     * @param shopId 店铺id
     * @param freightModelInfo 运费模板资料
     * @param userId 操作者id
     * @param userName 操作者姓名
     * @return 运费模板
     */
    @PostMapping("shops/{shopId}/freightmodels")
    public Object addFreightModel(@PathVariable Long shopId, @RequestBody FreightModelInfo freightModelInfo,
                                Long userId,String userName) {
        userId = 1L;
        userName = "test";
        // 非管理员返回错误
        if(shopId!=0){
            return Common.decorateReturnObject(new ReturnObject<>(ReturnNo.FIELD_NOTVALID));
        }
        return Common.decorateReturnObject(freightModelService.addFreightModel(freightModelInfo,userId,userName));
    }

    /**
     * 获得商品的运费模板
     * @param shopId 店铺id
     * @param name 模板名称
     * @param page 页
     * @param pageSize 页大小
     * @return 运费模板
     */
    @GetMapping("shops/{shopId}/freightmodels")
    public Object showFreightModel(@PathVariable Long shopId,@RequestParam(required = false) String name,
                                  @RequestParam(required = false)Integer page, @RequestParam(required = false)Integer pageSize){
        // 非管理员返回错误
        if(shopId!=0){
            return Common.decorateReturnObject(new ReturnObject<>(ReturnNo.FIELD_NOTVALID));
        }
        return Common.getPageRetObject(freightModelService.showFreightModel(name,page,pageSize));
    }

    /**
     * 获得商品的运费模板带Product
     * @param shopId 店铺id
     * @param id 商品id
     * @param name 模块名称
     * @param page 页
     * @param pageSize 页大小
     * @return 运费模板
     */
    @GetMapping("shops/{shopId}/products/{id}/freightmodels")
    public Object showFreightModelWithProductId(@PathVariable Long shopId,@PathVariable Long id,
                                               @RequestParam(required = false) String name,
                                               @RequestParam(required = false)Integer page, @RequestParam(required = false)Integer pageSize){
        // 非管理员返回错误
        if(shopId!=0){
            return Common.decorateReturnObject(new ReturnObject<>(ReturnNo.FIELD_NOTVALID));
        }
        return Common.getPageRetObject(freightModelService.showFreightModelWithProductId(id,name,page,pageSize));
    }

    /**
     * 管理员克隆运费模板
     * @param shopId 店铺id
     * @param id 运费模板id
     * @param userId 操作者id
     * @param userName 操作者姓名
     * @return 运费模板
     */
    @PostMapping("shops/{shopId}/freightmodels/{id}/clone")
    public Object cloneFreightModel(@PathVariable Long shopId,@PathVariable Long id,
                                  Long userId,String userName) {
        userId = 1L;
        userName = "test";
        // 非管理员返回错误
        if(shopId!=0){
            return Common.decorateReturnObject(new ReturnObject<>(ReturnNo.FIELD_NOTVALID));
        }
        return Common.decorateReturnObject(freightModelService.cloneFreightModel(id,userId,userName));
    }

    /**
     * 获得运费模板详情
     * @param id 运费模板id
     * @param shopId 店铺id
     * @param userId 操作者id
     * @param userName 操作者姓名
     * @return 运费模板
     */
    @GetMapping("shops/{shopId}/freightmodels/{id}")
    public Object showFreightModelById(@PathVariable Long id,@PathVariable Long shopId,
                                      Long userId,String userName){
        userId = 1L;
        userName = "test";
        if(shopId!=0){
            return Common.decorateReturnObject(new ReturnObject<>(ReturnNo.FIELD_NOTVALID));
        }
        return Common.decorateReturnObject(freightModelService.showFreightModelById(id,userId,userName));
    }

    /**
     * 管理员修改运费模板
     * @param shopId 店铺id
     * @param id 运费模板id
     * @param freightModelInfo 运费模板资料
     * @param userId 操作者id
     * @param userName 操作者姓名
     * @return 修改结果
     */
    @PutMapping("shops/{shopId}/freightmodels/{id}")
    public Object updateFreightModel(@PathVariable Long shopId,@PathVariable Long id, @RequestBody FreightModelInfo freightModelInfo,
                                  Long userId,String userName) {
        userId = 1L;
        userName = "test";
        // 非管理员返回错误
        if(shopId!=0){
            return Common.decorateReturnObject(new ReturnObject<>(ReturnNo.FIELD_NOTVALID));
        }
        return Common.decorateReturnObject(freightModelService.updateFreightModel(id,freightModelInfo,userId,userName));
    }

    /**
     * 管理员删除运费模板
     * @param shopId 店铺id
     * @param id 运费模板id
     * @param userId 操作者id
     * @param userName 操作者姓名
     * @return 修改结果
     */
    @DeleteMapping("shops/{shopId}/freightmodels/{id}")
    public Object deleteFreightModel(@PathVariable Long shopId,@PathVariable Long id,
                                     Long userId,String userName) {
        userId = 1L;
        userName = "test";
        // 非管理员返回错误
        if(shopId!=0){
            return Common.decorateReturnObject(new ReturnObject<>(ReturnNo.FIELD_NOTVALID));
        }
        return Common.decorateReturnObject(freightModelService.deleteFreightModel(id,userId,userName));
    }


    /**
     * 店家或管理员为商铺定义默认运费模板。会将原有的默认运费模板取消
     * @param shopId 店铺id
     * @param id 运费模板id
     * @param userId 操作者id
     * @param userName 操作者姓名
     * @return 修改结果
     */
    @PostMapping("shops/{shopId}/freightmodels/{id}/default")
    public Object updateDefaultModel(@PathVariable Long shopId,@PathVariable Long id,
                                     Long userId,String userName) {
        userId = 1L;
        userName = "test";
        // 非管理员返回错误
        if(shopId!=0){
            return Common.decorateReturnObject(new ReturnObject<>(ReturnNo.FIELD_NOTVALID));
        }
        return Common.decorateReturnObject(freightModelService.updateDefaultModel(id,userId,userName));
    }






}
