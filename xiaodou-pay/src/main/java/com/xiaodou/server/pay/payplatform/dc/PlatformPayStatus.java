package com.xiaodou.server.pay.payplatform.dc;

/**
 * @name @see com.xiaodou.server.pay.thirdpayplaform.PayStatus.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 *
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年5月26日
 * @description 三方支付平台支付状态
 * @version 1.0
 */
public enum PlatformPayStatus {
  /** SUCCESS 支付成功 */
  SUCCESS,
  /** REFUND 退款 */
  REFUND,
  /** NOTPAY 未支付 */
  NOTPAY,
  /** CLOSED 交易关闭 */
  CLOSED,
  /** REVOKED 交易取消 */
  REVOKED,
  /** USERPAYING 支付中 */
  USERPAYING,
  /** PAYERROR 支付失败 */
  PAYERROR
}
