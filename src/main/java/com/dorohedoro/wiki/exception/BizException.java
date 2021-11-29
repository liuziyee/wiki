package com.dorohedoro.wiki.exception;

import com.dorohedoro.wiki.util.ResCode;

/**
 * @Description
 * @Author liuziye
 * @Date 2021/11/26 8:49
 */
public class BizException extends RuntimeException{
    private ResCode code;

    public BizException(ResCode code) {
        super(code.getDesc());
        this.code = code;
    }
    
    public Long getCode() {
        return this.code.getCode();
    }
    
    public String getMsg() {
        return this.code.getDesc();
    }
}
