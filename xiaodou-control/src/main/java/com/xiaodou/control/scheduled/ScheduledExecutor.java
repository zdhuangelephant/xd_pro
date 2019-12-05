package com.xiaodou.control.scheduled;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutor {  
    private  ScheduledExecutorService scheduExec;   
    public long start;  
      
    ScheduledExecutor(){  
        this.scheduExec =  Executors.newScheduledThreadPool(2);    
        this.start = System.currentTimeMillis();  
    }  
         
    public void timer(){  
        scheduExec.scheduleAtFixedRate(new Runnable() {  
            public void run() {
            	Task.excute();
            }  
        },10,60,TimeUnit.SECONDS);  
    }  
}  