package com.xiaodou.forum.request.forum;

import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * 话题评论列表
 * 
 * @author hualong.li
 *
 */
public class ForumCommentListRequest {

  /** 话题ID */
  @NotEmpty
  private String forumId;
  
  /** 每页显示个数 默认10条 */
  private String size = "10";
  
  /** 评论类型 */
  @NotEmpty
  private String commentType;
  
  /** 最后一条评论ID */
  @NotEmpty
  private String commentId;

  public String getForumId() {
    return forumId;
  }

  public void setForumId(String forumId) {
    this.forumId = forumId;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public String getCommentType() {
    return commentType;
  }

  public void setCommentType(String commentType) {
    this.commentType = commentType;
  }

  public String getCommentId() {
    return commentId;
  }

  public void setCommentId(String commentId) {
    this.commentId = commentId;
  }

}
