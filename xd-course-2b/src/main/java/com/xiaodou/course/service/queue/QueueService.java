package com.xiaodou.course.service.queue;

import org.springframework.stereotype.Service;

import com.xiaodou.course.model.forum.ForumModel;
import com.xiaodou.course.model.forum.ForumPraiseModel;
import com.xiaodou.queue.aliyun.worker.AliyunWorker;
import com.xiaodou.queue.client.AbstractMQClient;
import com.xiaodou.queue.client.AbstractMQClient.MessageBox;
import com.xiaodou.queue.client.IMQClient;
import com.xiaodou.queue.manager.DefaultMessageQueueManager;
import com.xiaodou.queue.model.ContainerParamModel;

@Service("queueService")
public class QueueService {

  public enum Message {
    UpdateCommentNumber, AddLearnRecord, RecordLearnTimeForDay, UpdatePraiseNumber, AddReaderNum, CaculateCourseWareScore, UpdateUserCourseHourProgress, UpdatePersonProductInfo
  }

  private ContainerParamModel queueContainerModel = new ContainerParamModel();
  {
    this.queueContainerModel.setScanPath("com.xiaodou.course");
  }
  private IMQClient m = new AbstractMQClient(null, queueContainerModel, AliyunWorker.class,
      DefaultMessageQueueManager.class);


  public void updateCommentNumber(String itemId) {
    m.sendMessage(Message.UpdateCommentNumber.toString(), itemId);
  }

  public void updatePraiseNumber(ForumPraiseModel praiseModel) {
    m.sendMessage(Message.UpdatePraiseNumber.toString(), praiseModel);
  }

  public void addReaderNum(ForumModel model) {
    m.sendMessage(Message.AddReaderNum.toString(), model);
  }

  public void sendMessageBox(MessageBox box) {
    m.sendMessage(box);
  }


}
