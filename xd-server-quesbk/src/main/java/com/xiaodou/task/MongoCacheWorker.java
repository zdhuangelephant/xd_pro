package com.xiaodou.task;

import java.util.List;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.domain.MongoCacheDTO;
import com.xiaodou.frameworkcache.page.PageManager;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.summer.util.SpringWebContextHolder;


@HandlerMessage("MongoCache")
public class MongoCacheWorker extends AbstractDefaultWorker {

  /** serialVersionUID */
  private static final long serialVersionUID = 5497429856772578203L;

  
  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
      PageManager pageManager = SpringWebContextHolder.getBean("pageManager");
      MongoCacheDTO dto =
        FastJsonUtil.fromJson(message.getMessageBodyJson(), MongoCacheDTO.class);
    if (null != dto) {
      pageManager.addPage(dto.getKey(), dto.getObj(), 100000, false);
    }
  }
  
  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {
    LoggerUtil.error("ADD_MONGO_CACHE.", t);
  }

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {
    LoggerUtil.error("ADD_MONGO_CACHE.", t);
  }

}