package com.dorohedoro.wiki.config;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * @Description
 * @Author liuziye
 * @Date 2022/4/14 15:41
 */
@Configuration
public class QuartzConfig {
    @Autowired
    private JobFactory jobFactory;
    
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobFactory(jobFactory);
        return schedulerFactoryBean;
    }
    
    @Bean
    public Scheduler scheduler() {
        return schedulerFactoryBean().getScheduler();
    }
    
}
