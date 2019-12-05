package com.xiaodou.server.pay.request;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenRequest extends BaseRequest {

  /** outTradeNo 业务系统唯一流水号 */
  private String outTradeNo;

  public String getOutTradeNo() {
    return outTradeNo;
  }

  public void setOutTradeNo(String outTradeNo) {
    this.outTradeNo = outTradeNo;
  }

}
