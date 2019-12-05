package com.xiaodou.resources.service.forum;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.async.toC.enums.AsyncMessageEnums.AsyncResInfo;
import com.xiaodou.async.toC.model.AddCommentMessage;
import com.xiaodou.async.toC.model.MessageBodyEntity;
import com.xiaodou.async.toC.model.MessageBodyEntity.ShowEntity;
import com.xiaodou.common.util.RandomUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.resources.enums.forum.ForumResType;
import com.xiaodou.resources.model.forum.CommentUserModel;
import com.xiaodou.resources.model.forum.ForumCommentModel;
import com.xiaodou.resources.model.forum.ForumUserModel;
import com.xiaodou.resources.model.product.ProductItemModel;
import com.xiaodou.resources.model.product.ProductModel;
import com.xiaodou.resources.request.forum.ForumReplyCommentsRequest;
import com.xiaodou.resources.request.forum.ForumReplyPostRequest;
import com.xiaodou.resources.request.forum.TalkReplyPostRequest;
import com.xiaodou.resources.response.BaseResponse;
import com.xiaodou.resources.response.forum.ForumResponse;
import com.xiaodou.resources.response.resultype.ProductResType;
import com.xiaodou.resources.service.QueueService;
import com.xiaodou.resources.service.forum.facade.ForumServiceFacade;
import com.xiaodou.resources.service.product.AbstractService;
import com.xiaodou.resources.service.product.ProductItemService;
import com.xiaodou.resources.service.product.ProductService;
import com.xiaodou.resources.util.ForumUtil;
import com.xiaodou.resources.vo.task.UpdateTalkComment;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * 话题回复：回复帖子，回复评论
 * <p>
 * 并发问题会导致话题参与人数的统计误差
 * </p>
 * 
 * @author bing.cheng
 * 
 */
@Service("forumReplyService")
public class ForumReplyService {

  @Resource
  ForumServiceFacade forumServiceFacade;

  @Resource
  QueueService queueService;
  @Resource
  ProductItemService productItemService;
  @Resource
  ProductService productService;
  @Resource
  AbstractService abstractService;

  /**
   * 发表评论
   * 
   * @param req
   * @return
   * @throws Exception
   */
  public BaseResponse replyPost(ForumReplyPostRequest req) throws Exception {
    ForumResponse response = new ForumResponse(ResultType.SUCCESS);
    // 根据id查询话题内容
    ForumUserModel forumUserModel =
        forumServiceFacade.queryForumUserById(req.getResourcesId().toString());
    if (null == forumUserModel) {
      return new ForumResponse(ForumResType.NULLFORUM);
    }
    // 1、增加评论
    ForumCommentModel forumCommentModel = new ForumCommentModel();
    forumCommentModel.setId(getId());
    forumCommentModel.setResourcesId(req.getResourcesId());
    forumCommentModel.setReplyId(Long.parseLong(req.getUid()));
    forumCommentModel.setContent(req.getContent());
    forumCommentModel.setOperator(req.getUid());
    forumCommentModel.setOperatorip(req.getClientIp());
    forumCommentModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
    forumCommentModel = forumServiceFacade.insertCommentEntity(forumCommentModel);
    // 发送通知
    AddCommentMessage message = new AddCommentMessage();
    message.setModule(req.getModule());
    message.setSrcUid(forumCommentModel.getReplyId().toString());
    message.setToUid(forumUserModel.getPublisherId().toString());
    message.addCallBackContent("resourcesId", req.getResourcesId().toString());
    message.addCallBackContent("digest", forumUserModel.getDigest().toString());
    if (forumUserModel.getDigest().toString().equals("0")) {
      String mes = forumUserModel.getContent();
      if (mes.length() > 10) {
        mes = mes.substring(0, 10) + "...";
      }
      message.setRetCode(AsyncResInfo.AddTwiterComment.getRetCode());
      message.setRetDesc(String.format(AsyncResInfo.AddTwiterComment.getRetDesc(), mes));
      MessageBodyEntity e = new MessageBodyEntity();
      ShowEntity s = new ShowEntity();
      s.setInfo(String.format(AsyncResInfo.AddTwiterComment.getMesTmp(), mes));
      s.setContent(forumCommentModel.getContent());
      e.setMessageBody(s);
      message.setMessageBody(e.getMessageBody());
      message.addCallBackContent("name", mes);
    } else {
      message.setRetCode(AsyncResInfo.AddArticleComment.getRetCode());
      message.setRetDesc(String.format(AsyncResInfo.AddArticleComment.getRetDesc(),
          forumUserModel.getTitle()));
      MessageBodyEntity e = new MessageBodyEntity();
      ShowEntity s = new ShowEntity();
      s.setInfo(String.format(AsyncResInfo.AddArticleComment.getMesTmp(), forumUserModel.getTitle()));
      s.setContent(forumCommentModel.getContent());
      e.setMessageBody(s);
      message.setMessageBody(e.getMessageBody());
      message.addCallBackContent("name", forumUserModel.getTitle());
    }
    message.send();
    // 2、更新该帖子的回复总数
    try {
      queueService.updateCommentNumber(String.valueOf(req.getResourcesId()));
    } catch (Exception e) {
      LoggerUtil.error("回复帖子，更新该帖子的回复总数异常", e);
    }
    return response;
  }

  /**
   * 回复评论
   * 
   * @param pojo
   * @return
   */
  public BaseResponse replyComments(ForumReplyCommentsRequest req) {
    ForumResponse response = new ForumResponse(ResultType.SUCCESS);
    // 根据id查询话题内容
    ForumUserModel forumUserModel =
        forumServiceFacade.queryForumUserById(req.getResourcesId().toString());
    if (null == forumUserModel) {
      return new ForumResponse(ForumResType.NULLFORUM);
    }
    List<CommentUserModel> commentList =
        forumServiceFacade.queryForumCommentByForumId(req.getResourcesId().toString(),
            req.getCommentId(), 1, ForumUtil.getCommentListOutput());
    if (null != commentList && commentList.size() == 1) {
      CommentUserModel commentModel = commentList.get(0);
      // 1、增加评论的评论
      ForumCommentModel forumCommentModel = new ForumCommentModel();
      forumCommentModel.setId(getId());
      // 当前用户id
      forumCommentModel.setReplyId(Long.parseLong(req.getUid()));
      // 目标评论人id
      forumCommentModel.setTargeId(commentModel.getReplyId());
      forumCommentModel.setOperator(req.getUid());
      forumCommentModel.setOperatorip(req.getClientIp());
      forumCommentModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
      forumCommentModel.setTargeCommentId(commentModel.getId());
      forumCommentModel.setResourcesId(req.getResourcesId());// 评论id
      forumCommentModel.setTargeContent(this.concatTargeContent(commentModel));// 目标评论内容
      forumCommentModel.setContent(req.getCommentContent());
      forumCommentModel = forumServiceFacade.insertCommentEntity(forumCommentModel);
      // 发送通知
      AddCommentMessage message = new AddCommentMessage();
      message.setModule(req.getModule());
      message.setSrcUid(forumCommentModel.getReplyId().toString());
      message.setToUid(forumCommentModel.getTargeId().toString());
      message.addCallBackContent("resourcesId", req.getResourcesId().toString());
      message.addCallBackContent("digest", forumUserModel.getDigest().toString());
      if (forumUserModel.getDigest().toString().equals("0")) {
        String mes = forumUserModel.getContent();
        if (mes.length() > 10) {
          mes = mes.substring(0, 10) + "...";
        }
        message.setRetCode(AsyncResInfo.ReplyTwiterComment.getRetCode());
        message.setRetDesc(String.format(AsyncResInfo.ReplyTwiterComment.getRetDesc(), mes));
        MessageBodyEntity e = new MessageBodyEntity();
        ShowEntity s = new ShowEntity();
        s.setInfo(String.format(AsyncResInfo.ReplyTwiterComment.getMesTmp(), mes));
        s.setContent(commentModel.getContent());
        e.setMessageBody(s);
        message.setMessageBody(e.getMessageBody());
        message.addCallBackContent("name", mes);
      } else {
        message.setRetCode(AsyncResInfo.ReplyArticleComment.getRetCode());
        message.setRetDesc(String.format(AsyncResInfo.ReplyArticleComment.getRetDesc(),
            forumUserModel.getTitle()));
        MessageBodyEntity e = new MessageBodyEntity();
        ShowEntity s = new ShowEntity();
        s.setInfo(String.format(AsyncResInfo.ReplyArticleComment.getMesTmp(),
            forumUserModel.getTitle()));
        s.setContent(commentModel.getContent());
        e.setMessageBody(s);
        message.setMessageBody(e.getMessageBody());
        message.addCallBackContent("name", forumUserModel.getTitle());
      }
      message.send();
    } else {
      return new ForumResponse(ForumResType.NULLCOMMENT);
    }

    // 2、更新该帖子的回复总数
    try {
      queueService.updateCommentNumber(String.valueOf(req.getResourcesId()));
    } catch (Exception e) {
      LoggerUtil.error("回复帖子，更新该帖子的回复总数异常", e);
    }
    return response;
  }

  private Long getId() {
    return Long.parseLong((System.currentTimeMillis() + RandomUtil.randomNumber(6)).trim());
  }

  /**
   * 拼接目标评论内容并返回
   */
  private String concatTargeContent(CommentUserModel model) {
    StringBuilder builder = new StringBuilder();
    builder.append("回复 " + model.getUser().getNickName() + ": " + model.getContent());
    return builder.toString();
  }



  /**
   * 发表评论for讨论
   * 
   * @param req
   * @return
   * @throws Exception
   */
  public BaseResponse replyTalk(TalkReplyPostRequest req) throws Exception {
    ForumResponse response = new ForumResponse(ResultType.SUCCESS);
    ProductItemModel productItemModel = productItemService.findItemById(req.getItemId());
    if (null == productItemModel) {
      return new ForumResponse(ForumResType.NULLFORUM);
    }
    ProductModel product = productService.findProductById(req.getProductId());
    if (null == product) {
      return new ForumResponse(ForumResType.NULLPRODUCT);
    }
    if (!productItemService.checkItemId(req.getItemId().toString()).isRetOk())
      return new BaseResponse(ProductResType.FindItemIdFailed);
    // 1、增加评论
    ForumCommentModel forumCommentModel = new ForumCommentModel();
    forumCommentModel.setId(getId());
    forumCommentModel.setReplyId(Long.parseLong(req.getUid()));
    forumCommentModel.setContent(req.getContent());
    forumCommentModel.setOperator(req.getUid());
    forumCommentModel.setOperatorip(req.getClientIp());
    forumCommentModel.setItemId(req.getItemId().longValue());
    forumCommentModel.setProductId(req.getProductId().longValue());
    forumCommentModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
    forumCommentModel = forumServiceFacade.insertCommentEntity(forumCommentModel);
    // 发送通知
    // AddCommentMessage message = new AddCommentMessage();
    // message.setModule(req.getModule());
    // message.setSrcUid(forumCommentModel.getReplyId().toString());
    // message.setToUid(forumCommentModel.getPublisherId().toString());
    // message.addCallBackContent("itemId", req.getItemId().toString());
    // message.addCallBackContent("digest", "1");
    // message.setRetCode(AsyncResInfo.AddArticleComment.getRetCode());
    // message.setRetDesc(String.format(AsyncResInfo.AddArticleComment.getRetDesc(),
    // productItemModel.getName()));
    // MessageBodyEntity e = new MessageBodyEntity();
    // ShowEntity s = new ShowEntity();
    // s.setInfo(String.format(AsyncResInfo.AddArticleComment.getMesTmp(),
    // productItemModel.getName()));
    // s.setContent(forumCommentModel.getContent());
    // e.setMessageBody(s);
    // message.setMessageBody(e.getMessageBody());
    // message.addCallBackContent("name", productItemModel.getName());
    // message.send();
    // 2、更新该帖子的回复总数
    try {
      UpdateTalkComment vo = new UpdateTalkComment();
      vo.setItemId(req.getItemId().toString());
      vo.setProductId(req.getProductId().toString());
      vo.setUserId(req.getUid());
      queueService.updateTalkUnmber(vo);
    } catch (Exception e1) {
      LoggerUtil.error("回复帖子，更新该帖子的回复总数异常", e1);
    }
    return response;
  }

}
