package com.xiaodou.userCenter.model.message;

import java.util.List;
/**
 * @name StudentMessageBody 
 * CopyRright (c) 2017 by 李德洪
 *
 * @author 李德洪
 * @date 2017年5月10日
 * @description TODO
 * @version 1.0
 */
public class StudentMessageBody {
  private List<StudentMessageBodyDTO> messageBody;

  public List<StudentMessageBodyDTO> getMessageBody() {
    return messageBody;
  }

  public void setMessageBody(List<StudentMessageBodyDTO> messageBody) {
    this.messageBody = messageBody;
  }



  public static class StudentMessageBodyDTO {
    private Integer studentId;// 学生id
    private String telephone;// 手机号
    private String realName;// 真实姓名
    private String gender;// 性别
    private String identificationCardCode;// 身份证号
    private String admissionCardCode; // 准考证号
    private String pilotUnitName;// 试点单位名称

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

  }
}
