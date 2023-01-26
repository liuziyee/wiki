package com.dorohedoro.wiki.listener;

import com.dorohedoro.wiki.job.DemoJob;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class QuartzListener implements ApplicationListener<ContextRefreshedEvent> {
    
    @Autowired
    private Scheduler scheduler;
    
    @SneakyThrows
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        scheduler.clear();
        String id = DemoJob.ID;
        JobDetail job = JobBuilder.newJob(DemoJob.class)
                .storeDurably(true)
                .withIdentity(id)
                .build();
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
                .withIdentity(id)
                .build();
        scheduler.scheduleJob(job, trigger);
    }
}
