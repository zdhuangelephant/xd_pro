package com.xiaodou.forum.enums;

import java.net.InetAddress;
import java.net.UnknownHostException;

public enum ForumResType {

  /** NULLCATAGORY 话题分类不存在 */
  NULLCATAGORY("30101", "分类不存在"),
  /** NULLFORUM 话题不存在 */
  NULLFORUM("30401", "话题不存在"),
  /** NULLCOMMENT 目标评论不存在 */
  NULLCOMMENT("30301", "目标评论不存在"),
  /** NEWCOMMENT 有新的点赞或回复 */
  NEWCOMMENT("30501", "有新的点赞或回复");

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
  ForumResType(String code, String msg) {
    this.code = code;
    this.msg = msg;
    this.serverIp = "127.0.0.0";
    try {
      InetAddress addr = InetAddress.getLocalHost();
      this.serverIp = addr.getHostAddress().toString();// 获得本机IP
    } catch (UnknownHostException e) {}
  }
}
