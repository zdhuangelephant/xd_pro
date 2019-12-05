package com.xiaodou.oms.service.order;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.xiaodou.oms.dao.order.OrderItemDao;
import com.xiaodou.oms.entity.order.OrderItem;

/**
 * 订单条目Service类
 * @author bjfyzhao 2012-09-17 17:20
 *
 */
public class OrderItemService {
	@Resource
	private OrderItemDao orderItemDao;
	
	/**
	 * 添加订单条目明细
	 * @param orderItem
	 * @return true--添加成功 false--添加失败
	 */
	public boolean addOrderItem(OrderItem orderItem){
		if(null == orderItem){
			return false;
		}
		
		orderItem.setId(orderItemDao.querySeqId(""));// 订单号
		if(orderItem.getCreateTime()==null){
			orderItem.setCreateTime(new Timestamp(System.currentTimeMillis()));
		}
		int ret = this.orderItemDao.insert(orderItem);
		
		return ret > 0;
	}
	
	/**
	 * 更新订单条目信息
	 * @param condition
	 * @param orderItem
	 * @return true--更新成功 false--更新失败
	 */
	public Integer updateOrderItem(Map condition,OrderItem orderItem){
		if(null == orderItem){
			return 0;
		}
		
		return this.orderItemDao.update(condition, orderItem);
	}
	
	/**
	 * 更新指定的订单条目 orderItem.id 非空，否则拒绝更新
	 * @param orderItem
	 * @return true--更新成功,false--更新失败
	 */
	public Integer updateOrderItem(OrderItem orderItem){
		if(null == orderItem || StringUtils.isBlank(orderItem.getId())){
			return 0;
		}
		
		Map condition = new HashMap();
		condition.put("id", orderItem.getId());
		
		return this.orderItemDao.update(condition, orderItem);
	}
	
	/**
	 * 根据id查找订单条目
	 * @param id
	 * @return
	 */
	public OrderItem getOrderItemById(String id){
		if(StringUtils.isBlank(id)){
			return null;
		}
		
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("id", id);
		
		return this.orderItemDao.getById(id);
	}
	
	/**
	 * 根据orderId及prctId查找订单条目，唯一对应一个条目
	 * @param id
	 * @return
	 */
	public OrderItem getOrderItemByOrderIdAndPrctId(String orderId,Long prctId){
		if(StringUtils.isBlank(orderId)){
			return null;
		}
		if(null == prctId){
			return null;
		}
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("orderId", orderId);
		condition.put("prctId", prctId);
		List<OrderItem> orderItemList = this.orderItemDao.queryOrderItemList(condition);
		return orderItemList==null?null:orderItemList.get(0);
	}
	
	/**
	 * 订单明细列表查询 不分页
	 * @param condition
	 * @return
	 */
	public List<OrderItem> queryOrderItemList(Map condition){
		return this.orderItemDao.queryOrderItemList(condition);
	}
	
	/**
	 * 订单条目列表查询 分页
	 * @param condition
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
//	public Page<OrderItem> pagedQuery(Map condition,Integer pageSize,Integer pageNo){
//		return this.orderItemDao.pagedQuery(condition, pageSize, pageNo);
//	}
	
	/**
	 * 查找某一个小订单下的所有条目信息 不分页
	 * @param orderId
	 * @return
	 */
	public List<OrderItem> queryByOrderId(String orderId){
		if(StringUtils.isBlank(orderId)){
			return null;
		}
		
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("orderId", orderId);
		
		return this.orderItemDao.queryOrderItemList(condition);
	}
	
	/**
	 * 查找指定用户所有购买的商品列表 不分页
	 * @param buyAccountId
	 * @return
	 */
	public List<OrderItem> queryByBuyAccountId(String buyAccountId){
		if(StringUtils.isBlank(buyAccountId)){
			return null;
		}
		
		Map<String,Object> condition = new HashMap<String,Object>();
		condition.put("buyAccountId", buyAccountId);
		
		return this.orderItemDao.queryOrderItemList(condition);
	}
	
	/**
	 * 查找指定用户所有购买的商品列表 分页
	 * @param buyAccountId
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
//	public Page<OrderItem> pagedQueryByBuyAccountId(String buyAccountId,Integer pageSize,Integer pageNo){
//		Map<String,Object> condition = new HashMap<String,Object>();
//		condition.put("buyAccountId", buyAccountId);
//		
//		return this.orderItemDao.pagedQuery(condition, pageSize, pageNo);
//	}
	
	/**
	 * 根据大订单号查找用户一次购买的所有商品列表
	 * @param gorderId
	 * @return
	 */
	public List<OrderItem> queryItemListByGorderid(String gorderId){
		if(StringUtils.isBlank(gorderId)){
			return null;
		}
		
		List<OrderItem> orderItemList = this.orderItemDao.queryItemListByGorderId(gorderId);
		
		return orderItemList;
	}

  public OrderItem queryOrderItemDetail(Map<String, Object> inputParam,
      Map<String, Object> outputParam) {
    OrderItem orderItem = this.orderItemDao.queryOrderItemDetail(inputParam,outputParam);

    return orderItem;
  }

  public List<OrderItem> queryOrderItemList(Map condition, Map output) {
    List<OrderItem> orderItemList = this.orderItemDao.queryOrderItemList(condition,output);
    return orderItemList;
  }
	
}
