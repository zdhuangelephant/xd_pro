package com.xiaodou.oms.dao;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * <p>
 * 数据库类型
 * </p>
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年8月27日
 */
public enum OperationType {

  WRITE("WRITE"), READ("READ"), READWRITE("READWRITE"), DEFAULT("DEFAULT");
  private String value;

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  OperationType(String value) {
    this.value = value;
  }
  
  private final static Map<String, OperationType> typeMap = Maps.newHashMap();
  
  static {
    for(OperationType type : OperationType.values())
      typeMap.put(type.getValue(), type);
  }
  
  public static Map<String, OperationType> getEnumMap(){
    return Maps.newHashMap(typeMap);
  }
  
}
