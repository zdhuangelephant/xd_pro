package com.xiaodou.common.util.log.model;


/**
 * <p>异步消息记录日志实体类</p>
 *
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年7月3日
 */
public class MessageEntity<T> extends BaseEntity{
  
  private T pojo;
  
  private String tag;
  
  private String messageName;
  
  private String result;
  
  private Exception errmsg;

  public String getResult() {
    return result;
  }

  public void setResult(String result) {
    this.result = result;
  }

  public Exception getErrmsg() {
    return errmsg;
  }

  public void setErrmsg(Exception errmsg) {
    this.errmsg = errmsg;
  }

  public T getPojo() {
    return pojo;
  }

  public void setPojo(T pojo) {
    this.pojo = pojo;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  public String getMessageName() {
    return messageName;
  }

  public void setMessageName(String messageName) {
    this.messageName = messageName;
  }

  public MessageEntity(T pojo, String tag, String messageName) {
    this.pojo = pojo;
    this.tag = tag;
    this.messageName = messageName;
  }
  
}
