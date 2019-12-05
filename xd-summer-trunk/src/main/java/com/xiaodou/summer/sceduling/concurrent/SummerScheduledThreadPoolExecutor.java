package com.xiaodou.summer.sceduling.concurrent;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;


public class SummerScheduledThreadPoolExecutor extends ScheduledThreadPoolExecutor {

  public SummerScheduledThreadPoolExecutor(int corePoolSize, ThreadFactory threadFactory,
      RejectedExecutionHandler handler) {
    super(corePoolSize, threadFactory, handler);
  }

  public SummerScheduledThreadPoolExecutor(int corePoolSize, ThreadFactory threadFactory) {
    super(corePoolSize, threadFactory, new SummerRejectedExecutionHandler());
  }
  
}
