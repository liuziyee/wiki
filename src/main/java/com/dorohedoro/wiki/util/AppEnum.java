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
            }};
        
        public abstract Integer v();
    }
}
