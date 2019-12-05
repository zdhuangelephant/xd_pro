package com.xiaodou.forum.request.forum;

import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * 话题详情－评论部分
 * 
 * @author weichao.zhai
 * @time 2015年4月6日 下午4:56:06
 */
public class ForumDetailCommentsRequest extends BaseRequest{

  /** 话题ID */
  @NotEmpty
  private String forumId;

  /** 每页数量 */
  private Integer size = 20;

  /** 评论Id */
  private String commentId;

  public String getForumId() {
    return forumId;
  }

  public void setForumId(String forumId) {
    this.forumId = forumId;
  }

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

  public String getCommentId() {
    return commentId;
  }

  public void setCommentId(String commentId) {
    this.commentId = commentId;
  }

}
