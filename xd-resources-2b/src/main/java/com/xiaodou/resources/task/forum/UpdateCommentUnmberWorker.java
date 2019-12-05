package com.xiaodou.resources.task.forum;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.resources.constant.forum.Constant;
import com.xiaodou.resources.model.forum.ForumModel;
import com.xiaodou.resources.service.forum.facade.ForumServiceFacade;
import com.xiaodou.summer.util.SpringWebContextHolder;

/**
 * @name @see com.xiaodou.resources.task.forum.UpdateCommentUnmberWorker.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月16日
 * @description 更新回复数量worker
 * @version 1.0
 */
@HandlerMessage("UpdateCommentNumber")
public class UpdateCommentUnmberWorker extends AbstractDefaultWorker {

  /** serialVersionUID */
  private static final long serialVersionUID = -5220418099916651474L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    ForumServiceFacade forumServiceFacade = SpringWebContextHolder.getBean("forumServiceFacade");
    Long forumId = Long.parseLong(message.getMessageBodyJson());
    forumServiceFacade.updateForumRepliesNumber(forumId,
        forumServiceFacade.queryCommentNumber(forumId));
    // 更新专栏热度
    Map<String, Object> queryCond = Maps.newHashMap(), output = Maps.newHashMap();
    queryCond.put("id", forumId);
    output.put("columnId", Constant.YES);
    List<ForumModel> forumList = forumServiceFacade.queryForumList(queryCond, output);
    if (null == forumList || forumList.size() == 0) return;
    ForumModel model = forumList.get(0);
    if (StringUtils.isNotBlank(model.getColumnId()))
      forumServiceFacade.updateResourcesColumnistHeatById(model.getColumnId());
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {}

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {
    LoggerUtil.error("更新话题回复数失败", t);
  }

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {
    LoggerUtil.error("更新话题回复数失败", t);
  }
}
