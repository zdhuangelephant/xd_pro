package com.xiaodou.summer.sceduling.concurrent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.summer.exception.SummerTaskExecuteException;

/**
 * @Location xd-summer/com.xiaodou.summer.sceduling.concurrent.SummerThreadPoolExecutor.java
 * @Description SummerThreadPoolExcutor,重写了beforeExecute方法和afterExecute方法
 * @Author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @Date 上午2:36:09
 */
public class SummerThreadPoolExecutor extends ThreadPoolExecutor {

  public SummerThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime,
      TimeUnit unit, BlockingQueue<Runnable> workQueue) {
    super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
  }

  public SummerThreadPoolExecutor(int corePoolSize, int maxPoolSize, int keepAliveSeconds,
      TimeUnit seconds, BlockingQueue<Runnable> queue, ThreadFactory build,
      RejectedExecutionHandler rejectedExecutionHandler) {
    super(corePoolSize, maxPoolSize, keepAliveSeconds, seconds, queue, build,
        rejectedExecutionHandler);
  }

  @Override
  public void execute(Runnable command) {
    if (command instanceof SummerTask) {
      if (!((SummerTask) command).check()) return;
    }
    super.execute(command);
  }

  @Override
  protected void afterExecute(Runnable r, Throwable t) {
    if (t != null) {
      if (r instanceof ISummerTask) {
        ((ISummerTask) r).onException(t);
      } else {
        LoggerUtil.error("summer task execute exception",
            new SummerTaskExecuteException(Thread.currentThread(), t));
      }
    }
    super.afterExecute(r, t);
  }

}
 