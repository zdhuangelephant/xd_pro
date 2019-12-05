package com.xiaodou.st.dashboard.domain.message;

import java.util.List;

import com.xiaodou.jmsg.entity.AbstractMessagePojo;

public class ApplyMessage extends AbstractMessagePojo {

  /** 定时任务消息名 */
  private static final String ASYNC_MESSAGE_APPLY_SCHEDULER = "%s_applyScheduler";

  public ApplyMessage(ApplyMessageBody messageBody) {
    super(String.format(ASYNC_MESSAGE_APPLY_SCHEDULER, "2"));
    setTransferObject(messageBody);
  }

  public static class ApplyMessageBody {
    private List<ApplyMessageBodyDTO> messageBody;

    public List<ApplyMessageBodyDTO> getMessageBody() {
      return messageBody;
    }

    public void setMessageBody(List<ApplyMessageBodyDTO> messageBody) {
      this.messageBody = messageBody;
    }
  }

  public static class ApplyMessageBodyDTO {
    private Integer studentId;// 学生id
    private Long userId;
    private Integer productId;

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

    public Integer getProductId() {
      return productId;
    }

    public void setProductId(Integer productId) {
      this.productId = productId;
    }
  }

}
