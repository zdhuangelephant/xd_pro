package com.xiaodou.webfetch.task;

import java.util.List;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.webfetch.engine.SandBoxContext;
import com.xiaodou.webfetch.param.DependTaskGroup;
import com.xiaodou.webfetch.param.FetchTask;
import com.xiaodou.webfetch.service.QueueService;
import com.xiaodou.webfetch.service.QueueService.FlowTaskCommand;
import com.xiaodou.webfetch.service.QueueService.NotifyTaskCommand;
import com.xiaodou.webfetch.unique.Target;

/**
 * @name @see com.xiaodou.webfetch.task.NotifyTask.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月13日
 * @description 唤醒依赖任务流程
 * @version 1.0
 */
@HandlerMessage("NotifyTask")
public class NotifyTask extends AbstractDefaultWorker {

  /** serialVersionUID */
  private static final long serialVersionUID = -7663312950949680414L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback)
      throws Exception {
    NotifyTaskCommand command =
        FastJsonUtil.fromJson(message.getMessageBodyJson(), NotifyTaskCommand.class);
    SandBoxContext sandBox = SandBoxContext.getSandBox(command.getSandBoxId());
    DependTaskGroup taskGroup = sandBox.getTargetTaskGroup(new Target(command.getAction()));
    if (null != taskGroup && null != taskGroup.getTaskList() && !taskGroup.getTaskList().isEmpty()) {
      for (FetchTask task : taskGroup.getTaskList()) {
        QueueService.flowTask(new FlowTaskCommand(command.getSandBoxId(), task));
      }
    }
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback)
      throws Exception {}

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {
    LoggerUtil.error(String.format("唤醒依赖任务流程失败。[%s]", message.getMessageBodyJson()), t);
  }

}
