package com.xiaodou.server.mapi.domain;

import org.apache.commons.lang.StringUtils;


public class CourseDetail {
  private String courseCode = StringUtils.EMPTY;// 课程代码
  private String courseQuality = StringUtils.EMPTY;// 课程性质
  private String courseCredit = StringUtils.EMPTY;// 课程学分
  private String courseCheckType = StringUtils.EMPTY;// 课程考核方式
  private String courseName = StringUtils.EMPTY;// 课程名称
  private String courseDesc = StringUtils.EMPTY;// 课程简介
  private String examDate = StringUtils.EMPTY;// 课程考期


  public CourseDetail() {}

  public CourseDetail(String courseName, String courseCode, String courseCredit) {
    this.courseName = courseName;
    this.courseCode = courseCode;
    this.courseCredit = courseCredit;
  }

  public CourseDetail(String courseCode, String courseQuality, String courseCredit,
      String courseCheckType, String courseName, String courseDesc, String examDate) {
    super();
    this.courseCode = courseCode;
    this.courseQuality = courseQuality;
    this.courseCredit = courseCredit;
    this.courseCheckType = courseCheckType;
    this.courseName = courseName;
    this.courseDesc = courseDesc;
    this.examDate = examDate;
  }

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

  /* 课程说明 */
  public String getCourseExplain() {
    return String
        .format("课程代码:%s<br/>课程性质:%s<br/>课程学分:%s<br/>课程考核方式:%s", getCourseCode(),
            getCourseQuality(), getCourseCredit(), getCourseCheckType());
  }
}
