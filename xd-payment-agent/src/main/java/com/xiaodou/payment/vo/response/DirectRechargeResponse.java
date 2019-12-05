package com.xiaodou.payment.vo.response;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 直接领取红包，并充入现金账号的返回
 *
 * Date: 2014/9/15
 * Time: 14:04
 *
 * @author Tian.Dong
 */
public class DirectRechargeResponse {
  @JSONField(name = "code")
  private String code;

  @JSONField(name = "error_msg")
  private String errorMsg;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getErrorMsg() {
    return errorMsg;
  }

  public void setErrorMsg(String errorMsg) {
    this.errorMsg = errorMsg;
  }
}
