package com.xiaodou.ms.model.knowledge;

import java.util.Map;

import com.google.common.collect.Maps;


public enum ForumType {
  OPENCOURSE("1", "公开课"), SHARE("2", "知识分享"), CAMPUSVOICE("4", "校园之声"), UNKNOWN(null, null);

  private ForumType(String code, String value) {
    this.code = code;
    this.value = value;
  }

  private String code;
  private String value;

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  private static Map<String, ForumType> typeMap = Maps.newHashMap();

  static {
    for (ForumType type : ForumType.values())
      typeMap.put(type.getCode(), type);
  }

  public static ForumType getByCode(String code) {
    ForumType type = typeMap.get(code);
    if (null == type) return UNKNOWN;
    return type;
  }
}
