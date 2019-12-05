package com.xiaodou.payment.vo;

import com.xiaodou.common.util.FileUtil;
import com.xiaodou.payment.exception.PropertiesException;

/**
 * 业务线 代表了平台分配给业务线的信息
 * 
 * @author Jiejun.Gao
 */
public class PayMerchant {

  private static String PAYMENT_MERCHANT_ID = "payment.merchantCode_%s";
  private static String PAYMENT_KEY = "payment.key_%s";
  private static String PAYMENT_BUSINESS_TYPE = "payment.businessType_%s";

  private static Integer currency;// 币种：人民币4601
  private static String signType;// 签名方式

  private static String tokenUrl;// token的url
  private static String payUrl;// 支付url
  private static String refundUrl;// 退款url
  private static String payStatusUrl;// 支付状态查询url
  private static Integer mixpaymentType;// 支付方式
  private static String cardUrl;// 获取卡信息的url
  private static String payTypeUrl;// 获取支付方式url
  private static String payDetailUrl;
  private static String redBonusesUrl;// 送红包url

  private static String callbackHost;

  private static String continuePayNoticeUrl;
  /** 继续通知支付重试次数 */
  private static Integer retryContinuePayNoticeNum;

  private static FileUtil fileUtil = FileUtil
      .getInstance("/conf/custom/env/pay_mapping.properties");

  static {
    init();
  }

  public static void init() {
    signType = fileUtil.getProperties("payment.signType");
    payStatusUrl = fileUtil.getProperties("payment.payStatusUrl");
    payUrl = fileUtil.getProperties("payment.payUrl");
    tokenUrl = fileUtil.getProperties("payment.tokenUrl");
    payTypeUrl = fileUtil.getProperties("payment.payTypeUrl");
    payDetailUrl = fileUtil.getProperties("payment.payDetailUrl");
    currency = fileUtil.getPropertiesInt("payment.currency");
    callbackHost = fileUtil.getProperties("payment.callbackHost");
  }

  public static Integer getBusinessType(String productLine) {
    String businessType = fileUtil.getProperties(String.format(PAYMENT_BUSINESS_TYPE, productLine));
    if (businessType == null) {
      throw new PropertiesException("未找到businessType配置");
    }
    return Integer.parseInt(businessType);
  }

  public static String getMerchantId(String productLine) {
    String merchantId = fileUtil.getProperties(String.format(PAYMENT_MERCHANT_ID, productLine));
    if (merchantId == null) {
      throw new PropertiesException("未找到merchantId配置");
    }
    return merchantId;
  }

  public static String getKey(String productLine) {
    String key = fileUtil.getProperties(String.format(PAYMENT_KEY, productLine));
    if (key == null) {
      throw new PropertiesException("未找到key配置");
    }
    return key;
  }

  public static String getTokenUrl() {
    return tokenUrl;
  }

  public static String getPayUrl() {
    return payUrl;
  }

  public static String getRefundUrl() {
    return refundUrl;
  }

  public static String getPayStatusUrl() {
    return payStatusUrl;
  }

  public static String getContinuePayNoticeUrl() {
    return continuePayNoticeUrl;
  }

  public static Integer getMixpaymentType() {
    return mixpaymentType;
  }

  public static Integer getRetryContinuePayNoticeNum() {
    return retryContinuePayNoticeNum;
  }

  public static Integer getCurrency() {
    return currency;
  }

  public static String getSignType() {
    return signType;
  }

  public static String getCardUrl() {
    return cardUrl;
  }

  public static String getPayTypeUrl() {
    return payTypeUrl;
  }

  public static String getCallbackHost() {
    return callbackHost;
  }

  public static String getRedBonusesUrl() {
    return redBonusesUrl;
  }

  public static String getPayDetailUrl() {
    return payDetailUrl;
  }
}
