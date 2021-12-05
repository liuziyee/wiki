package com.dorohedoro.wiki.service;

import com.dorohedoro.wiki.bean.VO.CommentVO;
import com.dorohedoro.wiki.bean.VO.ReplyVO;
import com.dorohedoro.wiki.bean.domain.*;
import com.dorohedoro.wiki.mapper.CommentMapper;
import com.dorohedoro.wiki.mapper.ReplyMapper;
import com.dorohedoro.wiki.mapper.UserMapper;
import com.dorohedoro.wiki.util.AppEnum;
import com.dorohedoro.wiki.util.BeanUtil;
import com.dorohedoro.wiki.util.TimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author liuziye
 * @Date 2021/12/3 17:34
 */
@Service
public class CommentService extends BaseService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private ReplyMapper replyMapper;
    @Autowired
    private UserMapper userMapper;

    public List<CommentVO> getCommentTree(Long id) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andGoodsIdEqualTo(id);
        List<Comment> commentList = commentMapper.selectByExample(commentExample);
        if (CollectionUtils.isEmpty(commentList)) {
            return null;
        }
        List<Long> commentIdList = commentList.stream().map(Comment::getId).collect(Collectors.toList());

        ReplyExample replyExample = new ReplyExample();
        replyExample.createCriteria().andCommentIdIn(commentIdList);
        List<Reply> allReplyList = replyMapper.selectByExample(replyExample);

        List<CommentVO> commentVOList = BeanUtil.copyList(commentList, CommentVO.class);
        List<ReplyVO> allReplyVOList = BeanUtil.copyList(allReplyList, ReplyVO.class);
        //预先查出用户信息
        Map<Long, User> userMap = getUserMap(commentVOList, allReplyVOList);

        for (CommentVO comment : commentVOList) {
            Long commentId = comment.getId();

            User user = userMap.get(comment.getUserId());
            String label = String.join(" ", user.getName(), comment.getContent(), TimeUtil.getYMDHMS(comment.getCreateTime()));
            comment.setLabel(label);
            
            if (!CollectionUtils.isEmpty(allReplyVOList)) {
                List<ReplyVO> replyToCommentList = allReplyVOList.stream().
                        filter(o -> o.getRelaId().equals(commentId) && o.getType().equals(AppEnum.Reply.comment.v())).collect(Collectors.toList());
                comment.setChildren(replyToCommentList);
                if (!CollectionUtils.isEmpty(replyToCommentList)) {
                    getTree(replyToCommentList, allReplyVOList, userMap);
                }
            }
        }
        return commentVOList;
    }

    private Map<Long, User> getUserMap(List<CommentVO> commentVOList, List<ReplyVO> allReplyVOList) {
        Set<Long> commentUIDList = commentVOList.stream().map(CommentVO::getUserId).collect(Collectors.toSet());
        Set<Long> fromUIDList = allReplyVOList.stream().map(ReplyVO::getFromUid).collect(Collectors.toSet());
        Set<Long> toUIDList = allReplyVOList.stream().map(ReplyVO::getToUid).collect(Collectors.toSet());
        commentUIDList.addAll(fromUIDList);
        commentUIDList.addAll(toUIDList);
        List<User> userList = getUserInfo(new ArrayList<>(commentUIDList));
        Map<Long, User> userMap = userList.stream().collect(Collectors.toMap(User::getId, o -> o, (ov, nv) -> nv));
        return userMap;
    }

    public void getTree(List<ReplyVO> replyVOList, List<ReplyVO> allReplyVOList, Map<Long, User> userMap) {
        for (ReplyVO reply : replyVOList) {
            Long replyId = reply.getId();

            User fromUser = userMap.get(reply.getFromUid());
            User toUser = userMap.get(reply.getToUid());
            String label = String.join(" ", fromUser.getName() + "@" + toUser.getName(), 
                    reply.getContent(), TimeUtil.getYMDHMS(reply.getCreateTime()));
            reply.setLabel(label);
            
            List<ReplyVO> replyToReplyList = allReplyVOList.stream().
                    filter(o -> o.getRelaId().equals(replyId) && o.getType().equals(AppEnum.Reply.reply.v())).collect(Collectors.toList());
            reply.setChildren(replyToReplyList);
            if (!CollectionUtils.isEmpty(replyToReplyList)) {
                getTree(replyToReplyList, allReplyVOList, userMap);
            }
        }
    }
}
