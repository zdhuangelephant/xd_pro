package com.xiaodou.control.scheduled;  

import javax.servlet.ServletContextEvent;  
import javax.servlet.ServletContextListener;  


public class InitListener implements ServletContextListener {  
    public void contextDestroyed(ServletContextEvent e) {  
       
    }  

    public void contextInitialized(ServletContextEvent e) {  
    	 ScheduledExecutor test = new ScheduledExecutor();  
         test.timer();
    }  
}  
 