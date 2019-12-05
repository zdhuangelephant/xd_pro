package com.xiaodou.st.dashboard.domain.message;

import java.util.List;

import com.xiaodou.jmsg.entity.AbstractMessagePojo;

public class StudentMessage extends AbstractMessagePojo {

  /** 定时任务消息名 */
  private static final String ASYNC_MESSAGE_DASHBOARD_SCHEDULER = "%s_studentScheduler";

  public StudentMessage(StudentMessageBody messageBody) {
    super(String.format(ASYNC_MESSAGE_DASHBOARD_SCHEDULER, "2"));
    setTransferObject(messageBody);
  }

  public static class StudentMessageBody {
    private List<StudentMessageBodyDTO> messageBody;

    public List<StudentMessageBodyDTO> getMessageBody() {
      return messageBody;
    }

    public void setMessageBody(List<StudentMessageBodyDTO> messageBody) {
      this.messageBody = messageBody;
    }
  }

  public static class StudentMessageBodyDTO {
    private Integer studentId;// 学生id
    private String telephone;// 手机号
    private String realName;// 真实姓名
    private String gender;// 性别
    private String identificationCardCode;// 身份证号
    private String admissionCardCode; // 准考证号
    private String pilotUnitName;// 第三级单位名称
   // private List<Integer> productIdList;//报名课程

    public Integer getStudentId() {
      return studentId;
    }

    public void setStudentId(Integer studentId) {
      this.studentId = studentId;
    }

    public String getTelephone() {
      return telephone;
    }

    public void setTelephone(String telephone) {
      this.telephone = telephone;
    }

    public String getRealName() {
      return realName;
    }

    public void setRealName(String realName) {
      this.realName = realName;
    }

    public String getGender() {
      return gender;
    }

    public void setGender(String gender) {
      this.gender = gender;
    }

    public String getIdentificationCardCode() {
      return identificationCardCode;
    }

    public void setIdentificationCardCode(String identificationCardCode) {
      this.identificationCardCode = identificationCardCode;
    }

    public String getAdmissionCardCode() {
      return admissionCardCode;
    }

    public void setAdmissionCardCode(String admissionCardCode) {
      this.admissionCardCode = admissionCardCode;
    }

    public String getPilotUnitName() {
      return pilotUnitName;
    }

    public void setPilotUnitName(String pilotUnitName) {
      this.pilotUnitName = pilotUnitName;
    }

    // public List<Integer> getProductIdList() {
    // return productIdList;
    // }
    //
    // public void setProductIdList(List<Integer> productIdList) {
    // this.productIdList = productIdList;
    // }

  }
}
