package com.xiaodou.dashboard.enums;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * @name @see com.xiaodou.dashboard.enums.CommonEnums.java
 * @CopyRright (c) 2017 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年5月8日
 * @description 通用枚举
 * @version 1.0
 */
public class CommonEnums {

  public enum BussinessCode {
    Self_Taught("02", "自考平台"), Un_Known(null, "未知平台");
    BussinessCode(String code, String desc) {
      this.code = code;
      this.desc = desc;
    }

    private String code;
    private String desc;

    public String getCode() {
      return code;
    }

    public void setCode(String code) {
      this.code = code;
    }

    public String getDesc() {
      return desc;
    }

    public void setDesc(String desc) {
      this.desc = desc;
    }

  }

  private static final Map<String, BussinessCode> businessMap = Maps.newHashMap();
  static {
    for (BussinessCode businessCode : BussinessCode.values())
      businessMap.put(businessCode.code, businessCode);
  }

  public static BussinessCode getBussinessCodeByCode(String code) {
    BussinessCode businessCode = businessMap.get(code);
    if (null != businessCode) return businessCode;
    return BussinessCode.Un_Known;
  }

}
