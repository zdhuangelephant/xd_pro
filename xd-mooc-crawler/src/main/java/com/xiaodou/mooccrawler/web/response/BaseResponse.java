package com.xiaodou.mooccrawler.web.response;

import com.xiaodou.common.util.warp.FastJsonUtil;

/**
 * Created by zyp on 15/4/19.
 */
public class BaseResponse {

  /**
   * 编码
   */
  private Integer retCode;

  /**
   * 描述
   */
  private String retDesc;

  public BaseResponse() {}

  public Integer getRetCode() {
    return retCode;
  }

  public void setRetCode(Integer retCode) {
    this.retCode = retCode;
  }

  public String getRetDesc() {
    return retDesc;
  }

  public void setRetDesc(String retDesc) {
    this.retDesc = retDesc;
  }

  public BaseResponse(ResultType resultType) {
    this.retCode = resultType.getRetCode();
    this.retDesc = resultType.getRetDesc();
  }

  @Override
  public String toString() {
    return FastJsonUtil.toJson(this);
  }
}
