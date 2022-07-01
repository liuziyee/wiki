package com.dorohedoro.wiki.bean.domain;

import lombok.Data;

import java.util.List;

@Data
public class Comment {
    private Long id;

    private Long goodsId;

    private Long userId;

    private String content;

    private Long createTime;

    private String label;

    private List<Reply> children;
}