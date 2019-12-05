package com.xiaodou.course.task;

import java.util.List;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.course.service.facade.ProductServiceFacade;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.summer.util.SpringWebContextHolder;

/**
 * @name @see com.xiaodou.forum.task.UpdateCommentUnmberWorker.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月16日
 * @description 更新回复数量worker
 * @version 1.0
 */
@HandlerMessage(value="UpdateCommentNumber")
public class UpdateCommentNumberWorker extends AbstractDefaultWorker {

  /** serialVersionUID */
  private static final long serialVersionUID = -5220418099916651474L;

  /**
   * 记录每天学习时长
   */
  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    ProductServiceFacade productServiceFacade =
        SpringWebContextHolder.getBean("productServiceFacade");
    Long itemId = Long.parseLong(message.getMessageBodyJson());
    Integer commentNumber = productServiceFacade.queryCommentNumber(itemId);
    productServiceFacade.updateProductItemCommentNumById(itemId, commentNumber);
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {
    LoggerUtil.error("更新话题回复数失败", t);
  }

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {
    LoggerUtil.error("更新话题回复数失败", t);
  }
}
