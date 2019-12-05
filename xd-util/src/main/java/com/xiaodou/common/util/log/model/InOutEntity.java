package com.xiaodou.common.util.log.model;

import com.alibaba.fastjson.JSON;
import com.xiaodou.common.http.model.HttpResult;

/**
 * InOut日志
 * 
 * @author zhaodan
 * @version 1.0
 * @date 2014-3-26
 */
public class InOutEntity extends BaseEntity {

  /**
   * 目标URL
   */
  private String targetUrl;

  /**
   * 参数
   */
  private String params;

  /**
   * 响应信息
   */
  private String responseInfo;

  /**
   * 耗时(ms)
   */
  private Long useTime;

  /**
   * 请求结果
   */
  private String errMsg;

  public String getErrMsg() {
    return errMsg;
  }

  public void setErrMsg(String errMsg) {
    this.errMsg = errMsg;
  }

  public void setResult(HttpResult result) {
    this.useTime = result.getHttpTime();
    this.responseInfo = result.getContent();
    if (null != result.getErr()) {
      this.errMsg = result.getErr().getMessage();
    }
  }

  public String getTargetUrl() {
    return targetUrl;
  }

  public void setTargetUrl(String targetUrl) {
    this.targetUrl = targetUrl;
  }

  public String getParams() {
    return params;
  }

  public void setParams(String params) {
    this.params = params;
  }

  public String getResponseInfo() {
    return responseInfo;
  }

  public void setResponseInfo(String responseInfo) {
    this.responseInfo = responseInfo;
  }

  public String getUseTime() {
    return useTime + "ms";
  }

  public void setUseTime(Long useTime) {
    this.useTime = useTime;
  }

  @Override
  public String toString() {
    return JSON.toJSONString(this);
  }

}
