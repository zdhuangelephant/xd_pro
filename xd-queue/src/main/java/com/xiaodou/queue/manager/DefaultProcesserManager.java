package com.xiaodou.queue.manager;

import java.util.concurrent.ScheduledThreadPoolExecutor;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.queue.processer.IProcesser;
import com.xiaodou.summer.sceduling.concurrent.SummerScheduledTask;

/**
 * @name @see com.xiaodou.queue.manager.DefaultProcesserManager.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月22日
 * @description 拉取消息处理管理者
 * @version 1.0
 */
class DefaultProcesserManager extends SummerScheduledTask {

  /** 业务逻辑实现执行者 */
  private IProcesser _processer;

  /** _alive 存活状态 */
  private volatile boolean _alive = true;

  protected IProcesser getProcesser() {
    return _processer;
  }

  protected boolean isAlive() {
    return _alive;
  }

  /**
   * 构造函数，入参为具体执行业务逻辑
   * 
   * @param worker
   * @param messageQueueManager
   */
  public DefaultProcesserManager(IProcesser processer, ScheduledThreadPoolExecutor scheduler) {
    super(scheduler);
    this._processer = processer;
  }

  public void cancel() {
    this._alive = false;
  }

  @Override
  public void onException(Throwable t) {
    LoggerUtil.error("DefaultProcesserManager调度非逻辑异常.", t);
  }

  @Override
  public void doMain() {
    if (_alive) {
      try {
        _processer.process();
      } catch (Exception e) {
        onException(e);
      }
    } else {
      this.cancel();
    }
  }

}
