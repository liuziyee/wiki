package com.dorohedoro.wiki.job;

import com.dorohedoro.wiki.util.TimeUtil;
import com.dorohedoro.wiki.websocket.WSServer;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.Instant;

public class DemoJob extends QuartzJobBean {
    
    public static final String ID = "demo";

    @Autowired
    private WSServer wsServer;
    
    @Override
    protected void executeInternal(JobExecutionContext context) {
        wsServer.broadcastMsg(TimeUtil.getYMDHMS(Instant.now().toEpochMilli()), "");
    }
}
