package com.dorohedoro.wiki.bean.vo;

import lombok.Data;

@Data
public class ResponseBean<T> {
    private Long code;
    private T data;
    private String msg;
    private String token;
}
