package com.xiaodou.jmsg.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.jmsg.entity.sqlite.FailSendMessage;
import com.xiaodou.jmsg.prpo.ConfigProp;
import com.xiaodou.jmsg.prpo.SqliteProp;
import com.xiaodou.jmsg.service.facade.SqliteServiceFacade;
import com.xiaodou.summer.util.SpringWebContextHolder;
public class FailSendMessageService {
	
	public static void saveFailSendMessage(FailSendMessage message){
		SqliteServiceFacade sqliteServiceFacade = SpringWebContextHolder
				.getBean("sqliteServiceFacade");
		try{
			FailSendMessage failSendMessage=sqliteServiceFacade.getFailSendMessageById(message.getCustomTag());
			if(failSendMessage==null){			
				sqliteServiceFacade.addFailSendMessage(message);
			}
		}catch(Exception e){
			LoggerUtil.error("查询失败:", e);
		}
	}
	
	public static void reSendFailSendMessage(FailSendMessage message){
		SqliteServiceFacade sqliteServiceFacade = SpringWebContextHolder
				.getBean("sqliteServiceFacade");
		sqliteServiceFacade.addFailSendMessage(message);
	}
	
	public static void createTable(){
	    Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName(SqliteProp.getParams("jmsg_client_sqlite_jdbc.driverClassName"));
	      String url=SqliteProp.getParams("jmsg_client_sqlite_jdbc.url")+ConfigProp.getParams("config.projectName")+SqliteProp.getParams("jmsg_client_sqlite_jdbc.dbName");
	      c = DriverManager.getConnection(url);
	      stmt = c.createStatement();
	      String sql = "create table fail_send_message(custom_tag varchar(30) PRIMARY KEY,message varchar(50000),message_name varchar(500),re_send varchar(20),create_time timestamp,update_time timestamp);";
	      stmt.executeUpdate(sql);
	      stmt.close();
	      c.close();
	    } catch (Exception e) {
	    	LoggerUtil.error("建表失败", e);
	    }
	}
}
