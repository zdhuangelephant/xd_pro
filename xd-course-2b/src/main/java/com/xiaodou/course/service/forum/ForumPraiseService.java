package com.xiaodou.course.service.forum;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.RandomUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.course.cache.ForumPraiseCache;
import com.xiaodou.course.common.enums.ForumResType;
import com.xiaodou.course.model.forum.ForumModel;
import com.xiaodou.course.model.forum.ForumPraiseModel;
import com.xiaodou.course.service.facade.ForumServiceFacade;
import com.xiaodou.course.service.queue.QueueService;
import com.xiaodou.course.web.request.forum.ForumPraiseRequest;
import com.xiaodou.course.web.response.BaseResponse;
import com.xiaodou.course.web.response.forum.ForumResponse;
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
  ForumService forumService;
  @Resource
  ForumServiceFacade forumServiceFacade;
  @Resource
  QueueService queueService;


  /**
   * 判断话题是否存在
   */
  private ForumModel isExistedForum(String forumId) {
    // 查询话题表中是否有该话题数据
    return forumService.getForumModelById(forumId);
  }

  /**
   * 点赞
   */
  public BaseResponse insertPraise(ForumPraiseRequest forumPraiseRequest) {
    if (StringUtils.isBlank(forumPraiseRequest.getUid())) {
      return new BaseResponse(ResultType.SYSFAIL);
    }
    ForumModel forum = isExistedForum(forumPraiseRequest.getResourcesId());
    if (null == forum) {
      return new ForumResponse(ForumResType.NULLFORUM);
    }
    // 添加点赞表数据
    ForumPraiseModel forumPraiseModel =
        ForumPraiseCache.getForumPraiseFromCache(forum.getForumId().toString(), "0",
            forumPraiseRequest.getUid());
    if (null != forumPraiseModel) {
      return new ForumResponse(ForumResType.NULLFORUM);
    }
    forumPraiseModel = new ForumPraiseModel();
    forumPraiseModel.setId(Long.valueOf(RandomUtil.randomNumber(9)));
    forumPraiseModel.setResourcesId(Long.parseLong(forumPraiseRequest.getResourcesId()));
    forumPraiseModel.setReleaseUser(String.valueOf(forum.getAuthorId()));
    forumPraiseModel.setCommentId(Long.parseLong(("0")));
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
      ForumPraiseCache.addForumPraiseCache(forumPraiseModel, forum.getForumId().toString(), "0",
          forumPraiseRequest.getUid());
    } catch (Exception e) {
      LoggerUtil.error("[联合唯一索引不能有重复信息]", e);
      BaseResponse response = new BaseResponse(ResultType.VALFAIL);
      response.setRetdesc("不能重复点赞");
      return response;
    }
    // 找到对应的话题或回复更新点赞数
    updatePraise(true, Long.parseLong(forumPraiseRequest.getResourcesId()),
        0);
    return new BaseResponse(ResultType.SUCCESS);
  }



  /**
   * 取消点赞
   */
  public BaseResponse deletePraise(ForumPraiseRequest forumPraiseRequest) {
    if (StringUtils.isBlank(forumPraiseRequest.getUid())) {
      return new BaseResponse(ResultType.SYSFAIL);
    }
    ForumModel forum = isExistedForum(forumPraiseRequest.getResourcesId());
    if (null == forum) {
      return new ForumResponse(ForumResType.NULLFORUM);
    }
    Map<String, Object> input = new HashMap<String, Object>();
    input.put("resourcesId", forumPraiseRequest.getResourcesId());
    input.put("commentId", "0");
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
        0);
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

}
