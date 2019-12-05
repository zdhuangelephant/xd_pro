package com.xiaodou.resources.task.forum;

import java.util.List;

import com.xiaodou.common.util.RandomUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.resources.constant.quesbk.QuesBaseConstant;
import com.xiaodou.resources.model.quesbk.TaskScoreModel;
import com.xiaodou.resources.service.forum.facade.ForumServiceFacade;
import com.xiaodou.resources.service.product.facade.ProductServiceFacade;
import com.xiaodou.resources.service.quesbk.facade.QuesOperationFacade;
import com.xiaodou.resources.vo.task.UpdateTalkComment;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;
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
@HandlerMessage("UpdateTalkUnmber")
public class UpdateTalkUnmberWorker extends AbstractDefaultWorker {

  /** serialVersionUID */
  private static final long serialVersionUID = -5220418099916651474L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    ForumServiceFacade forumServiceFacade = SpringWebContextHolder.getBean("forumServiceFacade");
    QuesOperationFacade quesOperationFacade = SpringWebContextHolder.getBean("quesOperationFacade");
    ProductServiceFacade productServiceFacade =
        SpringWebContextHolder.getBean("productServiceFacade");
    UpdateTalkComment vo =
        FastJsonUtil.fromJson(message.getMessageBodyJson(), UpdateTalkComment.class);
    Integer myCount =
        forumServiceFacade.queryCommentNumber(vo.getUserId(), vo.getProductId(), vo.getItemId());
    Integer allCount =
        forumServiceFacade.queryCommentNumber(null, vo.getProductId(), vo.getItemId());
    // 更新我的回复数
    IQueryParam param = new QueryParam();
    param.addInput("userId", vo.getUserId());
    param.addInput("productId", vo.getProductId());
    param.addInput("taskId", vo.getItemId());
    param.addOutput("id", QuesBaseConstant.YES);
    Page<TaskScoreModel> scoreModelPage = quesOperationFacade.queryTaskScoreModel(param);
    if (null == scoreModelPage || null == scoreModelPage.getResult()
        || scoreModelPage.getResult().size() == 0) {
      TaskScoreModel model = new TaskScoreModel();
      model.setId(Long.valueOf(RandomUtil.randomNumber(9)));
      model.setProductId(Long.parseLong(vo.getProductId()));
      model.setUserId(Long.parseLong(vo.getUserId()));
      model.setTaskId(Long.parseLong(vo.getItemId()));
      model.setRecordNum(myCount);
      quesOperationFacade.insertTaskScoreModel(model);
    } else {
      TaskScoreModel model = scoreModelPage.getResult().get(0);
      model.setRecordNum(myCount);
      quesOperationFacade.updateTaskScoreModel(model);
    }
    // 更新评论数
    productServiceFacade.updateProductItemCommentNumById(Long.parseLong(vo.getItemId()), allCount);
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
