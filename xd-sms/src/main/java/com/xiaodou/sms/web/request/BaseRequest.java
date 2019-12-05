package com.xiaodou.sms.web.request;

import com.xiaodou.summer.vo.in.BaseValidatorPojo;

/**
 * 
 * @author weichao.zhai
 * @time 2015年4月6日 下午4:52:08
 */
public class BaseRequest extends BaseValidatorPojo {

  private String deviceId;

  public String getDeviceId() {
    return deviceId;
  }

  public void setDeviceId(String deviceId) {
    this.deviceId = deviceId;
  }

}
