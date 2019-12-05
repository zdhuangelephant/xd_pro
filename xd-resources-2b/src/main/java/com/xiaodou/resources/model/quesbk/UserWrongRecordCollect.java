package com.xiaodou.resources.model.quesbk;

import com.xiaodou.common.util.StringUtils;
import com.xiaodou.resources.model.BaseEntity;
import com.xiaodou.resources.util.QuesbkUtil;

/**
 * @name @see com.xiaodou.domain.UserWrongRecordCollect.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月13日
 * @description 用户错题统计记录
 * @version 1.0
 */
public class UserWrongRecordCollect extends BaseEntity {
  /**
   * 主键ID
   */
  private Long id;

  /**
   * 用户ID
   */
  private String userId;

  /**
   * 课程ID
   */
  private Long courseId;

  /**
   * 章节ID
   */
  private Long chapterId;

  /**
   * 章节名称
   */
  private String chapterName;

  /**
   * 父章节名称
   */
  private String parentChapterName;

  /**
   * 父章节ID
   */
  private Long parentChapter;
  /**
   * 问题数量
   */
  private int questionNumber;
  /**
   * 未掌握问题数量
   */
  private int uncatchQuesCount;
  /**
   * 待强化问题数量
   */
  private int catchingQuesCount;
  /**
   * 已消灭问题数量
   */
  private int catchedQuesCount;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = QuesbkUtil.trim(userId);
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

  public String getParentChapterName() {
    return parentChapterName;
  }

  public void setParentChapterName(String parentChapterName) {
    this.parentChapterName = parentChapterName;
  }

  public int getQuestionNumber() {
    return questionNumber;
  }

  public void setQuestionNumber(int questionNumber) {
    this.questionNumber = questionNumber;
  }

  public void addQuestionNumber(int questionNumber) {
    setQuestionNumber(this.questionNumber + questionNumber);
  }

  public String getChapterName() {
    return chapterName;
  }

  public void setChapterName(String chapterName) {
    this.chapterName = chapterName;
  }

  public Long getParentChapter() {
    return parentChapter;
  }

  public void setParentChapter(Long parentChapter) {
    this.parentChapter = parentChapter;
  }

  public Long getId() {
    return id;
  }

  public int getUncatchQuesCount() {
    return uncatchQuesCount;
  }

  public void setUncatchQuesCount(int uncatchQuesCount) {
    this.uncatchQuesCount = uncatchQuesCount;
  }

  public int getCatchingQuesCount() {
    return catchingQuesCount;
  }

  public void setCatchingQuesCount(int catchingQuesCount) {
    this.catchingQuesCount = catchingQuesCount;
  }

  public int getCatchedQuesCount() {
    return catchedQuesCount;
  }

  public void setCatchedQuesCount(int catchedQuesCount) {
    this.catchedQuesCount = catchedQuesCount;
  }

}
