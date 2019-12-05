package com.xiaodou.forum.service.queue;

import org.springframework.stereotype.Service;

import com.xiaodou.forum.model.forum.ForumPraiseModel;
import com.xiaodou.forum.model.forum.ForumRelateInfoModel;
import com.xiaodou.forum.model.forum.RelateInfoUserModel;
import com.xiaodou.forum.request.forum.ForumRequest;
import com.xiaodou.queue.client.AbstractMQClient;
import com.xiaodou.queue.client.IMQClient;
import com.xiaodou.queue.manager.DefaultMessageQueueManager;
import com.xiaodou.queue.worker.AliyunWorker;

@Service("queueService")
public class QueueService {

  private enum Message {
    UpdateCommentUnmber, UpdatePartakeNumber, UpdatePraiseNumber, UpdateForumCategoryNumber, CommentPushTask, UpdateRelateInfo
  }

  private IMQClient m = new AbstractMQClient(AliyunWorker.class, DefaultMessageQueueManager.class);

  public void updateCommentUnmber(String forumId) {
    m.sendMessage(Message.UpdateCommentUnmber.toString(), forumId);
  }

  public void updatePartakeNumber(String forumCategoryId) {
    m.sendMessage(Message.UpdatePartakeNumber.toString(), forumCategoryId);
  }

  public void updatePraiseNumber(ForumPraiseModel praiseModel) {
    m.sendMessage(Message.UpdatePraiseNumber.toString(), praiseModel);
  }

  public void updateForumCategoryNumber(ForumRequest forumRequest) {
    m.sendMessage(Message.UpdateForumCategoryNumber.toString(), forumRequest);
  }

  public void addAliyunMessage(String messageName, Object message) {
    m.sendMessage(messageName, message);
  }

  public void addPushTask(RelateInfoUserModel relateInfoUserModel) {
    m.sendMessage(Message.CommentPushTask.toString(), relateInfoUserModel);
  }

  public void updateRelateInfo(ForumRelateInfoModel relateInfoModel) {
    m.sendMessage(Message.UpdateRelateInfo.toString(), relateInfoModel);
  }
}
