package com.xiaodou.message.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class MessageModel implements Serializable {

  private static final long serialVersionUID = 2846308360210754036L;
  // 标识id
  private Long id;
  //push消息体
  private String message;
  //创建时间
  private Timestamp createTime;
  //push状态 0,准备发送
  private String status;
  //push返回说明
  private String msg;
  
  
  /** appMessageId 应用消息ID */
  private String appMessageId; 
  /** productLine 产品线 */
  private String productLine;
  
  
  
  public Long getId() {
    return id;
  }
  public void setId(Long id) {
    this.id = id;
  }
  public String getMessage() {
    return message;
  }
  public void setMessage(String message) {
    this.message = message;
  }
  public Timestamp getCreateTime() {
    return createTime;
  }
  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }
  public String getStatus() {
    return status;
  }
  public void setStatus(String status) {
    this.status = status;
  }
  public String getMsg() {
    return msg;
  }
  public void setMsg(String msg) {
    this.msg = msg;
  }
  public String getAppMessageId() {
    return appMessageId;
  }
  public void setAppMessageId(String appMessageId) {
    this.appMessageId = appMessageId;
  }
  public String getProductLine() {
    return productLine;
  }
  public void setProductLine(String productLine) {
    this.productLine = productLine;
  }
  
  
}
