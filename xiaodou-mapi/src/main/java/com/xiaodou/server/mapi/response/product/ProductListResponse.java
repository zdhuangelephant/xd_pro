package com.xiaodou.server.mapi.response.product;

import java.util.List;

import com.google.common.collect.Lists;
import com.xiaodou.server.mapi.domain.CourseDetail;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

public class ProductListResponse extends BaseResponse {

  public ProductListResponse() {}

  public ProductListResponse(ResultType resultType) {
    super(resultType);
  }

  /* 课程列表 */
  private List<CourseDetail> courseList = Lists.newArrayList();

  public ProductListResponse(List<CourseDetail> courseList) {
    super();
    this.courseList = courseList;
  }

  public List<CourseDetail> getCourseList() {
    courseList.add(new CourseDetail("课程1", "100010", "2"));
    courseList.add(new CourseDetail("课程2", "100020", "4"));
    courseList.add(new CourseDetail("课程3", "100030", "8"));
    return courseList;
  }

  public void setCourseList(List<CourseDetail> courseList) {
    this.courseList = courseList;
  }
}
