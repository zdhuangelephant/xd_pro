package com.xiaodou.st.dashboard.domain.http;

import java.util.List;

import lombok.Data;

import com.xiaodou.summer.vo.out.ResultType;

public class ApplyResponse extends ResponseBase {
  public ApplyResponse() {};

  public ApplyResponse(ResultType type) {
    super(type);
  }

  private List<ApplyResponseDTO> listApplyResponse;

  public List<ApplyResponseDTO> getListApplyResponse() {
    return listApplyResponse;
  }

  public void setListApplyResponse(List<ApplyResponseDTO> listApplyResponse) {
    this.listApplyResponse = listApplyResponse;
  }

  @Data
  public static class ApplyResponseDTO {

    private Integer studentId;// 学生id
    private Integer productId;//
    private Short applyStatus;
    private String telephone;
  }

  @Data
  public static class ApplyQueueMessageDTO {
    private List<ApplyResponseDTO> listApplyResponseDTO;
    private String syncId;
  }
}
