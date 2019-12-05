package com.xiaodou.st.dashboard.domain.http;

import java.util.List;

import lombok.Data;

import com.xiaodou.summer.vo.in.BaseValidatorPojo;

public class StudentRequest extends BaseValidatorPojo {

  private List<StudentRequestDTO> listStudentRequest;

  public List<StudentRequestDTO> getListStudentRequest() {
    return listStudentRequest;
  }

  public void setListStudentRequest(List<StudentRequestDTO> listStudentRequest) {
    this.listStudentRequest = listStudentRequest;
  }

  @Data
  public static class StudentRequestDTO {
    private String regionId; // 所属地域ID
    private String regionName;// 所属地域名称
    private Integer studentId;// 学生id
    private String telephone;// 手机号
    private String realName;// 真实姓名
    private String gender;// 性别
    private String identificationCardCode;// 身份证号
    private String admissionCardCode; // 准考证号
    private String pilotUnitName;// 第三级单位名称

    private String typeCode;// 专业编码
    private String syncId;// 同步ID

  }
}
