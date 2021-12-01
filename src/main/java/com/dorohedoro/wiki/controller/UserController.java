package com.dorohedoro.wiki.controller;

import com.dorohedoro.wiki.bean.VO.UserVO;
import com.dorohedoro.wiki.bean.VO.PageBean;
import com.dorohedoro.wiki.bean.VO.ResponseBean;
import com.dorohedoro.wiki.bean.domain.User;
import com.dorohedoro.wiki.service.UserService;
import com.dorohedoro.wiki.util.ResCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

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


    @GetMapping("/all")
    public ResponseBean getUserList(@Valid User userBO) {
        PageBean<UserVO> pageBean = userService.getUserList(userBO);

        ResponseBean<PageBean> res = new ResponseBean<>();
        res.setCode(ResCode.success.getCode());
        res.setData(pageBean);
        return res;
    }

    @PostMapping("/addOrUpd")
    public ResponseBean addOrUpdUser(@RequestBody User userBO) {
        ResponseBean res = new ResponseBean();
        userService.addOrUpdUser(userBO);
        res.setCode(ResCode.success.getCode());
        return res;
    }

    @PostMapping("/login")
    public ResponseBean login(@RequestBody User userBO) {
        Map<String, Object> dataMap = userService.login(userBO);
        ResponseBean<UserVO> res = new ResponseBean();
        res.setCode(ResCode.success.getCode());
        res.setData((UserVO) dataMap.get("vo"));
        res.setToken((String) dataMap.get("token"));
        return res;
    }

    @GetMapping("/logout/{token}")
    public ResponseBean logout(@PathVariable Long token) {
        userService.logout(token);
        ResponseBean res = new ResponseBean();
        res.setCode(ResCode.success.getCode());
        return res;
    }
}
