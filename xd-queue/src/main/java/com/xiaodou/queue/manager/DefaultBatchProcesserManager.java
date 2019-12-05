package com.xiaodou.queue.manager;

import java.util.concurrent.ScheduledThreadPoolExecutor;

import com.xiaodou.queue.processer.IProcesser;

/**
 * @name @see com.xiaodou.queue.manager.DefaultBatchProcesserManager.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月22日
 * @description 拉取消息批处理管理者
 * @version 1.0
 */
class DefaultBatchProcesserManager extends DefaultProcesserManager {

  /**
   * 构造函数，入参为具体执行业务逻辑
   * 
   * @param worker
   * @param messageQueueManager
   */
  public DefaultBatchProcesserManager(IProcesser processer, ScheduledThreadPoolExecutor scheduler) {
    super(processer, scheduler);
  }

  @Override
  public void doMain() {
    if (isAlive()) {
      try {
        getProcesser().processList();
      } catch (Exception e) {
        onException(e);
      }
    } else {
      this.cancel();
    }
  }

}
