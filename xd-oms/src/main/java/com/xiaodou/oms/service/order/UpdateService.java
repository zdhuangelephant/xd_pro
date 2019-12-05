package com.xiaodou.oms.service.order;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.oms.constant.order.OrderConstant;
import com.xiaodou.oms.dao.order.OrderDao;
import com.xiaodou.oms.dao.order.OrderItemDao;
import com.xiaodou.oms.entity.order.Gorder;
import com.xiaodou.oms.entity.order.Order;
import com.xiaodou.oms.entity.order.OrderItem;
import com.xiaodou.oms.entity.order.StatusLog;


/**
 * 危险方法！禁止Action直接访问
 * 
 * @author Administrator
 * 
 */
public class UpdateService {
  @Resource
  OrderDao orderDao = null;// 订单的数据访问接口
  @Resource
  QueryService queryService;
  @Resource
  OrderItemDao orderItemDao;


  /**
   * 写订单状态变化日志
   * 
   * @param orderId 订单ID
   * @param orderStatus 订单状态（变化后的）
   * @param logIp ip地址
   * @param note 备注
   * @param note 用户ID
   * @return
   */
  public Integer writeStatusLog(String orderId, Integer orderStatus, String logIp, String note,
      String buyAccountId) {
    StatusLog statusLog = new StatusLog();

    statusLog.setLogIp(logIp);
    statusLog.setLogTime(new Timestamp(System.currentTimeMillis()));
    statusLog.setNote(note);
    statusLog.setOrderId(orderId);
    statusLog.setOrderStatus(orderStatus);
    statusLog.setBuyAccountId(buyAccountId);
    return orderDao.insertStatusLog(statusLog);
  }

  public void updateOrder(Order order, String note, String remoteIp) {
    Map condition = new HashMap();
    if (null == order || null == order.getId() || "".equalsIgnoreCase(order.getId())) {
      OrderAlerter.alert("更新订单所需ID信息为空，请检查参数！");
      throw new RuntimeException();
    }
    condition.put("id", order.getId());

    updateOrder(condition, order, note, remoteIp);
  }


  public void updateOrderNote(String orderId, String note) {
    Order order = new Order();
    order.setNote(note);
    Map condition = new HashMap();
    condition.put("id", orderId);
    orderDao.updateOrder(condition, order);

  }

  public void updateOrderRealtionId(String orderId, String relationId) {
    Order order = new Order();
    order.setRelationId(relationId);
    Map condition = new HashMap();
    condition.put("id", orderId);
    orderDao.updateOrder(condition, order);
  }


  public Integer updateOrder(Order order, String note, List<Integer> orderStatusList,
      String remoteIp) {
    Map condition = new HashMap();
    if (null == order || null == order.getId() || "".equalsIgnoreCase(order.getId())) {
      OrderAlerter.alert("更新订单所需ID信息为空，请检查参数！");
      throw new RuntimeException();
    }
    condition.put("id", order.getId());
    condition.put("listOrderStatus", orderStatusList);

    return updateOrder(condition, order, note, remoteIp);
  }

  private Integer updateOrder(Map<String, Object> condition, Order order, String note,
      String remoteIp) {
    List<Order> listOrder = null;
    String orderId = (String) condition.get("id");

    order.setRemark(note);

    order.setUpdateTime(new Timestamp(System.currentTimeMillis()));

    Integer ret = orderDao.updateOrder(condition, order);

    // 指定了订单id,直接写log表
    if (null != orderId && !"".equalsIgnoreCase(orderId)) {
      if (remoteIp == null) {
        String serverIp = CommUtil.getServerIp();
        remoteIp = StringUtils.isNotEmpty(serverIp) ? serverIp : "127.0.0.1";
      }
      if (order.getOrderStatus() != null) {
        writeStatusLog(order.getId(), order.getOrderStatus(), remoteIp, note, null);
      }
    } else if (null == orderId || "".equalsIgnoreCase(orderId)) {
      // 没有指定订单id, 根据查询到的订单id写log表
      listOrder = queryService.queryOrderList(condition);
      for (int i = 0; i < listOrder.size(); i++) {
        order = listOrder.get(i);
        if (order.getOrderStatus() != null) {
          writeStatusLog(order.getId(), order.getOrderStatus(), order.getOrderIp(), note,
              order.getBuyAccountId());
        }
      }
    }
    return ret;
  }

  /**
   * 更新订单状态
   * 
   * @param order
   * @param note
   */
  public void updateOrderStatus(Order order, String note, String remoteIp) {

    updateOrder(order, note, remoteIp);
    // /**通知所有订单状态监听模块，当前只监听发货成功2及发货失败状态*/
    // if(OrderConstant.STATUS_DELIVERED.equals(order.getOrderStatus())
    // ||OrderConstant.STATUS_PAYSUCCESS.equals(order.getOrderStatus())
    // ||OrderConstant.STATUS_REFUND.equals(order.getOrderStatus())
    // ||OrderConstant.STATUS_HALF_DELIVERED.equals(order.getOrderStatus())
    // ||OrderConstant.STATUS_CLOSED.equals(order.getOrderStatus())
    // ||OrderConstant.STATUS_LOCKED.equals(order.getOrderStatus())){
    // orderNotifyDispatch.dispatchNotice(order);
    // }
  }

  public Integer updateOrderStatus(Order order, String note, List<Integer> orderStautsList,
      String remoteIp) {

    return updateOrder(order, note, orderStautsList, remoteIp);

  }

  /* =============================================================================================== */

  public void updateOrderStatus(Map condition, Order order, String note, String remoteIp) {
    // 付款成功：更新paySuccessTime
    if (OrderConstant.STATUS_PAYSUCCESS.equals(order.getOrderStatus())) {
      order.setPaySuccessTime(new Timestamp(System.currentTimeMillis()));
      // 发货成功：更新successTime
    } else if (OrderConstant.STATUS_DELIVERED.equals(order.getOrderStatus())) {
      if (null == order.getSuccessTime()) {
        order.setSuccessTime(new Timestamp(System.currentTimeMillis()));
      }
      // 交易成功时间：更新tradeSuccessTime
    } else if (OrderConstant.STATUS_TRANSACTIONSUCCESS.equals(order.getOrderStatus())) {
      if (null == order.getSuccessTime()) {
        order.setSuccessTime(new Timestamp(System.currentTimeMillis()));
      }
    }

    updateOrder(condition, order, note, remoteIp);
  }
  

  /**
   *  根据GorderId修改BusAccountID
    * updateBuyAccountIdByGorderId
    * @Title: updateBuyAccountIdByGorderId
    * @Description: TODO
    * @param input
    * @param gorder
    * @return
   */
  public Integer updateBuyAccountIdByGorderId(Map input, Gorder gorder) {
	  Order order = new Order();
	  order.setBuyAccountId(gorder.getBuyAccountId());
    return orderDao.updateBuyAccountIdByGorderId(input, order);
  }


  /***********************************
   * 与状态相关的方法 end *
   ***********************************/

  public boolean closeOrder(Order order, String ip, String note, List<Integer> orderStatusList,
      String closedReason, boolean isNeedCloseOrderItem) {
    if (isNeedCloseOrderItem) {
      Map condition = new HashMap();
      condition.put("orderId", order.getId());
      OrderItem item = new OrderItem();
      item.setItemStatus(OrderConstant.STATUS_CLOSED);
      item.setUpdateTime(new Timestamp(System.currentTimeMillis()));
      orderItemDao.update(condition, item);
    }
    order.setOrderStatus(OrderConstant.STATUS_CLOSED);
    order.setClosedReason(closedReason);
    Integer ret = updateOrderStatus(order, note, orderStatusList, ip);
    return ret > 0 ? true : false;

  }
  
  public void updateItemNote(String orderItemId, String note) {
    OrderItem item = new OrderItem();
    item.setNote(note);
    Map condition = new HashMap();
    condition.put("id", orderItemId);
    orderItemDao.update(condition, item);
  }

}
