package com.xiaodou.oms.agent.vo.request;



import java.util.Map;

/**
 * Created by zyp on 14-7-2.
 */
public class GorderOrderItemListReuqest extends BaseRequest {

    /**分页*/
    private Page page;

    /**gorder查询参数*/
    private GorderQueryParam gorderQueryParams;

    /**order查询参数*/
    private OrderQueryParam orderQueryParams;

    /**orderItem查询参数*/
    private OrderItemQueryParam orderItemQueryParams;

    /**gorder输出参数*/
    private Map<String, Object> gorderOutputProperties;

    /**order输出参数*/
    private Map<String, Object> orderOutputProperties;

    /**orderItem输出参数*/
    private Map<String, Object> orderItemOutputProperties;

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

    public OrderItemQueryParam getOrderItemQueryParams() {
        return orderItemQueryParams;
    }

    public void setOrderItemQueryParams(OrderItemQueryParam orderItemQueryParams) {
        this.orderItemQueryParams = orderItemQueryParams;
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

    public Map<String, Object> getOrderItemOutputProperties() {
        return orderItemOutputProperties;
    }

    public void setOrderItemOutputProperties(Map<String, Object> orderItemOutputProperties) {
        this.orderItemOutputProperties = orderItemOutputProperties;
    }
}
