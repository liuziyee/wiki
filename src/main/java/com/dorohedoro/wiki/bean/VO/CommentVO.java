package com.dorohedoro.wiki.bean.VO;

import com.dorohedoro.wiki.bean.domain.Reply;
import lombok.Data;

import java.util.List;
@Data
public class CommentVO {
    private Long id;

    private Long goodsId;

    private Long userId;

    private String content;

    private Long createTime;

    private String label;

    private List<ReplyVO> children;
    
}