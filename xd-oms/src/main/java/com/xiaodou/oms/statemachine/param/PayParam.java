package com.xiaodou.oms.statemachine.param;

import java.math.BigDecimal;

public class PayParam {

  /** 操作类型 0收款，1退款 */
  private Integer operType;
  /** 收退金额 */
  private BigDecimal amount;
  /** payment交易流水号 */
  private String payNo;
  /**
   * payRecord uuid
   */
  private String uuid;
  /** 失败原因 */
  private String failureReason;
  /**
   * 备注
   */
  private String remark;

  public String getUuid() {
    return uuid;
  }

  public void setUuid(String uuid) {
    this.uuid = uuid;
  }

  public Integer getOperType() {
    return operType;
  }

  public void setOperType(Integer operType) {
    this.operType = operType;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public String getPayNo() {
    return payNo;
  }

  public void setPayNo(String payNo) {
    this.payNo = payNo;
  }

  public String getFailureReason() {
    return failureReason;
  }

  public void setFailureReason(String failureReason) {
    this.failureReason = failureReason;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }
}
