package com.xiaodou.resources.task.quesbk;

import java.util.List;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.resources.enums.quesbk.ExamSubmitStatus;
import com.xiaodou.resources.model.quesbk.UserExamRecord;
import com.xiaodou.resources.model.quesbk.UserExamRecordDetail;
import com.xiaodou.resources.service.quesbk.facade.QuesOperationFacade;
import com.xiaodou.summer.util.SpringWebContextHolder;

@HandlerMessage("ExamRecord")
public class ExamRecordWorker extends AbstractDefaultWorker {

  /** serialVersionUID */
  private static final long serialVersionUID = 5497429856772578203L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    QuesOperationFacade quesOperationFacade = SpringWebContextHolder.getBean("quesOperationFacade");
    UserExamRecord userExamRecord =
        FastJsonUtil.fromJson(message.getMessageBodyJson(), UserExamRecord.class);
    UserExamRecord examRecord =
        quesOperationFacade
            .queryExamRecord(userExamRecord.getPaperNo(), userExamRecord.getUserId());
    if (null != examRecord) {
      userExamRecord.setId(examRecord.getId());
      UserExamRecordDetail oldDetail =
          FastJsonUtil.fromJson(examRecord.getExamDetail(), UserExamRecordDetail.class);
      if (ExamSubmitStatus.SUBMIT.getCode().equals(oldDetail.getStatus())) {
        quesOperationFacade.updateResetExamRecord(userExamRecord);
      } else {
        quesOperationFacade.updateContinueExamRecord(userExamRecord);
      }
    } else {
      quesOperationFacade.insertExamRecord(userExamRecord);
    }
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
