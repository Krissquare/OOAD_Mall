package cn.edu.xmu.oomall.core.util;

import cn.edu.xmu.oomall.core.model.VoObject;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 通用工具类
 *
 * @author Ming Qiu
 **/
public class Common {

    private static Logger logger = LoggerFactory.getLogger(Common.class);


    /**
     * 生成八位数序号
     *
     * @return 序号
     */
    public static String genSeqNum() {
        int maxNum = 36;
        int i;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmssS");
        LocalDateTime localDateTime = LocalDateTime.now();
        String strDate = localDateTime.format(dtf);
        StringBuffer sb = new StringBuffer(strDate);

        int count = 0;
        char[] str = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
                'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        Random r = new Random();
        while (count < 2) {
            i = Math.abs(r.nextInt(maxNum));
            if (i >= 0 && i < str.length) {
                sb.append(str[i]);
                count++;
            }
        }
        return sb.toString();
    }

    /**
     * 处理BindingResult的错误
     *
     * @param bindingResult
     * @return
     */
    public static Object processFieldErrors(BindingResult bindingResult, HttpServletResponse response) {
        Object retObj = null;
        if (bindingResult.hasErrors()) {
            StringBuffer msg = new StringBuffer();
            //解析原错误信息，封装后返回，此处返回非法的字段名称，原始值，错误信息
            for (FieldError error : bindingResult.getFieldErrors()) {
                msg.append(error.getDefaultMessage());
                msg.append(";");
            }
            logger.debug("processFieldErrors: msg = " + msg.toString());
            retObj = ResponseUtil.fail(ReturnNo.FIELD_NOTVALID, msg.toString());
            response.setStatus(HttpStatus.BAD_REQUEST.value());
        }
        return retObj;
    }

    /**
     * 处理返回对象
     *
     * @param returnObject 返回的对象
     * @return
     */
    public static Object getRetObject(ReturnObject<VoObject> returnObject) {
        ReturnNo code = returnObject.getCode();
        switch (code) {
            case OK:
                VoObject data = returnObject.getData();
                if (data != null) {
                    Object voObj = data.createVo();
                    return ResponseUtil.ok(voObj);
                } else {
                    return ResponseUtil.ok();
                }
            default:
                return ResponseUtil.fail(returnObject.getCode(), returnObject.getErrmsg());
        }
    }

    /**
     * 处理返回对象
     *
     * @param returnObject 返回的对象
     * @return TODO： 利用cloneVo方法可以生成任意类型v对象,从而把createVo方法从bo中移除
     */

    public static Object getListRetObject(ReturnObject<List> returnObject) {
        ReturnNo code = returnObject.getCode();
        switch (code) {
            case OK:
                List objs = returnObject.getData();
                if (objs != null) {
                    List<Object> ret = new ArrayList<>(objs.size());
                    for (Object data : objs) {
                        if (data instanceof VoObject) {
                            ret.add(((VoObject) data).createVo());
                        }
                    }
                    return ResponseUtil.ok(ret);
                } else {
                    return ResponseUtil.ok();
                }
            default:
                return ResponseUtil.fail(returnObject.getCode(), returnObject.getErrmsg());
        }
    }


    /**
     * 处理分页返回对象
     *
     * @param returnObject 返回的对象
     * @return TODO： 利用cloneVo方法可以生成任意类型v对象,从而把createVo方法从bo中移除
     */
    public static Object getPageRetObject(ReturnObject<PageInfo<VoObject>> returnObject) {
        ReturnNo code = returnObject.getCode();
        switch (code) {
            case OK:
                PageInfo<VoObject> objs = returnObject.getData();
                if (objs != null) {
                    List<Object> voObjs = new ArrayList<>(objs.getList().size());
                    for (Object data : objs.getList()) {
                        if (data instanceof VoObject) {
                            voObjs.add(((VoObject) data).createVo());
                        }
                    }

                    Map<String, Object> ret = new HashMap<>();
                    ret.put("list", voObjs);
                    ret.put("total", objs.getTotal());
                    ret.put("page", objs.getPageNum());
                    ret.put("pageSize", objs.getPageSize());
                    ret.put("pages", objs.getPages());
                    return ResponseUtil.ok(ret);
                } else {
                    return ResponseUtil.ok();
                }
            default:
                return ResponseUtil.fail(returnObject.getCode(), returnObject.getErrmsg());
        }
    }

    /**
     * 根据clazz实例化一个对象，并深度克隆bo中对应属性到这个新对象
     *
     * @param bo      business object
     * @param voClass vo对象类型
     * @return 深度克隆的vo对象
     */
    public static Object cloneVo(Object bo, Class voClass) {
        Object vo = null;
        try {
            vo = voClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException e) {
            logger.info(e.getMessage());
            return null;
        } catch (IllegalAccessException e) {
            logger.info(e.getMessage());
            return null;
        } catch (InvocationTargetException e) {
            logger.info(e.getMessage());
            return null;
        } catch (NoSuchMethodException e) {
            logger.info(e.getMessage());
            return null;
        }
        Class boClass = bo.getClass();
        Field[] boFs = boClass.getDeclaredFields();
        Field[] voFs = vo.getClass().getDeclaredFields();

        for (Field vf : voFs) {
            try {
                Field bf=bo.getClass().getDeclaredField(vf.getName());
                if (vf.getName().equals(bf.getName())) {
                    vf.setAccessible(true);
                    bf.setAccessible(true);
                    if ((vf.getType().isPrimitive() || vf.getType().getTypeName().startsWith("java.lang")
                            && vf.getType().equals(bf.getType()))) {
                        try {
                            vf.set(vo, bf.get(bo));
                        } catch (IllegalAccessException e) {
                            logger.info(e.getMessage());
                            return null;
                        }
                    } else if (!vf.getType().isPrimitive() && !bf.getType().isPrimitive()) {
                        try {
                            vf.set(vo, cloneVo(bf.get(bo), vf.getType()));
                        } catch (IllegalAccessException e) {
                            logger.info(e.getMessage());
                            return null;
                        }
                    }
                }
            } catch (NoSuchFieldException e) {
                logger.info(e.getMessage());
                return null;
            }
        }
        return vo;
    }


    public static Object getNullRetObj(ReturnObject<Object> returnObject, HttpServletResponse httpServletResponse) {
        ReturnNo code = returnObject.getCode();
        switch (code) {
            case RESOURCE_ID_NOTEXIST:
                httpServletResponse.setStatus(HttpStatus.NOT_FOUND.value());
                return ResponseUtil.fail(returnObject.getCode());
            default:
                return ResponseUtil.fail(returnObject.getCode(), returnObject.getErrmsg());
        }
    }

    /**
     * 根据 errCode 修饰 API 返回对象的 HTTP Status
     *
     * @param returnObject 原返回 Object
     * @return 修饰后的返回 Object
     */
    public static Object decorateReturnObject(ReturnObject returnObject) {
        switch (returnObject.getCode()) {
            case RESOURCE_ID_NOTEXIST:
                // 404：资源不存在
                return new ResponseEntity(
                        ResponseUtil.fail(returnObject.getCode(), returnObject.getErrmsg()),
                        HttpStatus.NOT_FOUND);
            case INTERNAL_SERVER_ERR:
                // 500：数据库或其他严重错误
                return new ResponseEntity(
                        ResponseUtil.fail(returnObject.getCode(), returnObject.getErrmsg()),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            case OK:
                // 200: 无错误
                Object data = returnObject.getData();
                if (data != null) {
                    return ResponseUtil.ok(data);
                } else {
                    return ResponseUtil.ok();
                }
            default:
                return ResponseUtil.fail(returnObject.getCode(), returnObject.getErrmsg());
        }
    }

    /**
     * 动态拼接字符串
     *
     * @param sep    分隔符
     * @param fields 拼接的字符串
     * @return StringBuilder
     * createdBy: Ming Qiu 2020-11-02 11:44
     */
    public static StringBuilder concatString(String sep, String... fields) {
        StringBuilder ret = new StringBuilder();

        for (int i = 0; i < fields.length; i++) {
            if (i > 0) {
                ret.append(sep);
            }
            ret.append(fields[i]);
        }
        return ret;
    }

    /**
     * 增加20%以内的随机时间
     * 如果timeout <0 则会返回60s+随机时间
     *
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

    /**
     * 设置所有po对象的createdBy, createName和gmtCreate字段属性
     *
     * @param po       po对象
     * @param userId   设置到createdBy
     * @param userName 设置到createName
     * @return 如果po对象没有这些属性或类型不对返回false，否则true
     */
    public static boolean setPoCreatedFields(Object po, long userId, String userName) {
        Class<?> aClass = po.getClass();
        try {
            Field createdBy = aClass.getDeclaredField("createdBy");
            createdBy.setAccessible(true);
            createdBy.set(po, userId);

        } catch (NoSuchFieldException e) {
            logger.info(e.getMessage());
            return false;
        } catch (IllegalAccessException ex) {
            logger.info(ex.getMessage());
            return false;
        }

        try {
            Field createName = aClass.getDeclaredField("createName");
            createName.setAccessible(true);
            createName.set(po, userName);
        } catch (NoSuchFieldException e) {
            logger.info(e.getMessage());
            return false;
        } catch (IllegalAccessException ex) {
            logger.info(ex.getMessage());
            return false;
        }
        try {
            Field createName = aClass.getDeclaredField("gmtCreate");
            createName.setAccessible(true);
            createName.set(po, LocalDateTime.now());
        } catch (NoSuchFieldException e) {
            logger.info(e.getMessage());
            return false;
        } catch (IllegalAccessException ex) {
            logger.info(ex.getMessage());
            return false;
        }
        return true;
    }

    /**
     * 设置所有po对象的modifiedBy, modiName和gmtModify字段属性
     *
     * @param po       po对象
     * @param userId   设置到modifiedBy
     * @param userName 设置到modiName
     * @return 如果po对象没有这些属性或类型不对返回false，否则true
     */
    public static boolean setPoModifiedFields(Object po, long userId, String userName) {
        Class<?> aClass = po.getClass();
        try {
            Field modifiedBy = aClass.getDeclaredField("modifiedBy");
            modifiedBy.setAccessible(true);
            modifiedBy.set(po, userId);
        } catch (NoSuchFieldException e) {
            logger.info(e.getMessage());
            return false;
        } catch (IllegalAccessException ex) {
            logger.info(ex.getMessage());
            return false;
        }

        try {
            Field modiName = aClass.getDeclaredField("modiName");
            modiName.setAccessible(true);
            modiName.set(po, userName);
        } catch (NoSuchFieldException e) {
            logger.info(e.getMessage());
            return false;
        } catch (IllegalAccessException ex) {
            logger.info(ex.getMessage());
            return false;
        }
        try {
            Field createName = aClass.getDeclaredField("gmtCreate");
            createName.setAccessible(true);
            createName.set(po, LocalDateTime.now());
        } catch (NoSuchFieldException e) {
            logger.info(e.getMessage());
            return false;
        } catch (IllegalAccessException ex) {
            logger.info(ex.getMessage());
            return false;
        }
        return true;
    }

}
