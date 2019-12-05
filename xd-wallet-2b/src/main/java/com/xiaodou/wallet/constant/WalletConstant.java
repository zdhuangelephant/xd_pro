package com.xiaodou.wallet.constant;

/**
 * @name @see com.xiaodou.wallet.constant.WalletConstant.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年6月6日
 * @description 用户支付平台常量池
 * @version 1.0
 */
public interface WalletConstant {

  /** PAY_COUNT 支付账单计算数量 */
  public static final Double PAY_COUNT = 1d;
  /** PAYMENT_BUSINESSTYPE_WALLET 用户账户平台-支付系统业务类型 */
  public static final String PAYMENT_PRODUCTLINE_WALLET = "91";
  /* aliPay 支付宝支付 */
  public static final String ALIPAY = "aliPay";
  /* applePay 苹果支付 */
  public static final String APPLEPAY = "applePay";
  /* wxPay 微信支付 */
  public static final String WEChAtPAY = "wxPay";
  /** PAGESIZE 分页数量 */
  public static final String PAGESIZE = "20";
  /********************************** 账户分类 *******************************/
  /** ACCOUNT_CLASSIFY_LOCAL 账户分类:实体货币账户 */
  public static final String ACCOUNT_CLASSIFY_LOCAL = "4601";
  /** ACCOUNT_CLASSIFY_ANDROID 账户分类:虚拟货币安卓账户 */
  public static final String ACCOUNT_CLASSIFY_ANDROID = "4702";
  /** ACCOUNT_CLASSIFY_IOS 账户分类:虚拟货币苹果账户 */
  public static final String ACCOUNT_CLASSIFY_IOS = "4701";
  /********************************** 业务类型 *******************************/
  public static final String BUSINESS_TYPE_ZKJ = "11020001";
  public static final String BUSINESS_TYPE_MOSHARE = "11030001";
}