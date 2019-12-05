package com.xiaodou.oms.vo.result.order;

import java.util.List;

import com.xiaodou.oms.entity.order.OrderItem;
import com.xiaodou.oms.vo.result.PageInfo;
import com.xiaodou.oms.vo.result.ResultInfo;
import com.xiaodou.oms.vo.result.ResultType;

public class GorderOrderItemListVO extends ResultInfo {
  
  public GorderOrderItemListVO(ResultType type){
    super(type);
  }
  
  /**
   * 分页信息
   */
  private PageInfo page;
  
  /**
   * 查询结果集
   */
  private List<OrderItem> list;

  public PageInfo getPage() {
    return page;
  }

  public void setPage(PageInfo page) {
    this.page = page;
  }

  public List<OrderItem> getList() {
    return list;
  }

  public void setList(List<OrderItem> list) {
    this.list = list;
  }
}
