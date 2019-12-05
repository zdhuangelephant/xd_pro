package com.xiaodou.server.pay.enums;

/**
 * @name AliTradeStatus CopyRright (c) 2018 by 李德洪
 * 
 * @author 李德洪
 * @date 2018年2月8日
 * @description 通知触发条件<br>
 *              true（触发通知）/false（不触发通知）具体值和签约配置时保持同步
 * @version 1.0
 */
public enum AliTradeStatus {
  
  TRADE_FINISHED("交易成功", true), TRADE_SUCCESS("支付成功", true), WAIT_BUYER_PAY("交易创建", true), TRADE_CLOSED(
      "交易关闭", false);

  private String desc;
  private Boolean remark;

  AliTradeStatus(String desc, Boolean remark) {
    this.desc = desc;
    this.remark = remark;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public Boolean getRemark() {
    return remark;
  }

  public void setRemark(Boolean remark) {
    this.remark = remark;
  }

}
