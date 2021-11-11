package com.dorohedoro.wiki.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.List;

/**
 * @Description
 * @Author liuziye
 * @Date 2021/11/7 9:31
 */
@Configuration
public class SpringMvcConfig implements WebMvcConfigurer {

    //@Autowired
    //LogInterceptor logInterceptor;
    //
    //@Override
    //public void addInterceptors(InterceptorRegistry registry) {
    //    registry.addInterceptor(logInterceptor)
    //            .addPathPatterns("/**")
    //            .excludePathPatterns("/login");
    //}

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedHeaders(CorsConfiguration.ALL)
                .allowedMethods(CorsConfiguration.ALL)
                .allowCredentials(true)
                .maxAge(3600);
    }
}
