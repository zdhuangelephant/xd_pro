package com.xiaodou.ms.enums;

import java.util.Map;

import com.google.common.collect.Maps;

public enum Platform {
  WECHAT_OFFICIAL_ACCOUNT("1", "微信公众号"), TOUTIAO("2", "头条"), COLUMN_ZHIHU("3", "知乎专栏"), UNKNOWN("-1",
      "未知平台");

  private Platform(String code, String value) {
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

  private static Map<String, Platform> typeMap = Maps.newHashMap();

  static {
    for (Platform type : Platform.values()) {
      typeMap.put(type.getCode(), type);
    }
  }

  public static Platform getByCode(String code) {
    Platform type = typeMap.get(code);
    if (null == type) return UNKNOWN;
    return type;
  }
}
