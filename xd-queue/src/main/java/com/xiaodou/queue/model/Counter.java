package com.xiaodou.queue.model;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @name @see com.xiaodou.queue.model.Counter.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月22日
 * @description 计数器
 * @version 1.0
 */
public class Counter {

  private final AtomicInteger totalCount = new AtomicInteger(0);

  private final AtomicInteger failCount = new AtomicInteger(0);
  
  public Integer getTotalCount() {
    return totalCount.get();
  }

  public Integer getFailCount() {
    return failCount.get();
  }

  void onFail() {
    failCount.incrementAndGet();
  }

  void onExcute() {
    totalCount.incrementAndGet();
  }
}
