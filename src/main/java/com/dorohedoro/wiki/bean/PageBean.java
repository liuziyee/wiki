package com.dorohedoro.wiki.bean;

import lombok.Data;

import java.util.List;

/**
 * @Description
 * @Author liuziye
 * @Date 2021/11/8 9:47
 */
@Data
public class PageBean<T> {
    private Integer page;
    private Integer size;
    private List<T> list;
    private Long total;
}
