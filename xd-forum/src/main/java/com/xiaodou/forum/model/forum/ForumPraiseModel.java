package com.xiaodou.forum.model.forum;

/**
 * 用户点赞记录model
 * 
 * @author bing.cheng
 * 
 */
public class ForumPraiseModel extends ForumBaseModel {

  /** 数据库自增id */
  private Long id;

  /** 帖子(话题)id */
  private Long forumId;

  /** 评论id */
  private Long commentId;

  /** 点赞人ID */
  private Long replyId;

  /** 标签-扩展字段 */
  private Integer tag;

  /** praise 点赞/取消赞 */
  private boolean praise;

  public boolean isPraise() {
    return praise;
  }

  public void setPraise(boolean praise) {
    this.praise = praise;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getForumId() {
    return forumId;
  }

  public void setForumId(Long forumId) {
    this.forumId = forumId;
  }

  public Long getCommentId() {
    return commentId;
  }

  public void setCommentId(Long commentId) {
    this.commentId = commentId;
  }

  public Long getReplyId() {
    return replyId;
  }

  public void setReplyId(Long replyId) {
    this.replyId = replyId;
  }

  public Integer getTag() {
    return tag;
  }

  public void setTag(Integer tag) {
    this.tag = tag;
  }

  @Override
  public String toString() {
    return "ForumPraiseModel [id=" + id + ", forumId=" + forumId + ", commentId=" + commentId
        + ", replyId=" + replyId + ", tag=" + tag + "]";
  }


}
