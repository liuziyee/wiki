package com.dorohedoro.wiki;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@MapperScan("com.dorohedoro.wiki.mapper")
@SpringBootApplication
public class WikiApplication {

    public static void main(String[] args) {
        SpringApplication.run(WikiApplication.class, args);
    }
}
