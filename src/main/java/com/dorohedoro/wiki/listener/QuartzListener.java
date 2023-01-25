package com.dorohedoro.wiki.listener;

import com.dorohedoro.wiki.job.TimestampJob;
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
    
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            String id = TimestampJob.ID;
            JobDetail commentStatJob = JobBuilder.newJob(TimestampJob.class)
                    .withIdentity(id)
                    .build();
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(id)
                    .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
                    .build();
            scheduler.scheduleJob(commentStatJob, trigger);
            scheduler.start();
        } catch (SchedulerException e) {
            log.error(e.getMessage(), e);
        }
    }
}
