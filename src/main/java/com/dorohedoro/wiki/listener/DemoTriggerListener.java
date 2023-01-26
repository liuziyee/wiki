package com.dorohedoro.wiki.listener;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.listeners.TriggerListenerSupport;

import java.time.LocalTime;
import java.util.Calendar;

@Slf4j
public class DemoTriggerListener extends TriggerListenerSupport {
    
    @Override
    public String getName() {
        return " ";
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext context) {
        log.info("触发器触发时间: {}", LocalTime.now());
    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
        Calendar now = Calendar.getInstance();
        int second = now.get(Calendar.SECOND);
        if (second % 10 == 5) {
            log.info("秒数为{},否决该次任务", second);
            return true;
        }
        return false;
    }
}
