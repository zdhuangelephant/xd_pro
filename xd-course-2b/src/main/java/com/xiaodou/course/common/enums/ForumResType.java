package com.xiaodou.course.common.enums;

import java.net.InetAddress;
import java.net.UnknownHostException;

public enum ForumResType {

  /** NULLCATAGORY 资源分类不存在 */
  NULLCATAGORY("30101", "分类不存在"),
  /** NULLFORUM 资源不存在 */
  NULLFORUM("30401", "资源不存在或被作者删除"),
  POSTFORUMFAIL("30402", "发布资源失败"),
  NULLCOLUMN("30404", "请选择专栏"),
  /** NULLCOMMENT 目标评论不存在 */
  NULLCOMMENT("30301", "目标评论不存在"),
  /** NEWCOMMENT 有新的点赞或回复 */
  NEWCOMMENT("30501", "有新的点赞或回复"),
  NEWDYNAMIC("30502", "有新的动态"),
  NULLPRODUCT("30403", "课程不存在"),
  NULLAUTHOR("30601","作者不存在"),
  /** OWNERCANTFOLLOWCULUMNIST 不能添加/取消关注自己发布的专栏 */
  OWNERCANTFOLLOWCULUMNIST("30601", "不能添加/取消关注自己发布的专栏"),
  /** HASFOLLOWCULUMNIST 不能重复关注已关注的专栏 */
  HASFOLLOWCULUMNIST("30602", "不能重复关注已关注的专栏"),
  /** HASNOTFOLLOWCULUMNIST 不能取消关注未关注的专栏 */
  HASNOTFOLLOWCULUMNIST("30603", "不能取消关注未关注的专栏");

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
