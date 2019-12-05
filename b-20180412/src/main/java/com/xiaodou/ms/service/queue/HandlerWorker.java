package com.xiaodou.ms.service.queue;

import java.util.List;

import com.xiaodou.ms.service.product.ProductQuestionService;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;

import javax.annotation.Resource;

@HandlerMessage("message")
public class HandlerWorker extends AbstractDefaultWorker {

  /**
   * serialVersionUID
   */
  private static final long serialVersionUID = -1466218785467772959L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {
  }

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
                          IMQCallBacker<List<DefaultMessage>> callback) {
  }

  @Override
  public void onException(Throwable t, DefaultMessage message,
                          IMQCallBacker<DefaultMessage> callback) {
  }

}
