package com.xiaodou.vo.response;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.constant.QuesBaseConstant;
import com.xiaodou.constant.ResultType;
import com.xiaodou.domain.product.CourseProduct;

public class BaseListResponse extends BaseResponse {

  public BaseListResponse(ResultType type) {
    super(type);
  }

  /**
   * 课程列表
   */
  private List<CourseInfo> courseInfo = Lists.newArrayList();

  public List<CourseInfo> getCourseInfo() {
    return courseInfo;
  }

  public void setCourseInfo(List<CourseInfo> courseInfo) {
    this.courseInfo = courseInfo;
  }

  /**
   * 课程信息
   * 
   * @author <a href=mailto:zhaodan@corp.51xiaodou.com>zhaodan</a>
   */
  public static class CourseInfo {
    /**
     * 课程名
     */
    private String courseName;
    /**
     * 课程ID
     */
    private String courseId;
    /**
     * 是否付费
     */
    private String expense;
    /**
     * 课程图片地址
     */
    private String courseImageUrl;

    public CourseInfo(CourseProduct product) {
      if (null != product.getId()) {
        this.courseId = product.getId().toString();
        this.courseName = product.getName();
        this.courseImageUrl = product.getImageUrl();
        if (null != product.getPayAmount())
          this.expense =
              product.getPayAmount().intValue() == 0 ? QuesBaseConstant.NO : QuesBaseConstant.YES;
      }
    }

    public String getCourseName() {
      return courseName;
    }

    public void setCourseName(String courseName) {
      this.courseName = courseName;
    }

    public String getCourseId() {
      return courseId;
    }

    public void setCourseId(String courseId) {
      this.courseId = courseId;
    }

    public String getExpense() {
      return expense;
    }

    public void setExpense(String expense) {
      this.expense = expense;
    }

    public String getCourseImageUrl() {
      return courseImageUrl;
    }

    public void setCourseImageUrl(String courseImageUrl) {
      this.courseImageUrl = courseImageUrl;
    }
  }
}
