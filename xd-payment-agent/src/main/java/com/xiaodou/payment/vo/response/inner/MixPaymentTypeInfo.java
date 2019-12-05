package com.xiaodou.payment.vo.response.inner;

import com.alibaba.fastjson.annotation.JSONField;

public class MixPaymentTypeInfo {

  @JSONField(name = "record_id")
  private long recordId;

  @JSONField(name = "trans_type")
  private int transType;

  @JSONField(name = "trans_type_desc")
  private String transTypeDesc;

  @JSONField(name = "amt")
  private double amt;

  @JSONField(name = "result_status")
  private int resultStatus;

  @JSONField(name = "result_status_desc")
  private String resultStatusDesc;

  public long getRecordId() {
    return recordId;
  }

  public void setRecordId(long recordId) {
    this.recordId = recordId;
  }

  public int getTransType() {
    return transType;
  }

  public void setTransType(int transType) {
    this.transType = transType;
  }

  public String getTransTypeDesc() {
    return transTypeDesc;
  }

  public void setTransTypeDesc(String transTypeDesc) {
    this.transTypeDesc = transTypeDesc;
  }

  public double getAmt() {
    return amt;
  }

  public void setAmt(double amt) {
    this.amt = amt;
  }

  public int getResultStatus() {
    return resultStatus;
  }

  public void setResultStatus(int resultStatus) {
    this.resultStatus = resultStatus;
  }

  public String getResultStatusDesc() {
    return resultStatusDesc;
  }

  public void setResultStatusDesc(String resultStatusDesc) {
    this.resultStatusDesc = resultStatusDesc;
  }
}
