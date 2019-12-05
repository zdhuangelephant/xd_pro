package com.xiaodou.ms.web.request.selftaught;

import com.xiaodou.ms.web.request.BaseRequest;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class CourseRequest extends BaseRequest {
  private String courseId;
  @NotEmpty
  private String courseName;// 课程名称
  @NotEmpty
  private String courseImage;// 课程图片
  @NotEmpty
  private String actStartTime;// 课程考期
  @NotEmpty
  private String isLatest;// 课程状态（是否为近期）
  @NotEmpty
  private String majorId;// 所属专业id
  @NotEmpty
  private String amount;// 课程章节数

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
