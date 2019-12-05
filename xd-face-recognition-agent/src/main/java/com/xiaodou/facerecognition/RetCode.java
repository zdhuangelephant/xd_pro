package com.xiaodou.facerecognition;


/**
 * @name RetCode
 * @CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月29日
 * @description 响应码枚举
 * @version 1.0
 */
public enum RetCode {
  Success("0", "成功"), SysErr("-99", "无法识别面部特征");
  RetCode(String code, String desc) {
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
