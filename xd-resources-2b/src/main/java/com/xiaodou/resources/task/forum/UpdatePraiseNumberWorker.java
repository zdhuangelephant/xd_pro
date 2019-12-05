package com.xiaodou.resources.task.forum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.resources.constant.forum.Constant;
import com.xiaodou.resources.model.forum.ForumCommentModel;
import com.xiaodou.resources.model.forum.ForumModel;
import com.xiaodou.resources.model.forum.ForumPraiseModel;
import com.xiaodou.resources.service.forum.facade.ForumServiceFacade;
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
@HandlerMessage("UpdatePraiseNumber")
public class UpdatePraiseNumberWorker extends AbstractDefaultWorker {

  /** serialVersionUID */
  private static final long serialVersionUID = -1780267920435577119L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    ForumServiceFacade forumServiceFacade = SpringWebContextHolder.getBean("forumServiceFacade");
    ForumPraiseModel praiseModel =
        FastJsonUtil.fromJson(message.getMessageBodyJson(), ForumPraiseModel.class);
    Map<String, Object> input = new HashMap<String, Object>();
    Map<String, Object> output = new HashMap<String, Object>();
    if (praiseModel.getResourcesId() > 0 && praiseModel.getCommentId() == 0) {
      // 给话题点赞
      input.put("id", praiseModel.getResourcesId());
      ForumModel forumModel = new ForumModel();
      List<ForumModel> forum = forumServiceFacade.queryForumList(input, output);
      if (null == forum || forum.size() <= 0) {
        return;
      } else {
        Map<String, Object> praiseInput = Maps.newHashMap();
        praiseInput.put("resourcesId", praiseModel.getResourcesId());
        praiseInput.put("commentId", 0);
        forumModel.setPraiseNumber(forumServiceFacade.queryPraiseNumber(praiseInput));
        forumServiceFacade.updateForumEntity(input, forumModel);
      }
      // 更新专栏热度
      Map<String, Object> _queryCond = Maps.newHashMap(), _output = Maps.newHashMap();
      _queryCond.put("id", praiseModel.getResourcesId());
      output.put("columnId", Constant.YES);
      List<ForumModel> forumList = forumServiceFacade.queryForumList(_queryCond, _output);
      if (null == forumList || forumList.size() == 0) return;
      ForumModel model = forumList.get(0);
      if (StringUtils.isNotBlank(model.getColumnId()))
        forumServiceFacade.updateResourcesColumnistHeatById(model.getColumnId());
    } else if (praiseModel.getResourcesId() > 0 && praiseModel.getCommentId() > 0) {
      // 给评论点赞
      input.put("id", praiseModel.getCommentId());
      ForumCommentModel forumCommentModel = new ForumCommentModel();
      List<ForumCommentModel> comment = forumServiceFacade.queryCommentList(input, output);
      if (null == comment || comment.size() <= 0) {
        return;
      } else {
        Map<String, Object> praiseInput = Maps.newHashMap();
        praiseInput.put("forumId", praiseModel.getResourcesId());
        praiseInput.put("commentId", praiseModel.getCommentId());
        forumCommentModel.setPraiseNumber(forumServiceFacade.queryPraiseNumber(praiseInput));
        forumServiceFacade.updateCommontEntity(input, forumCommentModel);
      }
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
