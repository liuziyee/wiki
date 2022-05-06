package com.dorohedoro.wiki.bean.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;
@Data
public class CommentVO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long goodsId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long userId;

    private String content;

    private Long createTime;

    private String label;

    private List<ReplyVO> children;
    
}