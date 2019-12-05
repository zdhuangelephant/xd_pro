package com.xiaodou.autopractise.enums;

import java.util.Random;

/**
 * @name @see com.xiaodou.autopractise.enums.UserStatus.java
 * @CopyRright (c) 2018 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2018年5月4日
 * @description 用户状态
 * @version 1.0
 */
public enum UserAbility {
  ALLRIGHT(0, "与标准答案一致"), RANDOM1O2(2, "随机错1~2道"), RANDOM2O3(3, "随机错1~3道");
  UserAbility(Integer code, String desc) {
    this.code = code;
    this.desc = desc;
  }

  private Integer code;
  private String desc;

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

  public static UserAbility getStatusByCode(Integer code) {
    if (null == code) {
      return ALLRIGHT;
    }
    if (ALLRIGHT.code == code) {
      return ALLRIGHT;
    }
    if (RANDOM1O2.code == code) {
      return RANDOM1O2;
    }
    if (RANDOM2O3.code == code) {
      return RANDOM2O3;
    }
    return ALLRIGHT;
  }

  public Integer getWrongQuesNum() {
    if (0 == getCode()) {
      return getCode();
    }
    return new Random(System.currentTimeMillis()).nextInt(getCode() - 1) + 1;
  }
}
