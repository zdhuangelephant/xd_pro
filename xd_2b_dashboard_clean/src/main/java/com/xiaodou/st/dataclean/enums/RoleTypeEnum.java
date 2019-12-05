package com.xiaodou.st.dataclean.enums;

import java.util.Map;

import com.google.common.collect.Maps;

public enum RoleTypeEnum {
  RoleTypeEnum_Taught_Unit(1, "自考办"), RoleTypeEnum_Chief_Unit(2, "主考院校"), RoleTypeEnum_Pilot_Unit(
      3, "试点单位"), RoleTypeEnum_Unknown(-1, "未知单位");
  private Integer code;
  private String desc;

  private RoleTypeEnum(Integer code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  private static final Map<Integer, RoleTypeEnum> roleTypeMap = Maps.newHashMap();
  static {
    for (RoleTypeEnum roleType : RoleTypeEnum.values()) {
      roleTypeMap.put(roleType.code, roleType);
    }
  }

  public static final RoleTypeEnum getByCode(Integer code) {
    if (null == code) return null;
    if (roleTypeMap.containsKey(code)) return roleTypeMap.get(code);
    return RoleTypeEnum_Unknown;
  }

}
