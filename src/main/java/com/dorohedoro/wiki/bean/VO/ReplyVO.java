package com.dorohedoro.wiki.bean.VO;

import lombok.Data;

import java.util.List;
@Data
public class ReplyVO {
    private Long id;

    private Long commentId;

    private Long relaId;

    private Long type;

    private Long fromUid;

    private Long toUid;

    private String content;

    private Long createTime;

    private String label;

    private List<ReplyVO> children;
}