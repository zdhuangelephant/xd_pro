package com.xiaodou.jmsg.server.service;

import org.springframework.stereotype.Service;

import com.xiaodou.jmsg.entity.DefaultMessage;
import com.xiaodou.jmsg.server.task.DefaultWorker;
import com.xiaodou.queue.client.AbstractMQClient;
import com.xiaodou.queue.client.IMQClient;
import com.xiaodou.queue.manager.DefaultMessageQueueManager;

/**
 * 
 * 
 * @author zhaodan
 * 
 */
@Service("queueService")
public class QueueService {

  public enum Message {
	  DoReceive,DoAutoReceive
  }

  private IMQClient m = new AbstractMQClient(DefaultWorker.class, DefaultMessageQueueManager.class);

  public void doReceive(String url) {
    m.sendMessage(Message.DoReceive.name(), url);
  }
  public void doAutoReceive(DefaultMessage o) {
	    m.sendMessage(Message.DoAutoReceive.name(), o);
 }
  

}
