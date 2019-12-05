package com.xiaodou.forum.util;

import java.util.HashMap;
import java.util.Map;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.forum.model.forum.CommentUserModel;
import com.xiaodou.forum.model.forum.ForumCommentModel;
import com.xiaodou.forum.model.forum.ForumModel;
import com.xiaodou.forum.model.forum.ForumRelateInfoModel;
import com.xiaodou.forum.vo.forum.Comment;

public class ForumUtil {

  public static Map<String, Object> getAbstractForumListOutput() {
    Map<String, Object> output = new HashMap<String, Object>();
    output.put("forumId", "1");
    output.put("top", "1");
    return output;
  }

  public static Map<String, Object> getForumListOutput() {
    Map<String, Object> output = new HashMap<String, Object>();
    CommUtil.getGeneralField(output, ForumModel.class);
    output.put("forumId", "1");
    output.put("userId", "1");
    output.put("userName", "1");
    output.put("userNickname", "1");
    output.put("userPortrait", "1");
    output.put("userAddress", "1");
    output.put("userAge", "1");
    return output;
  }

  public static Map<String, Object> getMyForumListOutput() {
    Map<String, Object> output = getForumListOutput();
    return output;
  }

  public static Map<String, Object> getCommentListOutput() {
    Map<String, Object> output = getMyCommentListOutput();
    output.put("userId", "1");
    output.put("userName", "1");
    output.put("userNickname", "1");
    output.put("userPortrait", "1");
    output.put("userAddress", "1");
    output.put("userAge", "1");
    return output;
  }

  public static Map<String, Object> getMyCommentListOutput() {
    Map<String, Object> output = new HashMap<String, Object>();
    CommUtil.getGeneralField(output, ForumCommentModel.class);
    output.put("title", "1");
    output.put("commentId", "1");
    return output;
  }

  public static Map<String, Object> getMyRelateInfoOutput() {
    Map<String, Object> output = new HashMap<String, Object>();
    CommUtil.getGeneralField(output, ForumRelateInfoModel.class);
    output.put("userId", "1");
    output.put("userName", "1");
    output.put("userNickname", "1");
    output.put("userPortrait", "1");
    output.put("userAddress", "1");
    output.put("userAge", "1");
    return output;
  }

  public static Comment convertForumCommentToComment(CommentUserModel model) {
    if (null == model) return null;
    Comment comment = new Comment();
    if (null != model.getId()) comment.setCommentId(model.getId().toString());
    if (StringUtils.isNotBlank(model.getContent())) comment.setContent(model.getContent());
    if (null != model.getForumId()) comment.setForumId(model.getForumId().toString());
    if (null != model.getUser() && StringUtils.isNotBlank(model.getUser().getNickName()))
      comment.setPeople(model.getUser().getNickName());
    if (null != model.getUser() && StringUtils.isNotBlank(model.getUser().getPortrait()))
      comment.setPortrait(model.getUser().getPortrait());
    if (null != model.getCreateTime()) comment.setTime(model.getCreateTime());
    if (null != model.getType()) comment.setType(model.getType().toString());

    return comment;

  }

}
