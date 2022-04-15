package com.dorohedoro.wiki.job;

import com.dorohedoro.wiki.service.GoodsService;
import com.dorohedoro.wiki.util.TimeUtil;
import com.dorohedoro.wiki.websocket.WSServer;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.Instant;

/**
 * @Description
 * @Author liuziye
 * @Date 2022/4/14 15:30
 */
@DisallowConcurrentExecution
@Slf4j
public class CommentStatJob extends QuartzJobBean {
    public static final String ID = "COMMENTSTAT";

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private WSServer wsServer;
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        //log.info("the quartz job is processing...");
        //try {
        //    TimeUnit.SECONDS.sleep(10);
        //} catch (InterruptedException e) {
        //    e.printStackTrace();
        //}
        
        wsServer.sendMsg(TimeUtil.getYMDHMS(Instant.now().toEpochMilli()), "");
    }
}
