package com.xiaodou.forum.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.forum.model.forum.ForumCategoryModel;
import com.xiaodou.forum.request.forum.ForumRequest;
import com.xiaodou.forum.service.facade.ForumServiceFacade;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.summer.util.SpringWebContextHolder;

@HandlerMessage("UpdateForumCategoryNumber")
public class UpdateForumCategoryNumberWorker extends AbstractDefaultWorker {

  /** serialVersionUID */
  private static final long serialVersionUID = 1732798807238118640L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    ForumServiceFacade forumServiceFacade = SpringWebContextHolder.getBean("forumServiceFacade");
    ForumRequest forumRequest =
        FastJsonUtil.fromJson(message.getMessageBodyJson(), ForumRequest.class);
    ForumCategoryModel forumCategoryModel = new ForumCategoryModel();
    Map<String, Object> input = new HashMap<String, Object>();
    input.put("id", forumRequest.getCategoryId());
    Map<String, Object> output = new HashMap<String, Object>();
    List<ForumCategoryModel> list = forumServiceFacade.queryCatagoryList(input, output);
    if (null == list || list.size() <= 0) {
      return;
    }
    forumCategoryModel.setForumNumber(forumServiceFacade.queryForumNumber(Long
        .parseLong(forumRequest.getCategoryId())));
    forumCategoryModel.setPeopleNumber(forumServiceFacade.queryCategoryPeopleNumber(Long
        .parseLong(forumRequest.getCategoryId())));
    forumServiceFacade.updateCatagoryEntity(input, forumCategoryModel);
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {

  }

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {
    LoggerUtil.error("更新分类话题数量失败", t);
  }

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {
    LoggerUtil.error("更新分类话题数量失败", t);
  }

}
