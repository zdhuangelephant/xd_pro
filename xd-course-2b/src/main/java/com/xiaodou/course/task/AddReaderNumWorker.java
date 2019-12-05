package com.xiaodou.course.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.course.model.forum.ForumModel;
import com.xiaodou.course.service.facade.ForumServiceFacade;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.enums.MessageType;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.summer.util.SpringWebContextHolder;

/**
 * @name @see com.xiaodou.resources.task.forum.UpdatePraiseNumberWorker.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月16日
 * @description 跟新点赞数量worker
 * @version 1.0
 */
@HandlerMessage(value = "AddReaderNum", type = MessageType.Multiple)
public class AddReaderNumWorker extends AbstractDefaultWorker {

  /** serialVersionUID */
  private static final long serialVersionUID = -1780267920435577119L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    ForumServiceFacade forumServiceFacade = SpringWebContextHolder.getBean("forumServiceFacade");
    ForumModel forum = FastJsonUtil.fromJson(message.getMessageBodyJson(), ForumModel.class);
    Map<String, Object> input = new HashMap<String, Object>();
    if (forum.getForumId() != null) {
      // 给话题点赞
      input.put("forumId", forum.getForumId());
      ForumModel forumModel = new ForumModel();
      forumModel.setForumReaderNum(forum.getForumReaderNum() + 1);
      forumServiceFacade.updateForumEntity(input, forumModel);

    } else {
      return;
    }

  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {
    LoggerUtil.error("点赞失败", t);
  }

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {
    LoggerUtil.error("点赞失败", t);
  }

}
