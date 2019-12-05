package com.xiaodou.payment.entity;

/**
 * Date: 2014/7/24
 * Time: 18:30
 *
 * @author Tian.Dong
 */
public class Protocol {
  public static final String GET = "get";
  public static final String POST = "post";

  private String method;
  private String url;

  public Protocol(){

  }
  public Protocol(String method) {
    this.method = method;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}
