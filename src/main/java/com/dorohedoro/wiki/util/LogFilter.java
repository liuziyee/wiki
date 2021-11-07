package com.dorohedoro.wiki.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.Instant;

/**
 * @Description
 * @Author liuziye
 * @Date 2021/11/7 14:31
 */
@Component
@Slf4j
public class LogFilter implements Filter {
    

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //打印请求信息
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        log.info("********** LogFilter开始 **********");
        log.info("请求地址: {} {}", httpRequest.getRequestURL().toString(), httpRequest.getMethod());
        log.info("远程地址: {}", httpRequest.getRemoteAddr());

        long startTime = Instant.now().toEpochMilli();
        chain.doFilter(request, response);
        log.info("********** LogFilter结束 耗时:{}ms **********", Instant.now().toEpochMilli() - startTime);
    }
}
