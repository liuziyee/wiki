package com.dorohedoro.wiki.controller;

import com.dorohedoro.wiki.bean.VO.UserVO;
import com.dorohedoro.wiki.bean.VO.PageBean;
import com.dorohedoro.wiki.bean.VO.ResponseBean;
import com.dorohedoro.wiki.bean.domain.User;
import com.dorohedoro.wiki.service.UserService;
import com.dorohedoro.wiki.service.UserService;
import com.dorohedoro.wiki.util.AppEnum;
import com.dorohedoro.wiki.util.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;

/**
 * @Description
 * @Author liuziye
 * @Date 2021/11/4 9:45
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    

    @GetMapping("/list")
    public ResponseBean getUserList(@Valid User userBO) {
        PageBean<UserVO> pageBean = userService.getUserList(userBO);

        ResponseBean<PageBean> res = new ResponseBean<>();
        res.setCode(ResultCode.success.getCode());
        res.setData(pageBean);
        res.setServerTime(Instant.now().toEpochMilli());
        return res;
    }

    @PostMapping("/addOrUpd")
    public ResponseBean addOrUpdUser(@RequestBody User userBO) {
        ResponseBean res = new ResponseBean();
        userService.addOrUpdUser(userBO);
        res.setCode(ResultCode.success.getCode());
        return res;
    }

    @PostMapping("/login")
    public ResponseBean login(@RequestBody User userBO) {
        UserVO userVO = userService.login(userBO);
        ResponseBean res = new ResponseBean();
        res.setCode(ResultCode.success.getCode());
        return res;
    }
}
