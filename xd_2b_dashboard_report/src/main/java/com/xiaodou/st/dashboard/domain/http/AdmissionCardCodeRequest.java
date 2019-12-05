package com.xiaodou.st.dashboard.domain.http;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.xiaodou.summer.vo.in.BaseValidatorPojo;

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
    private String syncId; // 同步记录ID
  }

}
