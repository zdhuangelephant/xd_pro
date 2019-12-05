package com.xiaodou.oms.service.message;

/**
 * <p>
 * 异步消息消息实体类
 * </p>
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年7月3日
 */
public class MessagePojo {
  private String gorderId;
  private String orderId;
  private MessageContext context;

  public String getGorderId() {
    return gorderId;
  }

  public void setGorderId(String gorderId) {
    this.gorderId = gorderId;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public MessageContext getContext() {
    return context;
  }

  public void setContext(MessageContext context) {
    this.context = context;
  }
}
