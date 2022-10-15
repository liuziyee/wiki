package com.dorohedoro.wiki.config;

import com.dorohedoro.wiki.util.RedisUtil;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class AutowireStaticInitializingSingleton implements SmartInitializingSingleton {
    @Autowired
    private AutowireCapableBeanFactory beanFactory;

    @Override
    public void afterSingletonsInstantiated() {
        beanFactory.autowireBean(new RedisUtil());
    }
}
