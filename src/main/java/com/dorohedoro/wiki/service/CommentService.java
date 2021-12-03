package com.dorohedoro.wiki.service;

import com.dorohedoro.wiki.bean.domain.Comment;
import com.dorohedoro.wiki.bean.domain.CommentExample;
import com.dorohedoro.wiki.bean.domain.Reply;
import com.dorohedoro.wiki.bean.domain.ReplyExample;
import com.dorohedoro.wiki.mapper.CommentMapper;
import com.dorohedoro.wiki.mapper.ReplyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author liuziye
 * @Date 2021/12/3 17:34
 */
@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private ReplyMapper replyMapper;

    public List<Comment> getCommentTree(Long id) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andGoodsIdEqualTo(id);
        List<Comment> commentList = commentMapper.selectByExample(commentExample);
        List<Long> commentIdList = commentList.stream().map(Comment::getId).collect(Collectors.toList());

        ReplyExample replyExample = new ReplyExample();
        replyExample.createCriteria().andCommentIdIn(commentIdList);
        List<Reply> allReplyList = replyMapper.selectByExample(replyExample);

        for (Comment comment : commentList) {
            Long commentId = comment.getId();
            List<Reply> replyToCommentList = allReplyList.stream().
                    filter(o -> o.getRelaId().equals(commentId) && o.getType().equals(1L)).collect(Collectors.toList());
            comment.setChildren(replyToCommentList);
            if (!CollectionUtils.isEmpty(replyToCommentList)) {
                getTree(replyToCommentList, allReplyList);
            }
        }
        return commentList;
    }
    
    public void getTree(List<Reply> replyList, List<Reply> allReplyList) {
        for (Reply reply : replyList) {
            Long replyId = reply.getId();
            List<Reply> replyToReplyList = allReplyList.stream().
                    filter(o -> o.getRelaId().equals(replyId) && o.getType().equals(2L)).collect(Collectors.toList());
            reply.setChildren(replyToReplyList);
            if (!CollectionUtils.isEmpty(replyToReplyList)) {
                getTree(replyToReplyList, allReplyList);
            }
        }
    }
}
