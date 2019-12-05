package com.xiaodou.course.vo.message;

import com.xiaodou.jmsg.entity.AbstractMessagePojo;

public class AddCreditMessage extends AbstractMessagePojo {

  /** ASYNC_MESSAGE_ADDCREDIT 增加积分消息名 */
  private final static String ASYNC_MESSAGE_ADDCREDIT = "%s_addCredit";

  public AddCreditMessage(String module, Object messageBody) {
    super(String.format(ASYNC_MESSAGE_ADDCREDIT, module));
    setTransferObject(messageBody);
  }

}
