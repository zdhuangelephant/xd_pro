package com.xiaodou.resources.task.forum;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.resources.constant.forum.ForumConstant;
import com.xiaodou.resources.model.forum.ForumRelateInfoModel;
import com.xiaodou.resources.model.forum.RelateInfoUserModel;
import com.xiaodou.resources.service.forum.facade.ForumServiceFacade;
import com.xiaodou.resources.util.ForumUtil;
import com.xiaodou.summer.util.SpringWebContextHolder;

@HandlerMessage("UpdateRelateInfo")
public class UpdateRelateInfoTaskWorker extends AbstractDefaultWorker {

  /** serialVersionUID */
  private static final long serialVersionUID = 8687484005603376303L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    ForumRelateInfoModel relateInfoModel =
        FastJsonUtil.fromJson(message.getMessageBodyJson(), ForumRelateInfoModel.class);
    if (null != relateInfoModel.getId()) {
      ForumServiceFacade forumServiceFacade = SpringWebContextHolder.getBean("forumServiceFacade");
      Map<String, Object> cond = new HashMap<String, Object>();
      Map<String, Object> input = new HashMap<String, Object>();
      input.put("id", relateInfoModel.getId());
      input.put("forumId", relateInfoModel.getResourcesId());
      input.put("targeId", relateInfoModel.getTargeId());
      cond.put("input", input);
      cond.put("output", ForumUtil.getMyRelateInfoOutput());
      List<RelateInfoUserModel> comments =
          forumServiceFacade.queryRelationInfoListByCondNoPage(cond);
      if (null == comments || comments.size() == 0) return;
      RelateInfoUserModel relateInfo = comments.get(0);
      if (null != relateInfo && ForumConstant.STATUS_INIT == relateInfo.getStatus()) {
        ForumRelateInfoModel newRelateInfo = new ForumRelateInfoModel();
        newRelateInfo.setId(relateInfo.getId());
        newRelateInfo.setStatus(ForumConstant.STATUS_READED);
        forumServiceFacade.updateForumRelateInfo(newRelateInfo);
      }
    }
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> callback) {

  }

  @Override
  public void onException(Throwable t, List<DefaultMessage> message,
      IMQCallBacker<List<DefaultMessage>> callback) {
    LoggerUtil.error("插入推送任务", t);
  }

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {
    LoggerUtil.error("插入推送任务", t);
  }

}
