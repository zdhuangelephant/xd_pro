package com.xiaodou.payment.out;

public class ResultInfo {

  /**
   * 消息到达 并成功返回
   */
  public static final String INFOOK = "1";

  /**
   * 消息发送 但未通过验证
   */
  public static final String INFOFAIL = "2";

  /**
   * 消息未能收到响应
   */
  public static final String NORESPONSE = "3";



}
