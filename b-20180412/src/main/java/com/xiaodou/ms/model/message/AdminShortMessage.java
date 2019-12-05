package com.xiaodou.ms.model.message;

import java.sql.Timestamp;

import com.xiaodou.ms.model.sms.SmsTemplateModel;

public class AdminShortMessage {
  //标识id
  private Long id;
  //后台短信操作状态（0.未发送1.已经发送）
  private String messageStatus;
  //供应商id
  private String merchantId;
  //模板id
  private String templateId;
  //电话号
  private String telephone ;
  //模板变量参数
  private String variables;
  // 创建时间
  private Timestamp createTime;
  // 创建人
  private String createUser;
  //最后操作时间
  private Timestamp updateTime;
  // 操作人
  private String updateUser;
  
  //模板对象
  private SmsTemplateModel templateModel;
  //模板变量
  private String variable;
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getMerchantId() {
    return merchantId;
  }
  public void setMerchantId(String merchantId) {
    this.merchantId = merchantId;
  }
  public String getTemplateId() {
    return templateId;
  }
  public void setTemplateId(String templateId) {
    this.templateId = templateId;
  }
  public Timestamp getCreateTime() {
    return createTime;
  }
  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }
  public String getCreateUser() {
    return createUser;
  }
  public void setCreateUser(String createUser) {
    this.createUser = createUser;
  }
  public Timestamp getUpdateTime() {
    return updateTime;
  }
  public void setUpdateTime(Timestamp updateTime) {
    this.updateTime = updateTime;
  }
  public String getUpdateUser() {
    return updateUser;
  }
  public void setUpdateUser(String updateUser) {
    this.updateUser = updateUser;
  }
  public String getMessageStatus() {
    return messageStatus;
  }
  public void setMessageStatus(String messageStatus) {
    this.messageStatus = messageStatus;
  }
  public String getTelephone() {
    return telephone;
  }
  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }
  public String getVariables() {
    return variables;
  }
  public void setVariables(String variables) {
    this.variables = variables;
  }
  public SmsTemplateModel getTemplateModel() {
    return templateModel;
  }
  public void setTemplateModel(SmsTemplateModel templateModel) {
    this.templateModel = templateModel;
  }
  public String getVariable() {
    return variable;
  }
  public void setVariable(String variable) {
    this.variable = variable;
  }
}
