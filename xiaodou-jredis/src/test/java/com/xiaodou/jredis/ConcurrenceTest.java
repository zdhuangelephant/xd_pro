package com.xiaodou.jredis;

import redis.clients.jedis.JedisPoolConfig;

public class ConcurrenceTest {
	private long count = 10000L;
	private JRedisPool pool;
	
	public ConcurrenceTest(boolean on) {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(500);
        config.setMaxIdle(500);
        config.setMaxWaitMillis(1000 * 100);
        config.setTestOnBorrow(false);
        if (on) {
        	pool = new JRedisPool(config, SystemSetting.HOST_HA);        	
        } else {
        	pool = new JRedisPool(SystemSetting.HOST_HA);
        }
	}
	
	void testJRedis() {
		long startTime = System.currentTimeMillis();
		for (long i = 0; i < count; i++) {
			try {
				String key = "test" + i;
				String value = "aabbcc" + i;
				String res = pool.getJRedis().set(key, value);
				if (!res.equals("OK")) {
					System.out.println("set res is " + res + " not equals value " + value);
				}
				res = pool.getJRedis().get(key);
				if (!res.equals(value)) {
					System.out.println("get res is " + res + " not equals value " + value);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		long endTime = System.currentTimeMillis();
		System.out.println("JRedis HA exectute times " + count + " using " 
				+ (endTime - startTime) + "ms, avg time is " + (endTime - startTime)/count + "ms");
	}
	
	public void start() {
		int threadCount = 32;
		JRedisThread[] threads = new JRedisThread[threadCount];
		
		for (int i = 0; i < threadCount; i++) {
			threads[i] = new JRedisThread();
			threads[i].id = "" + i;
			threads[i].start();
		}
		
		for (JRedisThread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void dump() {
		pool.dumpRandomCount();
	}
	
	public class JRedisThread extends Thread {
		public String id;
		
		@Override
		public void run() {
			System.out.println("thread id " + id + " start");
			testJRedis();			
			System.out.println("thread id " + id + " end");
		}
	}
	
	
	public static void main(String[] args) {		
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ConcurrenceTest test = new ConcurrenceTest(true);
		test.start();
		test.dump();
	}
}
