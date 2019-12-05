package com.xiaodou.ms.model.selftaught;

import java.sql.Timestamp;

import com.xiaodou.ms.web.request.selftaught.CourseRequest;

public class CourseModel {
  private String courseId;
  private String courseName;// 课程名称
  private String courseImage;// 课程图片
  private Timestamp examDate;// 课程考期
  private String isLatest;// 课程状态（是否为近期）
  private String majorId;// 所属专业id
  private Timestamp createTime;
  private String amount;// 课程章节数

  public CourseModel getCourseModel(CourseRequest request) {
    this.courseId = request.getCourseId();
    this.courseImage = request.getCourseImage();
    this.courseName = request.getCourseName();
    this.examDate = Timestamp.valueOf(request.getActStartTime() + ":00");
    this.isLatest = request.getIsLatest();
    this.majorId = request.getMajorId();
    this.amount = request.getAmount();
    return this;
  }

  public String getCourseId() {
    return courseId;
  }

  public void setCourseId(String courseId) {
    this.courseId = courseId;
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

  public Timestamp getExamDate() {
    return examDate;
  }

  public void setExamDate(Timestamp examDate) {
    this.examDate = examDate;
  }

  public String getIsLatest() {
    return isLatest;
  }

  public void setIsLatest(String isLatest) {
    this.isLatest = isLatest;
  }

  public String getMajorId() {
    return majorId;
  }

  public void setMajorId(String majorId) {
    this.majorId = majorId;
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }
}
