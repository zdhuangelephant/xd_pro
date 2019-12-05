package com.xiaodou.ms.model.knowledge;

import java.util.Map;

import com.google.common.collect.Maps;

public enum Platform {
  WECHAT_OFFICIAL_ACCOUNT(1, "微信公众号"), TOUTIAO(2, "头条"), COLUMN_ZHIHU(3, "知乎专栏"), UNKNOWN(-1,
      "未知平台");

  private Platform(Integer code, String value) {
    this.code = code;
    this.value = value;
  }

  private Integer code;
  private String value;

  public Integer getCode() {
    return code;
  }
  public void setCode(Integer code) {
    this.code = code;
  }
  public String getValue() {
    return value;
  }
  public void setValue(String value) {
    this.value = value;
  }

  private static Map<Integer, Platform> typeMap = Maps.newHashMap();

  static {
    for (Platform type : Platform.values()) {
      typeMap.put(type.getCode(), type);
    }
  }

  public static Platform getByCode(Integer code) {
    Platform type = typeMap.get(code);
    if (null == type) return UNKNOWN;
    return type;
  }
}
