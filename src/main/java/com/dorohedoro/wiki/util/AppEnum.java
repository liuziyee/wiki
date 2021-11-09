package com.dorohedoro.wiki.util;

/**
 * @Description
 * @Author liuziye
 * @Date 2021/11/4 17:43
 */
public class AppEnum {
    public enum ResultCode {
        normal {public Integer v() {
                return 0;
            }},
        auth {public Integer v() {return 1000;}},
        no_data {public Integer v() {return 1001;}};
        
        public abstract Integer v();
    }

    public enum RedisKey {}
    
}
