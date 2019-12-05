package com.xiaodou.server.pay.constant;

/**
 * @name @see com.xiaodou.server.pay.constant.PayConstant.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年5月27日
 * @description 常量接口
 * @version 1.0
 */
public interface PayConstant {
  /* 苹果校验凭证参数值 */
  public static final String ThirdTositenumero_key = "receipt-data";
  /** DEFAULT_OUT_TRADE_NO 默认订单号 */
  public static final String DEFAULT_OUT_TRADE_NO = "~";
  /** 付款标识位 收退接口 */
  /** FIRST_PAYID 一次支付中 */
  public static final Short FIRST_PAYID = 2003;
  /** FIRST_PAYID_FINISH 一次支付成功 */
  public static final Short FIRST_PAYID_FINISH = 2014;
  /** FIRST_PAYID_FAIL 一次支付失败 */
  public static final Short FIRST_PAYID_FAIL = 2024;
  /** SECOND_PAYID 二次支付中 */
  public static final Short SECOND_PAYID = 2005;
  /** SECOND_PAYID_FINISH 二次支付成功 */
  public static final Short SECOND_PAYID_FINISH = 2014;
  /** SECOND_PAYID_FAIL 二次支付失败 */
  public static final Short SECOND_PAYID_FAIL = 2024;
  /** 退款标志位 收退接口 */
  /** FIRST_REFUNDID 一次退款中 */
  public static final Short FIRST_REFUNDID = 2004;
  /** FIRST_REFUNDID_FINISH 一次退款成功 */
  public static final Short FIRST_REFUNDID_FINISH = 2014;
  /** FIRST_REFUNDID_FAIL 一次退款失败 */
  public static final Short FIRST_REFUNDID_FAIL = 2024;
  /** SECOND_REFUNDID 二次退款中 */
  public static final Short SECOND_REFUNDID = 2006;
  /** SECOND_REFUNDID_FINISH 二次退款成功 */
  public static final Short SECOND_REFUNDID_FINISH = 2016;
  /** SECOND_REFUNDID_FAIL 二次退款失败 */
  public static final Short SECOND_REFUNDID_FAIL = 2026;
  /******************************************** BUSINESS TYPE *********************************************/
  /** BUSINESS_TYPE_ST 业务类型-自考君 */
  public static final String BUSINESS_TYPE_ST = "11020001";
  /** BUSINESS_TYPE_WALLET 业务平台-用户账户系统 */
  public static final String BUSINESS_TYPE_WALLET = "11000091";
  /** BUSINESS_TYPE_MOSHARE 业务类型-慕享 */
  public static final String BUSINESS_TYPE_MOSHARE = "11030001";

  /******************************************** CURRENCY *********************************************/
  /** currency_yuan 币种 人民币 */
  public static final String PAYMENT_CURRENCY_YUAN = "4601";
  /** currency_ios 币种 IOS虚拟货币 */
  public static final String PAYMENT_CURRENCY_IOS = "4701";
  /** currency_android 币种 ANDROID虚拟货币 */
  public static final String PAYMENT_CURRENCY_ANDROID = "4702";
  /******************************************** MIX PAYMENT TYPE *********************************************/
  /** MIX_PAYMENT_CA 混合支付方式-现金账户 */
  public static final String MIX_PAYMENT_CA = "3201";
  /** MIX_PAYMENT_CA 混合支付方式-信用卡 暂不支持 */
  public static final String MIX_PAYMENT_CC = "3202";
  /** MIX_PAYMENT_CA 混合支付方式-现金账户+信用卡 暂不支持 */
  public static final String MIX_PAYMENT_CA_CC = "3203";
  /** MIX_PAYMENT_CA 混合支付方式-网关支付 */
  public static final String MIX_PAYMENT_DC = "3204";
  /** MIX_PAYMENT_CA 混合支付方式-现金账户+网关支付 暂不支持 */
  public static final String MIX_PAYMENT_CA_DC = "3205";
  /******************************************** PAY FROM *********************************************/
  public static final String PAY_FROM_CHI_MAIN = "20000001"; // 中文主站
  public static final String PAY_FROM_CHI_UNITED = "20000002"; // 中文联盟
  public static final String PAY_FROM_ENG_MAIN = "20000003"; // 英文网站
  public static final String PAY_FROM_ENG_UNITED = "20000004"; // 英文联盟
  public static final String PAY_FROM_IPHONE = "20000005"; // Iphone
  public static final String PAY_FROM_IPAD = "20000006"; // Ipad
  public static final String PAY_FROM_ANDROID = "20000007"; // Android
  public static final String PAY_FROM_H5 = "20000008"; // H5
  public static final String PAY_FROM_WAP = "20000009"; // Wap
  public static final String PAY_FROM_WINP = "20000010"; // Winphone
  public static final String PAY_FROM_UNKNOWN = "20000011"; // UnKnown
  /******************************************** PAYMENT PROVIDERID *********************************************/
  public static final String PAYMENT_PROVIDER_ALIPAY = "4201";// Alipay
  public static final String PAYMENT_PROVIDER_TENPAY = "4202";// Tenpay
  public static final String PAYMENT_PROVIDER_KUAIQIAN = "4203";// 为快钱预留，可以暂时不考虑
  public static final String PAYMENT_PROVIDER_JSYH = "4204";// 建设银行
  public static final String PAYMENT_PROVIDER_GSYH = "4205";// 工商银行(取得 url 用 POST 方式到工行)
  public static final String PAYMENT_PROVIDER_ZGYH = "4206";// 中国银行
  public static final String PAYMENT_PROVIDER_NYYH = "4207";// 农业银行
  public static final String PAYMENT_PROVIDER_JTYH = "4208";// 交通银行(取得 url 用 POST 方式到交行)
  public static final String PAYMENT_PROVIDER_ZSYH = "4211";// 招商银行(取得 url 用 POST 方式到招行)
  public static final String PAYMENT_PROVIDER_UNION = "4212";// 银联
  public static final String PAYMENT_PROVIDER_PAYPAL = "4213";// Paypal
  public static final String PAYMENT_PROVIDER_WXPAY = "4222";// 微信支付
  public static final String PAYMENT_PROVIDER_APPLEPAY = "4231";// 苹果支付
  /******************************************** PAYMETHOD *********************************************/
  public static final String PAYMETHOD_ACCOUNT = "4301";// 账户
  public static final String PAYMETHOD_CYBERBANK = "4302";// 网银
  public static final String PAYMETHOD_WX_OFFICIAL_JS = "4309";// 微信公众账号 JS 方式
  public static final String PAYMETHOD_WX_OFFICIAL_NATIVE = "4310";// 微信公众账号 native 方式
  public static final String PAYMETHOD_WX_APP = "4311";// 微信 APP 方式
  public static final String PAYMETHOD_WX_WEB = "4312";// 微信 WEB 扫描
  /******************************************** ID TYPE *********************************************/
  public static final String ID_TYPE_CARD = "8001";// 身份证
  public static final String ID_TYPE_PASSPORT = "8002";// 护照
  public static final String ID_TYPE_OTHER = "8003";// 其他证件
  public static final String ID_TYPE_ARMY = "8004";// 军官证
  public static final String ID_TYPE_POLICE = "8005";// 警官证
  public static final String ID_TYPE_HOME = "8006";// 回乡证

  /* 苹果校验返回参数值 APPLE_PAY_RETURN_STATUS_SUCCESS:0 成功 */
  public static final String APPLE_PAY_RETURN_STATUS_SUCCESS = "0";

  /* 收据信息是测试用（sandbox），但却被发送到产品环境中验证 */
  public static final String PAY_SANDBOX = "21007";
  /* 收据信息是产品环境中使用，但却被发送到测试环境中验证 */
  public static final String PAY_BUY = "21008";
}
