package com.xiaodou.oms.service.message;

import java.util.Map;

import com.xiaodou.oms.statemachine.Context;
import com.xiaodou.oms.statemachine.param.CoreParams;

/**
 * <p>
 * 异步消息实体中的Context对象
 * </p>
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年7月3日
 */
public class MessageContext {
  private CoreParams coreParams;
  private Map<Object, Object> otherParams;
  
  private MessageContext(){}
  
  @SuppressWarnings("unchecked")
  private MessageContext(Context context){
    this.coreParams = context.getCoreParams();
    this.otherParams = context.getOtherParams();
  }
  
  public static MessageContext newInstance(Context context){
    return new MessageContext(context);
  }
  
  public static MessageContext newInstance(){
    return new MessageContext();
  }

  public CoreParams getCoreParams() {
    return coreParams;
  }

  public void setCoreParams(CoreParams coreParams) {
    this.coreParams = coreParams;
  }

  public Map<Object, Object> getOtherParams() {
    return otherParams;
  }

  public void setOtherParams(Map<Object, Object> otherParams) {
    this.otherParams = otherParams;
  }
}
