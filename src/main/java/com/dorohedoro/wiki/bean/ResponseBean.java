package com.dorohedoro.wiki.bean;

import lombok.Data;

/**
 * @Description
 * @Author liuziye
 * @Date 2021/11/4 17:36
 */
@Data
public class ResponseBean<T> {
    private Integer code;
    private T data;
    private Long serverTime;
}