package com.xiaodou.resources.constant.quesbk;

// import com.xiaodou.common.util.IPUtil;
import com.xiaodou.common.util.StringUtils;

public enum ResultType {
  /**
   * 系统异常
   */
  SYSFAIL("-1", "系统异常"),

  SUCCESS("0", "成功"),

  /** common code */
  UNVALIDEDUCATIONID("00201", "用户未选择该专业"),

  UNVALIDCOURSEID("00202", "用户未购买该课程"),
  
  UNVALIDPROITEMID("00206", "无效的练习ID"),
  
  UNVALIDEXAMRECORDID("00207", "无效的练习记录ID"),
  
  EXAMMATCHEDCOURSEIDFAILED("00205", "练习课程不匹配"),

  UNVALIDCOURSEID4TARGETUSER("00203", "目标用户课程列表不存在该课程"),

  HASNOCOURSES("00204", "亲, 你还没购买课程哦~"),
  /** exam */
  CANTREPEATEXAM("00261", "您已提交过本次练习答案,请勿重复提交。"),

  UNVALIDEXAM("00262", "该测验阶段已结束，无法提交。"),
  
  UNVALIDEXAMTYPE("00263", "该练习类型不存在。"),
  /** storeQues */
  UNVALIDQUESINFO("00281", "无效的问题信息"),

  UNVALIDQUESINFO4EXAMTYPE("00241", "系统目前无法为该测试类型组织问题"),

  WAIT4SOMEPEOPLE("00351", "暂时无人参与PK,请稍等。"),

  MISSINGRECORD("00352", "无效的挑战记录ID"),

  CANTQUERYRECORD("00353", "无权查看该记录信息"),

  CANTCHALLENGESELF("00354", "不能将自己作为挑战对象"),

  HASUNACCEPTCHALLENGE("00355", "还有未完成的挑战。"),
  /** storeRecord */
  HasStore("00400", "已经收藏过此题"),

  StoreFail("00401", "收藏题目失败"),

  NotFindStore("00402", "没有收藏此题"),

  NotFindChapter("00403", "没有在此课程下找到章信息"),

  StatusFail("00404", "传入状态错误"),

  /** wrongRecord */
  WrongFail("00500", "添加错题失败"), NotFindWrong("00502", "没有收藏此题"),

  /* raise_wrong_ques */
  NotFindWrongType("00600", "举报错题类型错误"), WrongMsgNotEmpty("00601", "请输入举报原因");

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

  public String getCode() {
    return code;
  }

  public String getMsg() {
    return msg;
  }

  public String getServerIp() {
    return serverIp;
  }

  ResultType(String code, String msg) {
    this.code = code;
    this.msg = msg;
    // InetAddress addr = InetAddress.getLocalHost();
    // this.serverIp = addr.getHostAddress().toString();// 获得本机IP
    // this.serverIp = IPUtil.getServerIp();// 获得本机IP
    if (StringUtils.isBlank(this.serverIp)) {
      this.serverIp = "127.0.0.0";
    }
  }
}
