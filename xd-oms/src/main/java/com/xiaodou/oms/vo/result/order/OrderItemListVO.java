package com.xiaodou.oms.vo.result.order;

import java.util.List;

import com.xiaodou.oms.entity.order.OrderItem;
import com.xiaodou.oms.vo.result.ResultInfo;
import com.xiaodou.oms.vo.result.ResultType;

/**
 * <p>
 * 查询OrderItem列表VO
 * </p>
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年7月1日
 */
public class OrderItemListVO extends ResultInfo {
  public OrderItemListVO(ResultType type){
    super(type);
  }
  
  /**
   * 查询结果集
   */
  private List<OrderItem> list;

  public List<OrderItem> getList() {
    return list;
  }

  public void setList(List<OrderItem> list) {
    this.list = list;
  }
}
