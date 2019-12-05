package com.xiaodou.payment.constant;

public class PaymentStatus {

  /**
   * payment支付接口中正确请求返回的响应码
   */
  public static final String REP_OKCODE = "0";

  /**
   * payment 回调通知支付/退款处理中 状态
   */
  public static final Integer NOTIFY_DEALING = 0;

  /**
   * payment 回调通知支付/退款成功状态
   */
  public static final Integer NOTIFY_OKCODE = 1;
  /**
   * payment 回调通知支付/退款失败状态
   */
  public static final Integer NOTIFY_FAILCODE = 2;

  /**
   * 退款响应 没有支付过
   */
  public static final String REP_NO_PAY_CODE = "400_5004";

  /**
   * 退款响应 上次支付失败
   */
  public static final String REP_PAY_FAIL_CODE = "400_5011";

  /**
   * 找不到交易记录
   */
  public static final String REP_NO_PAY_RECORD = "400_5007";
  /**
   * 不允许此次继续支付操作 已经支付过了
   */
  public static final String REP_HAS_PAYED = "400_5012";
  /**
   * pmt返回系统错误的返回码
   */
  public static final String REP_ERROR_PREFIX = "500";
  /**
   * http返回不是200
   */
  public static final String REP_HTTP_RESULT_ERROR = "20001";

}
