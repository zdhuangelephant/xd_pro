package com.xiaodou.oms.statemachine.engine.model.temp;

import com.xiaodou.oms.entity.order.Gorder;
import com.xiaodou.oms.entity.order.Order;
import com.xiaodou.oms.statemachine.Context;

/**
 * <p>CompleteTempPojo</p>
 *
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年6月18日
 */
public class CompleteTemp implements IBaseTempPojo {
  
  /**
   * Order
   */
  private Order order;
  
  /**
   * Gorder
   */
  private Gorder gorder;
  
  /**
   * 上下文
   */
  private Context context;

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

  public Gorder getGorder() {
    return gorder;
  }

  public void setGorder(Gorder gorder) {
    this.gorder = gorder;
  }

  public Context getContext() {
    return context;
  }

  public void setContext(Context context) {
    this.context = context;
  }

  @Override
  public IBaseTempPojo initPojo(Context context) {
    Object dbOrder = context.getShares().get("dbOrder");
    if(null!=dbOrder){
      this.order = (Order)dbOrder;
      this.gorder = order.getGorder();
    }
    this.context = context;
    return this;
  }

}
