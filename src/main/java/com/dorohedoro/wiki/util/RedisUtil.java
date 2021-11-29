package com.dorohedoro.wiki.util;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @Description
 * @Author liuziye
 * @Date 2021/11/29 10:26
 */
public class RedisUtil {
    private static RedisTemplate redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisUtil.redisTemplate = redisTemplate;
    }

    public static String getKey(RedisKey prefix, Long id) {
        return prefix.getKey() + "_" + id;
    }

    public static void set(String key, Object obj, Long seconds) {
        redisTemplate.opsForValue().set(key, obj, seconds, TimeUnit.SECONDS);
    }

    public static void del(String key) {
        redisTemplate.delete(key);
    }
    
}