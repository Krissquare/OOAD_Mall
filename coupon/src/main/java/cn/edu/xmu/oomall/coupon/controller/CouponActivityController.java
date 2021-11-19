package cn.edu.xmu.oomall.coupon.controller;


import cn.edu.xmu.oomall.core.util.Common;
import cn.edu.xmu.oomall.core.util.ReturnNo;
import cn.edu.xmu.oomall.core.util.ReturnObject;
import cn.edu.xmu.oomall.coupon.model.vo.CouponActivityVo;
import cn.edu.xmu.oomall.coupon.service.CouponActivityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author RenJieZheng 22920192204334
 */
@RestController
@RequestMapping(value = "/",produces = "application/json;charset=UTF-8")
public class CouponActivityController {
    @Autowired
    CouponActivityService couponActivityService;

    static final Integer IMAGE_MAX_SIZE=1000000;
    /**
     * 查看优惠活动模块的所有活动
     * @return List<Map<String, Object>>
     */
    @GetMapping("couponactivities/states")
    public Object showAllState(){
        return  Common.decorateReturnObject(couponActivityService.showAllState());
    }

    /**
     * 管理员新建己方优惠活动
     * @param shopId 店铺id
     * @param couponActivityVo 优惠券信息
     * @return 插入结果
     */
    @PostMapping("shops/{shopId}/couponactivities")
    public Object addCouponActivity(@PathVariable Long shopId,
                                    Long userId,
                                    String userName,
                                    @Valid @RequestBody CouponActivityVo couponActivityVo,
                                    HttpServletResponse httpServletResponse,BindingResult bindingResult
                                    ){
        userId = 1L;
        userName = "aasdf";
        Object returnObject = Common.processFieldErrors(bindingResult, httpServletResponse);
        if (null != returnObject) {
            return returnObject;
        }
        //对输入数据进行合法性判断
        // 如果开始时间晚于结束时间
        if(couponActivityVo.getBeginTime()!=null&&couponActivityVo.getEndTime()!=null){
            if(couponActivityVo.getBeginTime().compareTo(couponActivityVo.getEndTime()) > 0){
                return Common.decorateReturnObject(new ReturnObject<>(ReturnNo.LATE_BEGINTIME));
            }
        }

        // 优惠卷领卷时间晚于活动开始时间
        if(couponActivityVo.getCouponTime()!=null&&couponActivityVo.getBeginTime()!=null){
            if(couponActivityVo.getCouponTime().compareTo(couponActivityVo.getBeginTime()) > 0){
                return Common.decorateReturnObject(new ReturnObject<>(ReturnNo.COUPON_LATE_COUPONTIME));
            }
        }
        return Common.decorateReturnObject(couponActivityService.addCouponActivity(userId,userName,shopId,couponActivityVo));
    }
    /**
     * 查看店铺所有状态的优惠活动列表
     * @param shopId 店铺id
     * @param state 状态
     * @param page 页
     * @param pageSize 页大小
     * @return 优惠活动列表
     */
    @GetMapping("shops/{shopId}/couponactivities")
    public Object showOwnInvalidCouponActivities(@PathVariable Long shopId,
                                                 Long userId,
                                                 String userName,
                                                 @RequestParam(required = false) Byte state,
                                                 @RequestParam(required = false,defaultValue = "1") Integer page,
                                                 @RequestParam(required = false,defaultValue = "5") Integer pageSize
                                                 ){
        userId = 1L;
        userName = "aasdf";

        return Common.getPageRetObject(couponActivityService.showOwnInvalidCouponActivities(userId,userName,shopId,state,page,pageSize));
    }

    /**
     * 上传图片文件
     * @param shopId 店铺id
     * @param id 活动id
     * @param request 请求
     * @return 上传结果
     */
    @PostMapping("shops/{shopId}/couponactivities/{id}/uploadImg")
    public Object addCouponActivityImageUrl(@PathVariable Long shopId,
                                            @PathVariable Long id,
                                            Long userId,
                                            String userName,
                                            HttpServletRequest request) {

        userId = 1L;
        userName = "aasdf";
        //对输入数据进行合法性判断
        List<MultipartFile> files = ((MultipartHttpServletRequest) request)
                .getFiles("file");
        if(files.size()<=0){
            return Common.decorateReturnObject(new ReturnObject<>(ReturnNo.FIELD_NOTVALID));
        }
        MultipartFile multipartFile=files.get(0);
        //图片超限
        if(multipartFile.getSize()>IMAGE_MAX_SIZE){
            return Common.decorateReturnObject(new ReturnObject<>(ReturnNo.IMG_SIZE_EXCEED));
        }
        return Common.decorateReturnObject(couponActivityService.addCouponActivityImageUrl(userId,userName,id,shopId,multipartFile));

    }

    /**
     * 查看所有的上线优惠活动列表
     * @param shopId 店铺id
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @param page 页
     * @param pageSize 页大小
     * @return 优惠活动列表 List<CouponActivityRetVo>
     */
    @GetMapping("couponactivities")
    public Object showOwnCouponActivities(@RequestParam(required = false) Long shopId,
                                          @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(required = false) LocalDateTime beginTime,
                                          @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(required = false) LocalDateTime endTime,
                                          @RequestParam(required = false,defaultValue = "1") Integer page,
                                          @RequestParam(required = false,defaultValue = "5") Integer pageSize
                                          ){
        //对输入数据进行合法性判断
        // 如果开始时间晚于结束时间
        if(beginTime.compareTo(endTime) > 0){
            return Common.decorateReturnObject(new ReturnObject<>(ReturnNo.LATE_BEGINTIME));
        }
        return Common.getPageRetObject(couponActivityService.showOwnCouponActivities(shopId,beginTime,endTime,page,pageSize));

    }

    /**
     * 查看店铺的所有状态优惠活动列表
     * @param shopId 店铺id
     * @param state 状态
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @param page 页
     * @param pageSize 页大小
     * @return 优惠活动列表 List<CouponActivityRetVo>
     */
    @GetMapping("shop/{shopId}/couponactivities")
    public Object showOwnCouponaAtivities1(@PathVariable Long shopId,
                                          @RequestParam(required = false) Byte state,
                                          @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(required = false) LocalDateTime beginTime,
                                          @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") @RequestParam(required = false) LocalDateTime endTime,
                                          @RequestParam(required = false,defaultValue = "1") Integer page,
                                          @RequestParam(required = false,defaultValue = "2") Integer pageSize){
        //对输入数据进行合法性判断
        // 如果开始时间晚于结束时间
        if(beginTime.compareTo(endTime) > 0){
            return Common.decorateReturnObject(new ReturnObject<>(ReturnNo.LATE_BEGINTIME));
        }
        return Common.getPageRetObject(couponActivityService.showOwnCouponActivities1(shopId,beginTime,endTime,state,page,pageSize));
    }

    /**
     * 查看优惠活动详情
     * @param id 活动id
     * @param shopId 店铺id
     * @return 优惠活动信息
     */
    @GetMapping("shops/{shopId}/couponactivities/{id}")
    public Object showOwnCouponActivityInfo(@PathVariable Long shopId,
                                            @PathVariable Long id,
                                            Long userId,
                                            String userName){
        userId = 1L;
        userName = "aasdf";
        return Common.decorateReturnObject(couponActivityService.showOwnCouponActivityInfo(userId,userName,id,shopId));
    }




}
