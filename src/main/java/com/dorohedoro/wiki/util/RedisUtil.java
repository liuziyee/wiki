package com.dorohedoro.wiki.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

public class RedisUtil {
    private static RedisTemplate redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisUtil.redisTemplate = redisTemplate;
    }

    public static String getKey(RedisKey prefix, Long id) {
        return String.join("_", prefix.getKey(), id.toString());
    }

    public static void set(String key, Object obj, Long seconds) {
        redisTemplate.opsForValue().set(key, obj, seconds, TimeUnit.SECONDS);
    }

    public static void del(String key) {
        redisTemplate.delete(key);
    }

    public static Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
    
}
