package com.xiaodou.vo.mq;

import com.xiaodou.jmsg.entity.AbstractMessagePojo;
import com.xiaodou.userCenter.request.AddCreditRequest;

public class AddCreditMessage extends AbstractMessagePojo {

  /** ASYNC_MESSAGE_ADDCREDIT 增加积分消息名 */
  private final static String ASYNC_MESSAGE_ADDCREDIT = "addCredit";

  public AddCreditMessage() {
    super(null);
  }

  public AddCreditMessage(AddCreditRequest messageBody) {
    super(ASYNC_MESSAGE_ADDCREDIT);
    setTransferObject(messageBody);
  }

}
