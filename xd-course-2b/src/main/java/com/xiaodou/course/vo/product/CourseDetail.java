package com.xiaodou.course.vo.product;

import com.xiaodou.common.util.StringUtils;


public class CourseDetail {
  private String courseCode = StringUtils.EMPTY;// 课程代码
  private String courseQuality = StringUtils.EMPTY;// 课程性质
  private String courseCredit = StringUtils.EMPTY;// 课程学分
  private String courseCheckType = StringUtils.EMPTY;// 课程考核方式
  private String courseName = StringUtils.EMPTY;// 课程名称
  private String courseDesc = StringUtils.EMPTY;// 课程简介
  private String examDate = StringUtils.EMPTY;// 课程考期

  public String getCourseCode() {
    return courseCode;
  }

  public void setCourseCode(String courseCode) {
    this.courseCode = courseCode;
  }

  public String getCourseQuality() {
    return courseQuality;
  }

  public void setCourseQuality(String courseQuality) {
    this.courseQuality = courseQuality;
  }

  public String getCourseCredit() {
    return courseCredit;
  }

  public void setCourseCredit(String courseCredit) {
    this.courseCredit = courseCredit;
  }

  public String getCourseCheckType() {
    return courseCheckType;
  }

  public void setCourseCheckType(String courseCheckType) {
    this.courseCheckType = courseCheckType;
  }

  public String getCourseName() {
    return courseName;
  }

  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }

  public String getCourseDesc() {
    return courseDesc;
  }

  public void setCourseDesc(String courseDesc) {
    this.courseDesc = courseDesc;
  }

  public String getExamDate() {
    return examDate;
  }

  public void setExamDate(String examDate) {
    this.examDate = examDate;
  }
}
