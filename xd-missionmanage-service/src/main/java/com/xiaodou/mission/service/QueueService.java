package com.xiaodou.mission.service;

import org.springframework.stereotype.Service;

import com.xiaodou.mission.domain.UserTaskHitCompleteModel;
import com.xiaodou.mission.vo.model.UserMissionList;
import com.xiaodou.mission.vo.request.EventRequest;
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
    /** DelayTask 添加延迟执行任务 */
    DelayTask,
    /** AddMissionRecord 添加用户任务记录 */
    AddMissionRecord,
    /** UserTaskHitComplete 用户完成任务统计 */
    UserTaskHitComplete
  }

  private IMQClient m = new AbstractMQClient(AliyunWorker.class, DefaultMessageQueueManager.class);

  public void pushDelayTask(EventRequest request) {
    m.sendMessage(Message.DelayTask.toString(), request);
  }

  public void addMissionRecord(UserMissionList userMissionList) {
    m.sendMessage(Message.AddMissionRecord.toString(), userMissionList);
  }

//  public void pushUserTaskHitComplete(UserTaskHitCompleteModel model) {
//    m.sendMessage(Message.UserTaskHitComplete.toString(), model);
//  }

}
