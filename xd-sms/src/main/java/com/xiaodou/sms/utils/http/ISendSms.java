package com.xiaodou.sms.utils.http;

import com.xiaodou.sms.vo.GetNumResult;
import com.xiaodou.sms.vo.MessageInfo;
import com.xiaodou.sms.vo.MessageResult;

public interface ISendSms {
  
  public MessageResult sendSms(MessageInfo messageInput);
  
  public GetNumResult getNumber();

}
