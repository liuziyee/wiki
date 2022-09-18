package com.dorohedoro.wiki.service;

import com.dorohedoro.wiki.bean.domain.User;
import com.dorohedoro.wiki.bean.domain.UserExample;
import com.dorohedoro.wiki.bean.vo.PageBean;
import com.dorohedoro.wiki.bean.vo.UserVO;
import com.dorohedoro.wiki.exception.BizException;
import com.dorohedoro.wiki.mapper.UserMapper;
import com.dorohedoro.wiki.util.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        userBO.setPassword(DigestUtils.md5DigestAsHex(userBO.getPassword().getBytes()));
        Long id = userBO.getId();
        if (id == null || id.equals(0L)) {
            String loginName = userBO.getLoginName();
            User user = new User();
            user.setLoginName(loginName);
            Long exist = userMapper.exist(user);
            log.info("exist: {}", exist);
            if (exist != null && !exist.equals(0L)) {
                throw new BizException(ResCode.user_exists);
            }
            userBO.setId(IDGenerator.nextId());
            userMapper.insertSelective(userBO);
        } else {
            userMapper.updateByPrimaryKeySelective(userBO);
        }
    }

    public Map<String, Object> login(User userBO) {
        String formPwd = DigestUtils.md5DigestAsHex(userBO.getPassword().getBytes());
        UserExample userExample = new UserExample();
        userExample.createCriteria().andLoginNameEqualTo(userBO.getLoginName());
        List<User> userList = userMapper.selectByExample(userExample);
        if (CollectionUtils.isEmpty(userList)) {
            throw new BizException(ResCode.login);
        }
        User user = userList.get(0);
        String pwd = user.getPassword();
        if (!formPwd.equals(pwd)) {
            throw new BizException(ResCode.login);
        }
        
        Map<String, Object> dataMap = new HashMap<>();
        UserVO userVO = BeanUtil.copy(user, UserVO.class);
        Long token = IDGenerator.nextId();
        String key = RedisUtil.getKey(RedisKey.token, token);
        RedisUtil.set(key, userVO, 3600L * 3);
        log.info("generate a SSO token:{} and put it in redis", token);
        dataMap.put("token", token.toString());
        dataMap.put("vo", userVO);
        return dataMap;
    }

    public void logout(Long token) {
        String key = RedisUtil.getKey(RedisKey.token, token);
        RedisUtil.del(key);
        log.info("delete a SSO token:{}", token);
    }
}

