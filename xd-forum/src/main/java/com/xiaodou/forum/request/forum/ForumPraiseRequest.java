package com.xiaodou.forum.request.forum;

import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * 点赞request
 * 
 * @author wuyunkuo
 *
 */
public class ForumPraiseRequest extends BaseRequest {

  /**
   * 帖子Id
   */
  @NotEmpty
  private String forumId;
  
  /**
   * 评论Id
   */
  private String commentId;

  public String getForumId() {
    return forumId;
  }

  public void setForumId(String forumId) {
    this.forumId = forumId;
  }

  public String getCommentId() {
    return commentId;
  }

  public void setCommentId(String commentId) {
    this.commentId = commentId;
  }
  
}
