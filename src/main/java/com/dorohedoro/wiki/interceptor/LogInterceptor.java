package com.dorohedoro.wiki.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("URL: {} {}", request.getRequestURL().toString(), request.getMethod());
        log.info("IP: {}", request.getRemoteAddr());

        long startTime = Instant.now().toEpochMilli();
        request.setAttribute("axiosBoostTime", startTime);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Long startTime= (Long) request.getAttribute("axiosBoostTime");
        log.info("{}ms", Instant.now().toEpochMilli() - startTime);
    }
}
