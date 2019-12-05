package com.xiaodou.constant;

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

  UNVALIDCOURSEID("00202", "用户未购买该课程或者该课程不在当前考期（课程失效）"),
  
  NOCOURSE("00206","课程不存在"),
  
  NOQUES("00207","题目不存在"),
  
  NOAUDIO("00210","录音不存在"),
  
  NOTYPE("00209","此类型题目无法上传录音"),

  TYPECODEMATCHEDCOURSEIDFAILED("00205", "专业与课程不匹配"),

  UNVALIDCOURSEID4TARGETUSER("00203", "目标用户课程列表不存在该课程"),

  HASNOCOURSES("00204", "亲, 你还没购买课程哦~"),

  CREDITNOTENOUGH("00205", "积分不足,无法完成操作"),
  /** exam */
  CANTREPEATEXAM("00261", "您已提交过本次练习答案,请勿重复提交。"),

  NOPAPER("00271", "试卷不存在"), PAPERWRONGMES("00272", "试卷信息不匹配"),

  /** storeQues */
  UNVALIDQUESINFO("00281", "无效的问题信息"),

  UNVALIDQUESINFO4EXAMTYPE("00241", "系统目前无法为该测试类型组织问题"),
  
  MISSINGEXAMRULE("00242", "未找到测试类型对应的命题蓝图"),

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

  ChapterFail("00804", "请先完成节闯关"),

  FinalExamFail("00904", "请先完成章总结闯关"),
  /** wrongRecord */
  WrongFail("00500", "添加错题失败"), NotFindWrong("00502", "没有收藏此题"),

  /* raise_wrong_ques */
  NotFindWrongType("00600", "举报错题类型错误"), WrongMsgNotEmpty("00601", "请输入举报原因"),

  /* face_recognition */
  MissSrcFace("00700", "用户尚未上传采集头像信息");

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
