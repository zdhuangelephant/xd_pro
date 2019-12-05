package com.xiaodou.message.web.response;
import com.xiaodou.sms.common.enums.ResultType;
import com.xiaodou.sms.web.response.BaseResponse;

public class MessageResponse extends BaseResponse {
  /**
   * 
   * @param status
   * @param statusDesc
   */
  private Integer status;
  private String statusDesc;

  public MessageResponse(ResultType type) {
    super(type);
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getStatusDesc() {
    return statusDesc;
  }

  public void setStatusDesc(String statusDesc) {
    this.statusDesc = statusDesc;
  }
}

