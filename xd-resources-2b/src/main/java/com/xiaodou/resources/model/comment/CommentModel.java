package com.xiaodou.resources.model.comment;

import java.sql.Timestamp;

import com.alibaba.fastjson.JSON;
import com.xiaodou.common.annotation.BaseField;
import com.xiaodou.common.annotation.GeneralField;

/**
 * @name @see com.xiaodou.course.model.comment.CommentModel.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年5月5日
 * @description 评论Model
 * @version 1.0
 */
public class CommentModel {
  /**
   * 主键
   */
  @BaseField
  @GeneralField
  private Long id;

  /** courseId 课程ID */
  @BaseField
  @GeneralField
  private Long courseId;

  /** chapterId 章ID */
  @BaseField
  @GeneralField
  private Long chapterId;

  /**
   * 节ID
   */
  @BaseField
  @GeneralField
  private Long itemId;

  /**
   * 内容
   */
  @BaseField
  @GeneralField
  private String content;

  /**
   * 类型
   */
  @BaseField
  @GeneralField
  private Integer type;

  /**
   * 评论人ID
   */
  @BaseField
  @GeneralField
  private Long replyId;

  @BaseField
  @GeneralField
  private Long targetId;

  @BaseField
  @GeneralField
  private Long targetCommentId;

  @BaseField
  @GeneralField
  private String images;

  @BaseField
  @GeneralField
  private String targetContent;

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

  public Long getTargetId() {
    return targetId;
  }

  public void setTargetId(Long targetId) {
    this.targetId = targetId;
  }

  public Long getTargetCommentId() {
    return targetCommentId;
  }

  public void setTargetCommentId(Long targetCommentId) {
    this.targetCommentId = targetCommentId;
  }

  public String getTargetContent() {
    return targetContent;
  }

  public void setTargetContent(String targetContent) {
    this.targetContent = targetContent;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getReplyId() {
    return replyId;
  }

  public void setReplyId(Long replyId) {
    this.replyId = replyId;
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
    this.operator = operator == null ? null : operator.trim();
  }

  public String getOperatorip() {
    return operatorip;
  }

  public void setOperatorip(String operatorip) {
    this.operatorip = operatorip == null ? null : operatorip.trim();
  }

  public String getImages() {
    return images;
  }

  public void setImages(String images) {
    this.images = images;
  }

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public Long getCourseId() {
    return courseId;
  }

  public void setCourseId(Long courseId) {
    this.courseId = courseId;
  }

  public Long getChapterId() {
    return chapterId;
  }

  public void setChapterId(Long chapterId) {
    this.chapterId = chapterId;
  }

  public Long getItemId() {
    return itemId;
  }

  public void setItemId(Long itemId) {
    this.itemId = itemId;
  }

}
