package com.xiaodou.forum.task;

import java.util.List;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.forum.service.facade.ForumServiceFacade;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.summer.util.SpringWebContextHolder;

/**
 * @name @see com.xiaodou.forum.task.UpdatePartakeNumberWorker.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月16日
 * @description 更新话题参与人数worker
 * @version 1.0
 */
@HandlerMessage("UpdatePartakeNumber")
public class UpdatePartakeNumberWorker extends AbstractDefaultWorker {

  /** serialVersionUID */
  private static final long serialVersionUID = 3132954293508217558L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    ForumServiceFacade forumServiceFacade = SpringWebContextHolder.getBean("forumServiceFacade");
    Long forumCategoryId = Long.parseLong(message.getMessageBodyJson());
    forumServiceFacade.updateCategoryPeopleNumber(forumCategoryId,
        forumServiceFacade.queryCategoryPeopleNumber(forumCategoryId));
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {
    LoggerUtil.error("更新话题类型参与人数失败", t);
  }

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {
    LoggerUtil.error("更新话题类型参与人数失败", t);
  }

}
