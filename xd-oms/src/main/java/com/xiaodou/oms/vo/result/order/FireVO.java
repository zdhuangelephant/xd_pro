package com.xiaodou.oms.vo.result.order;

import com.xiaodou.oms.vo.result.ResultInfo;
import com.xiaodou.oms.vo.result.ResultType;

/**
 * <p>
 * 触发状态机事件结果VO对象
 * </p>
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年7月7日
 */
public class FireVO extends ResultInfo {
  
  public FireVO(ResultType type){
    super(type);
  }

  /**
   * 大订单号
   */
  private String gorderId;

  /**
   * 订单号
   */
  private String orderId;

  public String getGorderId() {
    return gorderId;
  }

  public void setGorderId(String gorderId) {
    this.gorderId = gorderId;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

}
