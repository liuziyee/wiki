package com.dorohedoro.wiki.util;

/**
 * @Description
 * @Author liuziye
 * @Date 2021/11/29 11:32
 */
public enum RedisKey {
    token("token");

    private String key;

    RedisKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }

}
