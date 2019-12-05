package com.xiaodou.summer.vo.in;

import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.vo.in.BaseValidatorPojo;

public class BaseRequest extends BaseValidatorPojo {

  /**
   * 设备号
   */
  @NotEmpty
  private String deviceId;

  /**
   * 设备ip
   */
  @NotEmpty
  private String clientIp;
  
  /**
   * token值
   */
  private String token;

  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

  public String getClientIp() {
    return clientIp;
  }

  public void setClientIp(String clientIp) {
    this.clientIp = clientIp;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
  
}
