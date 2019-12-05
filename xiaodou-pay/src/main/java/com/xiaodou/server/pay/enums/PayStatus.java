package com.xiaodou.server.pay.enums;

/**
 * @name PayStatus 
 * CopyRright (c) 2018 by 李德洪
 *
 * @author 李德洪
 * @date 2018年3月6日
 * @description 支付状态（0:正在支付1:支付成功2:支付失败）
 * @version 1.0
 */
public enum PayStatus {
  PAYING(0, "正在支付"), SUCCESS(1, "支付成功"), FAIL(2, "支付失败");
  PayStatus(Integer code, String status) {
    this.code = code.shortValue();
    this.status = status;
  }

  private Short code;
  private String status;

  public Short getCode() {
    return code;
  }

  public void setCode(Short code) {
    this.code = code;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

}
