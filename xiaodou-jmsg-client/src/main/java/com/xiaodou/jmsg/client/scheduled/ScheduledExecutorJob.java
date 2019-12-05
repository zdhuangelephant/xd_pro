package com.xiaodou.jmsg.client.scheduled;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.xiaodou.jmsg.service.FailSendMessageService;
@Component("scheduledExecutorJob")
public class ScheduledExecutorJob {  
    private  ScheduledExecutorService scheduExec;   
    public long start;  
      
    ScheduledExecutorJob(){  
        this.scheduExec =  Executors.newScheduledThreadPool(1);    
        this.start = System.currentTimeMillis();  
    }  
    @PostConstruct      
    public void timer(){ 
    	FailSendMessageService.createTable();
        scheduExec.scheduleAtFixedRate(new Runnable() {  
            public void run() {
            	Task.excute();
            }  
        },60,120,TimeUnit.SECONDS);  
    }  
}  