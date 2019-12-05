package com.xiaodou.summer;

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
      c = DriverManager.getConnection("jdbc:sqlite:bin/test1.db");
      System.out.println("Opened database successfully");

      stmt = c.createStatement();
      {
        String sql =
            "CREATE TABLE xd_user " + "(ID INT PRIMARY KEY     NOT NULL,"
                + " NAME           TEXT    NOT NULL, " + " AGE            INT     NOT NULL)";
        stmt.executeUpdate(sql);
      }

      System.out.println("Create table successfully");

      int count = 100;

      System.out
          .println("******************************LocalCache start********************************");
      {
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
          DynamicLocalCache.getLocalCacheMap().put(Integer.toString(i),
              String.format("insert into xd_user values (%s, \"赵聃\", 28);", i));
        }
        long end = System.currentTimeMillis();
        long cost = end - start;
        cost = TimeUnit.MICROSECONDS.convert(cost, TimeUnit.MILLISECONDS);
        System.out.println(String.format("Every write cost %s us.", cost / count));
      }
      {
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
          DynamicLocalCache.getLocalCacheMap().get(Integer.toString(i));
        }
        long end = System.currentTimeMillis();
        long cost = end - start;
        cost = TimeUnit.MICROSECONDS.convert(cost, TimeUnit.MILLISECONDS);
        System.out.println(String.format("Every query cost %s us.", cost / count));
      }
      System.out
          .println("******************************LocalCache end********************************");


      System.out
          .println("******************************Origin start********************************");

      stmt = c.createStatement();
      {
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
          String sql = String.format("insert into xd_user values (%s, 'Paul', 28);", i);
          stmt.executeUpdate(sql);
        }
        long end = System.currentTimeMillis();
        long cost = end - start;
        cost = TimeUnit.MICROSECONDS.convert(cost, TimeUnit.MILLISECONDS);
        System.out.println(String.format("Every write cost %s us.", cost / count));
      }
      {
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
          String sql = String.format("select * from xd_user where id = %s;", i);
          stmt.executeUpdate(sql);
        }
        long end = System.currentTimeMillis();
        long cost = end - start;
        cost = TimeUnit.MICROSECONDS.convert(cost, TimeUnit.MILLISECONDS);
        System.out.println(String.format("Every query cost %s us.", cost / count));
      }
      System.out
          .println("******************************Origin end********************************");
      stmt.close();
      c.close();

      System.out
          .println("******************************Jedis start********************************");
      {
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
          JedisUtil.addHashMapToJedis("test", Integer.toString(i),
              String.format("insert into xd_user values (%s, 'Paul', 28);", i), 300);
        }
        long end = System.currentTimeMillis();
        long cost = end - start;
        cost = TimeUnit.MICROSECONDS.convert(cost, TimeUnit.MILLISECONDS);
        System.out.println(String.format("Every write cost %s us.", cost / count));
      }
      {
        long start = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
          JedisUtil.getHashMapValueFromJedis("test", Integer.toString(i));
        }
        long end = System.currentTimeMillis();
        long cost = end - start;
        cost = TimeUnit.MICROSECONDS.convert(cost, TimeUnit.MILLISECONDS);
        System.out.println(String.format("Every query cost %s us.", cost / count));
      }
      System.out.println("******************************Jedis end********************************");
    } catch (Exception e) {
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    }
  }
}
