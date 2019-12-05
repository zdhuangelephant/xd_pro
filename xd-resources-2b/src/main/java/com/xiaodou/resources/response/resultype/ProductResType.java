package com.xiaodou.resources.response.resultype;

import java.net.InetAddress;
import java.net.UnknownHostException;

public enum ProductResType {
  /**
   * 购买课程
   */
  InsufficientCoin("30100", "金币数量不足"), HasOrder("30101", "已经购买过课程"), NoChapter("30102", "该课程下没有章信息"), UNLOCKFailed(
      "30103", "购买课程后，第一章第一节解锁失败"), FindCourseIdFailed("30104", "课程不存在"), UnValidCourseId("30105",
      "用户未购买该课程"), OrderFailed("30106", "课程购买失败"), FindItemIdFailed("30107", "章节不存在"),
  /**
   * 
   */
  CantFindItem("30201", "找不到指定章节"), NullComment("30202", "目标回复不存在"),
  /**
   * 修改考期
   */
  FailModifyExamDate("30301", "修改课程考期失败"), ExceedCourseCount("30302", "超出可移动课程数"), AllYearCourse(
      "30303", "不是全年考试课程，无法移动"), CannotMoveCourse("30304", "该课程不可移动"),
  /**
   * 
   */
  NOFindLearnRecord("30401", "没有找到该条学习记录"),
  /**
   * 开通课程
   */
  NotFindCourse("30501", "没有找到课程"), GetProductPriceFail("30502", "获取课程价格失败"),
  /** 下单 */
  OrderRepeat("30601", "您已提交过订单"), GetTokenFail("30602", "系统故障，稍后重试"), PayFail("30603", "支付失败"),
  /**
   * 学习记时
   */
  NotReachTime("30701", "低于3秒，没有达到记录时间要求"),
  /**
   * 专业
   */
  NotFindMajor("30801", "专业不存在");
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
  ProductResType(String code, String msg) {
    this.code = code;
    this.msg = msg;
    this.serverIp = "127.0.0.0";
    try {
      InetAddress addr = InetAddress.getLocalHost();
      this.serverIp = addr.getHostAddress().toString();// 获得本机IP
    } catch (UnknownHostException e) {}
  }


}
