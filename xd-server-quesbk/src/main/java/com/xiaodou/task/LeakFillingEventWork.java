package com.xiaodou.task;

import java.util.List;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.mission.engine.event.LeakFillingEvent;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;

@HandlerMessage("LeakFillingEvent")
public class LeakFillingEventWork extends AbstractDefaultWorker {

  /** serialVersionUID */
  private static final long serialVersionUID = 5497429856772578203L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    LeakFillingEvent tollgateEvent =
        FastJsonUtil.fromJson(message.getMessageBodyJson(), LeakFillingEvent.class);
    if (null != tollgateEvent) {
      tollgateEvent.send();
    }
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {
    LoggerUtil.error("发送任务系统查漏补缺事件异常.", t);
  }

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {
    LoggerUtil.error("发送任务系统查漏补缺事件异常.", t);
  }

}
