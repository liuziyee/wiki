package com.dorohedoro.wiki.bean.VO;

import lombok.Data;

/**
 * @Description
 * @Author liuziye
 * @Date 2021/11/4 17:36
 */
@Data
public class ResponseBean<T> {
    private Long code;
    private T data;
    private String msg;
    private Long serverTime;
}
