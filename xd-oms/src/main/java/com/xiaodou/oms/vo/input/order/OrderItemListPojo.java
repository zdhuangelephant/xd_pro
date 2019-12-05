package com.xiaodou.oms.vo.input.order;

import java.util.Map;

import com.xiaodou.oms.vo.input.sign.BasePojo;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * <p>查询OrderItemList参数Pojo</p>
 *
 * @author <a href="mailto:dan.zhao@elong.corp.com">zhaodan</a>
 * @version 1.0
 * @date 2014年7月1日
 */
public class OrderItemListPojo extends BasePojo {
  
  /**
   * 订单号
   */
  @NotEmpty
  private String orderId;
  
  /**
   * 下单账户
   */
  private String buyAccountId;
  
  /**
   * 查询返回的属性信息
   */
  @NotEmpty
  private Map<String, Object> outputProperties;

  public String getBuyAccountId() {
    return buyAccountId;
  }

  public void setBuyAccountId(String buyAccountId) {
    this.buyAccountId = buyAccountId;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public Map<String, Object> getOutputProperties() {
    return outputProperties;
  }

  public void setOutputProperties(Map<String, Object> outputProperties) {
    this.outputProperties = outputProperties;
  }

}
