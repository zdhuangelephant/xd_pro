package com.xiaodou.resources.task.forum;

import java.util.List;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.resources.model.forum.ForumModel;
import com.xiaodou.resources.model.forum.ResourcesColumnist;
import com.xiaodou.resources.service.forum.facade.ForumServiceFacade;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;
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
@HandlerMessage("RefreshOpusNum")
public class RefreshOpusNumWorker extends AbstractDefaultWorker {

  /** serialVersionUID */
  private static final long serialVersionUID = -1780267920435577119L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
	  String forumModelJson = message.getMessageBodyJson();
	  ForumModel forumModel = FastJsonUtil.fromJson(forumModelJson, ForumModel.class);
	  ForumServiceFacade forumServiceFacade = SpringWebContextHolder.getBean("forumServiceFacade");
	  ResourcesColumnist columnist = new ResourcesColumnist();
	  IQueryParam param = new QueryParam();
	  param.addInput("columnistId", forumModel.getColumnId());
	  Integer opusNum = forumServiceFacade.queryForumNumberByColumnId(forumModel.getColumnId());
	  columnist.setId(forumModel.getColumnId());
	  columnist.setOpusNum(opusNum);
	  columnist.setLastedForum(forumModel.getTitle());
	  columnist.setLastedForumTime(forumModel.getCreateTime());
	  forumServiceFacade.updateResourcesColumnistById(columnist);
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {}

}
