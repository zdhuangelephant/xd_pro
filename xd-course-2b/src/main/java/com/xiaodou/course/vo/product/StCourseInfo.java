package com.xiaodou.course.vo.product;

import com.xiaodou.common.util.StringUtils;


public class StCourseInfo {
  /* 课程id */
  private String courseId = StringUtils.EMPTY;
  /* 课程码值 */
  private String courseCode = StringUtils.EMPTY;
  /* 课程名称 */
  private String courseName = StringUtils.EMPTY;
  /* 课程图片 */
  private String courseImage = StringUtils.EMPTY;
  /* 课程得分 */
  private String score = Integer.toString(0);
  /* 学习进度(上一次学习了第几章 */
  /* 章id */
  private String learnChapterId = StringUtils.EMPTY;
  /* 章索引 */
  private String learnChapterRate = StringUtils.EMPTY;
  /* 学习进度(上一次学习了第几节) */
  /* 节id */
  private String learnItemId = StringUtils.EMPTY;
  /* 节索引 */
  private String learnItemRate = StringUtils.EMPTY;
  /* 本课程已获取星级数量 */
  private String getStarCount = Integer.toString(0);
  /* 本课程星级总数 */
  private String totalStarCount = Integer.toString(0);
  /* 是否已经学习 0 未学习 1 已学习 */
  private String hasLearned = Integer.toString(0);
  /* 课程状态 1:有效2：无效 */
  private String courseStatus = Integer.toString(0);

  public String getCourseStatus() {
    return courseStatus;
  }

  public void setCourseStatus(String courseStatus) {
    this.courseStatus = courseStatus;
  }

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
  }

  public String getCourseCode() {
    return courseCode;
  }

  public void setCourseCode(String courseCode) {
    this.courseCode = courseCode;
  }

  public String getCourseName() {
    return courseName;
  }

  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }

  public String getCourseImage() {
    return courseImage;
  }

  public void setCourseImage(String courseImage) {
    this.courseImage = courseImage;
  }

  public String getScore() {
    return score;
  }

  public void setScore(String score) {
    this.score = score;
  }

  public String getLearnChapterId() {
    return learnChapterId;
  }

  public void setLearnChapterId(String learnChapterId) {
    this.learnChapterId = learnChapterId;
  }

  public String getLearnChapterRate() {
    return learnChapterRate;
  }

  public void setLearnChapterRate(String learnChapterRate) {
    this.learnChapterRate = learnChapterRate;
  }

  public String getLearnItemId() {
    return learnItemId;
  }

  public void setLearnItemId(String learnItemId) {
    this.learnItemId = learnItemId;
  }

  public String getLearnItemRate() {
    return learnItemRate;
  }

  public void setLearnItemRate(String learnItemRate) {
    this.learnItemRate = learnItemRate;
  }

  public String getGetStarCount() {
    return getStarCount;
  }

  public void setGetStarCount(String getStarCount) {
    this.getStarCount = getStarCount;
  }

  public String getTotalStarCount() {
    return totalStarCount;
  }

  public void setTotalStarCount(String totalStarCount) {
    this.totalStarCount = totalStarCount;
  }

  public String getHasLearned() {
    return hasLearned;
  }

  public void setHasLearned(String hasLearned) {
    this.hasLearned = hasLearned;
  }
}
