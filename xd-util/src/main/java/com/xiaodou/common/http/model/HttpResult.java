package com.xiaodou.common.http.model;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;

import com.xiaodou.common.http.HttpResultContentType;
import com.xiaodou.common.http.HttpResultStatus;
import com.xiaodou.common.http.HttpStatusSwitch;
import com.xiaodou.common.util.warp.FastJsonUtil;

public class HttpResult implements IHttpResult {

  /**
   * 请求开始时间
   */
  private long startTime;
  /**
   * 请求结束时间
   */
  private long endTime;
  /** contentType 内容类型 */
  private HttpResultContentType contentType;
  /**
   * 头信息
   */
  private Header[] headers;
  /**
   * Cookie信息
   */
  private Cookie[] cookies;
  /**
   * 尾信息
   */
  private Header[] footers;
  /**
   * 请求返回值
   */
  private String content;
  /**
   * 状态码
   */
  private int statusCode = HttpResultStatus.INIT.getCode();
  /**
   * http状态码
   */
  private Integer httpStatue;

  /**
   * 异常
   */
  private Exception err;

  public HttpResultContentType getContentType() {
    return contentType;
  }

  public void setContentType(HttpResultContentType contentType) {
    this.contentType = contentType;
  }

  public Integer getHttpStatue() {
    return httpStatue;
  }

  public void setHttpStatue(Integer httpStatue) {
    this.httpStatue = httpStatue;
  }

  public Header[] getFooters() {
    return footers;
  }

  public void setFooters(Header[] footers) {
    this.footers = footers;
  }

  public Cookie[] getCookies() {
    return cookies;
  }

  public void setCookies(Cookie[] cookies) {
    this.cookies = cookies;
  }

  public Exception getErr() {
    return err;
  }

  public void setErr(Exception err) {
    this.err = err;
  }

  public long getStartTime() {
    return startTime;
  }

  public void setStartTime(long startTime) {
    this.startTime = startTime;
  }

  public long getEndTime() {
    return endTime;
  }

  public void setEndTime(long endTime) {
    this.endTime = endTime;
  }

  public Header[] getHeaders() {
    return headers;
  }

  public void setHeaders(Header[] headers) {
    this.headers = headers;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }

  public long getHttpTime() {
    return this.endTime - this.startTime;
  }

  public boolean isResultOk() {
    return this.statusCode == HttpResultStatus.OK.getCode();

  }

  @Override
  public String toString() {
    return FastJsonUtil.toJson(this);
  }

  public void setStatusCode(HttpResultStatus status) {
    this.statusCode = status.getCode();
  }

  public String getStatusDesc() {
    return HttpStatusSwitch.getStatus(this.statusCode).getMessage();
  }
}
