package com.xiaodou.st.dashboard.domain.http;

import java.util.List;

import lombok.Data;

import com.xiaodou.summer.validator.annotion.NotEmpty;
import com.xiaodou.summer.vo.in.BaseValidatorPojo;

public class ApplyRequest extends BaseValidatorPojo{

  @NotEmpty
  private List<ApplyRequestDTO> listApplyRequest;

  public List<ApplyRequestDTO> getListApplyRequest() {
    return listApplyRequest;
  }

  public void setListApplyRequest(List<ApplyRequestDTO> listApplyRequest) {
    this.listApplyRequest = listApplyRequest;
  }

  @Data
  public static class ApplyRequestDTO {
    private Integer studentId;// 学生id
    private Long userId;
    private Integer productId;
    private String telephone;
    private String syncId; // 同步记录ID
  }
}
