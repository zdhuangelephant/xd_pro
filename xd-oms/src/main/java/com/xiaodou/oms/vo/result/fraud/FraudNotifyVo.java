package com.xiaodou.oms.vo.result.fraud;

import com.xiaodou.oms.vo.result.ResultInfo;


public class FraudNotifyVo extends ResultInfo{
  private String code;
  private String msg;
  private String data;
  public String getCode() {
    return code;
  }
  public void setCode(String code) {
    this.code = code;
  }
  public String getMsg() {
    return msg;
  }
  public void setMsg(String msg) {
    this.msg = msg;
  }
  public String getData() {
    return data;
  }
  public void setData(String data) {
    this.data = data;
  }
}
