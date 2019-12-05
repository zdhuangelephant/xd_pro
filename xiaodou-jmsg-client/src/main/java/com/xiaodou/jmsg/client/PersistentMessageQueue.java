package com.xiaodou.jmsg.client;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.xiaodou.amqp.thread.RabbitDaemonThreadFactory;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.jmsg.client.task.ReadFileTask;
import com.xiaodou.jmsg.client.task.WriteFileTask;

/**
 * @author zwl
 * @since 2014年7月15日
 * 
 */
public class PersistentMessageQueue {

  private static PersistentMessageQueue mq = new PersistentMessageQueue();
  private static BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<String>();

  static {
    try {
      // 启动WriteFileTask
      Executors.newScheduledThreadPool(1, new RabbitDaemonThreadFactory("PersistenceWrite"))
          .scheduleWithFixedDelay(new WriteFileTask(), 0, 2, TimeUnit.SECONDS);
      // 启动ReadFileTask
      Executors.newScheduledThreadPool(1, new RabbitDaemonThreadFactory("PersistenceRead"))
          .scheduleWithFixedDelay(new ReadFileTask(), 0, 5, TimeUnit.SECONDS);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private PersistentMessageQueue() {};

  public static PersistentMessageQueue getInstance() {
    return mq;
  }

  public void putQueue(String msg) {
    try {
      blockingQueue.put(msg);
    } catch (InterruptedException e) {
      LoggerUtil.error("putQueue failed", e);
    }
  }

  public String consume() {
    try {
      if (blockingQueue.isEmpty()) {
        return null;
      }
      return blockingQueue.take();
    } catch (Exception e) {
      LoggerUtil.error("consume failed", e);
      return null;
    }
  }

  public static BlockingQueue<String> getBlockingQueue() {
    return blockingQueue;
  }

}
