package com.xiaodou.course.vo.message;

import com.xiaodou.jmsg.entity.AbstractMessagePojo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class StudyCourseWareScoreMessage extends AbstractMessagePojo {

  /** ASYNC_MESSAGE_STUDYCOURSEWARESCORE 学习课件成绩消息名 */
  private final static String ASYNC_MESSAGE_STUDYCOURSEWARESCORE = "studyCourseWare";

  public StudyCourseWareScoreMessage(Object messageBody) {
    super(ASYNC_MESSAGE_STUDYCOURSEWARESCORE);
    setTransferObject(messageBody);
  }
}
