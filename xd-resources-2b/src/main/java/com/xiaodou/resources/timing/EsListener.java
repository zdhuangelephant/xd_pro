package com.xiaodou.resources.timing;  

import javax.servlet.ServletContextEvent;  
import javax.servlet.ServletContextListener;  


public class EsListener implements ServletContextListener {  
    public void contextDestroyed(ServletContextEvent e) {  
       
    }  

    public void contextInitialized(ServletContextEvent e) {  
    	 ScheduledExecutor test = new ScheduledExecutor();  
         test.timer();
    }  
}  
 