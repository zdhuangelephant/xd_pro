package com.xiaodou.server.mapi.response.product;

import com.xiaodou.server.mapi.domain.CourseDetail;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

/**
 * Created by ldh on 16/2/23.
 */
public class ProductDetailResponse extends BaseResponse {

  public ProductDetailResponse() {}

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
