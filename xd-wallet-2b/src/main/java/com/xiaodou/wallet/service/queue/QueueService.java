package com.xiaodou.wallet.service.queue;

import org.springframework.stereotype.Service;

@Service("queueService")
public class QueueService {

  public enum Message {
    NotifyBusiness, RetryNotifyBusiness;
  }

//  private IMQClient m = new AbstractMQClient(AliyunWorker.class, DefaultMessageQueueManager.class);



}
