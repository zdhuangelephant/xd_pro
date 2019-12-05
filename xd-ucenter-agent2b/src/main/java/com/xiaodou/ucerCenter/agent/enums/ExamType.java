package com.xiaodou.ucerCenter.agent.enums;

import java.util.Map;

import com.google.common.collect.Maps;

public enum ExamType {
  UNKNOW(0, "待完善"), YOUER(1, "幼儿"), XIAOXUE(2, "小学"), CHUZHONG(3, "中学");
  private int code;
  private String name;

  private ExamType(int code, String name) {
    this.code = code;
    this.name = name;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  private final static Map<Integer, ExamType> _allExamTypeType = Maps.newHashMap();

  private static void init() {
    for (ExamType examType : ExamType.values()) {
      if (null == examType) continue;
      _allExamTypeType.put(examType.getCode(), examType);
    }
  }

  static {
    init();
  }

  public static ExamType getByCode(Integer code) {
    if (null != code && _allExamTypeType.containsKey(code)) return _allExamTypeType.get(code);
    return UNKNOW;
  }
}
