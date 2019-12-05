package com.xiaodou.forum.service.forum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.forum.model.forum.CommentForumModel;
import com.xiaodou.forum.model.forum.ForumUserModel;
import com.xiaodou.forum.model.forum.RelateInfoUserModel;
import com.xiaodou.forum.request.forum.IgnoreRelateCommentsRequest;
import com.xiaodou.forum.request.forum.MyCommentRequest;
import com.xiaodou.forum.request.forum.RelateCommentsRequest;
import com.xiaodou.forum.response.forum.IgnoreRelateCommentsResponse;
import com.xiaodou.forum.response.forum.MyCommentResponse;
import com.xiaodou.forum.response.forum.RelateCommentResponse;
import com.xiaodou.forum.service.facade.ForumServiceFacade;
import com.xiaodou.forum.util.ForumUtil;
import com.xiaodou.forum.vo.forum.MyComment;
import com.xiaodou.forum.vo.forum.RelateComment;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * 
 * @author hualong.li
 * 
 */
@Service
public class CommentListService {

  @Resource
  ForumServiceFacade forumServiceFacade;


  /**
   * 我发布的话题列表
   * 
   * @param request
   * @return
   * @throws Exception
   */
  public MyCommentResponse getMyComment(MyCommentRequest request) throws Exception {
    MyCommentResponse response = new MyCommentResponse(ResultType.SUCCESS);
    List<MyComment> list =
        queryMyComment(request.getUserModel().getId(), request.getSize(), null,
            request.getCommentId());
    response.setList(list);
    return response;
  }

  private List<MyComment> queryMyComment(String userId, Integer size, String ids, String commentId) {
    Map<String, Object> cond = new HashMap<String, Object>();
    Map<String, Object> input = new HashMap<String, Object>();
    input.put("replyId", userId);
    // 这里去数据库中查询实际使用idUpper
    if (StringUtils.isNotBlank(commentId) && !"0".equals(commentId)) {
      input.put("idUpper", commentId);
    }
    cond.put("input", input);
    if (size == null) {
      cond.put("limitCount", 1);
    } else
      cond.put("limitCount", size);
    if (StringUtils.isNotBlank(ids)) {
      input.put("ids", ids);
    } else {
      Map<String, Object> sort = new HashMap<String, Object>();
      sort.put("createTime", "DESC");
      cond.put("sort", sort);
    }
    cond.put("output", ForumUtil.getMyCommentListOutput());
    List<CommentForumModel> comments = forumServiceFacade.queryCommentInForumListByCondNoPage(cond);

    List<MyComment> topComment = new ArrayList<MyComment>();
    for (CommentForumModel model : comments) {
      try {
        MyComment comment = new MyComment();
        comment.setId(model.getId().toString());
        comment.setForumId(model.getForumId().toString());
        comment.setTitle(model.getForum().getTitle());
        comment.setContent(model.getContent());
        comment.setTime(model.getCreateTime());
        comment.setTargeId(model.getTargeId().toString());
        comment.setTargeContent(this.getModifyTargeContent(model.getTargeContent(), 1));
        comment.setPublisherNickName(this.getPublisherNickName(model.getForumId()));
        topComment.add(comment);
      } catch (Exception e) {
        LoggerUtil.error("话题评论表中存在脏数据  : " + model.getId(), e);
        continue;
      }

    }

    return topComment;
  }

  /**
   * 根据用户id查询用户的昵称
   */
  private String getPublisherNickName(Long forumId) {
    ForumUserModel forumUser = forumServiceFacade.queryForumUserById(forumId.toString());
    if (null == forumUser || null == forumUser.getUser()) return null;
    String publisherNickName = forumUser.getUser().getNickName();
    return this.getModifyTargeContent(publisherNickName, 0);
  }

  /**
   * 针对我参与这块的目标评论内容
   * 
   * @param content：内容
   * @param flag：属于回帖还是回复评论
   */
  private String getModifyTargeContent(String content, Integer flag) {
    StringBuilder builder = new StringBuilder();
    if (null == content) return null;
    if (flag == 0) {
      builder.append("我评论了" + content + "的话题");
    } else if (flag == 1) {
      builder.append("我" + this.replaceString(content, "了", 1));
    } else {
      return null;
    }
    return builder.toString();
  }

  /**
   * 自定义replace方法，替换指定位置的字符
   * 
   * @param 原内容
   * @param 替换内容
   * @param 下角标（替换位置）
   */
  private String replaceString(String str, String str1, int a) {
    str = str.replace(":", "的评论:");
    String searchStr = " ";
    int index = str.indexOf(searchStr);
    int count = 1;
    while (count != a) {
      index = str.indexOf(searchStr, index + 1);
      count++;
    }
    return str.substring(0, index) + str1 + str.substring(index + 1);
  }

  public RelateCommentResponse myRelateComments(RelateCommentsRequest pojo) {
    RelateCommentResponse response = new RelateCommentResponse(ResultType.SUCCESS);
    response.setList(queryRelateComments(pojo.getUserModel().getId(), pojo.getSize(), null,
        pojo.getIdUpper()));
    return response;
  }

  public List<RelateComment> queryRelateComments(String userId, Integer size, String ids,
      String commentId) {

    Map<String, Object> cond = new HashMap<String, Object>();
    Map<String, Object> input = new HashMap<String, Object>();
    input.put("targeId", userId);
    // 这里去数据库中查询实际使用idUpper
    if (StringUtils.isNotBlank(commentId) && !"0".equals(commentId)) {
      input.put("idUpper", commentId);
    }
    cond.put("input", input);
    if (size == null) {
      cond.put("limitCount", 1);
    } else
      cond.put("limitCount", size);
    if (StringUtils.isNotBlank(ids)) {
      input.put("ids", ids);
    } else {
      Map<String, Object> sort = new HashMap<String, Object>();
      sort.put("createTime", "DESC");
      cond.put("sort", sort);
    }
    cond.put("output", ForumUtil.getMyRelateInfoOutput());
    List<RelateInfoUserModel> comments = forumServiceFacade.queryRelationInfoListByCondNoPage(cond);

    List<RelateComment> relateCommentList = Lists.newArrayList();
    for (RelateInfoUserModel model : comments) {
      try {
        RelateComment relateComment = new RelateComment();
        relateComment.setCid(model.getId().toString());
        relateComment.setType(model.getType().toString());
        relateComment.setStatus(model.getStatus().toString());
        relateComment.setForumId(model.getForumId().toString());
        if (null != model.getCommentId()) {
          relateComment.setCommentId(model.getCommentId().toString());// 赋值
        }
        /**
         * 查询点赞数 start
         */
        Map<String, Object> inputArgument = Maps.newHashMap();
        inputArgument.put("forumId", model.getForumId());
        inputArgument.put("commentId", model.getCommentId());
        relateComment.setPraiseNumber(forumServiceFacade.queryPraiseNumber(inputArgument)
            .toString());
        /**
         * 查询点赞数 start
         */
        relateComment.setTitle(model.getForumTitle());
        relateComment.setContent(model.getContent());
        relateComment.setTime(model.getCreateTime());
        if (null != model.getTargeId()) relateComment.setTargeId(model.getTargeId().toString());
        relateComment.setTargeContent(model.getUser().getNickName() + model.getTargeContent());
        if (null != model.getTargeCommentId())
          relateComment.setTargeCommentId(model.getTargeCommentId().toString());
        relateComment.setPublisherPortrait(model.getUser().getPortrait());
        relateComment.setPublisherNickName(model.getUser().getNickName());
        // 根据id查询话题内容
        ForumUserModel forumUserModel =
            forumServiceFacade.queryForumUserById(model.getForumId().toString());
        relateComment.setForumCategoryId(forumUserModel.getCategoryId());
        relateComment.setForumCategoryName(forumUserModel.getCategoryName());
        relateCommentList.add(relateComment);
      } catch (Exception e) {
        LoggerUtil.error("通知消息表中存在脏数据  : " + model.getId(), e);
        continue;
      }
    }
    return relateCommentList;
  }

  /**
   * 忽略所有未读消息
   * 
   * @param pojo
   * @return
   */
  public IgnoreRelateCommentsResponse ignoreRelateComments(IgnoreRelateCommentsRequest pojo) {
    forumServiceFacade.updateMyRelateInfoIgnore(pojo.getUserModel().getId());
    return new IgnoreRelateCommentsResponse(ResultType.SUCCESS);
  }
}
