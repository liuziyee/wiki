package com.dorohedoro.wiki;

import com.dorohedoro.wiki.bean.domain.User;
import com.dorohedoro.wiki.mapper.CommentMapper;
import com.dorohedoro.wiki.mapper.UserMapper;
import com.dorohedoro.wiki.util.IDGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;


@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class MybatisTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Test
    public void lock() throws Exception {
        userMapper.writeLock();
        TimeUnit.MINUTES.sleep(5);

        User user = new User();
        user.setId(IDGenerator.nextId());
        user.setLoginName("jiaozi");
        user.setName("jiaozi");
        user.setPassword("1994");
        userMapper.insertSelective(user);
        
        userMapper.unlock();
    }
    
    @Test
    public void limit() {
        log.info("{}", userMapper.limit(0, 10));
    }

    @Test
    public void orderBy() {
        log.info("{}", commentMapper.orderBy("create_time"));
    }
}
