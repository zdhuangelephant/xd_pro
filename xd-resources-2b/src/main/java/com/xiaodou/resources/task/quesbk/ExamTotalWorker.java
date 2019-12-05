package com.xiaodou.resources.task.quesbk;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.resources.cache.quesbk.QuesExamCache;
import com.xiaodou.resources.constant.quesbk.QuesBaseConstant;
import com.xiaodou.resources.model.quesbk.UserExamTotal;
import com.xiaodou.resources.model.quesbk.UserWrongRecord;
import com.xiaodou.resources.service.QueueService;
import com.xiaodou.resources.service.quesbk.facade.QuesOperationFacade;
import com.xiaodou.resources.util.StaticInfoProp;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.util.SpringWebContextHolder;

@HandlerMessage("ExamTotal")
public class ExamTotalWorker extends AbstractDefaultWorker {

  /** serialVersionUID */
  private static final long serialVersionUID = 7711372569423035504L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    UserExamTotal examTotal =
        FastJsonUtil.fromJson(message.getMessageBodyJson(), UserExamTotal.class);
    if (null == examTotal.getUserId() || null == examTotal.getCourseId()) return;
    try {
      if (StringUtils.isBlank(examTotal.getTag())) {
        Thread.sleep(5000);
      } else {
        while (QuesExamCache.isValidExamRecord(examTotal.getTag())
            && !QuesExamCache.isAllDealed(examTotal.getTag())) {
          Thread.sleep(1000);
        }
      }
    } catch (Exception e) {}
    QuesOperationFacade quesOperationFacade = SpringWebContextHolder.getBean("quesOperationFacade");
    UserExamTotal oldExamTotal =
        quesOperationFacade.queryExamTotal(examTotal.getUserId(), examTotal.getCourseId()
            .toString());
    if (null != oldExamTotal) {
      oldExamTotal.setScore(examTotal.getScore());
      process(oldExamTotal, quesOperationFacade);
      quesOperationFacade.updateExamTotal(oldExamTotal);
    } else {
      process(examTotal, quesOperationFacade);
      quesOperationFacade.insertExamTotal(examTotal);
    }
    if (StringUtils.isNotBlank(examTotal.getTag()))
      QuesExamCache.delExamRecord(examTotal.getTag());
    if (null != examTotal.getChapterRecord()) {
      QueueService queueService = SpringWebContextHolder.getBean("queueService");
      queueService.addUserChapterRecord(examTotal.getChapterRecord());
    }
    quesOperationFacade.updateUserScore(examTotal.getCourseId().toString(), examTotal.getUserId());
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
        if (wrongRecord.getWrongTimes() <= 0 && wrongRecord.getRightTimes() > 0) {
          rightCount++;
        } else if (wrongRecord.getWrongTimes() > 0
            && wrongRecord.getRightTimes() > StaticInfoProp.getWrongLimit()) {
          rightCount++;
        } else if (QuesBaseConstant.QUES_WRONG_RECORD_STATUS_UNCONTROL.equals(wrongRecord
            .getWrongStatus())) {
          unCatchWrongQues++;
        }
      }
    }
    oldExamTotal.setTotalQues(totalCount);
    oldExamTotal.setRightQues(rightCount);
    if (0 == totalCount)
      oldExamTotal.setRightPercent("0.00");
    else
      oldExamTotal.setRightPercent(QuesBaseConstant.D_FORMAT.format(new Double(rightCount)
          / new Double(totalCount)));
    oldExamTotal.setTotalRank(quesOperationFacade.countTotalQuesRank(totalCount,
        oldExamTotal.getUserId(), oldExamTotal.getCourseId().toString()) + 1);
    oldExamTotal.setRightRank(quesOperationFacade.countTotalQuesRank(rightCount,
        oldExamTotal.getUserId(), oldExamTotal.getCourseId().toString()) + 1);
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
