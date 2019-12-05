package com.xiaodou.queue.worker;

import java.util.List;

import com.xiaodou.aopagent.util.TraceWrapper;
import com.xiaodou.queue.base.AbstractMethodWithCallBack;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;


public abstract class AbstractDefaultWorker
    extends AbstractMethodWithCallBack<DefaultMessage, DefaultMessage>
    implements
      IWorker<DefaultMessage, DefaultMessage> {

  public AbstractDefaultWorker() {}

  /** UID */
  private static final long serialVersionUID = -8117021887505560545L;


  @Override
  public void excute(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
	TraceWrapper.getWrapper().getTraceParam().setTraceId(message.getTraceId());
	TraceWrapper.getWrapper().getTraceParam().setMyId(message.getMyId());
    excute0(message, callback);
  }

  @Override
  public void excute(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {
    excute0(message, callback);
  }

}
