package com.dorohedoro.wiki.controller;

import com.dorohedoro.wiki.bean.VO.CommentVO;
import com.dorohedoro.wiki.bean.VO.PageBean;
import com.dorohedoro.wiki.bean.VO.ResponseBean;
import com.dorohedoro.wiki.bean.domain.Comment;
import com.dorohedoro.wiki.bean.domain.Reply;
import com.dorohedoro.wiki.service.CommentService;
import com.dorohedoro.wiki.util.ResCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description
 * @Author liuziye
 * @Date 2021/12/3 17:23
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/all/{id}")
    public ResponseBean getCommentTree(@PathVariable Long id) {
        PageBean<CommentVO> pageBean = commentService.getCommentTree(id);
        ResponseBean<PageBean> res = new ResponseBean();
        res.setCode(ResCode.success.getCode());
        res.setData(pageBean);
        return res;
    }

    @GetMapping("/addComment")
    public ResponseBean addComment(HttpServletRequest request, Comment commentBO) {
        Long token = Long.valueOf(request.getHeader("token"));
        commentService.addComment(commentBO, token);
        ResponseBean res = new ResponseBean();
        res.setCode(ResCode.success.getCode());
        return res;
    }

    @PostMapping("/addReply")
    public ResponseBean addComment(HttpServletRequest request, @RequestBody Reply replyBO) {
        Long token = Long.valueOf(request.getHeader("token"));
        commentService.addReply(replyBO, token);
        ResponseBean res = new ResponseBean();
        res.setCode(ResCode.success.getCode());
        return res;
    }
    
    
}
