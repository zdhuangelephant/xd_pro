package com.xiaodou.resources.model.forum;

import java.sql.Timestamp;

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
  private Long resourcesId;

  /** 评论id */
  private Long commentId;

  /** 点赞人ID */
  private Long replyId;

  /** 标签-扩展字段 */
  private Integer tag;

  /** praise 点赞/取消赞 */
  private boolean praise;
  /** 发布人 */
  private String releaseUser;

  private Timestamp createTime;

  private Long productId;

  private Long itemId;

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public Long getItemId() {
    return itemId;
  }

  public void setItemId(Long itemId) {
    this.itemId = itemId;
  }

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
    return "ForumPraiseModel [id=" + id + ", resourcesId=" + resourcesId + ", commentId="
        + commentId + ", replyId=" + replyId + ", tag=" + tag + "]";
  }

  public Long getResourcesId() {
    return resourcesId;
  }

  public void setResourcesId(Long resourcesId) {
    this.resourcesId = resourcesId;
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public String getReleaseUser() {
    return releaseUser;
  }

  public void setReleaseUser(String releaseUser) {
    this.releaseUser = releaseUser;
  }


}
