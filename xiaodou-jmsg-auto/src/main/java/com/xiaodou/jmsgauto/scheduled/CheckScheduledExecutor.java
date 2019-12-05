package com.xiaodou.jmsgauto.scheduled;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CheckScheduledExecutor {  
    private  ScheduledExecutorService scheduExec;   
    public long start;  
      
    CheckScheduledExecutor(){  
        this.scheduExec =  Executors.newScheduledThreadPool(1);    
        this.start = System.currentTimeMillis();  
    }  
         
    public void timer(){  
        scheduExec.scheduleAtFixedRate(new Runnable() {  
            public void run() {
            	CheckTask.excute();
            }  
        },3,15,TimeUnit.MINUTES);  
    }  
}  