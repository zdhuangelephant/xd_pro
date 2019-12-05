package com.xiaodou.autobuild;

import java.sql.Timestamp;

import com.xiaodou.autobuild.annotations.Column;
import com.xiaodou.autobuild.annotations.Xml;

@Xml(tableName = "xd_raist_wrong_ques", outputDir = "src/test/resources/conf/mybatis")
public class RaiseWrongQues {
  /**
   * id 主键
   * 
   */
  @Column(isMajor = true, sortBy = true)
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
  @Column(listValue = true)
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
  @Column(likeValue = true)
  private String wrongMsg;
  /** createTime 创建时间 */
  @Column(betweenScope = true, sortBy = true)
  private Timestamp createTime;

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

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

}
