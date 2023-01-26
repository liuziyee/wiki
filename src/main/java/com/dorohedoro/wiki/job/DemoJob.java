package com.dorohedoro.wiki.job;

import com.dorohedoro.wiki.util.TimeUtil;
import com.dorohedoro.wiki.websocket.WSServer;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.Instant;

@Slf4j
public class DemoJob extends QuartzJobBean {
    
    public static final String ID = "demo";
    
    @Autowired
    private WSServer wsServer;
    
    @Override
    protected void executeInternal(JobExecutionContext context) {
        String now = TimeUtil.getYMDHMS(Instant.now().toEpochMilli());
        wsServer.broadcastMsg(now, "");
        log.info("任务执行时间: {}", now);
    }
}
