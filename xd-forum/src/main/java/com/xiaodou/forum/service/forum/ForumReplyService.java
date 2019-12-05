package com.xiaodou.forum.service.forum;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.RandomUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.forum.constant.ForumConstant;
import com.xiaodou.forum.enums.ForumResType;
import com.xiaodou.forum.model.forum.CommentUserModel;
import com.xiaodou.forum.model.forum.ForumCommentModel;
import com.xiaodou.forum.model.forum.ForumUserModel;
import com.xiaodou.forum.model.forum.RelateInfoUserModel;
import com.xiaodou.forum.request.forum.ForumReplyCommentsRequest;
import com.xiaodou.forum.request.forum.ForumReplyPostRequest;
import com.xiaodou.forum.response.forum.BaseResponse;
import com.xiaodou.forum.response.forum.ForumResponse;
import com.xiaodou.forum.service.facade.ForumServiceFacade;
import com.xiaodou.forum.service.queue.QueueService;
import com.xiaodou.forum.util.ForumUtil;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.model.UserModel;
import com.xiaodou.userCenter.response.BaseUserModel;

/**
 * 话题回复：回复帖子，回复评论 TODO 并发问题会导致话题参与人数的统计误差
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
        forumServiceFacade.queryForumUserByIdAndCategoryId(req.getForumId().toString(), req
            .getForumCategoryId().toString());
    if (null == forumUserModel) {
      return new ForumResponse(ForumResType.NULLFORUM);
    }
    // 1、增加评论
    ForumCommentModel forumCommentModel = new ForumCommentModel();
    forumCommentModel.setId(getId());
    forumCommentModel.setForumId(req.getForumId());
    forumCommentModel.setPublisherId(forumUserModel.getPublisherId());
    forumCommentModel.setNickName(req.getUserModel().getNickName());
    forumCommentModel.setReplyId(Long.parseLong(req.getUserModel().getId()));
    forumCommentModel.setContent(req.getContent());
    forumCommentModel.setOperator(req.getUserModel().getId().toString());
    forumCommentModel.setOperatorip(req.getClientIp());
    forumCommentModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
    forumCommentModel = forumServiceFacade.insertCommentEntity(forumCommentModel);
    // 发送通知
    pushRelateForumModel(forumUserModel, forumCommentModel, req.getUserModel());
    // 2、更新该帖子的回复总数
    try {
      queueService.updateCommentUnmber(String.valueOf(req.getForumId()));
    } catch (Exception e) {
      LoggerUtil.error("回复帖子，更新该帖子的回复总数异常", e);
    }

    // 3、更新话题分类参与人数
    try {
      queueService.updatePartakeNumber(String.valueOf(req.getForumCategoryId()));
    } catch (Exception e) {
      LoggerUtil.error("回复帖子，更新话题分类参与人数异常", e);
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
        forumServiceFacade.queryForumUserByIdAndCategoryId(req.getForumId().toString(), req
            .getForumCategoryId().toString());
    if (null == forumUserModel) {
      return new ForumResponse(ForumResType.NULLFORUM);
    }
    List<CommentUserModel> commentList =
        forumServiceFacade.queryForumCommentByForumId(req.getForumId().toString(),
            req.getCommentId(), 1, ForumUtil.getCommentListOutput());
    if (null != commentList && commentList.size() == 1) {
      CommentUserModel commentModel = commentList.get(0);
      // 1、增加评论的评论
      ForumCommentModel forumCommentModel = new ForumCommentModel();
      forumCommentModel.setId(getId());
      // 话题发布人ID
      forumCommentModel.setPublisherId(forumUserModel.getPublisherId());
      // 当前用户id
      forumCommentModel.setReplyId(Long.parseLong(req.getUserModel().getId()));
      // 目标评论人id
      forumCommentModel.setTargeId(commentModel.getReplyId());
      forumCommentModel.setOperator(req.getUserModel().getId().toString());
      forumCommentModel.setOperatorip(req.getClientIp());
      forumCommentModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
      forumCommentModel.setTargeCommentId(commentModel.getId());
      forumCommentModel.setForumId(req.getForumId());// 评论id
      forumCommentModel.setTargeContent(this.concatTargeContent(commentModel));// 目标评论内容
      forumCommentModel.setNickName(req.getUserModel().getNickName());
      forumCommentModel.setContent(req.getCommentContent());
      forumCommentModel = forumServiceFacade.insertCommentEntity(forumCommentModel);
      // 发送通知
      // pushRelateForumModel(forumUserModel, forumCommentModel);
      pushRelateCommentModel(forumUserModel, commentModel, forumCommentModel, req.getUserModel());
    } else {
      return new ForumResponse(ForumResType.NULLCOMMENT);
    }

    // 2、更新该帖子的回复总数
    try {
      queueService.updateCommentUnmber(String.valueOf(req.getForumId()));
    } catch (Exception e) {
      LoggerUtil.error("回复帖子，更新该帖子的回复总数异常", e);
    }

    // 3、更新话题分类参与人数
    try {
      queueService.updatePartakeNumber(String.valueOf(req.getForumCategoryId()));
    } catch (Exception e) {
      LoggerUtil.error("回复帖子，更新话题分类参与人数异常", e);
    }

    return response;
  }

  private Long getId() {
    return Long.parseLong((System.currentTimeMillis() + RandomUtil.randomNumber(6)).trim());
  }

  /**
   * 拼接目标评论内容并返回
   */
  private String concatTargeContent(ForumCommentModel model) {
    StringBuilder builder = new StringBuilder();
    builder.append("回复 " + model.getNickName() + ": " + model.getContent());
    return builder.toString();
  }

  private void pushRelateCommentModel(ForumUserModel forum, CommentUserModel oldComment,
      ForumCommentModel newComment, BaseUserModel baseUserModel) {
    RelateInfoUserModel model = new RelateInfoUserModel();
    if (null != forum && null != oldComment && null != newComment) {
      if (null == newComment.getReplyId()
          || newComment.getReplyId().equals(oldComment.getReplyId())) return;
      model.setTargeContent("回复了我的评论:" + newComment.getContent());
      model.setTargeCommentId(oldComment.getId());
      model.setTargeId(oldComment.getReplyId());
      model.setContent(oldComment.getContent());
      model.setCreateTime(new Timestamp(System.currentTimeMillis()));
      model.setForumId(Long.valueOf(forum.getId()));
      model.setCommentId(newComment.getId());
      model.setForumTitle(forum.getTitle());
      model.setReplyId(newComment.getReplyId());
      model.setType(ForumConstant.COMMENT_MES);
      model.setStatus(ForumConstant.STATUS_INIT);
      UserModel user = new UserModel();
      user.setNickName(baseUserModel.getNickName());
      user.setUserName(baseUserModel.getUserName());
      model.setUser(user);
      model.setTargetUser(oldComment.getUser());
      queueService.addPushTask(model);
    }
  }

  private void pushRelateForumModel(ForumUserModel forum, ForumCommentModel comment,
      BaseUserModel baseUserModel) {
    RelateInfoUserModel model = new RelateInfoUserModel();
    if (null != forum && null != comment) {
      if (null == comment.getReplyId() || comment.getReplyId().equals(forum.getPublisherId()))
        return;
      model.setTargeContent("回复了我的话题:" + comment.getContent());
      model.setTargeCommentId(0l);
      model.setTargeId(forum.getPublisherId());
      model.setCreateTime(new Timestamp(System.currentTimeMillis()));
      model.setForumId(Long.valueOf(forum.getId()));
      model.setCommentId(comment.getId());
      model.setForumTitle(forum.getTitle());
      model.setReplyId(comment.getReplyId());
      model.setType(ForumConstant.COMMENT_MES);
      model.setStatus(ForumConstant.STATUS_INIT);
      UserModel user = new UserModel();
      user.setNickName(baseUserModel.getNickName());
      user.setUserName(baseUserModel.getUserName());
      model.setUser(user);
      model.setTargetUser(forum.getUser());
      queueService.addPushTask(model);
    }
  }
}
