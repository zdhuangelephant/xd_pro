package com.xiaodou.message.enums;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Maps;

public enum JpushTagsEnums {
  XD_TEST("xd_test","开发版的测试环境"), XD_ONLINE("xd_online","发布版的线上环境"),  XD_PRE_RELEASE("xd_pre_release","开发版线上环境"), XD_DEMO("xd_demo","开发版的演示环境");

  private String name;
  private String msg;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }

  private JpushTagsEnums(String name, String msg) {
    this.name = name;
    this.msg = msg;
  }

  private static final Map<String, JpushTagsEnums> jpushMap = Maps.newConcurrentMap();

  public static void init() {
    for (JpushTagsEnums jpushEnum : JpushTagsEnums.values()) {
      if (null == jpushEnum) continue;
      jpushMap.put(jpushEnum.getName(), jpushEnum);
    }
  }

  static {
    init();
  }

  public static JpushTagsEnums getByName(String name) {
    if (StringUtils.isNotBlank(name) && jpushMap.containsKey(name)) {
      return jpushMap.get(name);
    }
    return null;
  }
  
  public static JpushTagsEnums getByName(List<String> list) {
    for(String name:list){
      if (StringUtils.isNotBlank(name) && jpushMap.containsKey(name)) {
        return jpushMap.get(name);
      }
    }
    return null;
  }
  
}
