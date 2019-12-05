package com.xiaodou.oms.agent.vo.request;

import java.util.Map;

/**
 * Created by zyp on 14-7-1.
 */
public class GorderOrderDetailRequest extends BaseRequest {


    /**订单号*/
    private String orderId;

    /**gorder属性*/
    private Map<String, Object> gorderOutputProperties;

    /**order属性*/
    private Map<String, Object> orderOutputProperties;

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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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
}
