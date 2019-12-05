package com.xiaodou.message.service.platform.jpush;

import org.springframework.stereotype.Service;

import com.xiaodou.message.model.MessageModel;
import com.xiaodou.message.service.platform.IMsPlatformService;
import com.xiaodou.message.utils.http.SendMsByJPush;
import com.xiaodou.message.vo.MessagePushInfo;
@Service("jPushMsService")
public class JPushMsService implements IMsPlatformService{

  @Override
  public MessageModel sendMs(MessagePushInfo messagePushInfo) {
    SendMsByJPush sendMsByJPush = new SendMsByJPush();
    return sendMsByJPush.sendNoticePush(messagePushInfo);
  }
  
}
