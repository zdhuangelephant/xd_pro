package com.xiaodou.oms.util.model;

import org.apache.commons.httpclient.Header;

import com.xiaodou.common.http.HttpResultStatus;
import com.xiaodou.common.http.model.HttpResult;
import com.xiaodou.common.util.warp.FastJsonUtil;


public class HttpResultLog {
  
  public HttpResultLog(HttpResult result){
    this.content = result.getContent();
    this.endTime = result.getEndTime();
    this.startTime = result.getStartTime();
    this.headers = result.getHeaders();
    this.statusCode = result.getStatusCode();
  }

  /**
   * 请求开始时间
   */
  private long startTime;
  /**
   * 请求结束时间
   */
  private long endTime;
  /**
   * 头信息
   */
  private Header[] headers;
  /**
   * 请求返回值
   */
  private String content;
  /**
   * 状态码
   */
  private int statusCode;

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
}
