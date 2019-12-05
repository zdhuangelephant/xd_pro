package com.xiaodou.oms.agent.vo.request;

import java.util.Map;

/**
 * Created by zyp on 14-7-4.
 */
public class OrderListRequest extends BaseRequest {

    private Page page;

    private OrderQueryParam queryParams;

    private Map<String, Object> outputProperties;

    public OrderQueryParam getQueryParams() {
        return queryParams;
    }

    public void setQueryParams(OrderQueryParam queryParams) {
        this.queryParams = queryParams;
    }

    public String getProductLine() {
        return productLine;
    }

    public void setProductLine(String productLine) {
        this.productLine = productLine;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Map<String, Object> getOutputProperties() {
        return outputProperties;
    }

    public void setOutputProperties(Map<String, Object> outputProperties) {
        this.outputProperties = outputProperties;
    }
}
