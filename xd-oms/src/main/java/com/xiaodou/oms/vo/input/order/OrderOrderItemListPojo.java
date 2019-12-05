package com.xiaodou.oms.vo.input.order;

import java.util.Map;

import com.xiaodou.oms.vo.input.Page;
import com.xiaodou.oms.vo.input.sign.BasePojo;
import com.xiaodou.summer.validator.annotion.NotEmpty;

/**
 * OrderOrderItemListPojo
  * @Title: OrderOrderItemListPojo
  * @Desription order - orderItem 两级联查 pojo
  * @author Guanguo.Gao
  * @date 2015年1月5日 上午10:14:31
 */
public class OrderOrderItemListPojo extends BasePojo{

    /**
     * 分页参数
     */
    @NotEmpty
    private Page page;
    
    /**
     * Order查询参数
     */
    @NotEmpty
    private Order orderQueryParams;
    
    /**
     * orderItem查询参数
     */
    @NotEmpty
    private OrderItem orderItemQueryParams;
    
    /**
     * order查询属性
     */
    @NotEmpty
    private Map<String, Object> orderOutputProperties;
    
    /**
     * orderItem查询属性
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
    public Order getOrderQueryParams() {
        return orderQueryParams;
    }

    /**
     * setter method for orderQueryParams
     * @Description the orderQueryParams to set
     * @param orderQueryParams 
     */
    public void setOrderQueryParams(Order orderQueryParams) {
        this.orderQueryParams = orderQueryParams;
    }

    /**
     * getter method for orderItemQueryParams
     * @return the orderItemQueryParams
     */
    public OrderItem getOrderItemQueryParams() {
        return orderItemQueryParams;
    }

    /**
     * setter method for orderItemQueryParams
     * @Description the orderItemQueryParams to set
     * @param orderItemQueryParams 
     */
    public void setOrderItemQueryParams(OrderItem orderItemQueryParams) {
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
