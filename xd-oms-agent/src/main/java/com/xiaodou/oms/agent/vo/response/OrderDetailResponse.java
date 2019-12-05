package com.xiaodou.oms.agent.vo.response;

import java.util.Map;

/**
 * Created by zyp on 14-7-1.
 */
public class OrderDetailResponse extends BaseResponse {

  /** 订单详情信息 */
  private Map<String, Object> order;

  public Map<String, Object> getOrder() {
    return order;
  }

  public void setOrder(Map<String, Object> order) {
    this.order = order;
  }
}
