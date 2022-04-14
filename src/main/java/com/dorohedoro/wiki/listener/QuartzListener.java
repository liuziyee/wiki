package com.dorohedoro.wiki.listener;

import com.dorohedoro.wiki.job.CommentStatJob;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Description
 * @Author liuziye
 * @Date 2022/4/14 16:03
 */
@Component
@Slf4j
public class QuartzListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private Scheduler scheduler;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        initCommentStatJob();
    }
    
    public void initCommentStatJob() {
        try {
            String id = CommentStatJob.ID;
            JobDetail commentStatJob = JobBuilder.newJob(CommentStatJob.class)
                    .withIdentity(id)
                    .build();
            CronTrigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity(id)
                    .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
                    .build();
            scheduler.scheduleJob(commentStatJob, trigger);
            scheduler.start();
        } catch (SchedulerException e) {
            log.info("something goes wrong when calling scheduleJob()");
        }

    }
}
