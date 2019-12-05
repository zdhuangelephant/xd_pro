package com.xiaodou.course.web.response.share;

import com.xiaodou.course.web.response.BaseResponse;
import com.xiaodou.summer.vo.out.ResultType;

public class StatisticsSharePvResponse extends BaseResponse {

  public StatisticsSharePvResponse() {}

  public StatisticsSharePvResponse(ResultType type) {
    super(type);
  }

  private String resourceId;
  private String pv;

  public String getResourceId() {
    return resourceId;
  }

  public void setResourceId(String resourceId) {
    this.resourceId = resourceId;
  }

  public String getPv() {
    return pv;
  }

  public void setPv(String pv) {
    this.pv = pv;
  }
}
