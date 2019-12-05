package com.xiaodou.dashboard.service.alarm;

import org.springframework.stereotype.Service;

import com.xiaodou.dashboard.vo.alarm.SendInfoVo;
import com.xiaodou.queue.client.AbstractMQClient;
import com.xiaodou.queue.client.IMQClient;
import com.xiaodou.queue.manager.DefaultMessageQueueManager;
import com.xiaodou.queue.model.ContainerParamModel;
import com.xiaodou.queue.worker.AliyunWorker;

/**
 * 异步消息队列
 * 
 * @author zhaodan
 * 
 */
@Service("queueService")
public class QueueService {

  public enum Message {
    SendMessage, SendMail, SendDing
  }

  private ContainerParamModel queueContainerModel = new ContainerParamModel();
  {
    queueContainerModel.setScanPath("com.xiaodou.dashboard");
  }
  private IMQClient m = new AbstractMQClient(null, queueContainerModel, AliyunWorker.class,
      DefaultMessageQueueManager.class);

  public void sendMessage(SendInfoVo pojo) {
    m.sendMessage(Message.SendMessage.toString(), pojo);
  }

  public void sendMail(SendInfoVo pojo) {
    m.sendMessage(Message.SendMail.toString(), pojo);
  }

  public void sendDing(SendInfoVo pojo) {
    m.sendMessage(Message.SendDing.toString(), pojo);
  }

}
