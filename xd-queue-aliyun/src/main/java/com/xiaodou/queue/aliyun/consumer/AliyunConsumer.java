package com.xiaodou.queue.aliyun.consumer;

import com.aliyun.mns.model.Message;
import com.xiaodou.queue.consumer.AbstractConsumer;

/**
 * @name @see com.xiaodou.queue.consumer.AliyunConsumer.java
 * @CopyRright (c) 2015 by XiaoDou NetWork Technology
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年8月22日
 * @description 阿里云拉取消息消费者默认指定消息体类型
 * @version 1.0
 */
public abstract class AliyunConsumer extends AbstractConsumer<Message, String> {

  /** serialVersionUID */
  private static final long serialVersionUID = 2539932133149092658L;

}
