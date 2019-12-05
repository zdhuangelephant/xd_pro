package com.xiaodou.ucenter;

import com.xiaodou.async.model.SystemMessage;

public class SystemMessageTest {

  public static void main(String[] args) {
    SystemMessage message = new SystemMessage();
    message.setModule("1");
    message.setSrcUid("-1");
    message.setToUid("871");
    message.setMessageName("2_systemmessage");
    message.setMessageBody("恭喜您中奖了！");
    message.send();
  }
  
}
