package com.xiaodou.oms.agent.vo.response;

import java.util.Map;

/**
 * Created by zyp on 14-7-1.
 */
public class OrderItemDetailResponse extends BaseResponse {

    /**订单项信息*/
    private Map<String, Object> orderItem;

    public Map<String, Object> getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(Map<String, Object> orderItem) {
        this.orderItem = orderItem;
    }
}
