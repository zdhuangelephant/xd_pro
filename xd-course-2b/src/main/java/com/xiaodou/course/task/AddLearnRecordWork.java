package com.xiaodou.course.task;

import java.util.List;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.course.model.user.UserLearnRecordModel;
import com.xiaodou.course.service.facade.ProductServiceFacade;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.summer.util.SpringWebContextHolder;

@HandlerMessage("AddLearnRecord")
public class AddLearnRecordWork extends AbstractDefaultWorker {
  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -4334009237028174065L;

  /**
   * 记录学习记录进度
   */
  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    UserLearnRecordModel learnRecord =
        FastJsonUtil.fromJson(message.getMessageBodyJson(), UserLearnRecordModel.class);
    ProductServiceFacade productServiceFacade =
        SpringWebContextHolder.getBean("productServiceFacade");
    productServiceFacade.insertUserLearnRecord(learnRecord);
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {
    LoggerUtil.error("添加学习记录失败", t);
  }
}
