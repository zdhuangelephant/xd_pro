package com.xiaodou.server.mapi.domain;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;

public class MajorDetail {
  /* 专业代码 */
  private String majorCode = StringUtils.EMPTY;
  /* 专业层次 */
  private String majorLevel = StringUtils.EMPTY;
  /* 主考院校 */
  private String chiefAcademy = StringUtils.EMPTY;
  /* 学位 */
  private String degree = StringUtils.EMPTY;
  /* 专业名称 */
  private String majorName = StringUtils.EMPTY;
  /* 专业描述 */
  private String majorDesc = StringUtils.EMPTY;
  /* 课程列表 */
  private List<CourseDetail> courseList = Lists.newArrayList();

  public String getMajorCode() {
    return majorCode;
  }

  public void setMajorCode(String majorCode) {
    this.majorCode = majorCode;
  }

  public String getMajorLevel() {
    return majorLevel;
  }

  public void setMajorLevel(String majorLevel) {
    this.majorLevel = majorLevel;
  }

  public String getChiefAcademy() {
    return chiefAcademy;
  }

  public void setChiefAcademy(String chiefAcademy) {
    this.chiefAcademy = chiefAcademy;
  }

  public String getDegree() {
    return degree;
  }

  public void setDegree(String degree) {
    this.degree = degree;
  }

  public String getMajorName() {
    return majorName;
  }

  public void setMajorName(String majorName) {
    this.majorName = majorName;
  }

  public String getMajorDesc() {
    return majorDesc;
  }

  public void setMajorDesc(String majorDesc) {
    this.majorDesc = majorDesc;
  }

  public String getCourseCount() {
    return (courseList != null) ? Integer.toString(courseList.size()) : Integer.toString(0);
  }

  public List<CourseDetail> getCourseList() {
    return courseList;
  }

  public void setCourseList(List<CourseDetail> courseList) {
    this.courseList = courseList;
  }

  /* 专业说明 */
  public String getMajorExplain() {
    return String.format("专业代码:%s<br/>专业层次:%s<br/>主考院校 :%s<br/>学位:%s", getMajorCode(),
        getMajorLevel(), getChiefAcademy(), getDegree());
  }

}
