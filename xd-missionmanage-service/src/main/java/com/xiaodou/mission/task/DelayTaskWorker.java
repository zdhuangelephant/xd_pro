package com.xiaodou.mission.task;

import java.util.List;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.mission.service.EventProcessService;
import com.xiaodou.mission.vo.request.EventRequest;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.summer.util.SpringWebContextHolder;

/**
 * @name @see com.xiaodou.mission.task.DelayTaskWorker.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年12月26日
 * @description 延迟任务执行Worker
 * @version 1.0
 */
@HandlerMessage("DelayTask")
public class DelayTaskWorker extends AbstractDefaultWorker {

  /** serialVersionUID */
  private static final long serialVersionUID = 5497429856772578203L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback)
      throws Exception {
    String messageBody = message.getMessageBodyJson();
    if (StringUtils.isJsonBlank(messageBody)) {
      return;
    }
    EventRequest request = FastJsonUtil.fromJson(messageBody, EventRequest.class);
    EventProcessService eventProcessService = SpringWebContextHolder.getBean("eventProcessService");
    eventProcessService.onEvent(request);
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {
    LoggerUtil.error("处理事件异常.", t);
  }

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {
    LoggerUtil.error("处理事件异常.", t);
  }

}
