package com.xiaodou.oms.vo.result.order;

import com.xiaodou.oms.entity.order.Order;
import com.xiaodou.oms.vo.result.ResultInfo;
import com.xiaodou.oms.vo.result.ResultType;

/**
 * <p>
 * 查询Order详情VO
 * </p>
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年6月30日
 */
public class OrderDetailVO extends ResultInfo {

  public OrderDetailVO(ResultType type) {
    super(type);
  }

  /**
   * gorder对象
   */
  private Order order;

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

}
