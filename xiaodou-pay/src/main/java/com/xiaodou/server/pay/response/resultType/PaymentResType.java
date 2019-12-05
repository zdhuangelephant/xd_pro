package com.xiaodou.server.pay.response.resultType;

import java.net.InetAddress;
import java.net.UnknownHostException;

public enum PaymentResType {
  /**
   * 生成订单接口
   */
  /** FindPayGoodsFailed 没有找到对应要支付的商品 */
  FindPayGoodsFailed("30000", "没有找到对应要支付的商品"),

  /**
   * 支付接口
   */
  /** FindPayFailed 没有找到对应支付数据 */
  FindPayFailed("30010", "没有找到对应支付数据"),
  /** PayIng 正在支付中 */
  PayIng("30011", "正在支付中"),
  /** UpdateTosFail 更新凭证失败 */
  UpdateTosFail("30012", "更新凭证失败"),
  /** TokenUnValable 无效token值 */
  TokenUnValable("30013", "无效token"),
  /** UnMatchedBusinessType 业务类型不匹配 */
  UnMatchedBusinessType("30014", "业务类型不匹配"),
  /** UnMatchedOutTradeNo 业务订单号不匹配 */
  UnMatchedOutTradeNo("30015", "业务订单号不匹配");


  /**
   * 结果码
   */
  private String code;

  /**
   * 提示信息
   */
  private String msg;

  /**
   * 服务器Ip
   */
  private String serverIp;

  public String getServerIp() {
    return serverIp;
  }

  public String getCode() {
    return code;
  }

  public String getMsg() {
    return msg;
  }

  /**
   * Constract
   * 
   * @param code 结果码
   * @param msg 提示信息
   * @throws UnknownHostException
   */
  PaymentResType(String code, String msg) {
    this.code = code;
    this.msg = msg;
    this.serverIp = "127.0.0.0";
    try {
      InetAddress addr = InetAddress.getLocalHost();
      this.serverIp = addr.getHostAddress().toString();// 获得本机IP
    } catch (UnknownHostException e) {}
  }

}
