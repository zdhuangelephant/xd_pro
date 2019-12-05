package com.xiaodou.oms.agent.model.statemachine;

/**
 * Created by zyp on 14-7-3.
 */
public class Message {

    /**支付单号*/
    private String gorderId;

    /**订单号*/
    private String orderId;

    /**消息体*/
    private MessageContext context;

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

    public MessageContext getContext() {
        return context;
    }

    public void setContext(MessageContext context) {
        this.context = context;
    }
}
