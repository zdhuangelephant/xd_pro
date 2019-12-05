package com.xiaodou.userCenter.model.property;

import java.util.HashMap;
import java.util.Map;

public enum HelpModelProperty {
  id("id", "1"), type("type", "1"), title("title", "1"), content("content", "1"), createTime("createTime", "1");
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

  private HelpModelProperty(String propertyName, String propertyType) {
    this.propertyName = propertyName;
    this.propertyType = propertyType;
  }
  
  /**
   * 
   * 所有出参map
   *
   * @return
   */
  public static Map<String, Object> getAllInfo(){
    Map<String, Object> output = new HashMap<String, Object>();
    output.put(HelpModelProperty.id.getPropertyName(), HelpModelProperty.id.getPropertyType());
    output.put(HelpModelProperty.title.getPropertyName(), HelpModelProperty.title.getPropertyType());
    output.put(HelpModelProperty.content.getPropertyName(), HelpModelProperty.content.getPropertyType());
    output.put(HelpModelProperty.createTime.getPropertyName(), HelpModelProperty.createTime.getPropertyType());
    return output;
  }
}
