package com.xiaodou.course.model.message;

import java.util.List;

public class ApplyMessageBody {
  private List<ApplyMessageBodyDTO> messageBody;

  public List<ApplyMessageBodyDTO> getMessageBody() {
    return messageBody;
  }

  public void setMessageBody(List<ApplyMessageBodyDTO> messageBody) {
    this.messageBody = messageBody;
  }

  public static class ApplyMessageBodyDTO {
    private Integer studentId;// 学生id
    private Long userId;
    private Long productId;

    public Integer getStudentId() {
      return studentId;
    }

    public void setStudentId(Integer studentId) {
      this.studentId = studentId;
    }

    public Long getUserId() {
      return userId;
    }

    public void setUserId(Long userId) {
      this.userId = userId;
    }

    public Long getProductId() {
      return productId;
    }

    public void setProductId(Long productId) {
      this.productId = productId;
    }
  }
}
