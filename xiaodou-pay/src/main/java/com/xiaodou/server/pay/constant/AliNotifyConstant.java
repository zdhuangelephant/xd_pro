package com.xiaodou.server.pay.constant;

public class AliNotifyConstant {
  
  /*支付成功   交易状态TRADE_SUCCESS的通知触发条件是商户签约的产品支持退款功能的前提下，买家付款成功；*/
  public static final String TRADE_SUCCESS = "TRADE_SUCCESS";
  
  /*交易成功  交易状态TRADE_FINISHED的通知触发条件是商户签约的产品不支持退款功能的前提下，买家付款成功；
   * 或者，商户签约的产品支持退款功能的前提下，交易已经成功并且已经超过可退款期限*/
  public static final String TRADE_FINISHED = "TRADE_FINISHED";
  
  /*交易创建*/
  public static final String WAIT_BUYER_PAY = "WAIT_BUYER_PAY";
  
  /*交易关闭    false（不触发通知）*/
  public static final String TRADE_CLOSED = "TRADE_CLOSED";
}
