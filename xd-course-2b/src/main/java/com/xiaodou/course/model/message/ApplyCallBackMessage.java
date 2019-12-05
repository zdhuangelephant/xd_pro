package com.xiaodou.course.model.message;

import java.util.List;

import com.xiaodou.jmsg.entity.AbstractMessagePojo;

public class ApplyCallBackMessage extends AbstractMessagePojo {

  /* student */
  /* 学生状态 0、未注册，1、注册成功，2、注册失败 */
  public static final Short NOT_REGISTER = 0;
  public static final Short HAS_REGISTER = 1;
  public static final Short FAIL_REGISTER = 2;

  /** 定时任务消息名 */
  private static final String ASYNC_MESSAGE_APPLY_SCHEDULER_CALLBACK = "%s_applySchedulerCallBack";

  public ApplyCallBackMessage(ApplyCallBackMessageBody messageBody) {
    super(String.format(ASYNC_MESSAGE_APPLY_SCHEDULER_CALLBACK, "2"));
    setTransferObject(messageBody);
  }

  /**
   * 消息体
   * 
   * @name MessageBody CopyRright (c) 2017 by 李德洪
   * 
   * @author 李德洪
   * @date 2017年4月20日
   * @description TODO
   * @version 1.0
   */
  public static class ApplyCallBackMessageBody {
    private List<ApplyCallBackMessageBodyDTO> messageBody;

    public List<ApplyCallBackMessageBodyDTO> getMessageBody() {
      return messageBody;
    }

    public void setMessageBody(List<ApplyCallBackMessageBodyDTO> messageBody) {
      this.messageBody = messageBody;
    }
  }


  public static class ApplyCallBackMessageBodyDTO {

    private Integer studentId;// 学生id
    private Long productId;//

    public Integer getStudentId() {
      return studentId;
    }

    public void setStudentId(Integer studentId) {
      this.studentId = studentId;
    }

    public Long getProductId() {
      return productId;
    }

    public void setProductId(Long productId) {
      this.productId = productId;
    }

  }

}
