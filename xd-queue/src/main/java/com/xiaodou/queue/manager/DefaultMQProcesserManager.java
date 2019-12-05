package com.xiaodou.queue.manager;

import java.util.concurrent.ScheduledThreadPoolExecutor;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.queue.processer.IProcesser;
import com.xiaodou.summer.sceduling.concurrent.SummerScheduledTask;

/**
 * @name @see com.xiaodou.queue.manager.DefaultMQProcesserManager.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月22日
 * @description 拉取消息消费主体管理者
 * @version 1.0
 */
public class DefaultMQProcesserManager<M> extends SummerScheduledTask {

  /** 业务逻辑实现执行者 */
  private IProcesser _processer;

  /**
   * 构造函数，入参为具体执行业务逻辑
   * 
   * @param worker
   * @param messageQueueManager
   */
  public DefaultMQProcesserManager(IProcesser _processer, ScheduledThreadPoolExecutor scheduler) {
    super(scheduler);
    this._processer = _processer;
  }

  protected volatile boolean _alive = true;

  public void cancel() {
    this._alive = false;
  }

  public IProcesser getProcesser() {
    return _processer;
  }

  public void setProcesser(IProcesser _processer) {
    this._processer = _processer;
  }

  @Override
  public void onException(Throwable t) {
    LoggerUtil.error("DefaultMQProcesserManager调度非逻辑异常.", t);
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
