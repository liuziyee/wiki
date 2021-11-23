package com.dorohedoro.wiki.controller;

import com.dorohedoro.wiki.bean.VO.ResponseBean;
import com.dorohedoro.wiki.util.AppEnum;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description
 * @Author liuziye
 * @Date 2021/11/9 9:29
 */
@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(BindException.class)
    public ResponseBean handleValidException(BindException e) {
        ResponseBean<Object> res = new ResponseBean<>();
        res.setCode(AppEnum.ResultCode.valid.v());
        res.setMsg(e.getBindingResult().getFieldError().getDefaultMessage());
        return res;
    }
}
