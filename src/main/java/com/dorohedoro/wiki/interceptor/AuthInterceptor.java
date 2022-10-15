package com.dorohedoro.wiki.interceptor;

import com.dorohedoro.wiki.util.CommonUtil;
import com.dorohedoro.wiki.util.RedisKey;
import com.dorohedoro.wiki.util.RedisUtil;
import com.dorohedoro.wiki.util.ResCode;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
            return true;
        }
        log.info("URL: {} {}", request.getRequestURL().toString(), request.getMethod());

        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            CommonUtil.sayError(response, ResCode.unauthorized);
            return false;
        }
        String key = RedisUtil.getKey(RedisKey.token, Long.valueOf(token));
        Object obj = RedisUtil.get(key);
        Gson gson = new Gson();
        log.info("token: {}, data: {}", token, gson.toJson(obj));
        if (obj == null) {
            CommonUtil.sayError(response, ResCode.unauthorized);
            return false;
        }
        return true;
    }
}
