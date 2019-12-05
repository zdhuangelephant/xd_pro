package com.xiaodou.resources.timing;

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
            	ResourcesScheduledTask.excute();
            }  
        },3*60,5*60,TimeUnit.SECONDS);  
    }  
}  