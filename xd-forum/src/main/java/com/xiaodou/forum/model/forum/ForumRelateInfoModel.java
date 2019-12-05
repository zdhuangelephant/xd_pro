package com.xiaodou.forum.model.forum;

import java.sql.Timestamp;

import com.xiaodou.common.annotation.BaseField;
import com.xiaodou.common.annotation.GeneralField;

/**
 * @name @see com.xiaodou.forum.model.forum.ForumRelateInfoModel.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年10月19日
 * @description 话题信息model,用于存储评论与点赞的通知消息
 * @version 1.0
 */
public class ForumRelateInfoModel {

  /**
   * 主键
   */
  @BaseField
  @GeneralField
  private Long id;

  /** type 信息类型 */
  @BaseField
  @GeneralField
  private Short type;

  /** status 状态 */
  @BaseField
  @GeneralField
  private Short status;

  /**
   * 话题ID
   */
  @BaseField
  @GeneralField
  private Long forumId;

  /** commentId 评论ID */
  @BaseField
  @GeneralField
  private Long commentId;

  /** forumTitle 话题标题 */
  @BaseField
  @GeneralField
  private String forumTitle;

  /**
   * 内容
   */
  @BaseField
  @GeneralField
  private String content;

  /**
   * 评论人ID
   */
  @BaseField
  @GeneralField
  private Long replyId;

  @BaseField
  @GeneralField
  private Long targeId;

  @BaseField
  @GeneralField
  private Long targeCommentId;

  @BaseField
  @GeneralField
  private String images;

  @BaseField
  @GeneralField
  private String targeContent;

  /**
   * 创建时间
   */
  @BaseField
  @GeneralField
  private Timestamp createTime;

  /**
   * 操作人
   */
  @BaseField
  private String operator;

  /**
   * 操作IP
   */
  @BaseField
  private String operatorip;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Short getType() {
    return type;
  }

  public void setType(Short type) {
    this.type = type;
  }

  public Short getStatus() {
    return status;
  }

  public void setStatus(Short status) {
    this.status = status;
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

  public String getForumTitle() {
    return forumTitle;
  }

  public void setForumTitle(String forumTitle) {
    this.forumTitle = forumTitle;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Long getReplyId() {
    return replyId;
  }

  public void setReplyId(Long replyId) {
    this.replyId = replyId;
  }

  public Long getTargeId() {
    return targeId;
  }

  public void setTargeId(Long targeId) {
    this.targeId = targeId;
  }

  public Long getTargeCommentId() {
    return targeCommentId;
  }

  public void setTargeCommentId(Long targeCommentId) {
    this.targeCommentId = targeCommentId;
  }

  public String getImages() {
    return images;
  }

  public void setImages(String images) {
    this.images = images;
  }

  public String getTargeContent() {
    return targeContent;
  }

  public void setTargeContent(String targeContent) {
    this.targeContent = targeContent;
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public String getOperatorip() {
    return operatorip;
  }

  public void setOperatorip(String operatorip) {
    this.operatorip = operatorip;
  }

}
