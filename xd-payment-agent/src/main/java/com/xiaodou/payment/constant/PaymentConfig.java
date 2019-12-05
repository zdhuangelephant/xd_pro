package com.xiaodou.payment.constant;

public class PaymentConfig {

  /**
   * 退款标志位 收退接口
   */
  public static final Integer REFUNDID = 2004;
  /**
   * 付款标识位 收退接口
   */
  public static final Integer PAYID = 2003;

  // /**CA支付 支付产品ID*/
  // public static final Integer PAYMENT_PRODUCT_ID=0;

  /******************************************** MIX PAYMENT TYPE *********************************************/
  /** MIX_PAYMENT_CA 混合支付方式-现金账户 */
  public static final Integer MIX_PAYMENT_CA = 3201;
  /** MIX_PAYMENT_CA 混合支付方式-信用卡 暂不支持 */
  public static final Integer MIX_PAYMENT_CC = 3202;
  /** MIX_PAYMENT_CA 混合支付方式-现金账户+信用卡 暂不支持 */
  public static final Integer MIX_PAYMENT_CA_CC = 3203;
  /** MIX_PAYMENT_CA 混合支付方式-网关支付 */
  public static final Integer MIX_PAYMENT_DC = 3204;
  /** MIX_PAYMENT_CA 混合支付方式-现金账户+网关支付 暂不支持 */
  public static final Integer MIX_PAYMENT_CA_DC = 3205;
  /******************************************** PAY FROM *********************************************/
  public static final Integer PAY_FROM_CHI_MAIN = 20000001; // 中文主站
  public static final Integer PAY_FROM_CHI_UNITED = 20000002; // 中文联盟
  public static final Integer PAY_FROM_ENG_MAIN = 20000003; // 英文网站
  public static final Integer PAY_FROM_ENG_UNITED = 20000004; // 英文联盟
  public static final Integer PAY_FROM_IPHONE = 20000005; // Iphone
  public static final Integer PAY_FROM_IPAD = 20000006; // Ipad
  public static final Integer PAY_FROM_ANDROID = 20000007; // Android
  public static final Integer PAY_FROM_H5 = 20000008; // H5
  public static final Integer PAY_FROM_WAP = 20000009; // Wap
  public static final Integer PAY_FROM_WINP = 20000010; // Winphone
  public static final Integer PAY_FROM_UNKNOWN = 20000011; // UnKnown
  /******************************************** PAYMENT PROVIDERID *********************************************/
  public static final Integer PAYMENT_PROVIDER_ALIPAY = 4201;// Alipay
  public static final Integer PAYMENT_PROVIDER_TENPAY = 4202;// Tenpay
  public static final Integer PAYMENT_PROVIDER_KUAIQIAN = 4203;// 为快钱预留，可以暂时不考虑
  public static final Integer PAYMENT_PROVIDER_JSYH = 4204;// 建设银行
  public static final Integer PAYMENT_PROVIDER_GSYH = 4205;// 工商银行(取得 url 用 POST 方式到工行)
  public static final Integer PAYMENT_PROVIDER_ZGYH = 4206;// 中国银行
  public static final Integer PAYMENT_PROVIDER_NYYH = 4207;// 农业银行
  public static final Integer PAYMENT_PROVIDER_JTYH = 4208;// 交通银行(取得 url 用 POST 方式到交行)
  public static final Integer PAYMENT_PROVIDER_ZSYH = 4211;// 招商银行(取得 url 用 POST 方式到招行)
  public static final Integer PAYMENT_PROVIDER_UNION = 4212;// 银联
  public static final Integer PAYMENT_PROVIDER_PAYPAL = 4213;// Paypal
  public static final Integer PAYMENT_PROVIDER_WXPAY = 4222;// 微信支付
  public static final Integer PAYMENT_PROVIDER_APPLEPAY = 4231;// 苹果支付
  /******************************************** PAYMETHOD *********************************************/
  public static final Integer PAYMETHOD_ACCOUNT = 4301;// 账户
  public static final Integer PAYMETHOD_CYBERBANK = 4302;// 网银
  public static final Integer PAYMETHOD_WX_OFFICIAL_JS = 4309;// 微信公众账号 JS 方式
  public static final Integer PAYMETHOD_WX_OFFICIAL_NATIVE = 4310;// 微信公众账号 native 方式
  public static final Integer PAYMETHOD_WX_APP = 4311;// 微信 APP 方式
  public static final Integer PAYMETHOD_WX_WEB = 4312;// 微信 WEB 扫描
  /******************************************** ID TYPE *********************************************/
  public static final Integer ID_TYPE_CARD = 8001;// 身份证
  public static final Integer ID_TYPE_PASSPORT = 8002;// 护照
  public static final Integer ID_TYPE_OTHER = 8003;// 其他证件
  public static final Integer ID_TYPE_ARMY = 8004;// 军官证
  public static final Integer ID_TYPE_POLICE = 8005;// 警官证
  public static final Integer ID_TYPE_HOME = 8006;// 回乡证
}
