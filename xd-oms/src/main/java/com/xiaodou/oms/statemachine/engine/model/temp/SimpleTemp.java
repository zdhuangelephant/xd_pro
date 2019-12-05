package com.xiaodou.oms.statemachine.engine.model.temp;

import com.xiaodou.oms.statemachine.Context;

/**
 * <p>SimpleTemplatePojo</p>
 *
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年6月18日
 */
public class SimpleTemp implements IBaseTempPojo {
  
  /**
   * 订单号
   */
  private String orderId;
  
  /**
   * 大订单号
   */
  private String gorderId;
  
  /**
   * 上下文
   */
  private Context context;

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public String getGorderId() {
    return gorderId;
  }

  public void setGorderId(String gorderId) {
    this.gorderId = gorderId;
  }

  public Context getContext() {
    return context;
  }

  public void setContext(Context context) {
    this.context = context;
  }

  @Override
  public IBaseTempPojo initPojo(Context context) {
    this.orderId = context.getCoreParams().getOrderId();
    this.gorderId = context.getCoreParams().getGorderId();
    this.context = context;
    return this;
  }

}
