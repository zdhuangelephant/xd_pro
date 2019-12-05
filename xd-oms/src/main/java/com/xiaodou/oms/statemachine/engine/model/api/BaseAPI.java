package com.xiaodou.oms.statemachine.engine.model.api;

/**
 * <p>基类API组件模型</p>
 * 定义了名称和返回值名称两个基础属性
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年6月13日
 */
public abstract class BaseAPI {
  
  /**
   * 名称-唯一
   */
  protected String name;
  
  /**
   * 设置API组件名
   * @param name 组件名
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * 设置返回值名
   * @param returnValueName 返回值名
   */
  public void setReturnValueName(String returnValueName) {
    this.returnValueName = returnValueName;
  }

  /**
   * 返回值名称
   */
  protected String returnValueName;

  /**
   * 获取API组件名称
   * @return 组件名称
   */
  public String getName() {
    return name;
  }

  /**
   * 获取返回值名称
   * @return 返回值名称
   */
  public String getReturnValueName() {
    return returnValueName;
  }

}
