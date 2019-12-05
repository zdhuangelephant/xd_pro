package com.xiaodou.webfetch.service;

import com.xiaodou.queue.aliyun.worker.AliyunWorker;
import com.xiaodou.queue.client.AbstractMQClient;
import com.xiaodou.queue.client.IMQClient;
import com.xiaodou.queue.manager.DefaultMessageQueueManager;
import com.xiaodou.webfetch.param.FetchTask;
import com.xiaodou.webfetch.unique.Target;

import lombok.Data;

/**
 * @name @see com.xiaodou.webfetch.service.QueueService.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月7日
 * @description 事件调度器
 * @version 1.0
 */
public class QueueService {

  /**
   * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
   * @description 消息类型枚举
   * @version 1.0
   */
  private enum Message {
    NotifyTask, NotifyAction, FlowTask
  }

  private static IMQClient m = new AbstractMQClient(AliyunWorker.class,
      DefaultMessageQueueManager.class);

  public static void notifyTask(NotifyTaskCommand command) {
    m.sendMessage(Message.NotifyTask.name(), command);
  }

  public static void notifyAction(NotifyActionCommand command) {
    m.sendMessage(Message.NotifyAction.name(), command);
  }
  
  public static void flowTask(FlowTaskCommand command) {
    m.sendMessage(Message.FlowTask.name(), command);
  }

  @Data
  public static class NotifyTaskCommand {
    /** sandBoxId 环境ID */
    private String sandBoxId;
    /** actionId 行为ID */
//    private Target action;
    private String action;
    public NotifyTaskCommand() {};
    public NotifyTaskCommand(String sandBoxId, String action) {
      this.sandBoxId = sandBoxId;
      this.action = action;
    }
  }

  @Data
  public static class NotifyActionCommand {
    /** sandBoxId 环境ID */
    private String sandBoxId;
    /** task 任务 */
    private FetchTask task;
    /** actionId 行为ID */
    private Target action;

    public NotifyActionCommand() {};
    
    public NotifyActionCommand(String sandBoxId, FetchTask task, Target action) {
      this.sandBoxId = sandBoxId;
      this.task = task;
      this.action = action;
    }
  }

  @Data
  public static class FlowTaskCommand {
    /** sandBoxId 环境ID */
    private String sandBoxId;
    /** task 任务 */
    private FetchTask task;
    
    public FlowTaskCommand() {};
    
    public FlowTaskCommand(String sandBoxId, FetchTask task) {
      this.sandBoxId = sandBoxId;
      this.task = task;
    }
  }

}
