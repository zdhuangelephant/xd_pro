package com.xiaodou.autotest.web.enums;

import java.util.Map;

import com.google.common.collect.Maps;
/**
 * @name @see com.xiaodou.autotest.web.util.DocHolder.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">huangzedong</a>
 * @date 2017年9月5日
 * @description 模版容器
 * @version 1.0
 */
public enum GroupTmp {
  /** UNKNOWN  */
  UNKNOWN((short)-1, "未知"), 
  /** HEADER  */
  HEADER((short)0, "头"), 
  /** PARAM  */
  PARAM((short)1, "参数"),
  /** PRECOND  */
  PRECOND((short)2, "前置条件"),
  /** PRESET  */
  PRESET((short)3, "预置参数"), 
  /** TESTCASE  */
  TESTCASE((short)4,"测试用例"),
  /** AFTERSET  */
  AFTERSET((short)5,"后置参数");

  private GroupTmp(Short code, String value) {
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

  private static Map<Short, GroupTmp> typeMap = Maps.newHashMap();

  static {
    for (GroupTmp type : GroupTmp.values()) {
      typeMap.put(type.getCode(), type);
    }
  }

  public static GroupTmp getByCode(String code) {
    GroupTmp type = typeMap.get(code);
    if (null == type) {return UNKNOWN;};
    return type;
  }
}
