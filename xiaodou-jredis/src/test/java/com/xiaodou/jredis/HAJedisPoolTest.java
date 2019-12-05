package com.xiaodou.jredis;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class HAJedisPoolTest {
  private static JRedisPool pool;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    pool = new JRedisPool(SystemSetting.HOST_HA);
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
    pool.destroy();
  }

  @Before
  public void setUp() throws Exception {}

  @After
  public void tearDown() throws Exception {}

  @Test
  public void testHAJedisPool() {}

  @Test
  public void testDestroy() {}

  @Test
  public void testGetJedis() {

  }

  @Test
  public void testSet() {
    try {
      String res = pool.getJRedis().set("test", "aabbcc");
      System.out.println("set result is " + res);
      assertEquals("OK", res);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testGet() {
    for (int i = 0; i < 1; i++) {
      String res = pool.getJRedis().get("test");
      System.out.println("get result is " + res);
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    String res = pool.getJRedis().get("test");
    System.out.println("get result is " + res);
    assertEquals("aabbcc", res);
  }

  @Test
  public void testHSet() {
    long res = pool.getJRedis().hset("testhash", "field1", "field1");
    System.out.println("hset result is " + res);
    assertEquals(0, res);
  }

  @Test
  public void testHGet() {
    String res = pool.getJRedis().hget("testhash", "field1");
    System.out.println("hget result is " + res);
    assertEquals("field1", res);
  }

  @Test
  public void testLPush() {
    pool.getJRedis().ltrim("testlist", 10, 11);
    long res = pool.getJRedis().lpush("testlist", "list1", "list2", "list3");
    System.out.println("lpush result is " + res);
    assertEquals(3, res);
  }

  @Test
  public void testLSet() {
    String res = pool.getJRedis().lset("testlist", 1, "list11");
    System.out.println("lset result is " + res);
    assertEquals("OK", res);
  }

  @Test
  public void testSAdd() {
    pool.getJRedis().spop("testsetadd");
    long res = pool.getJRedis().sadd("testsetadd", "set1");
    System.out.println("sadd result is " + res);
    assertEquals(1, res);
  }
}
