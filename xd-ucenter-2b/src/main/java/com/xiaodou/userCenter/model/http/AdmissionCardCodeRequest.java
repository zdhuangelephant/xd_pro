package com.xiaodou.userCenter.model.http;

import java.util.List;

import com.xiaodou.summer.vo.in.BaseValidatorPojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper=false)
public class AdmissionCardCodeRequest extends BaseValidatorPojo {

  private List<AdmissionCardCodeRequestDTO> listAdmissionCardCodeRequestDTO;

  @Data
  public static class AdmissionCardCodeRequestDTO {
    private Integer studentId;// 学生id
    
    private Long userId;// 业务系统id
    private String admissionCardCode; // 准考证号
    /***********/
    private String telephone;
  }

}
