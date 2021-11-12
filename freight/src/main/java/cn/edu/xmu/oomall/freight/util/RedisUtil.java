package cn.edu.xmu.oomall.freight.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author ziyi guo
 * @date 2021/11/11
 */

@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    public void del(String key) {
        if (key != null) {
            redisTemplate.delete(key);
        }
    }

    public Serializable get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    public  boolean set(String key, Serializable value, long timeout) {
        if (timeout <= 0) {
            timeout = 60;
        }

        long min = 1;
        long max = timeout / 5;
        try {
            timeout += (long) new Random().nextDouble() * (max - min);
            redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
