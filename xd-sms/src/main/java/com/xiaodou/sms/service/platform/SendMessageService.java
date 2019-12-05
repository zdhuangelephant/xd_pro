package com.xiaodou.sms.service.platform;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.sms.common.constant.Constant;
import com.xiaodou.sms.dao.ms.SmsChannelModelDao;
import com.xiaodou.sms.vo.MessageInfo;
import com.xiaodou.sms.vo.MessageResult;


@Service
public class SendMessageService {
	@Resource
	SmsChannelModelDao smsChannelModelDao;
	
	/**
	 * 发送短信服务
	 * @param channelId
	 * @param params 参数map，
	 *  1. 单发
	 *  	请提供相关参数：telephone－手机号；content－短信内容
	 *  2. 群发
	 *  	未知
	 * @return
	 */
	public MessageResult send(String channelId,String merchantId,Map<String, String> params){
		ISmsPlatformService service=PlatformServiceFactory.getSmsPlatformService(merchantId);
		
		switch (channelId) {
		case Constant.ChannelForCheckCode:
			MessageInfo messageInfo=new MessageInfo();
			messageInfo.setContent(params.get("content"));
			messageInfo.setTelephone(params.get("telephone"));
			return service.sendSms(messageInfo);
		default:
			return null;
		}
		
	}
	
}
