package com.xiaodou.oms.util.model;

import com.xiaodou.common.util.warp.FastJsonUtil;

/**
 * Date: 2014/11/17
 * Time: 17:08
 *
 * @author Tian.Dong
 */
public class QueryPaymentEntity {
  private String gorderId;
  private String retCode;
  private String retDesc;

  public String getGorderId() {
    return gorderId;
  }

  public void setGorderId(String gorderId) {
    this.gorderId = gorderId;
  }

  public String getRetCode() {
    return retCode;
  }

  public void setRetCode(String retCode) {
    this.retCode = retCode;
  }

  public String getRetDesc() {
    return retDesc;
  }

  public void setRetDesc(String retDesc) {
    this.retDesc = retDesc;
  }

  @Override
  public String toString() {
    return FastJsonUtil.toJson(this);
  }
}
