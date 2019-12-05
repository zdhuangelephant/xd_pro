package com.xiaodou.ms.web.response.selftaught;


import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.xiaodou.ms.model.selftaught.CourseModel;
import com.xiaodou.ms.web.response.BaseResponse;
import com.xiaodou.ms.web.response.ResultType;

public class CourseResponse extends BaseResponse {
  public CourseResponse(ResultType resultType) {
    super(resultType);
  }

  public CourseResponse() {}

  private String courseId;
  private String courseName;// 课程名称
  private String courseImage;// 课程图片
  private String actStartTime;// 课程考期
  private String isLatest;// 课程状态（是否为近期）
  private String majorId;// 所属专业id
  private String amount;// 课程章节数

  public CourseResponse getCourseResponse(CourseModel courseModel) {
    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    this.courseId = courseModel.getCourseId();
    this.courseName = courseModel.getCourseName();
    this.courseImage = courseModel.getCourseImage();
    this.actStartTime = sdf.format(courseModel.getExamDate());
    this.isLatest = courseModel.getIsLatest();
    this.majorId = courseModel.getMajorId();
    this.amount = courseModel.getAmount();
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

  public String getActStartTime() {
    return actStartTime;
  }

  public void setActStartTime(String actStartTime) {
    this.actStartTime = actStartTime;
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

  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }
}
