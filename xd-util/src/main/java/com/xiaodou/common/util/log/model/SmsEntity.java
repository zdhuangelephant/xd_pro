package com.xiaodou.common.util.log.model;

public class SmsEntity extends BaseEntity {
  /**
   * 模板号
   */
  private String templateId;
  /**
   *手机号
   */
  private String mobile;
  /**
   * 参数
   */
  private String param;
  /**
   * 响应
   */
  private String response;
  public String getTemplateId() {
    return templateId;
  }
  public void setTemplateId(String templateId) {
    this.templateId = templateId;
  }
  public String getMobile() {
    return mobile;
  }
  public void setMobile(String mobile) {
    this.mobile = mobile;
  }
  public String getParam() {
    return param;
  }
  public void setParam(String param) {
    this.param = param;
  }
  public String getResponse() {
    return response;
  }
  public void setResponse(String response) {
    this.response = response;
  }
  
}
