package com.xiaodou.ms.model.knowledge;

import java.util.Map;

import com.google.common.collect.Maps;


public enum ForumClassify {
  TEXT("1", "文本"), VEDIO("2", "视频"), AUDIO("3", "音频"), UNKNOWN(null, null);

  private ForumClassify(String code, String value) {
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

  private static Map<String, ForumClassify> typeMap = Maps.newHashMap();

  static {
    for (ForumClassify type : ForumClassify.values())
      typeMap.put(type.getCode(), type);
  }

  public static ForumClassify getByCode(String code) {
    ForumClassify classify = typeMap.get(code);
    if (null == classify) return UNKNOWN;
    return classify;
  }
}
