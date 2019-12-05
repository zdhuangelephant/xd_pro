package com.xiaodou.ms.web.request.admin;

import com.xiaodou.ms.web.request.BaseRequest;
import org.springframework.validation.Errors;

/**
 * Created by zyp on 14-9-26.
 */
public class EmailConfigRequest extends BaseRequest {

  /**
   * 邮件发送端口
   */
  private String emailPort;

  /**
   * 发件人地址
   */
  private String emailFrom;

  /**
   * 邮件服务器
   */
  private String emailServer;

  /**
   * 用户名
   */
  private String emailUser;

  /**
   * 密码
   */
  private String emailPassword;

  public String getEmailPort() {
    return emailPort;
  }

  public void setEmailPort(String emailPort) {
    this.emailPort = emailPort;
  }

  public String getEmailFrom() {
    return emailFrom;
  }

  public void setEmailFrom(String emailFrom) {
    this.emailFrom = emailFrom;
  }

  public String getEmailServer() {
    return emailServer;
  }

  public void setEmailServer(String emailServer) {
    this.emailServer = emailServer;
  }

  public String getEmailUser() {
    return emailUser;
  }

  public void setEmailUser(String emailUser) {
    this.emailUser = emailUser;
  }

  public String getEmailPassword() {
    return emailPassword;
  }

  public void setEmailPassword(String emailPassword) {
    this.emailPassword = emailPassword;
  }

  @Override
  public void validate(Object o, Errors errors) {
    //    super.validate(o, errors);
  }
}
