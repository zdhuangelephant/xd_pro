package com.xiaodou.queue.manager;

import java.util.Queue;
import java.util.UUID;

import com.google.common.collect.Queues;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.model.ContainerParamModel;

/**
 * 消息队列管理
 * 
 * @author bing.cheng
 * 
 */
public class DefaultMessageQueueManager extends AbstractMessageQueueManager {

  /**
   * 构造函数
   * 
   * @param msQueue
   * @param queueContainerModel
   */
  public DefaultMessageQueueManager(Queue<DefaultMessage> msQueue,
      ContainerParamModel queueContainerModel) {
    super(msQueue, queueContainerModel);
  }

  @Override
  public Queue<UUID> initDependencyQueue() {
    return Queues.newArrayBlockingQueue(20);
  }


}
