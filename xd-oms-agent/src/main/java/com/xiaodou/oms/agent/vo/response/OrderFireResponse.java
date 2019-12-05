package com.xiaodou.oms.agent.vo.response;

/**
 * Created by zyp on 14-7-16.
 */
public class OrderFireResponse extends BaseResponse {

    /**
     * 大订单号
     */
    private String gorderId;

    /**
     * 订单号
     */
    private String orderId;

    public String getGorderId() {
        return gorderId;
    }

    public void setGorderId(String gorderId) {
        this.gorderId = gorderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
