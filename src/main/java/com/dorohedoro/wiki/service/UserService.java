package com.dorohedoro.wiki.service;

import com.dorohedoro.wiki.bean.VO.PageBean;
import com.dorohedoro.wiki.bean.VO.UserVO;
import com.dorohedoro.wiki.bean.domain.User;
import com.dorohedoro.wiki.bean.domain.UserExample;
import com.dorohedoro.wiki.exception.BizException;
import com.dorohedoro.wiki.mapper.UserMapper;
import com.dorohedoro.wiki.mapper.UserMapper;
import com.dorohedoro.wiki.util.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @Description
 * @Author liuziye
 * @Date 2021/11/4 9:45c 
 */
@Service
@Slf4j
public class UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private HttpServletResponse response;

    public PageBean<UserVO> getUserList(User userBO) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        if (!StringUtils.isEmpty(userBO.getLoginName())) {
            criteria.andLoginNameLike("%" + userBO.getLoginName() + "%");
        }

        PageHelper.startPage(userBO.getPage(), userBO.getSize());
        List<User> userList = userMapper.selectByExample(userExample);
        List<UserVO> userVOList = BeanUtil.copyList(userList, UserVO.class);

        PageInfo<User> pageInfo = new PageInfo<>(userList);
        PageBean<UserVO> pageBean = new PageBean<>();
        pageBean.setList(userVOList);
        pageBean.setTotal(pageInfo.getTotal());
        return pageBean;
    }

    public void addOrUpdUser(User userBO){
        Long id = userBO.getId();
        if (id == null || id.equals(0L)) {
            //loginName判重
            String loginName = userBO.getLoginName();
            User user = new User();
            user.setLoginName(loginName);
            Long exist = userMapper.exist(user);
            log.info("exist: {}", exist);
            if (exist != null && !exist.equals(0L)) {
                throw new BizException(ResultCode.user_exists);
            }
            userBO.setId(IDGenerator.nextId());
            userMapper.insertSelective(userBO);
        } else {
            userMapper.updateByPrimaryKeySelective(userBO);
        }
    }
}

