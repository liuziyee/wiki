package com.dorohedoro.wiki;

import com.dorohedoro.wiki.bean.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class ReflectionTest {

    @Test
    public void getClazz() throws Exception {
        Class clazz = Class.forName("com.dorohedoro.wiki.bean.domain.User");

        clazz = User.class;

        User user = new User();
        clazz = user.getClass();

        ClassLoader clazzLoader = user.getClass().getClassLoader();
        clazz = clazzLoader.loadClass("com.dorohedoro.wiki.bean.domain.User");
    }

    @Test
    public void clazzApi() throws Exception {
        Class clazz = Class.forName("com.dorohedoro.wiki.bean.domain.User");

        log.info("toString: {}", clazz);
        log.info("runtime class: {}", clazz.getClass());
        log.info("package: {}", clazz.getPackage().getName());
        log.info("classFullPath: {}", clazz.getName());

        User user = (User) clazz.newInstance();
        Field id = clazz.getDeclaredField("id");
        id.setAccessible(true);
        id.set(user, 117L);
        log.info("id: {}", id.get(user));

        Field[] fields = clazz.getDeclaredFields();
        log.info("fields: {}", Arrays.stream(fields).map(o -> o.getName()).collect(Collectors.joining(",")));

        Class[] interfaces = clazz.getInterfaces();
        log.info("interfaces: {}", interfaces);

        Method setId = clazz.getMethod("setId", Long.class);
        setId.invoke(user, 117L);
        Method getId = clazz.getMethod("getId");
        Object uid = getId.invoke(user);
        log.info("runtime class: {}", uid.getClass());
    }
}
