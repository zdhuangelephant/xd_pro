package com.xiaodou.queue.manager;

import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.enums.CallBackStatus;
import com.xiaodou.queue.manager.DefaultMessageQueueObserver.MultipleWorkHandler;
import com.xiaodou.queue.manager.DefaultMessageQueueObserver.MultipleWorker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.summer.sceduling.concurrent.SummerScheduledTask;

/**
 * 消息消费执行者
 * 
 * @author bing.cheng
 * 
 */
class DefaultMQWorkerManager extends SummerScheduledTask {

  /** 业务逻辑实现执行者 */
  private MultipleWorker _workerList;

  /** _messageHandler 指定消息监听者 */
  private MultipleWorkHandler _messageHandler = new MultipleWorkHandler();

  public MultipleWorkHandler get_messageHandler() {
    return _messageHandler;
  }

  public void set_messageHandler(MultipleWorkHandler _messageHandler) {
    this._messageHandler = _messageHandler;
  }

  AbstractMessageQueueManager _messageQueueManager;

  /**
   * 构造函数，入参为具体执行业务逻辑
   * 
   * @param worker
   * @param messageQueueManager
   */
  public DefaultMQWorkerManager(AbstractDefaultWorker worker,
      AbstractMessageQueueManager messageQueueManager, ScheduledThreadPoolExecutor scheduler) {
    super(scheduler);
    this._workerList = new MultipleWorker(worker);
    this._messageQueueManager = messageQueueManager;
  }

  protected volatile boolean _alive = true;

  public void cancel() {
    this._alive = false;
  }

  public List<AbstractDefaultWorker> get_worker() {
    return _workerList;
  }

  public void set_worker(MultipleWorker _workerList) {
    this._workerList = _workerList;
  }

  @Override
  public void onException(Throwable t) {
    t.printStackTrace();
    LoggerUtil.error("MQWorkerManager调度非逻辑异常.", t);
  }

  @Override
  public void doMain() {
    if (_alive) {
      DefaultMessage message;
      try {
        while (_alive && (message = _messageQueueManager.getAndRemoveQueueMessage()) != null) {
          getWorker(message.getMessageName()).excute(message, new IMQCallBacker<DefaultMessage>() {
            @Override
            public void onSuccess(DefaultMessage message) {
              _messageQueueManager.callBack(CallBackStatus.SUCCESS, message);
            }

            @Override
            public void onFail(CallBackStatus staus, DefaultMessage message) {
              _messageQueueManager.callBack(staus, message);
            }
          });
          message.callBack();
        }
      } catch (Exception e) {
        onException(e);
      }
    } else {
      this.cancel();
    }
  }

  /**
   * worker路由器,根据消息名路由执行者
   * 
   * @param messageName
   * @return
   */
  protected MultipleWorker getWorker(String messageName) {
    if (_messageHandler.containsKey(messageName)) {
      return _messageHandler.get(messageName);
    }
    return _workerList;
  }

}
