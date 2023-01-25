package com.dorohedoro.wiki.config;

import com.dorohedoro.wiki.job.DemoJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {
    
    @Bean
    public JobDetail job() {
        return JobBuilder.newJob(DemoJob.class)
                .storeDurably(true)
                .withIdentity(DemoJob.ID)
                .build();
    }
    
    @Bean
    public Trigger trigger() {
        return TriggerBuilder.newTrigger()
                .forJob(job())
                .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?"))
                .withIdentity(DemoJob.ID)
                .build();
    }
}
