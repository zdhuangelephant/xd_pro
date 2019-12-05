package com.xiaodou.oms.statemachine.engine.model.api;


/**
 * <p>
 * 本地API组件模型
 * </p>
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年6月13日
 */
public class LocalAPI extends BaseAPI {
  
  /**
   * BeanId
   */
  private String beanId;

  /**
   * 类名
   */
  private String className;

  /**
   * 方法名
   */
  private String method;

  public String getBeanId() {
    return beanId;
  }

  public void setBeanId(String beanId) {
    this.beanId = beanId;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public String getMethod() {
    return method;
  }

  public void setMethod(String method) {
    this.method = method;
  }

}
