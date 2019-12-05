package com.xiaodou.userCenter.model.property;

import java.util.HashMap;
import java.util.Map;

public enum AppBindingModelProperty {
  id("id","1"),deviceId("deviceId","1"),pushId("pushId","1"),
  systemType("systemType","1"),userId("userId","1"),createTime("createTime","1");
  /** 属性名称 */
  private String propertyName;

  /** 属性类型 */
  private String propertyType;

  public String getPropertyName() {
    return propertyName;
  }

  public void setPropertyName(String propertyName) {
    this.propertyName = propertyName;
  }

  public String getPropertyType() {
    return propertyType;
  }

  public void setPropertyType(String propertyType) {
    this.propertyType = propertyType;
  }

  private AppBindingModelProperty(String propertyName, String propertyType) {
    this.propertyName = propertyName;
    this.propertyType = propertyType;
  }
  public static Map<String, Object> getAllInfo(){
    Map<String, Object> output = new HashMap<String, Object>();
    output.put(AppBindingModelProperty.id.propertyName, AppBindingModelProperty.id.propertyType);
    output.put(AppBindingModelProperty.deviceId.propertyName, AppBindingModelProperty.deviceId.propertyType);
    output.put(AppBindingModelProperty.pushId.propertyName, AppBindingModelProperty.pushId.propertyType);
    output.put(AppBindingModelProperty.systemType.propertyName, AppBindingModelProperty.systemType.propertyType);
    output.put(AppBindingModelProperty.userId.propertyName, AppBindingModelProperty.userId.propertyType);
    output.put(AppBindingModelProperty.createTime.propertyName, AppBindingModelProperty.createTime.propertyType);
    return output;
  }
}
