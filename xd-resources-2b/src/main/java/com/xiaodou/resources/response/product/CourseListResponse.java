package com.xiaodou.resources.response.product;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.resources.response.BaseResponse;
import com.xiaodou.resources.vo.product.CourseInfo;
import com.xiaodou.summer.vo.out.ResultType;

public class CourseListResponse extends BaseResponse {

  public CourseListResponse() {}

  public CourseListResponse(ResultType resultType) {
    super(resultType);
  }

  private List<CourseInfo> courseList = Lists.newArrayList();

  public List<CourseInfo> getCourseList() {
    return courseList;
  }

  public void setCourseList(List<CourseInfo> courseList) {
    this.courseList = courseList;
  }
}
