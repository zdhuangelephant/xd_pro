package com.xiaodou.task;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DuplicateKeyException;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.constant.QuesBaseConstant;
import com.xiaodou.domain.behavior.UserExamTotal;
import com.xiaodou.domain.behavior.UserWrongRecord;
import com.xiaodou.manager.facade.QuesOperationFacade;
import com.xiaodou.mission.engine.event.EventBuilder;
import com.xiaodou.mission.engine.event.WrongQuesCountEvent;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.util.SpringWebContextHolder;

@HandlerMessage("ExamTotal")
public class ExamTotalWorker extends AbstractDefaultWorker {

  /** serialVersionUID */
  private static final long serialVersionUID = 7711372569423035504L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback)
      throws Exception {
    UserExamTotal examTotal =
        FastJsonUtil.fromJson(message.getMessageBodyJson(), UserExamTotal.class);
    if (null == examTotal.getUserId() || null == examTotal.getCourseId()) return;
    QuesOperationFacade quesOperationFacade = SpringWebContextHolder.getBean("quesOperationFacade");
    UserExamTotal oldExamTotal =
        quesOperationFacade.queryExamTotal(examTotal.getUserId(), examTotal.getCourseId()
            .toString());
    if (null != oldExamTotal) {
      UserExamTotal _examTotal = new UserExamTotal();
      _examTotal.setId(oldExamTotal.getId());
      _examTotal.setUserId(oldExamTotal.getUserId());
      _examTotal.setCourseId(oldExamTotal.getCourseId());
      _examTotal.setMajorId(examTotal.getMajorId());
      _examTotal.setModule(examTotal.getModule());
      process(_examTotal, quesOperationFacade);
      quesOperationFacade.updateExamTotal(_examTotal);
    } else {
      process(examTotal, quesOperationFacade);
      try {
        quesOperationFacade.insertExamTotal(examTotal);
      } catch (Exception e) {
        if (e instanceof DuplicateKeyException) {
          oldExamTotal =
              quesOperationFacade.queryExamTotal(examTotal.getUserId(), examTotal.getCourseId()
                  .toString());
          UserExamTotal _examTotal = new UserExamTotal();
          _examTotal.setId(oldExamTotal.getId());
          _examTotal.setUserId(oldExamTotal.getUserId());
          _examTotal.setCourseId(oldExamTotal.getCourseId());
          _examTotal.setMajorId(examTotal.getMajorId());
          _examTotal.setModule(examTotal.getModule());
          process(_examTotal, quesOperationFacade);
          quesOperationFacade.updateExamTotal(_examTotal);
        } else {
          throw new Exception(e);
        }
      }
    }
  }

  private void process(UserExamTotal oldExamTotal, QuesOperationFacade quesOperationFacade) {
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("userId", oldExamTotal.getUserId());
    cond.put("courseId", oldExamTotal.getCourseId());
    Page<UserWrongRecord> userTotalList =
        quesOperationFacade.queryAbstractWrongRecordList(cond, null);
    Integer totalCount = 0, rightCount = 0, unCatchWrongQues = 0;
    if (userTotalList != null && userTotalList.getResult() != null
        && userTotalList.getResult().size() > 0) {
      totalCount = userTotalList.getResult().size();
      for (UserWrongRecord wrongRecord : userTotalList.getResult()) {
        int wrongStatus = Integer.parseInt(wrongRecord.getWrongStatus());
        if (Integer.parseInt(QuesBaseConstant.QUES_WRONG_RECORD_STATUS_RESOLVED) == wrongStatus
            || Integer.parseInt(QuesBaseConstant.QUES_WRONG_RECORD_STATUS_REMOVED) == wrongStatus) {
          rightCount++;
        } else if (Integer.parseInt(QuesBaseConstant.QUES_WRONG_RECORD_STATUS_UNCONTROL) == wrongStatus) {
          unCatchWrongQues++;
        }
      }
    }
    oldExamTotal.setTotalQues(totalCount);
    oldExamTotal.setRightQues(rightCount);
    if (StringUtils.isNotBlank(oldExamTotal.getModule())) {
      sendWrongQuesCountEvent(oldExamTotal, unCatchWrongQues);
    }
    if (0 == totalCount) {
      oldExamTotal.setRightPercent("0.00");
    } else {
      oldExamTotal.setRightPercent(QuesBaseConstant.D_FORMAT.format(new Double(rightCount)
          / new Double(totalCount)));
    }
    // oldExamTotal.setTotalRank(quesOperationFacade.countTotalQuesRank(totalCount,
    // oldExamTotal.getUserId(), oldExamTotal.getCourseId().toString()) + 1);
    // oldExamTotal.setRightRank(quesOperationFacade.countTotalQuesRank(rightCount,
    // oldExamTotal.getUserId(), oldExamTotal.getCourseId().toString()) + 1);
    // LoggerUtil.alarmInfo(FastJsonUtil.toJson(oldExamTotal));
  }

  private void sendWrongQuesCountEvent(UserExamTotal oldExamTotal, Integer unCatchWrongQues) {
    WrongQuesCountEvent event = EventBuilder.buildWrongQuesCountEvent();
    event.setUserId(oldExamTotal.getUserId());
    event.setModule(oldExamTotal.getModule());
    event.setMajorId(oldExamTotal.getMajorId());
    event.setCourseId(oldExamTotal.getCourseId().toString());
    event.setCount(unCatchWrongQues);
    event.send();
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {
    LoggerUtil.error("插入/更新错误记录异常.", t);
  }

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {
    LoggerUtil.error("插入/更新错误记录异常.", t);
  }

}
