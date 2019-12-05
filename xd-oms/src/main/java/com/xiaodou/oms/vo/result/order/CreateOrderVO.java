package com.xiaodou.oms.vo.result.order;

import com.xiaodou.oms.vo.result.ResultInfo;
import com.xiaodou.oms.vo.result.ResultType;

/**
 * <p>创建订单VO</p>
 *
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年6月20日
 */
public class CreateOrderVO extends ResultInfo {
  
  /**
   * 大订单ID
   */
  private String gorderId;
  
  /**
   * 订单ID列表
   */
  private String orderIdList;
  
  public String getGorderId() {
    return gorderId;
  }

  public void setGorderId(String gorderId) {
    this.gorderId = gorderId;
  }

  public String getOrderIdList() {
    return orderIdList;
  }

  public void setOrderIdList(String orderIdList) {
    this.orderIdList = orderIdList;
  }

  public CreateOrderVO(){
    super();
  }
  
  public CreateOrderVO(ResultType type){
    super(type);
  }

}
