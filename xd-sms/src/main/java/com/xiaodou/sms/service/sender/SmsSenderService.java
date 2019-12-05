package com.xiaodou.sms.service.sender;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.service.QueueService;
import com.xiaodou.common.service.QueueService.Message;
import com.xiaodou.sms.common.constant.Constant;
import com.xiaodou.sms.model.SmsTaskModel;
import com.xiaodou.sms.service.facade.ISmsServiceFacade;

/**
 * @name @see com.xiaodou.sms.service.sender.SmsSenderService.java
 * @CopyRright (c) 2015 by <a
 *             href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年7月31日
 * @description 发送短信队列服务
 * @version 1.0
 */
@Service("smsSenderService")
public class SmsSenderService {

	@Resource
	ISmsServiceFacade smsServiceFacade;

	@Resource
	QueueService queueService;

	/**
	 * 添加信息至队列
	 * 
	 * @param task
	 */
	public void addMessage(SmsTaskModel task) {
		switch (task.getChannelId().toString()) {
		case Constant.ChannelForCheckCode:
			queueService.addAliyunMessage(Message.sms_checkcode, task);
			break;
		default:
			queueService.addAliyunMessage(Message.sms_notice, task);
			break;
		}
	}

}
