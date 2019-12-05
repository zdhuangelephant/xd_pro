package com.xiaodou.wallet.agent.request;

import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * @name @see com.xiaodou.wallet.request.wallet.PayAmountRequest.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月2日
 * @description 完成支付请求
 * @version 1.0
 */
public class PayAmountRequest extends BusinessOperateRequest {

  /** subject 交易名称 */
  @NotEmpty
  private String subject;
  /** desc 交易描述 */
  @NotEmpty
  private String desc;
  /** amount 实际扣款金额 */
  @NotEmpty
  private Double amount;
  /** tradeNo 外部交易号 */
  @NotEmpty
  private String tradeNo;

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

  public String getDesc() {
    return desc;
  }

  public void setDesc(String desc) {
    this.desc = desc;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(Double amount) {
    this.amount = amount;
  }

  public String getTradeNo() {
    return tradeNo;
  }

  public void setTradeNo(String tradeNo) {
    this.tradeNo = tradeNo;
  }

}
