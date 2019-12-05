package com.xiaodou.server.mapi.response.product;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

public class AddCourseListResponse extends BaseResponse {

  public AddCourseListResponse() {}

  public AddCourseListResponse(ResultType resultType) {
    super(resultType);
  }

  private List<AddCourse> courseList = Lists.newArrayList();

  public List<AddCourse> getCourseList() {
    return courseList;
  }

  public void setCourseList(List<AddCourse> courseList) {
    this.courseList = courseList;
  }


  public static class AddCourse {
    /* 课程id */
    private String courseId = StringUtils.EMPTY;
    /* 课程码值 */
    private String courseCode = StringUtils.EMPTY;
    /* 课程名称 */
    private String courseName = StringUtils.EMPTY;
    /* 课程图片 */
    private String courseImage = StringUtils.EMPTY;
    /* 课程学分 */
    private String courseCredit = StringUtils.EMPTY;
    /** courseCoin 课程原价 */
    private String courseOriginalPrice = "0";
    /** courseCoin 课程优惠价 */
    private String coursePreferPrice = "0";
    /** courseChapterCount 课程章数 */
    private String courseChapterCount = "0";
    /* examDate 課程考期 */
    private String examDate = "201704";
    /* expDate 课程有效期 */
    private String expDate = "2016年11月01日至2017年04月23日";

    public AddCourse() {

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

    public String getCourseCredit() {
      return courseCredit;
    }

    public void setCourseCredit(String courseCredit) {
      this.courseCredit = courseCredit;
    }

    public String getCourseOriginalPrice() {
      return courseOriginalPrice;
    }

    public void setCourseOriginalPrice(String courseOriginalPrice) {
      this.courseOriginalPrice = courseOriginalPrice;
    }

    public String getCoursePreferPrice() {
      return coursePreferPrice;
    }

    public void setCoursePreferPrice(String coursePreferPrice) {
      this.coursePreferPrice = coursePreferPrice;
    }

    public String getCourseChapterCount() {
      return courseChapterCount;
    }

    public void setCourseChapterCount(String courseChapterCount) {
      this.courseChapterCount = courseChapterCount;
    }

    public String getExamDate() {
      return examDate;
    }

    public void setExamDate(String examDate) {
      this.examDate = examDate;
    }

    public String getExpDate() {
      return expDate;
    }

    public void setExpDate(String expDate) {
      this.expDate = expDate;
    }
  }
}
