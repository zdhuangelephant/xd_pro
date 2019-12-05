package com.xiaodou.userCenter.model.http;

import java.util.List;

import com.xiaodou.summer.vo.in.BaseValidatorPojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class StudentRequest extends BaseValidatorPojo {

  private List<StudentRequestDTO> listStudentRequest;

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
    private String pilotUnitName;// 试点单位名称

    private String typeCode;// 专业编码
  }
}
