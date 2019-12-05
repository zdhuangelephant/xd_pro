package com.xiaodou.summer.enums;

import java.util.HashMap;
import java.util.Map;

import com.xiaodou.common.util.StringUtils;

/**
 * @name Gender CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年7月13日
 * @description 性别枚举类
 * @version 1.0
 * @waste
 */
public enum Gender {
  UNKNOW(0, "待完善"), MAN(1, "男"), WOMAM(2, "女");
  private int code;
  private String name;

  private Gender(int code, String name) {
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

  private final static Map<String, Gender> _selectMap = new HashMap<String, Gender>() {
    private static final long serialVersionUID = 3105465691706879287L;

    private HashMap<String, Gender> initThis() {
      for (Gender gender : Gender.values()) {
        if (null == gender) continue;
        this.put(String.valueOf(gender.getCode()), gender);
        this.put(gender.getName(), gender);
      }
      return this;
    }
  }.initThis();

  public static Gender getByCode(String key) {
    if (StringUtils.isNotBlank(key)) {
      return _selectMap.get(key);
    }
    return UNKNOW;
  }
}
