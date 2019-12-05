package com.xiaodou.wallet.response;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @name @see com.xiaodou.wallet.response.WalletResType.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月2日
 * @description 钱包异常返回值枚举
 * @version 1.0
 */
public enum WalletResType {
  /**
   * 账户
   */
  AccountHasExits("40001", "账户已存在"),
  /**
   * 商品列表
   */
  EmptyGoods("40101", "商品列表为空"),
  /**
   * 获取验证码时状态枚举
   */
  FifteenLimit("20201", "当日验证码发送短信量已达上限"), OneMinuteNoRepeat("20202", "一分钟内不能重发"), CheckCodeTypeError(
      "20203", "验证码类型错误"), PhoneNumError("20204", "手机号无效"), CheckCodeOutDate("20302", "验证码过期"), CheckCodeError(
      "20303", "验证码错误"),
  /**
   * 支付/充值
   */
  CantFindWallet("40201", "账户不存在"), BalanceNotEnough("40202", "账户余额不足"), PayFail("40203", "充值失败"), MissGoodInfo(
      "40204", "未找到商品信息"), CantFindWalletBill("40205", "账单不存在"), CantFindTargetWallet("40206",
      "目标赞赏账户不存在"), CantFindGiftBill("40207", "赞赏账单不存在"), GiftFail("40208", "赞赏失败"), ExtractMoneyFail(
      "40209", "提现失败"), CantFindExtract("40210", "提现账单不存在"), ClearIncomeBillFail("40211",
      "生成赞赏结算账单失败"), BindAliPayAccountFail("40212", "支付宝账号错误，一般为手机号或邮箱"), BindAliPayAccountNULL(
      "40213", "请填写提现账户"), CantExtract("40214", "提现次数已超过当日提现次数");

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
  WalletResType(String code, String msg) {
    this.code = code;
    this.msg = msg;
    this.serverIp = "127.0.0.0";
    try {
      InetAddress addr = InetAddress.getLocalHost();
      this.serverIp = addr.getHostAddress().toString();// 获得本机IP
    } catch (UnknownHostException e) {}
  }
}
