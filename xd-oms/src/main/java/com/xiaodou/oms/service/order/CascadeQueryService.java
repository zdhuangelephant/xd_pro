package com.xiaodou.oms.service.order;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.oms.dao.order.CascadeQueryDao;
import com.xiaodou.oms.entity.order.Order;
import com.xiaodou.oms.entity.order.OrderItem;
import com.xiaodou.oms.util.model.QueryPaymentModel;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * 
 * @Title:OrderQueryService.java
 * 
 * @Description:三级订单的综合查询
 *
 * @author  zhaoyang
 * @date    Jan 27, 2014 2:03:41 PM
 * @version V1.0
 */
@Service
public class CascadeQueryService {
	
    @Resource
    CascadeQueryDao queryDao;
    
    /**
     * queryOrderItemDetail
      * @Title: queryOrderItemDetail
      * @Description: 查询orderItem详情
      * @param orderItemId
      * @param output
      * @return
     */
    @SuppressWarnings("rawtypes")
    public OrderItem queryOrderItemDetail(String orderItemId,Map output) {
		
	return this.queryDao.queryOrderItemDetail(orderItemId, output);
    }
    
    /**
     * queryOrderItemDetail
      * @Title: queryOrderItemDetail
      * @Description: 查询orderItem详情,根据条件
      * @param input
      * @param output
      * @return
     */
    @SuppressWarnings("rawtypes")
    public OrderItem queryOrderItemDetail(Map input,Map output) {
		
	return this.queryDao.queryOrderItemDetail(input, output);
    }
    
    /**
     * queryPagedOrderItemList
      * @Title: queryPagedOrderItemList
      * @Description: gorder - order - orderitem三级联查
      * @param parameterMap
      * @param outputMap
      * @param page
      * @return
     */
    @SuppressWarnings("rawtypes")
    public Page<OrderItem> queryPagedOrderItemList(Map parameterMap, Map outputMap,Page<OrderItem> page){
    	return this.queryDao.queryPagedOrderItemList(parameterMap, outputMap, page);
    }
    
    /**
     * queryPagedOrderList
      * @Title: queryPagedOrderList
      * @Description: gorder - order 两级联查
      * @param parameterMap
      * @param outputMap
      * @param page
      * @return
     */
    @SuppressWarnings("rawtypes")
    public Page<Order> queryPagedOrderList(Map parameterMap, Map outputMap,Page<Order> page){
    	return this.queryDao.queryPagedOrderList(parameterMap, outputMap, page);
    }
    
    /**
     * queryGorderOrderDetail
      * @Title: queryGorderOrderDetail
      * @Description: gorder 详情查询
      * @param input
      * @param output
      * @return
     */
    @SuppressWarnings("rawtypes")
    public Order queryGorderOrderDetail(Map input,Map output) {
        
        return this.queryDao.queryGorderOrderDetail(input, output);
    }

    /**
     * queryGorderPayRecordById
      * @Title: queryGorderPayRecordById
      * @Description: 查询payrecord记录
      * @param id
      * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public QueryPaymentModel queryGorderPayRecordById(String id){
	Map input = new HashMap();
	input.put("id", id);
	Map output = new HashMap();
	return this.queryDao.queryGorderPayRecordById(input, output);
    }

    /**
     * queryPagedOrderOrderitemList
      * @Title: queryPagedOrderOrderitemList
      * @Description: order-orderItem两级联查
      * @param queryParam
      * @param outputMap
      * @param page
     */
    public Page<OrderItem> queryPagedOrderOrderItemList(Map<String, Object> queryParam,
	    Map<String, Object> outputMap, Page<OrderItem> page) {
    	return this.queryDao.queryPagedOrderOrderItemList(queryParam, outputMap, page);
    }
    
    

}
