package com.xiaodou.forum.service.forum;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.forum.cache.ForumPraiseCache;
import com.xiaodou.forum.constant.ForumConstant;
import com.xiaodou.forum.model.forum.CommentUserModel;
import com.xiaodou.forum.model.forum.ForumModel;
import com.xiaodou.forum.model.forum.ForumPraiseModel;
import com.xiaodou.forum.model.forum.ForumUserModel;
import com.xiaodou.forum.model.forum.RelateInfoUserModel;
import com.xiaodou.forum.request.forum.ForumPraiseRequest;
import com.xiaodou.forum.response.forum.BaseResponse;
import com.xiaodou.forum.service.facade.ForumServiceFacade;
import com.xiaodou.forum.service.queue.QueueService;
import com.xiaodou.forum.util.ForumUtil;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.userCenter.model.UserModel;
import com.xiaodou.userCenter.response.BaseUserModel;

/**
 * 点赞service
 * 
 * @author wuyunkuo
 * 
 */
@Service("forumPraiseService")
public class ForumPraiseService {

  @Resource
  ForumServiceFacade forumServiceFacade;

  @Resource
  QueueService queueService;

  /**
   * 判断话题是否存在
   */
  private ForumUserModel isExistedForum(String forumId) {
    // 查询话题表中是否有该话题数据
    return forumServiceFacade.queryForumUserById(forumId);
  }

  /**
   * 判断评论是否存在
   */
  private CommentUserModel isExistedComment(String forumId, String commentId) {
    // 查询评论表中是否存在该评论
    CommentUserModel comment = new CommentUserModel();
    List<CommentUserModel> commentList =
        forumServiceFacade.queryForumCommentByForumId(forumId, commentId, 1,
            ForumUtil.getCommentListOutput());
    if (null == commentList || commentList.size() == 0) {
      return null;
    }
    comment = commentList.get(0);
    return comment;
  }

  /**
   * 点赞
   */
  public BaseResponse insertPraise(ForumPraiseRequest forumPraiseRequest) {
    if (StringUtils.isBlank(forumPraiseRequest.getUserModel().getId().toString())) {
      return new BaseResponse(ResultType.SYSFAIL);
    }
    String commentId = forumPraiseRequest.getCommentId();
    CommentUserModel comment = null;
    if (StringUtils.isBlank(forumPraiseRequest.getCommentId()))
      commentId = "0";
    else {
      commentId = forumPraiseRequest.getCommentId();
      comment = isExistedComment(forumPraiseRequest.getForumId(), commentId);
      if (null == comment) {
        return new BaseResponse(ResultType.SYSFAIL);
      }
    }
    ForumUserModel forum = isExistedForum(forumPraiseRequest.getForumId());
    if (null == forum) {
      return new BaseResponse(ResultType.SYSFAIL);
    }
    // 添加点赞表数据
    ForumPraiseModel forumPraiseModel =
        ForumPraiseCache.getForumPraiseFromCache(forum.getId().toString(), commentId,
            forumPraiseRequest.getUserModel().getId().toString());
    if (null != forumPraiseModel) {
      return new BaseResponse(ResultType.SUCCESS);
    }
    forumPraiseModel = new ForumPraiseModel();
    forumPraiseModel.setForumId(Long.parseLong(forumPraiseRequest.getForumId()));

    forumPraiseModel.setCommentId(Long.parseLong((commentId)));
    // 在token中取
    forumPraiseModel.setReplyId(Long.parseLong(forumPraiseRequest.getUserModel().getId()));
    forumPraiseModel.setOperator(forumPraiseRequest.getUserModel().getId().toString());
    forumPraiseModel.setOperatorip(forumPraiseRequest.getClientIp());
    try {
      forumPraiseModel = forumServiceFacade.insertPraiseEntity(forumPraiseModel);
      if (null == forumPraiseModel) {
        return new BaseResponse(ResultType.SYSFAIL);
      }
      ForumPraiseCache.addForumPraiseCache(forumPraiseModel, forum.getId().toString(), commentId,
          forumPraiseRequest.getUserModel().getId().toString());
    } catch (Exception e) {
      LoggerUtil.error("[联合唯一索引不能有重复信息]", e);
      return new BaseResponse(ResultType.SUCCESS);
    }

    // 发送通知消息
    if (null == comment)
      pushRelateForumModel(forum, forumPraiseModel, forumPraiseRequest.getUserModel());
    else
      pushRelateCommentModel(forum, comment, forumPraiseModel, forumPraiseRequest.getUserModel());

    // 找到对应的话题或回复更新点赞数
    updatePraise(true, Long.parseLong(forumPraiseRequest.getForumId()), Long.parseLong(commentId));
    return new BaseResponse(ResultType.SUCCESS);
  }

  private void pushRelateCommentModel(ForumUserModel forum, CommentUserModel comment,
      ForumPraiseModel forumPraiseModel, BaseUserModel baseUserModel) {
    RelateInfoUserModel model = new RelateInfoUserModel();
    if (null != forum && null != comment) {
      if (null == forumPraiseModel.getReplyId()
          || forumPraiseModel.getReplyId().equals(comment.getReplyId())) return;
      model.setTargeContent("赞了我的评论");
      model.setTargeCommentId(comment.getId());
      model.setTargeId(comment.getReplyId());
      model.setContent(comment.getContent());
      model.setCreateTime(new Timestamp(System.currentTimeMillis()));
      model.setForumId(Long.valueOf(forum.getId()));
      model.setForumTitle(forum.getTitle());
      model.setReplyId(forumPraiseModel.getReplyId());
      model.setType(ForumConstant.PRAISE_MES);
      model.setStatus(ForumConstant.STATUS_INIT);
      UserModel user = new UserModel();
      user.setNickName(baseUserModel.getNickName());
      user.setUserName(baseUserModel.getUserName());
      model.setUser(user);
      model.setTargetUser(comment.getUser());
      queueService.addPushTask(model);
    }
  }

  private void pushRelateForumModel(ForumUserModel forum, ForumPraiseModel forumPraiseModel,
      BaseUserModel baseUserModel) {
    RelateInfoUserModel model = new RelateInfoUserModel();
    if (null != forum) {
      if (null == forumPraiseModel.getReplyId()
          || forumPraiseModel.getReplyId().equals(forum.getPublisherId())) return;
      model.setTargeContent("赞了我的话题");
      model.setTargeCommentId(0l);
      model.setTargeId(forum.getPublisherId());
      model.setCreateTime(new Timestamp(System.currentTimeMillis()));
      model.setForumId(Long.valueOf(forum.getId()));
      model.setForumTitle(forum.getTitle());
      model.setReplyId(forumPraiseModel.getReplyId());
      model.setType(ForumConstant.PRAISE_MES);
      model.setStatus(ForumConstant.STATUS_INIT);
      UserModel user = new UserModel();
      user.setNickName(baseUserModel.getNickName());
      user.setUserName(baseUserModel.getUserName());
      model.setUser(user);
      model.setTargetUser(forum.getUser());
      queueService.addPushTask(model);
    }
  }

  /**
   * 取消点赞
   */
  public BaseResponse deletePraise(ForumPraiseRequest forumPraiseRequest) {
    if (StringUtils.isBlank(forumPraiseRequest.getUserModel().getId().toString())) {
      return new BaseResponse(ResultType.SYSFAIL);
    }
    String commentId = forumPraiseRequest.getCommentId();
    if (StringUtils.isBlank(forumPraiseRequest.getCommentId()))
      commentId = "0";
    else {
      commentId = forumPraiseRequest.getCommentId();
      CommentUserModel comment = isExistedComment(forumPraiseRequest.getForumId(), commentId);
      if (null == comment) {
        return new BaseResponse(ResultType.SYSFAIL);
      }
    }
    ForumModel forum = isExistedForum(forumPraiseRequest.getForumId());
    if (null == forum) {
      return new BaseResponse(ResultType.SYSFAIL);
    }
    Map<String, Object> input = new HashMap<String, Object>();
    input.put("forumId", forumPraiseRequest.getForumId());
    input.put("commentId", commentId);
    // 在token中取
    input.put("replyId", forumPraiseRequest.getUserModel().getId());
    int flag = forumServiceFacade.deletePraiseList(input);
    if (flag != 1) return new BaseResponse(ResultType.SYSFAIL);
    // 找到对应的话题或回复更新点赞数
    updatePraise(false, Long.parseLong(forumPraiseRequest.getForumId()), Long.parseLong(commentId));
    return new BaseResponse(ResultType.SUCCESS);
  }

  /**
   * 点赞取消点赞接口 更新点赞数
   */
  public void updatePraise(boolean praise, long forumId, long commentId) {
    ForumPraiseModel model = new ForumPraiseModel();
    model.setForumId(forumId);
    model.setCommentId(commentId);
    model.setPraise(praise);
    queueService.updatePraiseNumber(model);
  }

}
