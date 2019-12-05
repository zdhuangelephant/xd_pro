package com.xiaodou.resources.task.quesbk;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.mission.engine.event.EventBuilder;
import com.xiaodou.mission.engine.event.WrongQuesCountEvent;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.resources.constant.quesbk.QuesBaseConstant;
import com.xiaodou.resources.service.quesbk.facade.QuesOperationFacade;
import com.xiaodou.resources.vo.task.RefreshCourseWrongQuesNum;
import com.xiaodou.summer.util.SpringWebContextHolder;

@HandlerMessage("RefreshCourseWrongQuesNum")
public class RefreshCourseWrongQuesNumWorker extends AbstractDefaultWorker {

  /** serialVersionUID */
  private static final long serialVersionUID = 1813142309009827148L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    RefreshCourseWrongQuesNum vo =
        FastJsonUtil.fromJson(message.getMessageBodyJson(), RefreshCourseWrongQuesNum.class);
    QuesOperationFacade quesOperationFacade = SpringWebContextHolder.getBean("quesOperationFacade");
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("userId", vo.getUserId());
    cond.put("module", vo.getModule());
    cond.put("courseId", vo.getCourseId());
    cond.put("wrongStatus", QuesBaseConstant.QUES_WRONG_RECORD_STATUS_UNCONTROL);
    Integer count = quesOperationFacade.countWrongRecord(cond);
    WrongQuesCountEvent event = EventBuilder.buildWrongQuesCountEvent();
    event.setCourseId(vo.getCourseId());
    event.setModule(vo.getModule());
    event.setUserId(vo.getUserId());
    event.setCount(count);
    event.send();
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {

  }

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {}

}
