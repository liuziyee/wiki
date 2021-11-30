package com.dorohedoro.wiki.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;

/**
 * @Description
 * @Author liuziye
 * @Date 2021/11/7 15:05
 */
//@Component
//@Slf4j
//public class LogInterceptor implements HandlerInterceptor {
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        log.info("<<<<<<<<<<开始>>>>>>>>>>");
//        log.info("请求地址: {} {}", request.getRequestURL().toString(), request.getMethod());
//        log.info("远程地址: {}", request.getRemoteAddr());
//
//        long startTime = Instant.now().toEpochMilli();
//        request.setAttribute("axiosBoostTime", startTime);
//        return true;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        Long startTime= (Long) request.getAttribute("axiosBoostTime");
//        log.info("<<<<<<<<<<结束 耗时:{}>>>>>>>>>>", Instant.now().toEpochMilli() - startTime);
//    }
//}
