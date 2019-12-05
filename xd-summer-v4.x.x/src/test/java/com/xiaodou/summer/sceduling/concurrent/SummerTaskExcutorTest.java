package com.xiaodou.summer.sceduling.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.Resource;

import org.junit.Test;

import com.xiaodou.summer.BaseSpringTest;

public class SummerTaskExcutorTest extends BaseSpringTest {

  @Resource
  SummerTaskExecutor summerTaskExecutor;

  @Test
  public void testRun() throws InterruptedException {
    final AtomicInteger count = new AtomicInteger(0);
    for (int i = 0; i < 3; i++) {
      SummerTask task = new SummerTask(100) {
        @Override
        public void onException(Throwable t) {
          t.printStackTrace();
        }

        @Override
        public void run() {
          System.out.println(count.incrementAndGet());
        }
      };
      Thread.sleep(100);
      summerTaskExecutor.execute(task);
    }
    System.out.println(count.get());
  }

  @Test
  public void testException() throws InterruptedException {
    final AtomicInteger count = new AtomicInteger(0);
    for (int i = 0; i < 3; i++) {
      SummerTask task = new SummerTask(1) {
        @Override
        public void onException(Throwable t) {
          System.out.println("Catched EXCEPTION!");
        }

        @Override
        public void run() {
          int incrementAndGet = count.incrementAndGet();
          System.out.println(incrementAndGet);
          throw new RuntimeException("EXCEPTION!");
        }
      };
      summerTaskExecutor.execute(task);
    }
    Thread.sleep(5000);
    System.out.println(count.get());
  }
}
