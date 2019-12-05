package com.xiaodou.st.dashboard.domain.message;

import java.util.List;

public class ApplyCallBackMessageBody {
  private List<ApplyCallBackMessageBodyDTO> messageBody;

  public List<ApplyCallBackMessageBodyDTO> getMessageBody() {
    return messageBody;
  }

  public void setMessageBody(List<ApplyCallBackMessageBodyDTO> messageBody) {
    this.messageBody = messageBody;
  }

  public static class ApplyCallBackMessageBodyDTO {

    private Integer studentId;// 学生id
    private Integer productId;//

    public Integer getStudentId() {
      return studentId;
    }

    public void setStudentId(Integer studentId) {
      this.studentId = studentId;
    }

    public Integer getProductId() {
      return productId;
    }

    public void setProductId(Integer productId) {
      this.productId = productId;
    }

  }
}
