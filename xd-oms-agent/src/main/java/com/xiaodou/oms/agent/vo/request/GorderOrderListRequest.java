package com.xiaodou.oms.agent.vo.request;


import com.xiaodou.oms.agent.model.Gorder;
import com.xiaodou.oms.agent.model.Order;

import java.util.Map;

/**
 * Created by zyp on 14-7-4.
 */
public class GorderOrderListRequest extends BaseRequest {

    /**分页*/
    private Page page;

    /**gorder查询属性*/
    private GorderQueryParam gorderQueryParams;

    /**order查询属性*/
    private OrderQueryParam orderQueryParams;

    private Map<String, Object> gorderOutputProperties;

    private Map<String, Object> orderOutputProperties;

    public GorderQueryParam getGorderQueryParams() {
        return gorderQueryParams;
    }

    public void setGorderQueryParams(GorderQueryParam gorderQueryParams) {
        this.gorderQueryParams = gorderQueryParams;
    }

    public OrderQueryParam getOrderQueryParams() {
        return orderQueryParams;
    }

    public void setOrderQueryParams(OrderQueryParam orderQueryParams) {
        this.orderQueryParams = orderQueryParams;
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
