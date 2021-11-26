package com.dorohedoro.wiki.controller;

import com.dorohedoro.wiki.bean.VO.ResponseBean;
import com.dorohedoro.wiki.exception.BizException;
import com.dorohedoro.wiki.util.AppEnum;
import com.dorohedoro.wiki.util.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Description
 * @Author liuziye
 * @Date 2021/11/9 9:29
 */
@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {

    @ExceptionHandler(BindException.class)
    public ResponseBean handleValidException(BindException e) {
        ResponseBean res = new ResponseBean();
        res.setCode(ResultCode.valid.getCode());
        res.setMsg(e.getBindingResult().getFieldError().getDefaultMessage());
        return res;
    }

    @ExceptionHandler(BizException.class)
    public ResponseBean handleBizException(BizException e) {
        ResponseBean res = new ResponseBean();
        log.error("BizException: ", e);
        res.setCode(e.getCode());
        res.setMsg(e.getMsg());
        return res;
    }
}
