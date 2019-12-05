package com.xiaodou.summer.sceduling.concurrent;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.xiaodou.summer.sceduling.concurrent.SummerTaskExecutor.SummerThreadFactoryBuilder;

public class SummerScheduleExcutor {
  static SummerThreadFactoryBuilder _summerThreadFactoryBuilder = new SummerThreadFactoryBuilder();
  {
    _summerThreadFactoryBuilder.setNameFormat("xd-queue-default");
    _summerThreadFactoryBuilder.setDaemon(false);
  }

  static ScheduledThreadPoolExecutor _scheduExec = new SummerScheduledThreadPoolExecutor(1,
      _summerThreadFactoryBuilder.build());

  public static void main(String[] args) {
    _scheduExec.scheduleWithFixedDelay(new SummerScheduledTask() {
      @Override
      public void onException(Throwable t) {
        System.out.println("错误被catch住了");
      }

      @SuppressWarnings("finally")
      @Override
      public void doMain() {
        System.out.println("############doMain##############");
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        } finally {
          throw new RuntimeException("出错了");
        }
      }
    }, 0, 50, TimeUnit.MILLISECONDS);
  }
}
