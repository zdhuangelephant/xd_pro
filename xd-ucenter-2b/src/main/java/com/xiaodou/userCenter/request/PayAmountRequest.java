package com.xiaodou.userCenter.request;

import org.springframework.validation.Errors;


/**
 * @name @see com.xiaodou.userCenter.request.PayAmountRequest.java
 * @CopyRright (c) 2016 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年4月15日
 * @description 用户支付请求
 * @version 1.0
 */
public class PayAmountRequest extends BaseRequest {

  private Integer amount;

  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }

  @Override
  public Errors validate() {
    Errors errors = super.validate();
    if (null == amount || amount == 0) errors.rejectValue("amount", null, null, "关键参数验证失败");
    return errors;
  }

}
