package com.xiaodou.queue.client;

import java.util.UUID;

import com.xiaodou.queue.client.AbstractMQClient.MessageBox;

/**
 * @name @see com.xiaodou.queue.client.IMQClient.java
 * @CopyRright (c) 2015 by <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2015年7月23日
 * @description MQ客户端接口:定义客户端行为
 * @version 1.0
 */
public interface IMQClient {

  public UUID sendMessage(String messageName, Object message);

  public void sendMessage(MessageBox messageBox);
}
