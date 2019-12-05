package com.xiaodou.course.vo.product;

/**
 * Created by zyp on 15/8/16.
 */
public class ProductListDetail {

  // 产品id
  private Integer courseId;

  // 产品名称
  private String courseName;

  // 课程信息
  private CourseInfo info;

  public CourseInfo getInfo() {
    return info;
  }

  public void setInfo(CourseInfo info) {
    this.info = info;
  }

  public Integer getCourseId() {
    return courseId;
  }

  public void setCourseId(Integer courseId) {
    this.courseId = courseId;
  }

  public String getCourseName() {
    return courseName;
  }

  public void setCourseName(String courseName) {
    this.courseName = courseName;
  }

}
