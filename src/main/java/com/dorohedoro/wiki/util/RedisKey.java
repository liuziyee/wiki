package com.dorohedoro.wiki.util;

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
