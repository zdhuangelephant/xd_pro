package com.xiaodou.ucenter;

import redis.clients.jedis.Jedis;

public class jedistest {

  public static void main(String[] args) {
    try {

      String host = "9b6384e52b0b4fe0.m.cnbja.kvstore.aliyuncs.com";// 控制台显示访问地址
      int port = 6379;
      Jedis jedis = new Jedis(host, port);
      // 鉴权信息由用户名:密码拼接而成
      jedis.auth("9b6384e52b0b4fe0:Mrz367Java");// instance_id:password
      String key = "redis";
      String value = "aliyun-redis";
      // select db默认为0
      jedis.select(1);
      // set一个key
      jedis.set(key, value);
      System.out.println("Set Key " + key + " Value: " + value);
      // get 设置进去的key
      String getvalue = jedis.get(key);
      System.out.println("Get Key " + key + " ReturnValue: " + getvalue);
      jedis.quit();
      jedis.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
