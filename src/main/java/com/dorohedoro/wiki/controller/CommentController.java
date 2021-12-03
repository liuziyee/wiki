package com.dorohedoro.wiki.controller;

import com.dorohedoro.wiki.bean.VO.ResponseBean;
import com.dorohedoro.wiki.bean.domain.Comment;
import com.dorohedoro.wiki.service.CommentService;
import com.dorohedoro.wiki.util.ResCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        List<Comment> commentTree = commentService.getCommentTree(id);
        ResponseBean<List<Comment>> res = new ResponseBean();
        res.setCode(ResCode.success.getCode());
        res.setData(commentTree);
        return res;
    }
}
