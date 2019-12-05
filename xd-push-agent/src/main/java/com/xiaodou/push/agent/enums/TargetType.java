package com.xiaodou.push.agent.enums;

/**
 * @name @see cpm.xiaodou.push.agent.enums.TargetType.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月14日
 * @description 目标设备类型
 * @version 1.0
 */
public enum TargetType {
  /** ALL 全部設備 */
  ALL("0"), 
  /** ANDROID 安卓設備 */
  ANDROID("1"), 
  /** IOS IOS設備 */
  IOS("2");
  
  private String typeCode;
  private TargetType(String typeCode){
    this.typeCode = typeCode;
  }
  
  public String getTypeCode() {
    return typeCode;
  }
  public void setTypeCode(String typeCode) {
    this.typeCode = typeCode;
  }
}
