package com.xiaodou.resources.task.forum;

import java.util.List;

import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.resources.model.forum.ResourcesColumnist;
import com.xiaodou.resources.service.forum.facade.ForumServiceFacade;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.summer.util.SpringWebContextHolder;

/**
 * @name RefreshFollowerNumWorker CopyRright (c) 2016 by zhaodan
 * 
 * @author zhaodan
 * @date 2016年9月1日
 * @description 刷新专栏关注者数量
 * @version 1.0
 */
@HandlerMessage("RefreshFollowerNum")
public class RefreshFollowerNumWorker extends AbstractDefaultWorker {

  /** serialVersionUID */
  private static final long serialVersionUID = -3983365733699779170L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> arg1) {
    String columnistId = message.getMessageBodyJson();
    ForumServiceFacade forumServiceFacade = SpringWebContextHolder.getBean("forumServiceFacade");
    ResourcesColumnist columnist = new ResourcesColumnist();
    IQueryParam param = new QueryParam();
    param.addInput("columnistId", columnistId);
    Integer followerNum = forumServiceFacade.countResourcesColumnistFollower(param);
    columnist.setId(columnistId);
    columnist.setFollowerNum(followerNum);
    forumServiceFacade.updateResourcesColumnistById(columnist);
    forumServiceFacade.updateResourcesColumnistHeatById(columnistId);
  }

  @Override
  public void domain(List<DefaultMessage> message, IMQCallBacker<List<DefaultMessage>> arg1) {
    // TODO Auto-generated method stub

  }

  @Override
  public void onException(Throwable arg0, List<DefaultMessage> arg1,
      IMQCallBacker<List<DefaultMessage>> arg2) {
    // TODO Auto-generated method stub

  }

  @Override
  public void onException(Throwable arg0, DefaultMessage arg1, IMQCallBacker<DefaultMessage> arg2) {
    // TODO Auto-generated method stub

  }

}
