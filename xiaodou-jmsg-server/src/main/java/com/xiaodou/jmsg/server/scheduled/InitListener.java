package com.xiaodou.jmsg.server.scheduled;  

import javax.servlet.ServletContextEvent;  
import javax.servlet.ServletContextListener;  

import com.xiaodou.jmsg.server.service.MessageBusService;


public class InitListener implements ServletContextListener {  
    public void contextDestroyed(ServletContextEvent e) {  
       
    }  

    public void contextInitialized(ServletContextEvent e) {  
    	 MessageBusService.createTable();
    	 ConfScheduledExecutor test = new ConfScheduledExecutor();  
         test.timer();
    }  
}  
 