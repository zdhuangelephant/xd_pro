package com.xiaodou.oms.agent.vo.request;

import java.util.Map;

/**
 * OrderOrderItemListRequest
  * @Title: OrderOrderItemListRequest
  * @Desription order - orderItem 级联查询
  * @author Guanguo.Gao
  * @date 2015年1月4日 下午7:48:38
 */
public class OrderOrderItemListRequest extends BaseRequest{

    /**
     * 分页信息
     */
    private Page page;
    
    /**
     * order查询参数
     */
    private OrderQueryParam orderQueryParams;
    
    /**
     * orderItem查询参数
     */
    private OrderItemQueryParam orderItemQueryParams;
    
    /**
     * order输出参数
     */
    private Map<String, Object> orderOutputProperties;
    
    /**
     * orderItem输出参数
     */
    private Map<String, Object> orderItemOutputProperties;

    /**
     * getter method for page
     * @return the page
     */
    public Page getPage() {
        return page;
    }

    /**
     * setter method for page
     * @Description the page to set
     * @param page 
     */
    public void setPage(Page page) {
        this.page = page;
    }

    /**
     * getter method for orderQueryParams
     * @return the orderQueryParams
     */
    public OrderQueryParam getOrderQueryParams() {
        return orderQueryParams;
    }

    /**
     * setter method for orderQueryParams
     * @Description the orderQueryParams to set
     * @param orderQueryParams 
     */
    public void setOrderQueryParams(OrderQueryParam orderQueryParams) {
        this.orderQueryParams = orderQueryParams;
    }

    /**
     * getter method for orderItemQueryParams
     * @return the orderItemQueryParams
     */
    public OrderItemQueryParam getOrderItemQueryParams() {
        return orderItemQueryParams;
    }

    /**
     * setter method for orderItemQueryParams
     * @Description the orderItemQueryParams to set
     * @param orderItemQueryParams 
     */
    public void setOrderItemQueryParams(OrderItemQueryParam orderItemQueryParams) {
        this.orderItemQueryParams = orderItemQueryParams;
    }

    /**
     * getter method for orderOutputProperties
     * @return the orderOutputProperties
     */
    public Map<String, Object> getOrderOutputProperties() {
        return orderOutputProperties;
    }

    /**
     * setter method for orderOutputProperties
     * @Description the orderOutputProperties to set
     * @param orderOutputProperties 
     */
    public void setOrderOutputProperties(Map<String, Object> orderOutputProperties) {
        this.orderOutputProperties = orderOutputProperties;
    }

    /**
     * getter method for orderItemOutputProperties
     * @return the orderItemOutputProperties
     */
    public Map<String, Object> getOrderItemOutputProperties() {
        return orderItemOutputProperties;
    }

    /**
     * setter method for orderItemOutputProperties
     * @Description the orderItemOutputProperties to set
     * @param orderItemOutputProperties 
     */
    public void setOrderItemOutputProperties(
    	Map<String, Object> orderItemOutputProperties) {
        this.orderItemOutputProperties = orderItemOutputProperties;
    }
    
}
