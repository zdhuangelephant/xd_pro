package com.xiaodou.message.service.sender;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.service.QueueService;
import com.xiaodou.common.service.QueueService.Message;
import com.xiaodou.message.vo.MessagePushInfo;

/**
 * @name @see com.xiaodou.sms.service.sender.SmsSenderService.java
 * @CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年7月31日
 * @description 发送短信队列服务
 * @version 1.0
 */
@Service("msSenderService")
public class MsSenderService {

  @Resource(name = "queueService")
  QueueService queueService;

  /**
   * 添加信息至队列
   * 
   * @param task
   */
  public void addPushMessage(MessagePushInfo messagePushInfo) {
    queueService.addAliyunMessage(Message.pm_notice, messagePushInfo);
  }



}
