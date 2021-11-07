package cn.edu.xmu.oomall.core.util;

import cn.edu.xmu.oomall.core.model.VoObject;
import cn.edu.xmu.oomall.core.util.*;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 通用工具类
 * @author Ming Qiu
 **/
public class Common {

    private static Logger logger = LoggerFactory.getLogger(Common.class);

    /**
     * 处理BindingResult的错误
     * @param bindingResult
     * @return
     */
    public static Object processFieldErrors(BindingResult bindingResult, HttpServletResponse response) {
        Object retObj = null;
        if (bindingResult.hasErrors()){
            StringBuffer msg = new StringBuffer();
            //解析原错误信息，封装后返回，此处返回非法的字段名称，原始值，错误信息
            for (FieldError error : bindingResult.getFieldErrors()) {
                msg.append(error.getDefaultMessage());
                msg.append(";");
            }
            logger.debug("processFieldErrors: msg = "+ msg.toString());
            retObj = cn.edu.xmu.privilegegateway.util.ResponseUtil.fail(ReturnNo.FIELD_NOTVALID, msg.toString());
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
        return retObj;
    }

    /**
     * 处理返回对象
     * @param returnObject 返回的对象
     * @return
     */
    public static Object getRetObject(cn.edu.xmu.privilegegateway.util.ReturnObject<VoObject> returnObject) {
        ReturnNo code = returnObject.getCode();
        switch (code){
            case ReturnNo.OK:
                VoObject data = returnObject.getData();
                if (data != null){
                    Object voObj = data.createVo();
                    return cn.edu.xmu.privilegegateway.util.ResponseUtil.ok(voObj);
                }else{
                    return cn.edu.xmu.privilegegateway.util.ResponseUtil.ok();
                }
            default:
                return cn.edu.xmu.privilegegateway.util.ResponseUtil.fail(returnObject.getCode(), returnObject.getErrmsg());
        }
    }

    /**
     * 处理返回对象
     * @param returnObject 返回的对象
     * @return
     */
    public static Object getListRetObject(cn.edu.xmu.privilegegateway.util.ReturnObject<List> returnObject) {
        ReturnNo code = returnObject.getCode();
        switch (code){
            case ReturnNo.OK:
                List objs = returnObject.getData();
                if (objs != null){
                    List<Object> ret = new ArrayList<>(objs.size());
                    for (Object data : objs) {
                        if (data instanceof VoObject) {
                            ret.add(((VoObject)data).createVo());
                        }
                    }
                    return cn.edu.xmu.privilegegateway.util.ResponseUtil.ok(ret);
                }else{
                    return cn.edu.xmu.privilegegateway.util.ResponseUtil.ok();
                }
            default:
                return cn.edu.xmu.privilegegateway.util.ResponseUtil.fail(returnObject.getCode(), returnObject.getErrmsg());
        }
    }


    /**
     * 处理分页返回对象
     * @param returnObject 返回的对象
     * @return
     */
    public static Object getPageRetObject(cn.edu.xmu.privilegegateway.util.ReturnObject<PageInfo<VoObject>> returnObject) {
        ReturnNo code = returnObject.getCode();
        switch (code){
            case ReturnNo.OK:

                PageInfo<VoObject> objs = returnObject.getData();
                if (objs != null){
                    List<Object> voObjs = new ArrayList<>(objs.getList().size());
                    for (Object data : objs.getList()) {
                        if (data instanceof VoObject) {
                            voObjs.add(((VoObject)data).createVo());
                        }
                    }

                    Map<String, Object> ret = new HashMap<>();
                    ret.put("list", voObjs);
                    ret.put("total", objs.getTotal());
                    ret.put("page", objs.getPageNum());
                    ret.put("pageSize", objs.getPageSize());
                    ret.put("pages", objs.getPages());
                    return cn.edu.xmu.privilegegateway.util.ResponseUtil.ok(ret);
                }else{
                    return cn.edu.xmu.privilegegateway.util.ResponseUtil.ok();
                }
            default:
                return cn.edu.xmu.privilegegateway.util.ResponseUtil.fail(returnObject.getCode(), returnObject.getErrmsg());
        }
    }




    public static Object getNullRetObj(cn.edu.xmu.privilegegateway.util.ReturnObject<Object> returnObject, HttpServletResponse httpServletResponse) {
        ReturnNo code = returnObject.getCode();
        switch (code) {
            case ReturnNo.RESOURCE_ID_NOTEXIST:
                httpServletResponse.setStatus(HttpStatus.NOT_FOUND.value());
                return cn.edu.xmu.privilegegateway.util.ResponseUtil.fail(returnObject.getCode());
            default:
                return cn.edu.xmu.privilegegateway.util.ResponseUtil.fail(returnObject.getCode(), returnObject.getErrmsg());
        }
    }

    /**
     * 根据 errCode 修饰 API 返回对象的 HTTP Status
     * @param returnObject 原返回 Object
     * @return 修饰后的返回 Object
     */
    public static Object decorateReturnObject(ReturnObject returnObject) {
        switch (returnObject.getCode()) {
            case ReturnNo.RESOURCE_ID_NOTEXIST:
                // 404：资源不存在
                return new ResponseEntity(
                        cn.edu.xmu.privilegegateway.util.ResponseUtil.fail(returnObject.getCode(), returnObject.getErrmsg()),
                        HttpStatus.NOT_FOUND);
            case ReturnNo.INTERNAL_SERVER_ERR:
                // 500：数据库或其他严重错误
                return new ResponseEntity(
                        cn.edu.xmu.privilegegateway.util.ResponseUtil.fail(returnObject.getCode(), returnObject.getErrmsg()),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            case ReturnNo.OK:
                // 200: 无错误
                Object data = returnObject.getData();
                if (data != null){
                    return cn.edu.xmu.privilegegateway.util.ResponseUtil.ok(data);
                }else{
                    return cn.edu.xmu.privilegegateway.util.ResponseUtil.ok();
                }
            default:
                return ResponseUtil.fail(returnObject.getCode(), returnObject.getErrmsg());
        }
    }

    /**
     * 动态拼接字符串
     * @param sep 分隔符
     * @param fields 拼接的字符串
     * @return StringBuilder
     * createdBy: Ming Qiu 2020-11-02 11:44
     */
    public static StringBuilder concatString(String sep, String... fields){
        StringBuilder ret = new StringBuilder();

        for (int i = 0; i< fields.length; i++){
            if (i > 0){
                ret.append(sep);
            }
            ret.append(fields[i]);
        }
        return ret;
    }

    /**
     * 增加20%以内的随机时间
     * 如果timeout <0 则会返回60s+随机时间
     * @param timeout 时间
     * @return 增加后的随机时间
     */
    public static long addRandomTime(long timeout) {
        if (timeout <= 0) {
            timeout = 60;
        }
        //增加随机数，防止雪崩
        timeout += (long) new Random().nextDouble() * (timeout / 5 - 1);
        return timeout;
    }

}
