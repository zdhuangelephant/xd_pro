package com.xiaodou.oms.agent.vo.request;

import java.util.Map;

/**
 * Created by zyp on 14-7-4.
 */
public class OrderItemListRequest extends BaseRequest {

    private String orderId;

    private String buyAccountId;

    private Map<String, Object> outputProperties;

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

    public String getBuyAccountId() {
        return buyAccountId;
    }

    public void setBuyAccountId(String buyAccountId) {
        this.buyAccountId = buyAccountId;
    }

    public Map<String, Object> getOutputProperties() {
        return outputProperties;
    }

    public void setOutputProperties(Map<String, Object> outputProperties) {
        this.outputProperties = outputProperties;
    }
}
