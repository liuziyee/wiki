package com.dorohedoro.wiki.bean.VO;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import java.util.List;
@Data
public class ReplyVO {
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long commentId;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long relaId;

    private Long type;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long fromUid;
    @JsonSerialize(using = ToStringSerializer.class)
    private Long toUid;

    private String content;

    private Long createTime;

    private String label;

    private List<ReplyVO> children;
}