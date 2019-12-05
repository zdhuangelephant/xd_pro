package com.xiaodou.payment.vo.response;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.xiaodou.common.util.warp.FastJsonUtil;

public class ResponseBase {

  private String retcode;

  private String retdesc;

  private String serverIp;

  public String getServerIp() {
    return serverIp;
  }

  public void setServerIp(String serverIp) {
    this.serverIp = serverIp;
  }

  public ResponseBase() {

  }

  public ResponseBase(String retcode, String retdesc, String serverIp) {
    this.retcode = retcode;
    this.retdesc = retdesc;
    this.serverIp = serverIp;
  }

  public String getRetcode() {

    return this.retcode;
  }

  public void setRetcode(String retcode) {

    this.retcode = retcode;
  }

  public String getRetdesc() {

    return this.retdesc;
  }

  public void setRetdesc(String retdesc) {

    this.retdesc = retdesc;
  }

  public void appendRetdesc(String retdesc) {
    this.retdesc += retdesc;
  }

  public String toString0() {
    return FastJsonUtil.toJson(this);
  }

  /**
   * 错误描述
   */
  private String message = "";


  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  @Override
  public String toString() {
    return FastJsonUtil.toJson(this, SerializerFeature.DisableCircularReferenceDetect);
  }

  public boolean isRetOk() {
    return "0".equals(getRetcode());
  }

}
