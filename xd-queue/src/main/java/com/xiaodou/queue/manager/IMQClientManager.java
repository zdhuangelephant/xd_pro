package com.xiaodou.queue.manager;

import com.xiaodou.queue.enums.CallBackStatus;
import com.xiaodou.queue.message.DefaultMessage;

/**
 * @name @see com.xiaodou.queue.manager.IMessageQueueManager.java
 * @CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年7月22日
 * @description 消息队列客户端管理者接口:定义管理者行为规范
 * @version 1.0
 * @waste
 */
public interface IMQClientManager {

  /**
   * 插入新消息
   * 
   * @param message
   */
  public void addMessage(DefaultMessage message);

  /**
   * 回调消息执行结果
   * 
   * @param message
   */
  public void callBack(CallBackStatus status, DefaultMessage message);
}
