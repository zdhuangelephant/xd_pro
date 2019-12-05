package com.xiaodou.common.util.log.model;

/**
 * OutIn日志
 * 
 * @author zhaodan
 * @version 1.0
 * @date 2014-3-26
 */
public class OutInEntity extends BaseEntity {

  /**
   * 目标service
   */
  private String targetClass;
  /**
   * 目标方法
   */
  private String targetMethod;
  /**
   * 入参
   */
  private String params;
  /**
   * 方法响应结果
   */
  private String responseInfo;
  /**
   * 耗时(ms)
   */
  private Long useTime;

  public String getTargetClass() {
    return targetClass;
  }

  public void setTargetClass(String targetClass) {
    this.targetClass = targetClass;
  }

  public String getTargetMethod() {
    return targetMethod;
  }

  public void setTargetMethod(String targetMethod) {
    this.targetMethod = targetMethod;
  }

  public String getParams() {
    return params;
  }

  public void setParams(Object params) {
    this.params = params.toString();
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

  public void setResponseInfo(Object responseInfo) {
    this.responseInfo = responseInfo.toString();
  }

  public String getUseTime() {
    return useTime + "ms";
  }

  public void setUseTime(Long useTime) {
    this.useTime = useTime;
  }

}
