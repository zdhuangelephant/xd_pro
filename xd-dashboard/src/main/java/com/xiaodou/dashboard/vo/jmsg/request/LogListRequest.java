package com.xiaodou.dashboard.vo.jmsg.request;

public class LogListRequest {

  private String datepicker;
  private String messageName;
  private String customTag;
  private String messageData;
  private Integer pageNo = 1;
  private String result;

  public String getDatepicker() {
    return datepicker;
  }

  public void setDatepicker(String datepicker) {
    this.datepicker = datepicker;
  }

  public String getMessageName() {
    return messageName;
  }

  public void setMessageName(String messageName) {
    this.messageName = messageName;
  }

  public String getCustomTag() {
    return customTag;
  }

  public void setCustomTag(String customTag) {
    this.customTag = customTag;
  }

  public String getMessageData() {
    return messageData;
  }

  public void setMessageData(String messageData) {
    this.messageData = messageData;
  }

  public Integer getPageNo() {
    return pageNo;
  }

  public void setPageNo(Integer pageNo) {
    this.pageNo = pageNo;
  }

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

}
