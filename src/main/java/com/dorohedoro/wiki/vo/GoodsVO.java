package com.dorohedoro.wiki.vo;

import lombok.Data;

/**
 * @Description
 * @Author liuziye
 * @Date 2021/11/4 18:31
 */
@Data
public class GoodsVO {
    private String name;

    private Long pid;

    private String description;

    private String cover;

    private Long viewCount;

    private Long followCount;

    private Long createTime;

    private Long updateTime;
}
