package com.xiaodou.oms.agent.vo.response;

/**
 * Created by zyp on 14-6-24.
 */
public class NewOrderResponse extends BaseResponse {

    /**
     * 支付订单号
     */
    private String gorderId;

    /**
     * 订单号列表
     */
    private String orderIdList;

    public String getGorderId() {
        return gorderId;
    }

    public void setGorderId(String gorderId) {
        this.gorderId = gorderId;
    }

    public String getOrderIdList() {
        return orderIdList;
    }

    public void setOrderIdList(String orderIdList) {
        this.orderIdList = orderIdList;
    }
}
