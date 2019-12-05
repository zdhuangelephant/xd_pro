package com.xiaodou.oms.vo.result.order;

import java.util.List;

import com.xiaodou.oms.entity.order.Order;
import com.xiaodou.oms.vo.result.PageInfo;
import com.xiaodou.oms.vo.result.ResultInfo;
import com.xiaodou.oms.vo.result.ResultType;

/**
 * <p>
 * 查询Order列表VO
 * </p>
 * 
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年7月1日
 */
public class OrderListVO extends ResultInfo {
  
  public OrderListVO(ResultType type){
    super(type);
  }
  
  /**
   * 分页信息
   */
  private PageInfo page;
  
  /**
   * 查询结果集
   */
  private List<Order> list;

  public PageInfo getPage() {
    return page;
  }

  public void setPage(PageInfo page) {
    this.page = page;
  }

  public List<Order> getList() {
    return list;
  }

  public void setList(List<Order> list) {
    this.list = list;
  }

}
