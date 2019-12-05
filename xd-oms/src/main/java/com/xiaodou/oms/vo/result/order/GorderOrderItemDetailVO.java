package com.xiaodou.oms.vo.result.order;

import com.xiaodou.oms.entity.order.OrderItem;
import com.xiaodou.oms.vo.result.ResultInfo;
import com.xiaodou.oms.vo.result.ResultType;

/**
 * <p>三级联查详情VO</p>
 *
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年6月30日
 */
public class GorderOrderItemDetailVO extends ResultInfo {
  
  private OrderItem orderItem;

  public GorderOrderItemDetailVO(ResultType type) {
    super(type);
  }

  public OrderItem getOrderItem() {
    return orderItem;
  }

  public void setOrderItem(OrderItem orderItem) {
    this.orderItem = orderItem;
  }


}