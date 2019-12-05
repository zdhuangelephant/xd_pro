package com.xiaodou.queue.test;

import java.util.List;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;

public class MyWorker extends AbstractDefaultWorker {

  private static final long serialVersionUID = 7921639761093832107L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    System.out.println("worker is working!!!" + FastJsonUtil.toJson(message.getMessageBodyJson()));
  }

  @Override
  public void domain(List<DefaultMessage> messageLst, IMQCallBacker<List<DefaultMessage>> callback) {
    for (DefaultMessage message : messageLst) {
      System.out
          .println("worker is working!!!" + FastJsonUtil.toJson(message.getMessageBodyJson()));
    }
  }

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {
    t.printStackTrace();
  }

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {
    t.printStackTrace();
  }

}
