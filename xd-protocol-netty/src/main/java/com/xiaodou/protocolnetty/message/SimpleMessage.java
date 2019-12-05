package com.xiaodou.protocolnetty.message;

import com.xiaodou.standard.protocol.MessageAble;
import com.xiaodou.standard.protocol.TargetSocket;


/**
 * @name SimpleMessage 
 * CopyRright (c) 2018 by <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 *
 * @author <a href=mailto:zhuweijin@corp.51xiaodou.com>zhuweijin</a>
 * @date 2018年2月26日
 * @description TODO
 * @version 1.0
 */
public class SimpleMessage implements MessageAble{

  private String uniqueMessageName;
  private String messageContent;
  
  @Override
  public String uniqueMessageName() {
    
    return uniqueMessageName;
  }

  @Override
  public void setUniqueMessageName(String uniqueMessageName) {
    this.uniqueMessageName = uniqueMessageName;
  }

  @Override
  public String messageContent() {
    // TODO Auto-generated method stub
    return messageContent;
  }

  @Override
  public void setMessageContent(String messageContent) {
    this.messageContent= messageContent;
    
  }

  @Override
  public TargetSocket from() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public TargetSocket to() {
    // TODO Auto-generated method stub
    return null;
  }

}
