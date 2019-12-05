package com.xiaodou.queue.enums;

/**
 * @name @see com.xiaodou.queue.enums.WeightEnum.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月22日
 * @description 权重枚举值
 * @version 1.0
 */
public enum WeightEnum {
  ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5);
  private Integer code;
  public Integer getCode() {
    return code;
  }
  WeightEnum(int code) {
    this.code = code;
  }
}
