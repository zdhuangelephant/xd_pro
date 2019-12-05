package com.xiaodou.ms.queue;

import org.springframework.stereotype.Service;

import com.xiaodou.ms.model.course.CourseKeywordResourceModel;
import com.xiaodou.queue.aliyun.worker.AliyunWorker;
import com.xiaodou.queue.client.AbstractMQClient;
import com.xiaodou.queue.client.IMQClient;
import com.xiaodou.queue.manager.DefaultMessageQueueManager;
import com.xiaodou.queue.model.ContainerParamModel;

@Service("queueService")
public class QueueService {

  private enum Message {
    AddKeyworkResourceAndUpdateCourseResource
  }

  private ContainerParamModel queueContainerModel = new ContainerParamModel();
  {
    this.queueContainerModel.setScanPath("com.xiaodou.ms");
  }
  private IMQClient m = new AbstractMQClient(null, queueContainerModel, AliyunWorker.class,
      DefaultMessageQueueManager.class);


  public void addKeyworkResourceAndUpdateCourseResource(CourseKeywordResourceModel ckrm) {
    m.sendMessage(Message.AddKeyworkResourceAndUpdateCourseResource.toString(), ckrm);
  }


}
