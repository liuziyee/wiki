package com.dorohedoro.wiki.util;

public class AppEnum {
    
    public enum YesOrNo {
        no  {public Long v() {return 0L;}},
        yes {public Long v() {return 1L;}},
        all {public Long v() {return -1L;}};

        public abstract Long v();
    }
    
    public enum Reply {
        comment  {public Long v() {return 1L;}},
        reply {public Long v() {return 2L;}};

        public abstract Long v();
    }
}
