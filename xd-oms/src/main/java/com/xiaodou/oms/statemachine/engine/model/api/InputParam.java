package com.xiaodou.oms.statemachine.engine.model.api;

import com.xiaodou.oms.exception.ExceptionMessageProp;

/**
 * 入参组件模型
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年6月13日
 */
public class InputParam {

  /**
   * 参数名称
   */
  private String name;

  /**
   * 参数类型
   */
  private Class<?> type;

  /**
   * 参数值
   */
  private Object value;

  /**
   * 获取参数值
   * 
   * @return 参数值
   */
  public Object getValue() {
    return value;
  }

  /**
   * 设置参数值
   * 
   * @param value 参数值
   */
  public void setValue(Object value) {
    this.value = value;
  }

  public void setType(Class<?> type) {
    this.type = type;
  }

  /**
   * 获取参数名称
   * 
   * @return 参数名称
   */
  public String getName() {
    return name;
  }

  /**
   * 设置参数名称
   * 
   * @param name 参数名称
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * 获取参数类型
   * 
   * @return 参数类型
   */
  public Class<?> getType() {
    return type;
  }

  /**
   * 设置参数类型
   * 
   * @param type 参数类型
   */
  public void setType(String type) {
    try {
      this.type = Class.forName(type);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(ExceptionMessageProp.getErrMessage("error.api.inputparam.mistype",
          type));
    }
  }

}
