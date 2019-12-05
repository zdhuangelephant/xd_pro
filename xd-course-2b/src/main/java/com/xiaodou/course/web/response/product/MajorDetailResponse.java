package com.xiaodou.course.web.response.product;

import com.xiaodou.course.vo.product.MajorDetail;
import com.xiaodou.course.web.response.BaseResponse;
import com.xiaodou.course.web.response.resultType.ProductResType;
import com.xiaodou.summer.vo.out.ResultType;

public class MajorDetailResponse extends BaseResponse {

  public MajorDetailResponse() {}

  public MajorDetailResponse(ResultType resultType) {
    super(resultType);
  }

  public MajorDetailResponse(ProductResType resultType) {
    super(resultType);
  }

  private MajorDetail majorDetail = new MajorDetail();

  public MajorDetail getMajorDetail() {
    return majorDetail;
  }

  public void setMajorDetail(MajorDetail majorDetail) {
    this.majorDetail = majorDetail;
  }

}
