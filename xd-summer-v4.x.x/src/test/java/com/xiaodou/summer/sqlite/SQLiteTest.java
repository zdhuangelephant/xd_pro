package com.xiaodou.summer.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;

import com.xiaodou.summer.BaseSpringTest;
import com.xiaodou.summer.sqlite.dao.UserDao;
import com.xiaodou.summer.sqlite.domain.User;

public class SQLiteTest extends BaseSpringTest {

  @Resource
  UserDao userDao;

  // @Before
  public void addRecord() {
    User user = new User();
    user.setId(1);
    user.setName("赵聃");
    user.setAge(28);
    userDao.addEntity(user);
  }

  @Test
  public void testOrigin() {
    Connection c = null;
    Statement stmt = null;
    try {
      Class.forName("org.sqlite.JDBC");
      c = DriverManager.getConnection("jdbc:sqlite:bin/sql.db");
      System.out
          .println("******************************testOrigin********************************");

      int count = 10000;
      long start = System.currentTimeMillis();
      stmt = c.createStatement();
      for (int i = 0; i < count; i++) {
        String sql = String.format("insert into xd_user1 values (%s, \"赵聃\", 28);", i);
        stmt.executeUpdate(sql);
      }
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

  @Test
  public void testMybatis() {
    System.out.println("******************************testMybatis********************************");
    int count = 10000;
    long start = System.currentTimeMillis();
    for (int i = 0; i < count; i++) {
      User user = new User();
      user.setId(i);
      user.setName("赵聃");
      user.setAge(28);
      userDao.addEntity(user);
    }
    long end = System.currentTimeMillis();
    long cost = end - start;
    cost = TimeUnit.MICROSECONDS.convert(cost, TimeUnit.MILLISECONDS);
    System.out.println(String.format("All cost %s us.", cost));
    System.out.println(String.format("Every cost %s us.", cost / count));
  }

  // @Test
  public void testRecord() {
    User user = new User();
    user.setId(1);
    user = userDao.findEntityById(user);
    Assert.assertTrue("测试记录失败", user.getName().equals("赵聃"));
  }

}
