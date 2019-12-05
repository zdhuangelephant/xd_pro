package com.xiaodou.jmsgauto.scheduled;  

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.xiaodou.jmsgauto.service.MessageBusService;


public class InitListener implements ServletContextListener {  
    public void contextDestroyed(ServletContextEvent e) {  
       
    }  

    public void contextInitialized(ServletContextEvent e) {  
    	 MessageBusService.createTable();
         CheckScheduledExecutor test = new CheckScheduledExecutor();  
         test.timer();
    }  
}  
 