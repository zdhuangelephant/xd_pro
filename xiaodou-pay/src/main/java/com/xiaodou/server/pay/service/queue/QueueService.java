package com.xiaodou.server.pay.service.queue;

import org.springframework.stereotype.Service;

import com.xiaodou.queue.client.AbstractMQClient;
import com.xiaodou.queue.client.IMQClient;
import com.xiaodou.queue.manager.DefaultMessageQueueManager;
import com.xiaodou.queue.worker.AliyunWorker;
import com.xiaodou.server.pay.vo.CallbackBusinessPojo;

@Service("queueService")
public class QueueService {

  public enum Message {
    CallBackBusiness(""), RetryCallBackBusiness("");
    Message(String message) {
      this.message = message;
    }

    private String message;

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }

  }

  private IMQClient m = new AbstractMQClient(AliyunWorker.class, DefaultMessageQueueManager.class);

  public void callBackBusiness(CallbackBusinessPojo pojo) {
    m.sendMessage(Message.CallBackBusiness.name(), pojo);
  }

}
