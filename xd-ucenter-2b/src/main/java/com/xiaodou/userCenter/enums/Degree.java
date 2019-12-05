package com.xiaodou.userCenter.enums;

import java.util.Map;

import com.google.common.collect.Maps;

public enum Degree {
  UNKNOW(0, "待完善"), YANJIUSHENG(1, "研究生"), BENKE(2, "本科"), DAZHUAN(3, "大专"), ZHONGZHUAN(4, "中专");
  private int code;
  private String name;

  private Degree(int code, String name) {
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

  private final static Map<Integer, Degree> _allDegreeType = Maps.newHashMap();

  private static void init() {
    for (Degree degree : Degree.values()) {
      if (null == degree) continue;
      _allDegreeType.put(degree.getCode(), degree);
    }
  }

  static {
    init();
  }

  public static Degree getByCode(Integer code) {
    if (null != code && _allDegreeType.containsKey(code)) return _allDegreeType.get(code);
    return UNKNOW;
  }
}
