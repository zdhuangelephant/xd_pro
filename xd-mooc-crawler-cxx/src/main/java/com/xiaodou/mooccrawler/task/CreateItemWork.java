package com.xiaodou.mooccrawler.task;

import java.util.List;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.mooccrawler.dao.MoocItemDao;
import com.xiaodou.mooccrawler.domain.MoocItem;
import com.xiaodou.mooccrawler.holder.MoocCourseHolder;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.summer.util.SpringWebContextHolder;

@HandlerMessage("CreateItem")
public class CreateItemWork extends AbstractDefaultWorker {

  /** serialVersionUID */
  private static final long serialVersionUID = 1L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback)
      throws Exception {
    String mes = message.getMessageBodyJson();
    MoocItem item = FastJsonUtil.fromJson(mes, MoocItem.class);
    MoocItemDao itemDao = SpringWebContextHolder.getBean(MoocItemDao.class);
    IQueryParam param = new QueryParam();
    param.addInput("href", item.getHref());
    param.addOutputs(CommUtil.getAllField(MoocItem.class));
    Page<MoocItem> itemPage = itemDao.findEntityListByCond(param, null);
    if (null != itemPage && null != itemPage.getResult() && !itemPage.getResult().isEmpty()) {
      return;
    }
    itemDao.addEntity(item);
    MoocCourseHolder.getProcessor(item.getCourseId()).push(item);
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback)
      throws Exception {

  }

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {
    LoggerUtil.error(message.getMessageBodyJson(), t);
  }

}
