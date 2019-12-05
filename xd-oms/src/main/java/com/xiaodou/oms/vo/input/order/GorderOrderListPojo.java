package com.xiaodou.oms.vo.input.order;

import java.util.Map;

import com.xiaodou.oms.vo.input.Page;
import com.xiaodou.oms.vo.input.sign.BasePojo;
import com.xiaodou.summer.validator.annotion.NotEmpty;

public class GorderOrderListPojo extends BasePojo {
  
  /**
   * 分页参数
   */
  @NotEmpty
  private Page page;
  
  /**
   * Gorder查询参数
   */
  @NotEmpty
  private Gorder gorderQueryParams;
  
  /**
   * Order查询参数
   */
  @NotEmpty
  private Order orderQueryParams;
  
  /**
   * Gorder查询返回的属性信息
   */
  @NotEmpty
  private Map<String, Object> gorderOutputProperties;
  
  /**
   * Order查询返回的属性信息
   */
  @NotEmpty
  private Map<String, Object> orderOutputProperties;

  public Map<String, Object> getGorderOutputProperties() {
    return gorderOutputProperties;
  }

  public void setGorderOutputProperties(Map<String, Object> gorderOutputProperties) {
    this.gorderOutputProperties = gorderOutputProperties;
  }

  public Map<String, Object> getOrderOutputProperties() {
    return orderOutputProperties;
  }

  public void setOrderOutputProperties(Map<String, Object> orderOutputProperties) {
    this.orderOutputProperties = orderOutputProperties;
  }

  public Page getPage() {
    return page;
  }

  public void setPage(Page page) {
    this.page = page;
  }

  public Gorder getGorderQueryParams() {
    return gorderQueryParams;
  }

  public void setGorderQueryParams(Gorder gorderQueryParams) {
    this.gorderQueryParams = gorderQueryParams;
  }

  public Order getOrderQueryParams() {
    return orderQueryParams;
  }

  public void setOrderQueryParams(Order orderQueryParams) {
    this.orderQueryParams = orderQueryParams;
  }
}
