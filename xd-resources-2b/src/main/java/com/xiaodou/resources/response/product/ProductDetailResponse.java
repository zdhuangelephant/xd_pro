package com.xiaodou.resources.response.product;

import com.xiaodou.resources.response.BaseResponse;
import com.xiaodou.resources.vo.product.CourseDetail;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * Created by ldh on 16/2/23.
 */
public class ProductDetailResponse extends BaseResponse {

  public ProductDetailResponse(ResultType resultType) {
    super(resultType);
  }

  // 产品详情
  private CourseDetail courseDetail = new CourseDetail();

  public CourseDetail getCourseDetail() {
    return courseDetail;
  }

  public void setCourseDetail(CourseDetail courseDetail) {
    this.courseDetail = courseDetail;
  }

}
