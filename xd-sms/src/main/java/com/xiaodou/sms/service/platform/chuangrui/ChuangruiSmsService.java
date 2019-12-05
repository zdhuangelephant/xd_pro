package com.xiaodou.sms.service.platform.chuangrui;

import com.xiaodou.sms.service.platform.ISmsPlatformService;
import com.xiaodou.sms.utils.http.SendSmsByChuangrui;
import com.xiaodou.sms.vo.MessageInfo;
import com.xiaodou.sms.vo.MessageResult;
import com.xiaodou.sms.vo.PlatformInfo;

public class ChuangruiSmsService implements ISmsPlatformService {

  SendSmsByChuangrui sendSmsByChuangrui = new SendSmsByChuangrui();

  @Override
  public MessageResult sendSms(MessageInfo messageInput) {
    return sendSmsByChuangrui.sendSms(messageInput);
  }

  @Override
  public MessageResult queryAccount(PlatformInfo platformInfo) {
    return sendSmsByChuangrui.getNumber();
  }

}
