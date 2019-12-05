package com.xiaodou.resources.model.quesbk;

import java.util.Date;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.resources.constant.quesbk.QuesBaseConstant;
import com.xiaodou.resources.model.BaseEntity;
import com.xiaodou.resources.util.QuesbkUtil;

/**
 * @name @see com.xiaodou.domain.UserWrongRecord.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月13日
 * @description 用户错题记录
 * @version 1.0
 */
public class UserWrongRecord extends BaseEntity {
  /**
   * 错误状态
   */
  private Boolean wrong;
  /**
   * 主键ID
   */
  private Long id;

  /**
   * 用户ID
   */
  private String userId;

  /** module 所属模块 */
  private String module;

  /**
   * 课程ID
   */
  private Long courseId;

  /**
   * 章节ID
   */
  private Long chapterId;

  /**
   * 问题ID
   */
  private Long questionId;

  /**
   * 问题详情
   */
  private QuesbkQues quesDetail;

  /**
   * 错误答案
   */
  private String wrongAnswer;

  /**
   * 错误次数
   */
  private Integer wrongTimes = 0;

  /**
   * 连续正确次数
   */
  private Integer rightTimes = 0;

  /**
   * 总练习次数
   */
  private Integer totalTimes = 1;

  /**
   * 练习时间
   */
  private Date examTime;

  /**
   * 错题状态 1 未掌握 2 待强化 3 已消灭 4 已移除(默认)
   */

  private String wrongStatus = QuesBaseConstant.QUES_WRONG_RECORD_STATUS_REMOVED;

  /** tag 唯一标识 */
  private String tag;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = QuesbkUtil.trim(userId);
  }

  public String getModule() {
    return module;
  }

  public void setModule(String module) {
    this.module = module;
  }

  public Integer getRightTimes() {
    return rightTimes;
  }

  public void setRightTimes(Integer rightTimes) {
    this.rightTimes = rightTimes;
  }

  public void addRightTimes() {
    this.rightTimes++;
  }

  public Boolean getWrong() {
    return wrong;
  }

  public void setWrong(Boolean wrong) {
    this.wrong = wrong;
    if (wrong) setWrongStatus(QuesBaseConstant.QUES_WRONG_RECORD_STATUS_UNCONTROL);
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getCourseId() {
    return courseId;
  }

  public void setCourseId(Long courseId) {
    this.courseId = courseId;
  }

  public void setCourseId(String courseId) {
    if (StringUtils.isNotBlank(courseId)) this.courseId = Long.parseLong(courseId);
  }

  public Long getChapterId() {
    return chapterId;
  }

  public void setChapterId(Long chapterId) {
    this.chapterId = chapterId;
  }

  public Long getQuestionId() {
    return questionId;
  }

  public void setQuestionId(Long questionId) {
    this.questionId = questionId;
  }

  public Integer getWrongTimes() {
    return wrongTimes;
  }

  public void setWrongTimes(Integer wrongTimes) {
    this.wrongTimes = wrongTimes;
  }

  public void addWrongTimes() {
    this.wrongTimes++;
  }

  public Integer getTotalTimes() {
    return totalTimes;
  }

  public void setTotalTimes(Integer totalTimes) {
    this.totalTimes = totalTimes;
  }

  public QuesbkQues getQuesDetail() {
    return quesDetail;
  }

  public void setQuesDetail(QuesbkQues quesDetail) {
    this.quesDetail = quesDetail;
  }

  public Long getId() {
    return id;
  }

  public String getWrongAnswer() {
    return wrongAnswer;
  }

  public void setWrongAnswer(String wrongAnswer) {
    this.wrongAnswer = wrongAnswer == null ? null : wrongAnswer.trim();
  }

  public Date getExamTime() {
    return examTime;
  }

  public void setExamTime(Date examTime) {
    this.examTime = examTime;
  }

  public String getWrongStatus() {
    return wrongStatus;
  }

  public void setWrongStatus(String wrongStatus) {
    this.wrongStatus = wrongStatus;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

}
