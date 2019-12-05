package com.xiaodou.oms.agent.model.statemachine;

import java.math.BigDecimal;

public class PayParam {

    /**操作类型-收款 */
    public static final Integer OPER_TYPE_PAY = 0;

    /**操作类型-退款 */
    public static final Integer OPER_TYPE_REFUND = 1;

    /**状态-处理成功 */
    public static final Integer PAY_STATUS_SUCCESS = 2;

    /**状态-处理失败 */
    public static final Integer PAY_STATUS_FAILURE = -1;

    /**状态-未找到前置交易 */
    public static final Integer PAY_STATUS_NO_PRE_NO = 3;

    /** 操作类型 0收款，1退款 */
    private Integer operType;

    /** 收退金额 */
    private BigDecimal amount;

    /** payment交易流水号 */
    private String payNo;

    /** 失败原因 */
    private String failureReason;

    /**处理*/
    private String uuid;

    /**备注*/
    private String note;

    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
