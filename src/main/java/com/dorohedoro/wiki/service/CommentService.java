package com.dorohedoro.wiki.service;

import com.dorohedoro.wiki.bean.vo.CommentVO;
import com.dorohedoro.wiki.bean.vo.PageBean;
import com.dorohedoro.wiki.bean.vo.ReplyVO;
import com.dorohedoro.wiki.bean.vo.UserVO;
import com.dorohedoro.wiki.bean.domain.*;
import com.dorohedoro.wiki.exception.BizException;
import com.dorohedoro.wiki.mapper.CommentMapper;
import com.dorohedoro.wiki.mapper.GoodsMapper;
import com.dorohedoro.wiki.mapper.ReplyMapper;
import com.dorohedoro.wiki.mapper.UserMapper;
import com.dorohedoro.wiki.util.*;
import com.dorohedoro.wiki.websocket.WSServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author liuziye
 * @Date 2021/12/3 17:34
 */
@Service
@Slf4j
public class CommentService extends BaseService {

    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private ReplyMapper replyMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private WSServer wsServer;

    public PageBean<CommentVO> getCommentTree(Long id) {
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

        commentVOList.sort(Comparator.comparing(CommentVO::getCreateTime).reversed());
        PageBean<CommentVO> pageBean = new PageBean<>();
        pageBean.setList(commentVOList);
        int total = commentList.size() + allReplyList.size();
        pageBean.setTotal((long) total);
        return pageBean;
    }

    public void addComment(Comment commentBO, Long token) {
        String key = RedisUtil.getKey(RedisKey.token, token);
        UserVO user = (UserVO) RedisUtil.get(key);
        if (user == null) {
            throw new BizException(ResCode.token_expire);
        }
        
        Comment comment = BeanUtil.copy(commentBO, Comment.class);
        comment.setId(IDGenerator.nextId());
        comment.setUserId(user.getId());
        comment.setCreateTime(Instant.now().toEpochMilli());
        commentMapper.insertSelective(comment);

        Long goodsId = commentBO.getGoodsId();
        Goods goods = goodsMapper.selectByPrimaryKey(goodsId);
        wsServer.sendMsg(String.join("&", user.getName(), goods.getName()), token.toString());
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
            String label = String.join(" ", fromUser.getName() + " 回复 " + toUser.getName(), 
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

    public void addReply(Reply replyBO, Long token) {
        Reply reply = BeanUtil.copy(replyBO, Reply.class);
        String key = RedisUtil.getKey(RedisKey.token, token);
        UserVO user = (UserVO) RedisUtil.get(key);
        reply.setId(IDGenerator.nextId());
        reply.setFromUid(user.getId());
        reply.setCreateTime(Instant.now().toEpochMilli());
        replyMapper.insertSelective(reply);
    }
}
