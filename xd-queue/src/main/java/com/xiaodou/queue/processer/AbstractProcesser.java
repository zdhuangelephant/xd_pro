package com.xiaodou.queue.processer;

import java.util.List;

import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.consumer.AbstractConsumer;
import com.xiaodou.queue.puller.IPuller;


/**
 * @name @see com.xiaodou.queue.puller.aliyun.AbstractPuller.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月22日
 * @description 拉取執行者主干方法
 * @version 1.0
 */
public abstract class AbstractProcesser<M, C> implements IProcesser {

  protected IPuller<M, C> puller;

  protected AbstractConsumer<M, C> consumer;

  /** serialVersionUID */
  private static final long serialVersionUID = 7288418811941866646L;

  @Override
  public void process() {
    M pull;
    while (null != (pull = puller.pull())) {
      consumer.consumer(pull, getCallBack(pull));
    }
  }

  @Override
  public void processList() {
    List<M> pullList;
    while (null != (pullList = puller.pullList())) {
      consumer.consumerList(pullList, getCallBackList(pullList));
    }
  }

  public abstract IMQCallBacker<C> getCallBack(M pull);

  public abstract IMQCallBacker<List<C>> getCallBackList(List<M> pullList);

}
