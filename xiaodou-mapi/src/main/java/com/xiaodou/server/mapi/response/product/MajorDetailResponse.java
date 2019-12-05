package com.xiaodou.server.mapi.response.product;

import com.xiaodou.server.mapi.domain.MajorDetail;
import com.xiaodou.server.mapi.response.selftaught.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

public class MajorDetailResponse extends BaseResponse {

  public MajorDetailResponse() {}

  public MajorDetailResponse(ResultType resultType) {
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
