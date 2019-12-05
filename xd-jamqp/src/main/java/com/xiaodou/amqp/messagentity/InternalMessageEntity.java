package com.xiaodou.amqp.messagentity;

public class InternalMessageEntity {
  private String message;
  private long tag;

  public InternalMessageEntity(String message, long tag) {
    this.message = message;
    this.tag = tag;
  }

  public String getMessage() {
    return message;
  }

  public long getTag() {
    return tag;
  }
}
