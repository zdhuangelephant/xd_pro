package com.xiaodou.ms.web.request.admin;

import com.xiaodou.ms.web.request.BaseRequest;
import org.springframework.validation.Errors;

/**
 * Created by zyp on 14-9-26.
 */
public class SecurityConfigRequest extends BaseRequest {

  /**
   * 最大登陆失败次数
   */
  public String loginFailTime;

  /**
   * 是否开启后台操作日志
   */
  public String openOperLog;

  public String getLoginFailTime() {
    return loginFailTime;
  }

  public void setLoginFailTime(String loginFailTime) {
    this.loginFailTime = loginFailTime;
  }

  public String getOpenOperLog() {
    return openOperLog;
  }

  public void setOpenOperLog(String openOperLog) {
    this.openOperLog = openOperLog;
  }

  @Override
  public void validate(Object o, Errors errors) {
    //    super.validate(o, errors);
  }
}
