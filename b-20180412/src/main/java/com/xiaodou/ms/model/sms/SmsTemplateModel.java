package com.xiaodou.ms.model.sms;

import java.sql.Timestamp;

/**
 * Created by zyp on 15/6/26.
 */
public class SmsTemplateModel {

  private Integer id;
  
  private String messageContent;

  private String description;

  private Integer status;

  private Timestamp createTime;

  private Integer typeId;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getMessageContent() {
	return messageContent;
}

public void setMessageContent(String messageContent) {
	this.messageContent = messageContent;
}

public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public Integer getTypeId() {
    return typeId;
  }

  public void setTypeId(Integer typeId) {
    this.typeId = typeId;
  }
}
