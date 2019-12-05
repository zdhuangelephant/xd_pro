package com.xiaodou.task;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.constant.QuesBaseConstant;
import com.xiaodou.frameworkcache.page.PageManager;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.service.QuesRecordService;
import com.xiaodou.summer.util.SpringWebContextHolder;
import com.xiaodou.vo.request.ExamResultPojo;
import com.xiaodou.vo.request.LearnRecordViewRequest;
import com.xiaodou.vo.response.LearnRecordViewResponse;

@HandlerMessage("UpdateLearnRecordView")
public class UpdateLearnRecordViewWorker extends AbstractDefaultWorker {

  /** serialVersionUID */
  private static final long serialVersionUID = 1744525511433456484L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    ExamResultPojo examResultPojo =
        FastJsonUtil.fromJson(message.getMessageBodyJson(), ExamResultPojo.class);
    QuesRecordService quesRecordService = SpringWebContextHolder.getBean("quesRecordService");
    PageManager pageManager = SpringWebContextHolder.getBean("pageManager");
    LearnRecordViewRequest request = new LearnRecordViewRequest();
    request.setCourseId(examResultPojo.getCourseId());
    request.setMajorId(examResultPojo.getMajorId());
    request.setModule(examResultPojo.getModule());
    request.setTypeCode(examResultPojo.getTypeCode());
    request.setUid(examResultPojo.getUid());
    LearnRecordViewResponse response = quesRecordService.learnRecordView0(request);
    if (null != response) {
      pageManager.addPage(request.getCacheKey(), response, QuesBaseConstant.MONGO_CACHE_TIME_DAYS,
          true, TimeUnit.DAYS);
    }
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {
    LoggerUtil.error("插入/更新闯关状态异常.", t);
  }

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {
    LoggerUtil.error("插入/更新闯关状态异常.", t);
  }

}
