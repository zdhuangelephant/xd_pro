package com.xiaodou.resources.enums.quesbk;

/**
 * @name FrequencyType CopyRright (c) 2016 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年10月27日
 * @description 考核频率
 * @version 1.0
 */
public enum FrequencyType {
  NOLIMIT("不限", 0), ONE("1次", 1), TWO("2次", 2), THREE("3次", 3);
  /**
   * 类型名称
   */
  private String typeName;

  /**
   * 类型Id
   */
  private Integer typeId;

  FrequencyType(String typeName, Integer typeId) {
    this.typeName = typeName;
    this.typeId = typeId;
  }

  public String getTypeName() {
    return typeName;
  }


  public Integer getTypeId() {
    return typeId;
  }
}
