package com.xiaodou.resources.task.forum;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.async.toC.model.NoticeToColumnistFollower;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.resources.constant.forum.Constant;
import com.xiaodou.resources.model.forum.ForumModel;
import com.xiaodou.resources.model.forum.ResourcesColumnistFollower;
import com.xiaodou.resources.service.forum.facade.ForumServiceFacade;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.summer.util.SpringWebContextHolder;

@HandlerMessage("NoticeToFollower")
public class NoticeToFollowerWorker extends AbstractDefaultWorker {

  /** serialVersionUID */
  private static final long serialVersionUID = -1780267920435577119L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    String forumModelJson = message.getMessageBodyJson();
    ForumModel forumModel = FastJsonUtil.fromJson(forumModelJson, ForumModel.class);
    ForumServiceFacade forumServiceFacade = SpringWebContextHolder.getBean("forumServiceFacade");
    // 1 获取关注列表
    IQueryParam param = new QueryParam();
    param.addInput("columnistId", forumModel.getColumnId());
    param.addOutput("userId", Constant.YES);
    Page<ResourcesColumnistFollower> followerPage =
        forumServiceFacade.queryResourcesFollowerList(param, null);
    if (null == followerPage || null == followerPage.getResult()
        || followerPage.getResult().size() == 0) return;
    List<String> toUidList = Lists.newArrayList();
    for (ResourcesColumnistFollower follower : followerPage.getResult())
      toUidList.add(follower.getUserId());
    // 2 发送专栏通知
    sendMessage(forumModel.getModule(), Long.toString(forumModel.getPublisherId()), toUidList);
  }

  private void sendMessage(String module, String srcUid, List<String> toUidList) {
    NoticeToColumnistFollower message = new NoticeToColumnistFollower();
    message.setModule(module);
    message.setSrcUid(srcUid);
    message.setToUidList(toUidList);
    message.send();
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
