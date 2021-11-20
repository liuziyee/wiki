package com.dorohedoro.wiki.util;

/**
 * @Description
 * @Author liuziye
 * @Date 2021/11/4 17:43
 */
public class AppEnum {
    public enum ResultCode {
        success {public Long v() {
                return 0L;
            }},
        valid {public Long v() { return 10L;}},
        auth {public Long v() {return 20L;}},
        db {public Long v() {return 30L;}};
        
        public abstract Long v();
    }

    public enum YesOrNo {
        no  {public Long v() {return 0L;}},
        yes {public Long v() {return 1L;}},
        all {public Long v() {return -1L;}};

        public abstract Long v();
    }
    
}
