package com.dorohedoro.wiki.advice;

import com.dorohedoro.wiki.util.IDGenerator;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;

@Component
@Aspect
@Slf4j
public class LogAspect {
    @Pointcut("execution(* com.dorohedoro..*Controller..*(..))")
    public void logPointCut() {
    }

    @Before("logPointCut()")
    public void doBefore(JoinPoint joinPoint) {
        MDC.put("LOG_ID", String.valueOf(IDGenerator.nextId()));
        MDC.put("TIMESTAMP", String.valueOf(System.currentTimeMillis()));
        
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Signature signature = joinPoint.getSignature();

        log.info("URL: {} {}", request.getRequestURL().toString(), request.getMethod());
        log.info("@{}.{}", signature.getDeclaringTypeName(), signature.getName());

        Object[] args = joinPoint.getArgs();
        Object[] arguments = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof ServletRequest || args[i] instanceof ServletResponse || args[i] instanceof MultipartFile) {
                continue;
            }
            arguments[i] = args[i];
        }

        Gson gson = new Gson();
        log.info("请求数据: {}", gson.toJson(arguments));
    }

    @Around("logPointCut()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = Instant.now().toEpochMilli();
        Object res = proceedingJoinPoint.proceed();

        Gson gson = new Gson();
        log.info("响应数据: {}", gson.toJson(res));
        log.info("{}ms", Instant.now().toEpochMilli() - startTime);
        return res;
    }
}
