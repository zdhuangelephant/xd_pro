package com.xiaodou.userCenter.model.message;

import java.util.List;

import com.xiaodou.jmsg.entity.AbstractMessagePojo;
/**
 * @name StudentCallBackMessage 
 * CopyRright (c) 2017 by 李德洪
 *
 * @author 李德洪
 * @date 2017年5月10日
 * @description TODO
 * @version 1.0
 */
public class StudentCallBackMessage extends AbstractMessagePojo {


  /** 定时任务消息名 */
  private static final String ASYNC_MESSAGE_STUDENT_SCHEDULER_CALLBACK =
      "%s_studentSchedulerCallBack";

  public StudentCallBackMessage(StudentCallBackMessageBody messageBody) {
    super(String.format(ASYNC_MESSAGE_STUDENT_SCHEDULER_CALLBACK, "2"));
    setTransferObject(messageBody);
  }

  /**
   * 消息体
   * 
   * @name MessageBody CopyRright (c) 2017 by 李德洪
   * 
   * @author 李德洪
   * @date 2017年4月20日
   * @description TODO
   * @version 1.0
   */
  public static class StudentCallBackMessageBody {
    private List<StudentCallBackMessageBodyDTO> messageBody;

    public List<StudentCallBackMessageBodyDTO> getMessageBody() {
      return messageBody;
    }

    public void setMessageBody(List<StudentCallBackMessageBodyDTO> messageBody) {
      this.messageBody = messageBody;
    }
  }


  public static class StudentCallBackMessageBodyDTO {

    private Integer studentId;// 学生id
    private Long userId;// 业务系统id
    private Short studentStatus;// 学生状态 0、未注册，1、注册成功，2、注册失败

    public Integer getStudentId() {
      return studentId;
    }

    public void setStudentId(Integer studentId) {
      this.studentId = studentId;
    }

    public Long getUserId() {
      return userId;
    }

    public void setUserId(Long userId) {
      this.userId = userId;
    }

    public Short getStudentStatus() {
      return studentStatus;
    }

    public void setStudentStatus(Short studentStatus) {
      this.studentStatus = studentStatus;
    }
  }

}
