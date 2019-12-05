package com.xiaodou.jmsgserver;

import java.sql.*;
import java.util.concurrent.TimeUnit;

import com.xiaodou.common.cache.local.DynamicLocalCache;
import com.xiaodou.common.cache.redis.JedisUtil;

public class SQLiteJDBC {
  public static void main(String args[]) {
	  Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:db/jmsg.db");
	      System.out
	          .println("******************************testOrigin********************************");

	      int count = 10000;
	      long start = System.currentTimeMillis();
	      stmt = c.createStatement();
	      String sql = "create table fail_message(custom_tag varchar(30) PRIMARY KEY,message varchar(50000),unique_url varchar(500),message_state varchar(10),create_time timestamp,update_time timestamp);";
	      stmt.executeUpdate(sql);
	      long end = System.currentTimeMillis();
	      long cost = end - start;
	      cost = TimeUnit.MICROSECONDS.convert(cost, TimeUnit.MILLISECONDS);
	      System.out.println(String.format("All cost %s us.", cost));
	      System.out.println(String.format("Every cost %s us.", cost / count));
	      stmt.close();
	      c.close();
	    } catch (Exception e) {
	      System.err.println(e.getClass().getName() + ": " + e.getMessage());
	      System.exit(0);
	    }
  }
}
