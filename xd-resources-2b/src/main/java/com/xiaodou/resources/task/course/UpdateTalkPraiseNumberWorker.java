package com.xiaodou.resources.task.course;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.queue.annotation.HandlerMessage;
import com.xiaodou.queue.callback.IMQCallBacker;
import com.xiaodou.queue.message.DefaultMessage;
import com.xiaodou.queue.worker.AbstractDefaultWorker;
import com.xiaodou.resources.model.forum.ForumCommentModel;
import com.xiaodou.resources.model.forum.ForumPraiseModel;
import com.xiaodou.resources.model.product.ProductItemModel;
import com.xiaodou.resources.service.forum.facade.ForumServiceFacade;
import com.xiaodou.resources.service.product.ProductItemService;
import com.xiaodou.resources.service.product.facade.ProductServiceFacade;
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
@HandlerMessage("UpdateTalkPraiseNumber")
public class UpdateTalkPraiseNumberWorker extends AbstractDefaultWorker {

  /** serialVersionUID */
  private static final long serialVersionUID = -1780267920435577119L;

  @Override
  public void domain(DefaultMessage message, IMQCallBacker<DefaultMessage> callback) {
    ProductItemService productItemService = SpringWebContextHolder.getBean("productItemService");
    ForumServiceFacade forumServiceFacade = SpringWebContextHolder.getBean("forumServiceFacade");
    ProductServiceFacade productServiceFacade =
        SpringWebContextHolder.getBean("productServiceFacade");
    ForumPraiseModel praiseModel =
        FastJsonUtil.fromJson(message.getMessageBodyJson(), ForumPraiseModel.class);
    Map<String, Object> input = new HashMap<String, Object>();
    Map<String, Object> output = new HashMap<String, Object>();
    if (praiseModel.getItemId() > 0 && praiseModel.getCommentId() == 0) {
      // 给讨论点赞
      ProductItemModel queryTALK = productItemService.findItemById(praiseModel.getItemId());
      if (null == queryTALK) {
        return;
      } else {
        Map<String, Object> praiseInput = Maps.newHashMap();
        praiseInput.put("itemId", praiseModel.getItemId());
        praiseInput.put("commentId", 0);
        productServiceFacade.updateProductItemPraiseNumById(praiseModel.getItemId(),
            forumServiceFacade.queryPraiseNumber(praiseInput));
      }
    } else if (praiseModel.getItemId() > 0 && praiseModel.getCommentId() > 0) {
      // 给评论点赞
      input.put("id", praiseModel.getCommentId());
      ForumCommentModel forumCommentModel = new ForumCommentModel();
      List<ForumCommentModel> comment = forumServiceFacade.queryCommentList(input, output);
      if (null == comment || comment.size() <= 0) {
        return;
      } else {
        Map<String, Object> praiseInput = Maps.newHashMap();
        praiseInput.put("itemId", praiseModel.getItemId());
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
    LoggerUtil.error("讨论点赞失败", t);
  }

  @Override
  public void onException(Throwable t, DefaultMessage message,
      IMQCallBacker<DefaultMessage> callback) {
    LoggerUtil.error("讨论点赞失败", t);
  }

}
