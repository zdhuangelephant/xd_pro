package com.xiaodou.push.agent.enums;

/**
 * @name @see cpm.xiaodou.push.agent.enums.SpreadRange.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月14日
 * @description 传播范围
 * @version 1.0
 */
public enum SpreadRange {
  /** ALL 全部 */
  ALL("0"), /** INTENDED 指定目标 */
  /** 通过ALIAS别名指定目标 */
  ALIAS("1"),
  /** 通过TAG标签指定目标 */
  TAG("2"),
  /** 通过registration_id注册ID来指定目标 */
  REGISTRATION_ID("3"),
  /** 通过别名ALIAS和标签TAG来指定目标 */
  ALIAS_TAG("4"),
  /** 通过ALIAS和REGISTRATION_ID来指定目标 */
  ALIAS_REGISTRATION_ID("5"),
  /** 通过TAG和REGISTRATION_ID来指定目标 */
  TAG_REGISTRATION_ID("6"),
  /** 通过ALIAS,TAG和REGISTRATION_ID来指定目标 */
  ALIAS_TAG_REGISTRATION_ID("7");
  private String spreadRange;
  private SpreadRange(String spreadRange){
    this.spreadRange = spreadRange;
  }
  public String getSpreadRange() {
    return spreadRange;
  }
  public void setSpreadRange(String spreadRange) {
    this.spreadRange = spreadRange;
  }
}
