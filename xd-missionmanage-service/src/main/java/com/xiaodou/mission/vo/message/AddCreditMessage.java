package com.xiaodou.mission.vo.message;

import com.xiaodou.jmsg.entity.AbstractMessagePojo;
import com.xiaodou.userCenter.request.AddCreditRequest;

/**
 * @name AddCreditMessage
 * @CopyRright (c) 2017 by zhaodan
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2017年3月24日
 * @description 增加积分异步消息参数类
 * @version 1.0
 */
public class AddCreditMessage extends AbstractMessagePojo {

  /** ASYNC_MESSAGE_ADDCREDIT 增加积分消息名 */
  private final static String ASYNC_MESSAGE_ADDCREDIT = "addCredit";

  public AddCreditMessage(String module, AddCreditRequest message) {
    super(ASYNC_MESSAGE_ADDCREDIT);
    setTransferObject(message);
  }
}
