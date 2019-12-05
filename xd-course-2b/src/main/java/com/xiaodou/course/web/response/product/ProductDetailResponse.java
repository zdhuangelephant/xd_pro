package com.xiaodou.course.web.response.product;

import com.xiaodou.course.vo.product.CourseDetail;
import com.xiaodou.course.web.response.BaseResponse;
import com.xiaodou.course.web.response.resultType.ProductResType;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * Created by ldh on 16/2/23.
 */
public class ProductDetailResponse extends BaseResponse {

  public ProductDetailResponse(ResultType resultType) {
    super(resultType);
  }

  public ProductDetailResponse(ProductResType resType) {
    super(resType);
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
