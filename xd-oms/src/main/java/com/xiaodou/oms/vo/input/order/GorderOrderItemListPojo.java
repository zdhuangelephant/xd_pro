package com.xiaodou.oms.vo.input.order;

import java.util.Map;

import com.xiaodou.oms.vo.input.Page;
import com.xiaodou.oms.vo.input.sign.BasePojo;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * <p>三级联查列表参数Pojo</p>
 *
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年7月2日
 */
public class GorderOrderItemListPojo extends BasePojo {
  
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
   * OrderItem查询参数
   */
  @NotEmpty
  private OrderItem orderItemQueryParams;
  
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
  
  /**
   * OrderItem查询返回的属性信息
   */
  @NotEmpty
  private Map<String, Object> orderItemOutputProperties;

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

  public OrderItem getOrderItemQueryParams() {
    return orderItemQueryParams;
  }

  public void setOrderItemQueryParams(OrderItem orderItemQueryParams) {
    this.orderItemQueryParams = orderItemQueryParams;
  }

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

  public Map<String, Object> getOrderItemOutputProperties() {
    return orderItemOutputProperties;
  }

  public void setOrderItemOutputProperties(Map<String, Object> orderItemOutputProperties) {
    this.orderItemOutputProperties = orderItemOutputProperties;
  }
}
