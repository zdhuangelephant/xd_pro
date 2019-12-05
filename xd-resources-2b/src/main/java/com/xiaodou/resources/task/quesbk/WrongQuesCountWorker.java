package com.xiaodou.resources.task.quesbk;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.mission.engine.event.EventBuilder;
import com.xiaodou.mission.engine.event.WrongQuesCountEvent;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.resources.constant.quesbk.QuesBaseConstant;
import com.xiaodou.resources.model.quesbk.UserWrongRecord;
import com.xiaodou.resources.service.quesbk.facade.QuesOperationFacade;
import com.xiaodou.resources.vo.task.WrongQuesCountVo;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.util.SpringWebContextHolder;

@HandlerMessage("WrongQuesCount")
public class WrongQuesCountWorker extends AbstractDefaultWorker {

  /** serialVersionUID */
  private static final long serialVersionUID = 1381672978949693425L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    if (StringUtils.isJsonBlank(message.getMessageBodyJson())) return;
    WrongQuesCountVo vo =
        FastJsonUtil.fromJson(message.getMessageBodyJson(), WrongQuesCountVo.class);
    QuesOperationFacade quesOperationFacade = SpringWebContextHolder.getBean("quesOperationFacade");
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("userId", vo.getUserId());
    cond.put("courseId", vo.getCourseId());
    Page<UserWrongRecord> userTotalList =
        quesOperationFacade.queryAbstractWrongRecordList(cond, null);
    Integer unCatchWrongQues = 0;
    if (userTotalList != null && userTotalList.getResult() != null
        && userTotalList.getResult().size() > 0) {
      for (UserWrongRecord wrongRecord : userTotalList.getResult()) {
        if (QuesBaseConstant.QUES_WRONG_RECORD_STATUS_UNCONTROL
            .equals(wrongRecord.getWrongStatus())) unCatchWrongQues++;
      }
    }
    WrongQuesCountEvent event = EventBuilder.buildWrongQuesCountEvent();
    event.setUserId(vo.getUserId());
    event.setModule(vo.getModule());
    event.setCourseId(vo.getCourseId());
    event.setCount(unCatchWrongQues);
    event.send();
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {}


}
