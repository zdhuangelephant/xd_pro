package com.xiaodou.forum.request.forum;

import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * 回复评论
 * 
 * @author bing.cheng
 *
 */
public class ForumReplyCommentsRequest extends BaseRequest {
  /**
   * 话题Id
   */
  @NotEmpty
  private Long forumId;

  /** 话题分类ID */
  @NotEmpty
  private Integer forumCategoryId;

  /**
   * 评论内容
   */
  @NotEmpty
  private String commentContent;

  /**
   * 评论id
   */
  @NotEmpty
  private String commentId;

  public Long getForumId() {
    return forumId;
  }

  public void setForumId(Long forumId) {
    this.forumId = forumId;
  }

  public Integer getForumCategoryId() {
    return forumCategoryId;
  }

  public void setForumCategoryId(Integer forumCategoryId) {
    this.forumCategoryId = forumCategoryId;
  }

  public String getCommentContent() {
    return commentContent;
  }

  public void setCommentContent(String commentContent) {
    this.commentContent = commentContent;
  }

  public String getCommentId() {
    return commentId;
  }

  public void setCommentId(String commentId) {
    this.commentId = commentId;
  }

}
