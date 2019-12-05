package com.xiaodou.sms.service.platform.huyi;

import com.xiaodou.sms.service.platform.ISmsPlatformService;
import com.xiaodou.sms.utils.http.SendSmsByHuyi;
import com.xiaodou.sms.vo.MessageInfo;
import com.xiaodou.sms.vo.MessageResult;
import com.xiaodou.sms.vo.PlatformInfo;

/**
 * 
 * huyi 发送短信service
 * 
 * @author weirong.li
 * @version 1.0
 * @since JDK1.7
 */
public class HuyiSmsService implements ISmsPlatformService {

  SendSmsByHuyi sendSmsByHuyi = new SendSmsByHuyi();

  /**
   * 发送短信接口
   * 
   * @param messageInput
   * @return
   */
  public MessageResult sendSms(MessageInfo messageInfo) {
    return sendSmsByHuyi.sendSms(messageInfo);
  }

  /**
   * 查询账号余额接口
   * 
   * @param messageInput
   * @return
   */
  public MessageResult queryAccount(PlatformInfo platformInfo) {
    return sendSmsByHuyi.getNumber();
  }


}
