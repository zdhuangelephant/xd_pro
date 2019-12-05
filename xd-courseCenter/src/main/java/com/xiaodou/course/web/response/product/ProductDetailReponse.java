package com.xiaodou.course.web.response.product;

import java.util.List;

import com.xiaodou.course.vo.product.Chapter;
import com.xiaodou.course.vo.product.CourseDetail;
import com.xiaodou.course.web.response.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * Created by zyp on 15/8/9.
 */
public class ProductDetailReponse extends BaseResponse {

  // 产品详情
  private CourseDetail courseDetail;

  // 章节列表
  private List<Chapter> courseItemList;

  public List<Chapter> getCourseItemList() {
    return courseItemList;
  }

  public void setCourseItemList(List<Chapter> courseItemList) {
    this.courseItemList = courseItemList;
  }

  public CourseDetail getCourseDetail() {
    return courseDetail;
  }

  public void setCourseDetail(CourseDetail courseDetail) {
    this.courseDetail = courseDetail;
  }

  public ProductDetailReponse(ResultType resultType) {
    super(resultType);
  }
}
