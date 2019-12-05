package com.xiaodou.mooccrawler.service;

import org.springframework.stereotype.Service;

import com.xiaodou.queue.aliyun.worker.AliyunWorker;
import com.xiaodou.queue.client.AbstractMQClient;
import com.xiaodou.queue.client.AbstractMQClient.MessageBox;
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
    CreateCourse, CreateChapter, CreateItem, CreateResource
  }

  private IMQClient m = new AbstractMQClient(AliyunWorker.class, DefaultMessageQueueManager.class);

  public void sendMessageBox(MessageBox box) {
    m.sendMessage(box);
  }
}
