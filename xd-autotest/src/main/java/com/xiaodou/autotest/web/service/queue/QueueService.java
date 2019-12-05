package com.xiaodou.autotest.web.service.queue;

import org.springframework.stereotype.Service;

import com.xiaodou.autotest.core.model.Action;
import com.xiaodou.autotest.web.vo.SendInfoVo;
import com.xiaodou.queue.aliyun.worker.AliyunWorker;
import com.xiaodou.queue.client.AbstractMQClient;
import com.xiaodou.queue.client.IMQClient;
import com.xiaodou.queue.manager.DefaultMessageQueueManager;

/**
 * 异步记录错题队列
 * 
 * @author zhaodan
 * 
 */
@Service("queueService")
public class QueueService {

  public enum Message {
    /** ExecuteTask  */
    ExecuteTask,
    /** SendMail  */
    SendMail
  }

  private IMQClient m = new AbstractMQClient(AliyunWorker.class, DefaultMessageQueueManager.class);

  public void executeTask(Action action) {
    m.sendMessage(Message.ExecuteTask.toString(), action);
  }

  public void sendMail(SendInfoVo sendInfoVo) {
    m.sendMessage(Message.SendMail.toString(), sendInfoVo);
  }

}
