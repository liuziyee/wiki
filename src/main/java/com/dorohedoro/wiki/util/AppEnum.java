package com.dorohedoro.wiki.util;

/**
 * @Description
 * @Author liuziye
 * @Date 2021/11/4 17:43
 */
public class AppEnum {
    public enum ResultCode {
        success {public Integer v() {
                return 0;
            }},
        valid {public Integer v() { return 10;}},
        auth {public Integer v() {return 20;}};
        
        public abstract Integer v();
    }

    public enum YesOrNo {
        no  {public Long v() {return 0L;}},
        yes {public Long v() {return 1L;}},
        all {public Long v() {return -1L;}};

        public abstract Long v();
    }
    
}
