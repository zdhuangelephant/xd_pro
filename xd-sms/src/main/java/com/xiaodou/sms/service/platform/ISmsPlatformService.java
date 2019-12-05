package com.xiaodou.sms.service.platform;

import com.xiaodou.sms.vo.MessageInfo;
import com.xiaodou.sms.vo.MessageResult;
import com.xiaodou.sms.vo.PlatformInfo;

public interface ISmsPlatformService {
    
    /**
     * 发送短信接口
     * @param messageInput
     * @return
     */
    public MessageResult sendSms(MessageInfo messageInput);
    
    /**
     * 查询账号余额接口
     * @param messageInput
     * @return
     */
    public MessageResult queryAccount(PlatformInfo platformInfo);

}
