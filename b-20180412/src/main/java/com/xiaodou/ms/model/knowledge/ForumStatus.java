package com.xiaodou.ms.model.knowledge;

import java.util.Map;

import com.google.common.collect.Maps;


public enum ForumStatus {
  SHOW((short) 1, "是"), HIDDEN((short) 0, "否"), UNKNOWN(null, null);

  private ForumStatus(Short code, String value) {
    this.code = code;
    this.value = value;
  }

  private Short code;
  private String value;

  public Short getCode() {
    return code;
  }

  public void setCode(Short code) {
    this.code = code;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  private static Map<Short, ForumStatus> typeMap = Maps.newHashMap();

  static {
    for (ForumStatus type : ForumStatus.values())
      typeMap.put(type.getCode(), type);
  }

  public static ForumStatus getByCode(Short code) {
    ForumStatus type = typeMap.get(code);
    if (null == type) return UNKNOWN;
    return type;
  }
}
