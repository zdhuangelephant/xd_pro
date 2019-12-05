package com.xiaodou.userCenter.enums;

/**
 * 
 * @name MajorLevel CopyRright (c) 2016 by 51xiaodou.com
 * 
 * @author lidehong
 * @date 2016年5月6日
 * @description 专业层次(1：本科，2：专科)
 * @version 1.0
 */
public enum MajorLevel {
  benke("1", "本科"), zhuanke("2", "专科");

  private MajorLevel(String code, String value) {
    this.code = code;
    this.value = value;
  }

  public String code;
  public String value;

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
}
