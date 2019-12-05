package com.xiaodou.forum.service.forum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.forum.model.forum.CommentUserModel;
import com.xiaodou.forum.model.forum.ForumPraiseModel;
import com.xiaodou.forum.model.forum.ForumRelateInfoModel;
import com.xiaodou.forum.model.forum.ForumUserModel;
import com.xiaodou.forum.request.forum.ForumDetailCommentsRequest;
import com.xiaodou.forum.request.forum.ForumDetailForumRequest;
import com.xiaodou.forum.response.forum.ForumDetailCommentsResponse;
import com.xiaodou.forum.response.forum.ForumDetailForumResponse;
import com.xiaodou.forum.service.facade.ForumServiceFacade;
import com.xiaodou.forum.service.queue.QueueService;
import com.xiaodou.forum.util.FileUtils;
import com.xiaodou.forum.util.ForumUtil;
import com.xiaodou.forum.vo.forum.Comment;
import com.xiaodou.forum.vo.forum.Forum;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * 
 * 话题详情服务
 * 
 * <p>
 * 修改历史: <br>
 * 修改日期 修改人员 版本 修改内容<br>
 * -------------------------------------------------<br>
 * 2015年4月15日 上午8:45:51 weichao.zhai 1.0 初始化创建<br>
 * </p>
 * 
 * @author weichao.zhai
 * @version 1.0
 */
@Service
public class ForumDetailService {

  @Resource
  ForumServiceFacade forumServiceFacade;

  @Resource
  QueueService queueService;

  /**
   * 
   * 话题详情－查询话题内容
   * 
   * @param request
   * @return
   */
  public ForumDetailForumResponse forumContent(ForumDetailForumRequest request) {
    ForumDetailForumResponse response = new ForumDetailForumResponse(ResultType.SUCCESS);
    if (StringUtils.isNotBlank(request.getRelateCommentId())) {
      ForumRelateInfoModel model = new ForumRelateInfoModel();
      model.setId(Long.parseLong(request.getRelateCommentId()));
      model.setForumId(Long.parseLong(request.getForumId()));
      model.setTargeId(Long.parseLong(request.getUserModel().getId()));
      queueService.updateRelateInfo(model);
    }
    // 根据id查询话题内容
    ForumUserModel forumUserModel = forumServiceFacade.queryForumUserById(request.getForumId());
    if (null != forumUserModel) {
      if (null != request.getUserModel()) {
        // 根据用户id和话题id查询话题点赞是否被点赞过
        Map<String, Object> queryCond = new HashMap<String, Object>();
        queryCond.put("forumId", request.getForumId());
        // 这里保证查询的commentID为0，即查询的为针对话题点赞的记录
        queryCond.put("commentId", "0");
        if (null != request.getUserModel()) {
          queryCond.put("operator", request.getUserModel().getId());
        }
        List<ForumPraiseModel> praiseList = forumServiceFacade.queryPraiseList(queryCond, null);
        // 这里记录条目大于0就认为是点赞成功，可以抛出异常供分析，程序本身要做容错
        if (null != praiseList && praiseList.size() > 0) {
          if (praiseList.size() > 1)
            LoggerUtil.error("针对话题点赞数异常。大于1条点赞。" + FastJsonUtil.toJson(praiseList),
                new RuntimeException());
          forumUserModel.setIsPraise("1");
        }
      } else {
        forumUserModel.setIsPraise("0");
      }
      response.setForum(new Forum(forumUserModel));
    }
    return response;

  }

  /**
   * 
   * 话题详情－查询话题内容(没有分页)
   * 
   * @param request
   * @return
   */
  public ForumDetailCommentsResponse forumComments(ForumDetailCommentsRequest request) {
    ForumDetailCommentsResponse response = new ForumDetailCommentsResponse(ResultType.SUCCESS);

    // 用于存储评论列表，最后统一针对点赞的评论做处理
    Map<Long, Comment> commentMap = new HashMap<Long, Comment>();

    if (StringUtils.isBlank(request.getCommentId())) {
      List<CommentUserModel> hotComments =
          forumServiceFacade.queryForumCommentByForumIdForHot(request.getForumId(),
              FileUtils.FORUM_PROP.getPropertiesInt("forum.detail.comment.hot.size"),
              ForumUtil.getCommentListOutput());
      List<Comment> hot = new ArrayList<Comment>();
      for (CommentUserModel model : hotComments) {
        // 先尝试从Map中取，如果能取到，则使用map中的对象
        Comment comment = commentMap.get(model.getId());
        // 如果不能取到，则根据数据库表数据做转化，然后存储至map中
        if (null == comment) {
          comment = convertForumCommentToComment(model);
          commentMap.put(model.getId(), comment);
        }
        hot.add(comment);
      }
      response.setHot(hot);
    }

    List<CommentUserModel> comments =
        forumServiceFacade.queryForumCommentByForumIdPull(request.getForumId(),
            request.getCommentId(), request.getSize(), ForumUtil.getCommentListOutput());
    List<Comment> list = new ArrayList<Comment>();
    for (CommentUserModel model : comments) {
      Comment comment = commentMap.get(model.getId());
      if (null == comment) {
        comment = convertForumCommentToComment(model);
        commentMap.put(model.getId(), comment);
      }
      list.add(comment);
    }

    if (null != request.getUserModel()) {
      Map<String, Object> inputArgument = new HashMap<String, Object>();
      inputArgument.put("forumId", request.getForumId());
      if (null != request.getUserModel()) {
        inputArgument.put("operator", request.getUserModel().getId());
      }
      Map<String, Object> outputField = new HashMap<String, Object>();
      outputField.put("commentId", "1");
      List<ForumPraiseModel> forumPraiseModels =
          forumServiceFacade.queryPraiseList(inputArgument, outputField);

      for (ForumPraiseModel forumPraiseModel : forumPraiseModels) {
        // 这里注意key的类型要保持一致，这里都为long类型
        if (commentMap.containsKey(forumPraiseModel.getCommentId())) {
          commentMap.get(forumPraiseModel.getCommentId()).setIsPraise("1");
        }
      }
    }

    response.setList(list);

    return response;

  }

  /**
   * 构造评论返回模型
   * 
   * @param model
   * @return
   */
  public Comment convertForumCommentToComment(CommentUserModel model) {
    if (null == model) return null;
    Comment comment = new Comment();
    if (null != model.getId()) comment.setCommentId(model.getId().toString());
    if (StringUtils.isNotBlank(model.getNickName())) comment.setNickName(model.getNickName());
    if (StringUtils.isNotBlank(model.getContent())) comment.setContent(model.getContent());
    if (null != model.getForumId()) comment.setForumId(model.getForumId().toString());
    if (null != model.getTargeId()) comment.setTargeId(model.getTargeId().toString());
    if (null != model.getTargeContent()) comment.setTargeContent(model.getTargeContent());
    if (null != model.getUser() && StringUtils.isNotBlank(model.getUser().getNickName()))
      comment.setPeople(model.getUser().getNickName());
    if (null != model.getUser() && StringUtils.isNotBlank(model.getUser().getPortrait()))
      comment.setPortrait(model.getUser().getPortrait());
    if (null != model.getCreateTime()) comment.setTime(model.getCreateTime());
    if (null != model.getType()) comment.setType(model.getType().toString());
    if (null != model.getPraiseNumber())
      comment.setPraiseNumber(model.getPraiseNumber().toString());

    return comment;

  }
}
