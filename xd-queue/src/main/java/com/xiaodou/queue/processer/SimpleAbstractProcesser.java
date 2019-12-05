package com.xiaodou.queue.processer;

import java.util.List;

import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.consumer.AbstractConsumer;
import com.xiaodou.queue.puller.IPuller;

public abstract class SimpleAbstractProcesser<M, C> extends AbstractConsumer<M, C>
    implements
      IPuller<M, C>,
      IProcesser {

  /** serialVersionUID */
  private static final long serialVersionUID = -3317602615979495370L;

  @Override
  public void process() {
    M pull;
    while (null != (pull = pull())) {
      consumer(pull, getCallBack(pull));
    }
  }

  @Override
  public void processList() {
    List<M> pullList;
    while (null != (pullList = pullList())) {
      consumerList(pullList, getCallBackList(pullList));
    }
  }

  public abstract IMQCallBacker<C> getCallBack(M pull);

  public abstract IMQCallBacker<List<C>> getCallBackList(List<M> pullList);
}
