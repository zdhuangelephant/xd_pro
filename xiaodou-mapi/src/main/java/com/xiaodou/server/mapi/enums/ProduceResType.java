package com.xiaodou.server.mapi.enums;

import java.net.InetAddress;
import java.net.UnknownHostException;

public enum ProduceResType {
  InsufficientCoin("30100", "金币数量不足"),
  HasOrder("30101", "已经购买过课程"),
  NoChapter("30102", "该课程下没有章信息"),
  AddChapterFaile("30103", "购买课程后，第一章解锁失败"),
  NothingOwnProduct("30104", "该用户没有购买过任何课程");

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
  ProduceResType(String code, String msg) {
    this.code = code;
    this.msg = msg;
    this.serverIp = "127.0.0.0";
    try {
      InetAddress addr = InetAddress.getLocalHost();
      this.serverIp = addr.getHostAddress().toString();// 获得本机IP
    } catch (UnknownHostException e) {}
  }


}
