package com.xiaodou.jredis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class PerformanceTest {
	private final static int count = 10000;
	
	void testJRedis() {
		JRedisPool pool = new JRedisPool(SystemSetting.HOST_HA);
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < count; i++) {
			try {
				pool.getJRedis().set("test", "aabbcc" + i);
				pool.getJRedis().get("test");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		long endTime = System.currentTimeMillis();
		System.out.println("JRedis HA exectute times " + count + " using " 
				+ (endTime - startTime) + "ms, avg time is " + (endTime - startTime)/count + "ms");
	}
	
	void testJedis() {
		JedisPool pool = new JedisPool(SystemSetting.HOST_33);
		long startTime = System.currentTimeMillis();

		Jedis jedis;
		for (int i = 0; i < count; i++) {
			jedis = pool.getResource();
			jedis.set("test", "aabbcc" + i);
			jedis.get("test");
			pool.returnResource(jedis);
		}
		long endTime = System.currentTimeMillis();
		System.out.println("Jedis exectute times " + count + " using " 
				+ (endTime - startTime) + "ms, avg time is " + (endTime - startTime)/count + "ms");
	}
	
	public static void main(String[] args) {
		PerformanceTest test = new PerformanceTest();
		test.testJRedis();
		test.testJedis();
	}
}
