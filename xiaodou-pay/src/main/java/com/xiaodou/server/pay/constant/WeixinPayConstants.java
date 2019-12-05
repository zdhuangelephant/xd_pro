package com.xiaodou.server.pay.constant;

import com.xiaodou.server.pay.prop.WxPayProp;


public class WeixinPayConstants {
  /* 应用相关信息 */
  private static String APP_ID = "wx.config.appid_%s";// 在微信开发平台登记的app应用
  private static String APP_SECRET = "wx.config.appsecret_%s";// 在微信开发平台登记的app应用
  private static String PARTNER = "wx.config.partner_%s";// 商户号
  private static String PARTNER_KEY = "wx.config.partnerkey_%s";// 不是商户登录密码，是商户在微信平台设置的32位长度的api秘钥，
  private static String CREATE_ORDER_URL = "wx.config.createOrderUrl";
  private static String NOTIFY_URL = "wx.config.notifyUrl";// 异步通知地址
  /**
   * 服务号相关信息
   */
  private static String H5_APP_ID = "wx.config.h5AppId_%s";
  private static String H5_APP_SECRECT = "wx.config.H5AppSecrect_%s";// 服务号的appSecrect
  private static String H5_MCH_ID = "wx.config.H5MchId_%s";// 开通微信支付分配的商户号
  private static String H5_API_KEY = "wx.config.H5ApiKey_%s";// 商户API密钥 自行去商户平台设置
  private static String H5_SIGN_TYPE = "wx.config.H5SignType";// 签名加密方式

  public static String getH5AppSecret(String businessType) {
    return WxPayProp.getParams(String.format(H5_APP_SECRECT, businessType));
  }

  public static String getH5MchId(String businessType) {
    return WxPayProp.getParams(String.format(H5_MCH_ID, businessType));
  }

  public static String getH5ApiKey(String businessType) {
    return WxPayProp.getParams(String.format(H5_API_KEY, businessType));
  }

  public static String getH5SignType() {
    return WxPayProp.getParams(H5_SIGN_TYPE);
  }

  public static String getH5AppId(String businessType) {
    return WxPayProp.getParams(String.format(H5_APP_ID, businessType));
  }

  public static String getAppId(String businessType) {
    return WxPayProp.getParams(String.format(APP_ID, businessType));
  }

  public static String getAppSecret(String businessType) {
    return WxPayProp.getParams(String.format(APP_SECRET, businessType));
  }

  public static String getPartner(String businessType) {
    return WxPayProp.getParams(String.format(PARTNER, businessType));
  }

  public static String getPartnerKey(String businessType) {
    return WxPayProp.getParams(String.format(PARTNER_KEY, businessType));
  }

  public static String getCreateOrderUrl() {
    return WxPayProp.getParams(CREATE_ORDER_URL);
  }

  public static String getNotifyUrl() {
    return WxPayProp.getParams(NOTIFY_URL);
  }

  // 用于告知微信服务器
  // 调用成功
  /**
   * 微信基础接口地址
   */
  // 获取token接口(GET)
  private static String H5_TOKEN_URL = "wx.config.H5TokenUrl";

  public static String getH5TokenUrl() {
    return WxPayProp.getParams(H5_TOKEN_URL);
  }

  // oauth2授权接口(GET)
  private static String H5_OAUTH2_URL = "wx.config.H5Oauth2Url";

  public static String getH5Oauth2Url() {
    return WxPayProp.getParams(H5_OAUTH2_URL);
  }

  // 刷新access_token接口（GET）
  private static String REFRESH_TOKEN_URL = "wx.config.RefreshTokenUrl";

  public static String getRefreshTokenUrl() {
    return WxPayProp.getParams(REFRESH_TOKEN_URL);
  }

  // 菜单创建接口（POST）
  private static String MENU_CREATE_URL = "wx.config.MenuCreateUrl";

  public static String getMenuCreateUrl() {
    return WxPayProp.getParams(MENU_CREATE_URL);
  }

  // 菜单查询（GET）
  private static String MENU_GET_URL = "wx.config.MenuGetUrl";

  public static String getMenuGetUrl() {
    return WxPayProp.getParams(MENU_GET_URL);
  }

  // 菜单删除（GET）
  private static String MENU_DELETE_URL = "wx.config.MenuDeleteUrl";

  public static String getMenuDeleteUrl() {
    return WxPayProp.getParams(MENU_DELETE_URL);
  }

  /**
   * 微信支付接口地址
   */
  // 微信支付统一接口(POST)
  private static String UNIFIED_ORDER_URL = "wx.config.UnifiedOrderUrl";

  public static String getUnifiedOrderUrl() {
    return WxPayProp.getParams(UNIFIED_ORDER_URL);
  }

  // 微信退款接口(POST)
  private static String REFUND_URL = "wx.config.RefundUrl";

  public static String getRefundUrl() {
    return WxPayProp.getParams(REFUND_URL);
  }

  // 订单查询接口(POST)
  private static String CHECK_ORDER_URL = "wx.config.CheckOrderUrl";

  public static String getCheckOrderUrl() {
    return WxPayProp.getParams(CHECK_ORDER_URL);
  }

  // 关闭订单接口(POST)
  private static String CLOSE_ORDER_URL = "wx.config.CloseOrderUrl";

  public static String getCloseOrderUrl() {
    return WxPayProp.getParams(CLOSE_ORDER_URL);
  }

  // 退款查询接口(POST)
  private static String CHECK_REFUND_URL = "wx.config.CheckRefundUrl";

  public static String getCheckRefundUrl() {
    return WxPayProp.getParams(CHECK_REFUND_URL);
  }

  // 对账单接口(POST)
  private static String DOWNLOAD_BILL_URL = "wx.config.DownloadBillUrl";

  public static String getDownloadBillUrl() {
    return WxPayProp.getParams(DOWNLOAD_BILL_URL);
  }

  // 短链接转换接口(POST)
  private static String SHORT_URL = "wx.config.ShortUrl";

  public static String getShortUrl() {
    return WxPayProp.getParams(SHORT_URL);
  }

  // 接口调用上报接口(POST)
  private static String REPORT_URL = "wx.config.ReportUrl";

  public static String getReportUrl() {
    return WxPayProp.getParams(REPORT_URL);
  }

}
