package com.xiaodou.resources.service.forum;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.async.toC.enums.AsyncMessageEnums.AsyncResInfo;
import com.xiaodou.async.toC.model.AddPraiseMesssage;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.resources.cache.forum.ForumPraiseCache;
import com.xiaodou.resources.enums.forum.ForumResType;
import com.xiaodou.resources.model.forum.CommentUserModel;
import com.xiaodou.resources.model.forum.ForumModel;
import com.xiaodou.resources.model.forum.ForumPraiseModel;
import com.xiaodou.resources.model.forum.ForumUserModel;
import com.xiaodou.resources.request.BaseRequest;
import com.xiaodou.resources.request.forum.AnyCountRequest;
import com.xiaodou.resources.request.forum.ForumPraiseRequest;
import com.xiaodou.resources.response.BaseResponse;
import com.xiaodou.resources.response.forum.CommonCountListResponse;
import com.xiaodou.resources.response.forum.ForumResponse;
import com.xiaodou.resources.response.forum.SumResponse;
import com.xiaodou.resources.service.QueueService;
import com.xiaodou.resources.service.forum.facade.ForumServiceFacade;
import com.xiaodou.resources.util.ForumUtil;
import com.xiaodou.resources.util.IDGenerator;
import com.xiaodou.resources.vo.forum.CommonCount;
import com.xiaodou.summer.vo.out.ResultType;

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
    if (StringUtils.isBlank(forumPraiseRequest.getUid())) {
      return new BaseResponse(ResultType.SYSFAIL);
    }
    String commentId = forumPraiseRequest.getCommentId();
    CommentUserModel comment = null;
    if (StringUtils.isBlank(forumPraiseRequest.getCommentId()))
      commentId = "0";
    else {
      commentId = forumPraiseRequest.getCommentId();
      comment = isExistedComment(forumPraiseRequest.getResourcesId(), commentId);
      if (null == comment) {
        return new ForumResponse(ForumResType.NULLCOMMENT);
      }
    }
    ForumUserModel forum = isExistedForum(forumPraiseRequest.getResourcesId());
    if (null == forum) {
      return new ForumResponse(ForumResType.NULLFORUM);
    }
    // 添加点赞表数据
    ForumPraiseModel forumPraiseModel =
        ForumPraiseCache.getForumPraiseFromCache(forum.getId().toString(), commentId,
            forumPraiseRequest.getUid());
    if (null != forumPraiseModel) {
      return new ForumResponse(ForumResType.NULLFORUM);
    }
    forumPraiseModel = new ForumPraiseModel();
    forumPraiseModel.setId(Long.parseLong(IDGenerator.getSeqID()));
    forumPraiseModel.setResourcesId(Long.parseLong(forumPraiseRequest.getResourcesId()));
    forumPraiseModel.setReleaseUser(String.valueOf(forum.getPublisherId()));
    forumPraiseModel.setCommentId(Long.parseLong((commentId)));
    // 在token中取
    forumPraiseModel.setReplyId(Long.parseLong(forumPraiseRequest.getUid()));
    forumPraiseModel.setOperator(forumPraiseRequest.getUid());
    forumPraiseModel.setOperatorip(forumPraiseRequest.getClientIp());
    forumPraiseModel.setCreateTime(new Timestamp(System.currentTimeMillis()));
    try {
      forumPraiseModel = forumServiceFacade.insertPraiseEntity(forumPraiseModel);
      if (null == forumPraiseModel) {
        return new BaseResponse(ResultType.SYSFAIL);
      }
      ForumPraiseCache.addForumPraiseCache(forumPraiseModel, forum.getId().toString(), commentId,
          forumPraiseRequest.getUid());
    } catch (Exception e) {
      LoggerUtil.error("[联合唯一索引不能有重复信息]", e);
      BaseResponse response = new BaseResponse(ResultType.VALFAIL);
      response.setRetdesc("不能重复点赞");
      return response;
    }
    // 发送通知消息
    AddPraiseMesssage message = new AddPraiseMesssage();
    message.setModule(forumPraiseRequest.getModule());
    message.setSrcUid(forumPraiseRequest.getUid());
    if (StringUtils.isBlank(forumPraiseRequest.getCommentId()))
      message.setToUid(forumPraiseModel.getReleaseUser());
    else
      message.setToUid(comment.getUser().getId().toString());
    message.addCallBackContent("resourcesId", forum.getId());
    message.addCallBackContent("digest", forum.getDigest().toString());
    if (StringUtils.isBlank(forumPraiseRequest.getCommentId())) {
      if (forum.getDigest().toString().equals("0")) {
        String mes = forum.getContent();
        if (mes.length() > 10) {
          mes = mes.substring(0, 10) + "...";
        }
        message.setRetCode(AsyncResInfo.AddPraiseAsk.getRetCode());
        message.setRetDesc(String.format(AsyncResInfo.AddPraiseAsk.getRetDesc(), mes));
        message.setMessageBody(String.format(AsyncResInfo.AddPraiseAsk.getMesTmp(),
            forum.getContent()));
        message.addCallBackContent("name", mes);
      } else {
        message.setRetCode(AsyncResInfo.AddPraise.getRetCode());
        message.setRetDesc(String.format(AsyncResInfo.AddPraise.getRetDesc(), forum.getTitle()));
        message.setMessageBody(String.format(AsyncResInfo.AddPraise.getMesTmp(), forum.getTitle()));
        message.addCallBackContent("name", forum.getTitle());
      }
    } else {
      if (forum.getDigest().toString().equals("0")) {
        String mes = forum.getContent();
        if (mes.length() > 10) {
          mes = mes.substring(0, 10) + "...";
        }
        message.setRetCode(AsyncResInfo.AddPraiseAskComment.getRetCode());
        message.setRetDesc(String.format(AsyncResInfo.AddPraiseAskComment.getRetDesc(), mes));
        message.setMessageBody(String.format(AsyncResInfo.AddPraiseAskComment.getMesTmp(), mes));
        message.addCallBackContent("name", mes);

      } else {
        message.setRetCode(AsyncResInfo.AddPraiseComment.getRetCode());
        message.setRetDesc(String.format(AsyncResInfo.AddPraiseComment.getRetDesc(),
            forum.getTitle()));
        message.setMessageBody(String.format(AsyncResInfo.AddPraiseComment.getMesTmp(),
            forum.getTitle()));
        message.addCallBackContent("name", forum.getTitle());
      }
    }
    message.send();
    // 找到对应的话题或回复更新点赞数
    updatePraise(true, Long.parseLong(forumPraiseRequest.getResourcesId()),
        Long.parseLong(commentId));
    return new BaseResponse(ResultType.SUCCESS);
  }



  /**
   * 取消点赞
   */
  public BaseResponse deletePraise(ForumPraiseRequest forumPraiseRequest) {
    if (StringUtils.isBlank(forumPraiseRequest.getUid())) {
      return new BaseResponse(ResultType.SYSFAIL);
    }
    String commentId = forumPraiseRequest.getCommentId();
    if (StringUtils.isBlank(forumPraiseRequest.getCommentId()))
      commentId = "0";
    else {
      commentId = forumPraiseRequest.getCommentId();
      CommentUserModel comment = isExistedComment(forumPraiseRequest.getResourcesId(), commentId);
      if (null == comment) {
        return new ForumResponse(ForumResType.NULLCOMMENT);
      }
    }
    ForumModel forum = isExistedForum(forumPraiseRequest.getResourcesId());
    if (null == forum) {
      return new ForumResponse(ForumResType.NULLFORUM);
    }
    Map<String, Object> input = new HashMap<String, Object>();
    input.put("resourcesId", forumPraiseRequest.getResourcesId());
    input.put("commentId", commentId);
    // 在token中取
    input.put("replyId", forumPraiseRequest.getUid());
    int flag = forumServiceFacade.deletePraiseList(input);
    if (flag != 1) {
      BaseResponse response = new BaseResponse(ResultType.VALFAIL);
      response.setRetdesc("取消点赞失败");
      return response;
    }
    // 找到对应的话题或回复更新点赞数
    updatePraise(false, Long.parseLong(forumPraiseRequest.getResourcesId()),
        Long.parseLong(commentId));
    return new BaseResponse(ResultType.SUCCESS);
  }

  /**
   * 点赞取消点赞接口 更新点赞数
   */
  public void updatePraise(boolean praise, long forumId, long commentId) {
    ForumPraiseModel model = new ForumPraiseModel();
    model.setResourcesId(forumId);
    model.setCommentId(commentId);
    model.setPraise(praise);
    queueService.updatePraiseNumber(model);
  }

  /**
   * 获取个人被点赞的总数
   */
  public SumResponse pariseCountByUser(BaseRequest pojo) {
    SumResponse response = new SumResponse(ResultType.SUCCESS);
    Map<String, Object> input = new HashMap<String, Object>();
    input.put("releaseUser", pojo.getUid());
    Integer i = forumServiceFacade.queryPraiseNumber(input);
    response.setSum(i);
    return response;
  }
/**
 * 
 * 所有人的被点赞数量
 * 
 * @param columnId
 * @return
 */
public CommonCountListResponse getAllResourcesCount(AnyCountRequest pojo) {
	CommonCountListResponse response = new CommonCountListResponse(ResultType.SUCCESS);
	Map<String, Object> input = new HashMap<String, Object>();
	Map<String, Object> cond = new HashMap<String, Object>();
	List<String> idList = Arrays.asList(pojo.getIds().split(","));
	input.put("ids",idList);
	cond.put("input", input);
	List<CommonCount> list = forumServiceFacade.queryUsersPraiseNumber(cond);
	response.setList(list);
	return response;
}
}
