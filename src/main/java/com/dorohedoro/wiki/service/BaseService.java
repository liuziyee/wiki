package com.dorohedoro.wiki.service;

import com.dorohedoro.wiki.bean.domain.User;
import com.dorohedoro.wiki.bean.domain.UserExample;
import com.dorohedoro.wiki.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BaseService {

    @Autowired
    private UserMapper userMapper;

    public List<User> getUserInfo(List<Long> uidList) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(uidList);
        List<User> userList = userMapper.selectByExample(userExample);
        return userList;
    }
}
