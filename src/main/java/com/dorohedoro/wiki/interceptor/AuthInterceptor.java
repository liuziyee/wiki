package com.dorohedoro.wiki.interceptor;

import ch.qos.logback.core.pattern.color.RedCompositeConverter;
import com.dorohedoro.wiki.util.CommonUtil;
import com.dorohedoro.wiki.util.RedisKey;
import com.dorohedoro.wiki.util.RedisUtil;
import com.dorohedoro.wiki.util.ResCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.Locale;

/**
 * @Description
 * @Author liuziye
 * @Date 2021/11/29 21:50
 */
@Component
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().toUpperCase().equals("OPTIONS")) {
            return true;
        }
        request.setAttribute("startTime", Instant.now().toEpochMilli());
        log.info("<<<<<<<<<<Auth日志开始>>>>>>>>>>");
        log.info("请求地址: {} {}", request.getRequestURL().toString(), request.getMethod());

        String token = request.getHeader("token");
        log.info("token: {}", token);
        if (StringUtils.isEmpty(token)) {
            CommonUtil.sayError(response, ResCode.unauthorized);
            return false;
        }
        String key = RedisUtil.getKey(RedisKey.token, Long.valueOf(token));
        Object obj = RedisUtil.get(key);
        log.info("obj: {}", obj);
        if (obj == null) {
            CommonUtil.sayError(response, ResCode.unauthorized);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //log.info("<<<<<<<<<<Auth日志结束 耗时:{}ms>>>>>>>>>>", Instant.now().toEpochMilli() - (Long)request.getAttribute("startTime"));
    }
}
