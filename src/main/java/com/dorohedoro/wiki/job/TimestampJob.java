package com.dorohedoro.wiki.job;

import com.dorohedoro.wiki.util.TimeUtil;
import com.dorohedoro.wiki.websocket.WSServer;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.Instant;

@Slf4j
public class TimestampJob extends QuartzJobBean {
    
    public static final String ID = "timestamp";

    @Autowired
    private WSServer wsServer;
    
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        wsServer.broadcastMsg(TimeUtil.getYMDHMS(Instant.now().toEpochMilli()), "");
    }
}
