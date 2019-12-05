package com.xiaodou.oms.vo.result.pay;

import com.xiaodou.oms.vo.result.ResultInfo;
import com.xiaodou.oms.vo.result.ResultType;

/**
 * 返回第一次支付需要的信息
 *
 * Date: 2014/8/27
 * Time: 13:19
 *
 * @author Tian.Dong
 */
public class FirstPayInfoVO extends ResultInfo {
  public FirstPayInfoVO(){

  }
  public FirstPayInfoVO(ResultType resultType) {
    super(resultType);
  }

  private String notifyUrl;
  private String tradeNo;
  private double payAmount;

  public String getNotifyUrl() {
    return notifyUrl;
  }

  public void setNotifyUrl(String notifyUrl) {
    this.notifyUrl = notifyUrl;
  }

  public String getTradeNo() {
    return tradeNo;
  }

  public void setTradeNo(String tradeNo) {
    this.tradeNo = tradeNo;
  }

  public double getPayAmount() {
    return payAmount;
  }

  public void setPayAmount(double payAmount) {
    this.payAmount = payAmount;
  }
}
