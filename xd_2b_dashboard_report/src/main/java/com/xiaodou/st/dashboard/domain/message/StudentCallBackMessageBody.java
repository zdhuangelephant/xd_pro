package com.xiaodou.st.dashboard.domain.message;

import java.util.List;

public class StudentCallBackMessageBody {
  private List<StudentCallBackMessageBodyDTO> messageBody;

  public List<StudentCallBackMessageBodyDTO> getMessageBody() {
    return messageBody;
  }

  public void setMessageBody(List<StudentCallBackMessageBodyDTO> messageBody) {
    this.messageBody = messageBody;
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
