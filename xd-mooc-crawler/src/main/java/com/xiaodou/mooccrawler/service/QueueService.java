package com.xiaodou.mooccrawler.service;

import org.springframework.stereotype.Service;

import com.xiaodou.mooccrawler.holder.TaskHolder.Task;
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
    AddTask, AddInfo, FinishResource
  }

  private IMQClient m = new AbstractMQClient(AliyunWorker.class, DefaultMessageQueueManager.class);

  public void addTask(Task task) {
    m.sendMessage(Message.AddTask.name(), task);
  }

  public void addInfo(Task task) {
    m.sendMessage(Message.AddInfo.name(), task);
  }

  public void finishResource(Task task) {
    m.sendMessage(Message.FinishResource.name(), task);
  }

}
