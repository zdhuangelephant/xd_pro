package com.xiaodou.common.service;

import org.springframework.stereotype.Service;

import com.xiaodou.common.worker.SmsCheckCodeHandler;
import com.xiaodou.queue.client.AbstractMQClient;
import com.xiaodou.queue.client.IMQClient;
import com.xiaodou.queue.manager.DefaultMessageQueueManager;

/**
 * @name @see com.xiaodou.common.service.QueueService.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月22日
 * @description 异步消息队列
 * @version 1.0
 */
@Service("queueService")
public class QueueService {

  public enum Message {
    sms_checkcode, sms_notice, pm_notice
  }

  private IMQClient client = new AbstractMQClient(SmsCheckCodeHandler.class,
      DefaultMessageQueueManager.class);

  public void addAliyunMessage(Message messageName, Object message) {
    client.sendMessage(messageName.toString(), message);
  }

}
