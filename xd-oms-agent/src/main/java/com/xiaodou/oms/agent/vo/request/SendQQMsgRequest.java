package com.xiaodou.oms.agent.vo.request;

/**
 * Date: 2014/12/23
 * Time: 16:36
 *
 * @author Tian.Dong
 */
public class SendQQMsgRequest {
  private String name;
  private String content;
  private String type;


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }
}