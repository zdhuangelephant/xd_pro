package com.xiaodou.payment.vo;

/**
 * 客户端类型
 * 
 * @author Jiejun.Gao
 */
public enum ClientType {

  /**
   * 中文网站
   */
  CHINAWEB(20000001, "中文网站"),
  /**
   * 中文联盟
   */
  CHINALEAGUE(20000002, "中文联盟"),
  /**
   * 英文网站
   */
  ENGLISHWEB(20000003, "英文网站"),
  /**
   * 英文联盟
   */
  ENGLISHLEAGUE(20000004, "英文联盟"),
  /**
   * iphone
   */
  IPHONEAPP(20000005, "Iphone"),
  /**
   * ipad
   */
  IPADAPP(20000006, "Ipad"),
  /**
   * Android
   */
  ANDROIDAPP(20000007, "Android"),
  /**
   * H5
   */
  H5APP(20000008, "H5"),
  /**
   * wap
   */
  WAP(20000009, "Wap"),
  /**
   * windowphone
   */
  WINPHONE(20000010, "Winphone"),
  /**
   * unknown
   */
  UNKNOWN(20000011, "UNKNOWN");

  private int code;
  private String info;

  ClientType(int code, String info) {
    this.code = code;
    this.info = info;
  }

  public int getCode() {
    return code;
  }

  public String getInfo() {
    return info;
  }



}
