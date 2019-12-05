package com.xiaodou.resources.model.quesbk;

import com.xiaodou.resources.model.BaseEntity;


public class RaiseWrongQues extends BaseEntity{
  /**
   * id 主键
   * 
   */
  private Long id;
  /**
   * 用户ID
   * 
   */
  private String userId;
  /**
   * 课程ID
   * 
   */
  private Long courseId;
  /**
   * // 章ID
   * 
   */
  private Long chapterId;
  /**
   * 题目ID
   * 
   */
  private Long quesId;
  /**
   * 错误类型
   * 
   */
  private String wrongType;
  /**
   * 错误描述
   * 
   */
  private String wrongMsg;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
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

  public Long getQuesId() {
    return quesId;
  }

  public void setQuesId(Long quesId) {
    this.quesId = quesId;
  }

  public String getWrongType() {
    return wrongType;
  }

  public void setWrongType(String wrongType) {
    this.wrongType = wrongType;
  }

  public String getWrongMsg() {
    return wrongMsg;
  }

  public void setWrongMsg(String wrongMsg) {
    this.wrongMsg = wrongMsg;
  }

}
