package com.xiaodou.oms.agent.vo.request;

import java.util.Map;

/**
 * Created by zyp on 14-7-1.
 */
public class GorderOrderItemDetailRequest extends BaseRequest {

    /**订单项id*/
    private String orderItemId;

    /**订单号*/
    private String orderId;

    /**gorder 输出项*/
    private Map<String, Object> gorderOutputProperties;

    /**order 输出项*/
    private Map<String, Object> orderOutputProperties;

    /**orderItem 输出项*/
    private Map<String, Object> orderItemOutputProperties;

    /**购买者*/
    private String buyAccountId;

    public String getBuyAccountId() {
        return buyAccountId;
    }

    public void setBuyAccountId(String buyAccountId) {
        this.buyAccountId = buyAccountId;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public String getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(String orderItemId) {
        this.orderItemId = orderItemId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public Map<String, Object> getGorderOutputProperties() {
        return gorderOutputProperties;
    }

    public void setGorderOutputProperties(Map<String, Object> gorderOutputProperties) {
        this.gorderOutputProperties = gorderOutputProperties;
    }
}
