package com.xiaodou.oms.statemachine;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.oms.constant.order.OrderConstant;
import com.xiaodou.oms.entity.order.Gorder;
import com.xiaodou.oms.entity.order.Order;
import com.xiaodou.oms.entity.order.OrderItem;
import com.xiaodou.oms.entity.order.PayRecord;
import com.xiaodou.oms.entity.order.PayRequest;
import com.xiaodou.oms.exception.OrderException;
import com.xiaodou.oms.exception.ValidationException;
import com.xiaodou.oms.service.facade.OrderServiceFacade;
import com.xiaodou.oms.statemachine.param.PayParam;
import com.xiaodou.oms.util.IDGenerator;

/**
 * @Title:ServiceAdapter.java
 * @Description:状态机和业务逻辑层之间的适配器，负责接收状态机传来的参数，调用具体的业务逻辑层的代码。
 * @author zhaoyang
 * @date June 25, 2014 1:33:35 PM
 * @version V1.0
 */
public class ServiceAdapter {

  @Resource
  OrderServiceFacade orderServiceFacade;

  /**
   * 支付回调处理订单逻辑：修改大小订单状态、支付成功时间及记日志。
   * 
   * @param payStatus 1 支付成功；-1 支付失败
   * @param gorderId
   * @param remoteIp
   * @return 更新后的Gorder含支付成功时间
   * @throws Exception
   */
  public Gorder gorderPayCallback(Integer payStatus, String gorderId, String remoteIp)
      throws Exception {
    if (StringUtils.isBlank(remoteIp)) {
      throw new ValidationException(" remoteIp must not be null");
    }
    if (StringUtils.isBlank(gorderId)) {
      throw new ValidationException(" gorderId must not be null");
    }
    if (payStatus == null) {
      throw new ValidationException(" payStatus must not be null");
    }
    if (!OrderConstant.STATUS_PAYSUCCESS.equals(payStatus)
        && !OrderConstant.STATUS_PAYFAILURE.equals(payStatus)) {
      throw new ValidationException(" payStatus must be 1 or -1");
    }
    Gorder gorder = new Gorder();
    gorder.setId(gorderId);
    return orderServiceFacade.gorderPayCallback(payStatus, gorder, remoteIp);

  }

  /**
   * 支付回调处理支付记录逻辑：修改支付状态，填充支付失败原因、支付回调时间
   * 
   * @param payNo
   * @param payRecordStatus
   * @param failureReason
   * @throws Exception
   */
  public void updatePayRecord(String payNo, Integer payRecordStatus, String failureReason)
      throws Exception {

    if (StringUtils.isBlank(payNo)) {
      throw new ValidationException(" payNo must not be null");
    }
    if (payRecordStatus == null) {
      throw new ValidationException(" payRecordStatus must not be null");
    }
    if (!PayRequest.PAY_STATUS_SUCCESS.equals(payRecordStatus)
        && !PayRequest.PAY_STATUS_FAILURE.equals(payRecordStatus)) {
      throw new ValidationException(" payRecordStatus must be 2 or -1");
    }
    orderServiceFacade.paymentCallback(payNo, payRecordStatus,
        new Timestamp(System.currentTimeMillis()), failureReason, null);
  }

  /**
   * 修改payRecord的支付金额
   * 
   * @param gorderId
   * @param amount
   * @throws Exception
   */
  public void updatePayRecordAmount(String gorderId, BigDecimal amount) throws Exception {

    if (StringUtils.isBlank(gorderId)) {
      throw new ValidationException(" gorderId must not be null");
    }
    if (amount == null) {
      throw new ValidationException(" amount must not be null");
    }
    PayRecord cond = new PayRecord();
    cond.setGorderId(gorderId);
    cond.setOperType(PayRecord.OPER_TYPE_PAY);
    PayRecord entity = new PayRecord();
    entity.setAmount(amount);
    orderServiceFacade.updatePayRecord(cond, entity);
  }

  /**
   * 生成 payrequest记录
   * 
   * @param payParam payParam
   * @param payCallbackUrl 回调url
   * @param order
   * @throws Exception
   */
  public void createPayRequest(PayParam payParam, String payCallbackUrl, Order order)
      throws Exception {

    if (payParam == null) {
      throw new ValidationException("payParam is null");
    }

    if (payParam.getAmount() == null) {
      throw new ValidationException("amount value is illegal");
    }

    if (payParam.getAmount().compareTo(new BigDecimal("0")) < 0) {
      throw new ValidationException("amount value is illegal");
    }

    if (payParam.getAmount().compareTo(new BigDecimal("0")) == 0) {
      return;
    }

    if (payParam.getOperType() == null) {
      throw new ValidationException(" operType must not be null");
    }
    if (payParam.getOperType() != 1 && payParam.getOperType() != -1) {
      throw new ValidationException(" operType must not be 1 or 0");
    }
    payParam.setUuid(UUID.randomUUID().toString());
    PayRecord queryParam = new PayRecord();
    queryParam.setGorderId(order.getGorderId());
    queryParam.setOperType(PayRecord.OPER_TYPE_PAY);
    PayRecord payRecord = orderServiceFacade.queryLastPayNo(order.getGorderId());

    if (payRecord == null) {
      throw new ValidationException("上一条payRecord记录找不到，gorderId=" + order.getGorderId());
    }
    PayRequest payRequest = new PayRequest();
    payRequest.setId(IDGenerator.getSeqID("xd_order_sequence_id"));
    payRequest.setAmount(payParam.getAmount());
    payRequest.setBuyAccountId(order.getBuyAccountId());
    payRequest.setGorderId(order.getGorderId());
    payRequest.setOrderId(order.getId());
    payRequest.setOperType(payParam.getOperType());
    payRequest.setPayType(PayRequest.PAY_TYPE_ONE);
    payRequest.setProductType(payRecord.getProductType());
    payRequest.setPayStatus(PayRequest.PAY_STATUS_WAIT);
    payRequest.setPrePayNo(payRecord.getPayNo());
    payRequest.setClientType(payRecord.getClientType());
    payRequest.setOuterOrigin(payRecord.getOuterOrigin());
    payRequest.setCallbackUrl(payCallbackUrl);
    payRequest.setUuid(payParam.getUuid());
    payRequest.setRemark(payParam.getRemark());
    orderServiceFacade.addPayRequest(payRequest);
  }

  /**
   * 对订单做退款，退款金额为订单支付金额
   * 
   * @param payCallbackUrl
   * @param order
   * @throws Exception
   */
  public void createPayRequestFromOrder(PayParam payParam, String payCallbackUrl, Order order)
      throws Exception {

    if (order == null || order.getPayAmount() == null) {
      throw new ValidationException("amount value is illegal");
    }

    if (payParam == null) {
      throw new ValidationException("payParam is null");
    }

    payParam.setUuid(UUID.randomUUID().toString());

    PayRecord payRecord = orderServiceFacade.queryLastPayNo(order.getGorderId());

    if (payRecord == null) {
      throw new ValidationException("上一条payRecord记录找不到，gorderId=" + order.getGorderId());
    }
    PayRequest payRequest = new PayRequest();
    payRequest.setId(IDGenerator.getSeqID("xd_order_sequence_id"));
    payRequest.setAmount(order.getPayAmount());
    payRequest.setBuyAccountId(order.getBuyAccountId());
    payRequest.setGorderId(order.getGorderId());
    payRequest.setOrderId(order.getId());
    payRequest.setOperType(PayRequest.OPER_TYPE_REFUND);
    payRequest.setPayType(PayRequest.PAY_TYPE_ONE);
    payRequest.setProductType(payRecord.getProductType());
    payRequest.setPayStatus(PayRequest.PAY_STATUS_WAIT);
    payRequest.setPrePayNo(payRecord.getPayNo());
    payRequest.setClientType(payRecord.getClientType());
    payRequest.setOuterOrigin(payRecord.getOuterOrigin());
    payRequest.setCallbackUrl(payCallbackUrl);
    payRequest.setUuid(payParam.getUuid());
    orderServiceFacade.addPayRequest(payRequest);
  }

  /**
   * 更新订单状态及记日志
   * 
   * @param orderId
   * @param ip
   * @param toState
   * @throws Exception
   */
  public void updateOrderStatus(String orderId, String ip, Integer toState) throws Exception {
    if (StringUtils.isBlank(orderId)) {
      throw new ValidationException(" orderId must not be null");
    }
    if (StringUtils.isBlank(ip)) {
      throw new ValidationException(" ip must not be null");
    }

    Order order = new Order();
    order.setId(orderId);
    order.setOrderStatus(toState);
    orderServiceFacade.updateOrder(order, null, ip);
  }

  /**
   * 更新关单原因
   * 
   * @param orderId
   * @param ip
   * @param closedReason
   * @throws Exception
   */
  public void updateOrderClosedReason(String orderId, String ip, String closedReason)
      throws Exception {
    if (StringUtils.isBlank(orderId)) {
      throw new ValidationException(" orderId must not be null");
    }
    if (StringUtils.isBlank(ip)) {
      throw new ValidationException(" ip must not be null");
    }
    if (StringUtils.isBlank(closedReason)) {
      throw new ValidationException(" reason must not be null");
    }
    Order order = new Order();
    order.setId(orderId);
    order.setClosedReason(closedReason);
    orderServiceFacade.updateOrder(order, null, ip);
  }

  /**
   * 更新订单状态、供应商订单号及记日志
   * 
   * @param orderId
   * @param ip
   * @param toState
   * @throws Exception
   */
  public void updateOrderStatusAndMerchantOrderId(String orderId, String ip,
      String merchantOrderId, Integer toState) throws Exception {
    if (StringUtils.isBlank(orderId)) {
      throw new ValidationException(" orderId must not be null");
    }
    if (StringUtils.isBlank(ip)) {
      throw new ValidationException(" ip must not be null");
    }

    if (StringUtils.isBlank(merchantOrderId)) {
      throw new ValidationException(" merchantOrderId must not be null");
    }

    Order order = new Order();
    order.setId(orderId);
    order.setMerchantOrderId(merchantOrderId);
    order.setOrderStatus(toState);
    orderServiceFacade.updateOrder(order, null, ip);
  }

  /**
   * 更新订单状态及misc字段
   * 
   * @param orderId
   * @param ip
   * @param toState
   * @throws Exception
   */
  public void updateOrderStatusAndMerchantInfo(String orderId, String ip, Order orderUpdateInfo,
      Integer toState) throws Exception {
    if (StringUtils.isBlank(orderId)) {
      throw new ValidationException(" orderId must not be null");
    }
    if (StringUtils.isBlank(ip)) {
      throw new ValidationException(" ip must not be null");
    }

    if (orderUpdateInfo == null || StringUtils.isBlank(orderUpdateInfo.getMisc())) {
      throw new ValidationException(" orderUpdateInfo.misc must not be null");
    }

    Order order = new Order();
    order.setId(orderId);
    order.setOrderStatus(toState);
    order.setMisc(orderUpdateInfo.getMisc());
    orderServiceFacade.updateOrder(order, null, ip);

  }

  /**
   * 更新订单的note字段
   * 
   * @param orderId
   * @param orderUpdateInfo
   * @throws Exception
   */
  public void addOrderNote(String orderId, Order orderUpdateInfo) throws Exception {
    if (StringUtils.isBlank(orderId)) {
      throw new ValidationException(" orderId must not be null");
    }

    orderServiceFacade.updateOrderNote(orderId, orderUpdateInfo.getNote());

  }

  /**
   * 更新itemMisc
   * 
   * @param orderId
   * @param orderItemList
   * @throws Exception
   */
  public void updateOrderItemMisc(String orderId, List<OrderItem> orderItemList) throws Exception {
    if (StringUtils.isBlank(orderId)) {
      throw new ValidationException(" orderId must not be null");
    }

    OrderItem item = new OrderItem();
    Map codition = new HashMap();
    codition.put("orderId", orderId);

    for (OrderItem orderItem : orderItemList) {
      if (StringUtils.isBlank(orderItem.getMisc())) {
        throw new ValidationException(" orderItem misc must not be null");
      }

      codition.put("id", orderItem.getId());
      item.setMisc(orderItem.getMisc());
      item.setUpdateTime(new Timestamp(System.currentTimeMillis()));
      orderServiceFacade.updateOrderItem(codition, item);
    }
  }

  /**
   * 更新item 供应商订单号
   * 
   * @param orderId
   * @param orderItemList
   * @throws Exception
   */
  public void updateOrderItemMerchantPrctId(String orderId, List<OrderItem> orderItemList)
      throws Exception {
    if (StringUtils.isBlank(orderId)) {
      throw new ValidationException(" orderId must not be null");
    }

    OrderItem item = new OrderItem();
    Map codition = new HashMap();
    codition.put("orderId", orderId);

    for (OrderItem orderItem : orderItemList) {
      if (StringUtils.isBlank(orderItem.getMerchantPrctId())) {
        throw new ValidationException(" orderItem MerchantPrctId must not be null");
      }

      codition.put("id", orderItem.getId());
      item.setMerchantPrctId(orderItem.getMerchantPrctId());
      item.setUpdateTime(new Timestamp(System.currentTimeMillis()));
      orderServiceFacade.updateOrderItem(codition, item);
    }
  }


  /**
   * 更新orderItem的状态、产品信息（misc字段）
   * 
   * @param orderId
   * @param orderItemList
   * @throws Exception
   */
  public void updateOrderItemStatusAndMisc(String orderId, List<OrderItem> orderItemList)
      throws Exception {

    if (StringUtils.isBlank(orderId)) {
      throw new ValidationException(" orderId must not be null");
    }

    OrderItem item = new OrderItem();
    Map codition = new HashMap();
    codition.put("orderId", orderId);

    for (OrderItem orderItem : orderItemList) {
      if (StringUtils.isBlank(orderItem.getMisc())) {
        throw new ValidationException(" orderItem misc must not be null");
      }

      if (orderItem.getItemStatus() == null) {
        throw new ValidationException(" orderItem itemStatus must not be null");
      }
      codition.put("id", orderItem.getId());
      item.setMisc(orderItem.getMisc());
      item.setItemStatus(orderItem.getItemStatus());
      item.setUpdateTime(new Timestamp(System.currentTimeMillis()));
      orderServiceFacade.updateOrderItem(codition, item);
    }

  }

  /**
   * 更新item价格信息
   * 
   * @param orderId
   * @param orderItemList
   * @throws Exception
   */
  public void updateOrderItemUnitPrice(String orderId, List<OrderItem> orderItemList)
      throws Exception {
    if (StringUtils.isBlank(orderId)) {
      throw new ValidationException(" orderId must not be null");
    }

    OrderItem item = new OrderItem();
    Map codition = new HashMap();
    codition.put("orderId", orderId);

    for (OrderItem orderItem : orderItemList) {
      if (orderItem.getItemOrderAmount() == null) {
        throw new ValidationException(" orderItem ItemOrderAmount must not be null");
      }
      if (orderItem.getOriginalItemPayAmount() == null) {
        throw new ValidationException(" orderItem OriginalItemPayAmount must not be null");
      }
      if (orderItem.getItemPayAmount() == null) {
        throw new ValidationException(" orderItem ItemPayAmount must not be null");
      }
      // if (orderItem.getOkAmount()==null){
      // throw new
      // ValidationException(" orderItem okAmount must not be null:itemId "+orderItem.getId());
      // }
      codition.put("id", orderItem.getId());
      // item.setUnitPrice(orderItem.getUnitPrice());
      item.setOkAmount(orderItem.getOkAmount());
      item.setItemOrderAmount(orderItem.getItemOrderAmount());
      item.setOriginalItemPayAmount(orderItem.getOriginalItemPayAmount());
      item.setItemPayAmount(orderItem.getItemPayAmount());
      item.setUpdateTime(new Timestamp(System.currentTimeMillis()));
      orderServiceFacade.updateOrderItem(codition, item);
    }
  }


  /**
   * 更新orderItem的状态、产品信息（misc字段）、成功金额字段
   * 
   * @param orderId
   * @param orderItemList
   * @throws Exception
   */
  public void updateOrderItemStatusAndMerchantInfo(String orderId, List<OrderItem> orderItemList)
      throws Exception {

    if (StringUtils.isBlank(orderId)) {
      throw new ValidationException(" orderId must not be null");
    }

    OrderItem item = new OrderItem();
    Map codition = new HashMap();
    codition.put("orderId", orderId);

    for (OrderItem orderItem : orderItemList) {
      if (StringUtils.isBlank(orderItem.getMisc())) {
        throw new ValidationException(" orderItem misc must not be null");
      }
      if (orderItem.getOkAmount() == null) {
        throw new ValidationException(" orderItem okAmount must not be null");
      }
      if (orderItem.getItemStatus() == null) {
        throw new ValidationException(" orderItem itemStatus must not be null");
      }
      codition.put("id", orderItem.getId());
      item.setOkAmount(orderItem.getOkAmount());
      item.setMisc(orderItem.getMisc());
      item.setItemStatus(orderItem.getItemStatus());
      item.setUpdateTime(new Timestamp(System.currentTimeMillis()));
      orderServiceFacade.updateOrderItem(codition, item);
    }

  }

  /**
   * 更新orderItem的状态
   * 
   * @param orderId
   * @param orderItemList
   * @throws Exception
   */
  public void updateOrderItemStatus(String orderId, List<OrderItem> orderItemList) throws Exception {

    if (StringUtils.isBlank(orderId)) {
      throw new ValidationException(" orderId must not be null");
    }

    OrderItem item = new OrderItem();
    Map codition = new HashMap();
    codition.put("orderId", orderId);

    for (OrderItem orderItem : orderItemList) {
      if (orderItem.getItemStatus() == null) {
        throw new ValidationException(" orderItem itemStatus must not be null");
      }
      codition.put("id", orderItem.getId());
      item.setItemStatus(orderItem.getItemStatus());
      item.setUpdateTime(new Timestamp(System.currentTimeMillis()));
      orderServiceFacade.updateOrderItem(codition, item);
    }

  }

  /**
   * 更新订单产品信息字段misc
   * 
   * @param orderId
   * @param ip
   * @param orderUpdateInfo
   * @throws Exception
   */
  public void updateOrderMisc(String orderId, String ip, Order orderUpdateInfo) throws Exception {
    if (StringUtils.isBlank(orderId)) {
      throw new ValidationException(" orderId must not be null");
    }
    if (StringUtils.isBlank(ip)) {
      throw new ValidationException(" ip must not be null");
    }

    if (orderUpdateInfo == null || StringUtils.isBlank(orderUpdateInfo.getMisc())) {
      throw new ValidationException(" orderUpdateInfo misc must not be null");
    }
    Order order = new Order();
    order.setId(orderId);
    order.setMisc(orderUpdateInfo.getMisc());
    orderServiceFacade.updateOrder(order, null, ip);
  }

  /**
   * 更新订单价格
   * 
   * @param orderId
   * @param ip
   * @param orderUpdateInfo
   * @throws Exception
   */
  public void updateOrderPrice(String orderId, String ip, Order orderUpdateInfo) throws Exception {
    if (StringUtils.isBlank(orderId)) {
      throw new ValidationException(" orderId must not be null");
    }
    if (StringUtils.isBlank(ip)) {
      throw new ValidationException(" ip must not be null");
    }
    if (orderUpdateInfo == null || orderUpdateInfo.getOrderAmount() == null
        || orderUpdateInfo.getOriginalPayAmount() == null || orderUpdateInfo.getPayAmount() == null) {
      throw new ValidationException(" price must not be null");
    }

    Order order = new Order();
    order.setId(orderId);
    order.setOrderAmount(orderUpdateInfo.getOrderAmount());
    order.setOriginalPayAmount(orderUpdateInfo.getOriginalPayAmount());
    order.setPayAmount(orderUpdateInfo.getPayAmount());
    orderServiceFacade.updateOrder(order, null, ip);
  }

  /**
   * 更新gorder价格
   * 
   * @param gorderId
   * @param ip
   * @param gorderUpdateInfo
   * @throws Exception
   */
  public void updateGorderPrice(String gorderId, String ip, Gorder gorderUpdateInfo)
      throws Exception {
    if (StringUtils.isBlank(gorderId)) {
      throw new ValidationException(" gorderId must not be null");
    }
    if (StringUtils.isBlank(ip)) {
      throw new ValidationException(" ip must not be null");
    }
    if (gorderUpdateInfo == null || gorderUpdateInfo.getGorderAmount() == null
        || gorderUpdateInfo.getOriginalGpayAmount() == null
        || gorderUpdateInfo.getGpayAmount() == null) {
      throw new ValidationException(" gorder price must not be null");
    }
    Map<String, Object> cond = new HashMap<String, Object>();
    cond.put("id", gorderId);
    cond.put("gorderAmount", gorderUpdateInfo.getGorderAmount());
    cond.put("originalGpayAmount", gorderUpdateInfo.getOriginalGpayAmount());
    cond.put("gpayAmount", gorderUpdateInfo.getGpayAmount());
    orderServiceFacade.updateGorder(gorderUpdateInfo, ip);
  }


  /**
   * 更新订单完成时间
   * 
   * @param orderId
   * @param ip
   * @param orderParam
   * @throws Exception
   */
  public void updateOrderSuccessTime(String orderId, String ip, Order orderParam) throws Exception {

    if (StringUtils.isBlank(orderId)) {
      throw new ValidationException(" orderId must not be null");
    }
    if (StringUtils.isBlank(ip)) {
      throw new ValidationException(" ip must not be null");
    }

    Timestamp successTime =
        orderParam.getSuccessTime() != null ? orderParam.getSuccessTime() : new Timestamp(
            System.currentTimeMillis());
    Order order = new Order();
    order.setId(orderId);
    order.setSuccessTime(successTime);
    orderServiceFacade.updateOrder(order, null, ip);
  }

  public Boolean closeGorderWithOutRefund(String gorderId, String ip, String closedReason,
      Boolean isNeedCloseOrderItem, Order dbOrder) throws ValidationException, OrderException {
    if (StringUtils.isBlank(gorderId)) {
      gorderId = dbOrder.getGorderId();
    }
    if (StringUtils.isBlank(ip)) {
      throw new ValidationException(" ip must not be null");
    }
    if (StringUtils.isBlank(closedReason)) {
      throw new ValidationException(" orderUpdateInfo misc must not be null");
    }
    if (null == isNeedCloseOrderItem) isNeedCloseOrderItem = Boolean.FALSE;
    Gorder gorder = new Gorder();
    gorder.setId(gorderId);
    return orderServiceFacade.closeGorderWithOutRefund(gorder, ip, "关闭订单，原因：" + closedReason, null,
        closedReason, isNeedCloseOrderItem);
  }

  /**
   * 关闭订单及记日志
   * 
   * @param gorderId
   * @param ip
   * @param closedReason
   * @param isNeedCloseOrderItem
   * @param dbOrder
   * @return
   * @throws ValidationException
   * @throws OrderException
   */
  public Boolean closeGorder(String gorderId, String ip, String closedReason,
      Boolean isNeedCloseOrderItem, Order dbOrder, PayParam payParam) throws ValidationException,
      OrderException {
    if (StringUtils.isBlank(gorderId)) {
      gorderId = dbOrder.getGorderId();
    }
    if (StringUtils.isBlank(ip)) {
      throw new ValidationException(" ip must not be null");
    }
    if (StringUtils.isBlank(closedReason)) {
      throw new ValidationException(" orderUpdateInfo misc must not be null");
    }
    if (null == isNeedCloseOrderItem) isNeedCloseOrderItem = Boolean.FALSE;

    if (payParam == null) {
      throw new ValidationException("payParam is null");
    }

    Gorder gorder = new Gorder();
    gorder.setId(gorderId);
    payParam.setUuid(UUID.randomUUID().toString());
    return orderServiceFacade.closeGorder(gorder, ip, "关闭订单，原因：" + closedReason, null,
        closedReason, isNeedCloseOrderItem, payParam.getUuid());
  }


  /**
   * 更新orderItem退款金额
   * 
   * @param orderItemList
   * @return
   * @throws ValidationException
   */
  public Boolean updateOrderItemRefundAmount(List<OrderItem> orderItemList) throws Exception {

    for (OrderItem orderItem : orderItemList) {

      String orderItemId = orderItem.getId();

      if (orderItem.getRefundAmount() == null) {
        throw new ValidationException("refundAmount不能为空");
      }

      if (orderItem.getRefundAmount().compareTo(BigDecimal.ZERO) < 0) {
        throw new ValidationException("refundAmount不能小于0");
      }

      if (orderItem.getRefundAmount().compareTo(BigDecimal.ZERO) == 0) {
        continue;
      }

      if (orderItem.getId() == null) {
        throw new ValidationException("orderitem id不能为空");
      }

      Map<String, Object> inputParam = new HashMap<String, Object>();
      inputParam.put("id", orderItemId);
      Map<String, Object> outputParam = new HashMap<String, Object>();
      outputParam.put("refundAmount", "1");
      outputParam.put("okAmount", "");
      outputParam.put("id", "1");
      OrderItem updateOrderItem = orderServiceFacade.queryOrderItemDetail(inputParam, outputParam);

      if (updateOrderItem == null) {
        throw new ValidationException("orderitem 不存在");
      }

      BigDecimal updateRefundAmount = orderItem.getRefundAmount();

      if (!(updateOrderItem.getRefundAmount() == null || updateOrderItem.getRefundAmount()
          .compareTo(BigDecimal.ZERO) <= 0)) {
        updateRefundAmount = updateRefundAmount.add(updateOrderItem.getRefundAmount());
      }

      if (updateRefundAmount.compareTo(updateOrderItem.getOkAmount()) > 0) {
        throw new Exception("退款金额不能大于支付金额");
      }

      OrderItem updateInfo = new OrderItem();
      updateInfo.setRefundAmount(updateRefundAmount);
      updateInfo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
      Map<String, Object> updateCodition = new HashMap<String, Object>();
      updateCodition.put("id", orderItemId);
      Integer result = orderServiceFacade.updateOrderItem(updateCodition, updateInfo);
      if (result == 0) {
        throw new Exception("退款金额更新失败");
      }
    }
    return true;
  }

  /**
   * 公共判重方法
   * 
   * @param tag
   * @return
   */
  public String startOperation(String tag) {
    if (orderServiceFacade.queryTagForStart(tag))
      return "success\"retcode\":0";
    else {
      return "\"retcode\":-1";
    }
  }

  /**
   * 公共判重方法
   * 
   * @param tag
   * @return
   */
  public void finishOperation(String tag) {
    orderServiceFacade.updateTagForFinish(tag);
  }

  /**
   * 增加order退款金额
   * 
   * @param order
   * @return
   * @throws ValidationException
   */
  public Boolean updateOrderRefundAmount(Order order) throws Exception {

    String orderId = order.getId();

    if (order.getRefundAmount() == null) {
      throw new ValidationException("refundAmount不能为空");
    }

    if (order.getRefundAmount().compareTo(BigDecimal.ZERO) < 0) {
      throw new ValidationException("refundAmount不能小于0");
    }

    if (order.getRefundAmount().compareTo(BigDecimal.ZERO) == 0) {
      return true;
    }

    if (order.getId() == null) {
      throw new ValidationException("order id不能为空");
    }

    Map<String, Object> inputParam = new HashMap<String, Object>();
    inputParam.put("id", orderId);
    Map<String, Object> outputParam = new HashMap<String, Object>();
    outputParam.put("refundAmount", "1");
    outputParam.put("payAmount", "");
    outputParam.put("id", "1");
    Order updateOrder = orderServiceFacade.queryOrderDetail(inputParam, outputParam);

    if (updateOrder == null) {
      throw new ValidationException("order 不存在");
    }

    BigDecimal updateRefundAmount = order.getRefundAmount();

    if (!(updateOrder.getRefundAmount() == null || updateOrder.getRefundAmount().compareTo(
        BigDecimal.ZERO) <= 0)) {
      updateRefundAmount = updateRefundAmount.add(updateOrder.getRefundAmount());
    }

    if (updateRefundAmount.compareTo(updateOrder.getPayAmount()) > 0) {
      throw new Exception("退款金额不能大于支付金额");
    }

    Order updateInfo = new Order();
    updateInfo.setId(order.getId());
    updateInfo.setRefundAmount(updateRefundAmount);
    updateInfo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
    orderServiceFacade.updateOrder(updateInfo, "add refundAmount:" + order.getRefundAmount(),
        CommUtil.getServerIp());
    return true;
  }

  /**
   * 
   * updateGorderOrderStatusAndMerchantOrderId
   * 
   * @Title: updateGorderOrderStatusAndMerchantOrderId
   * @Description:
   * @param orderId
   * @param ip
   * @param merchantOrderId
   * @param toState
   * @throws Exception
   */
  public void updateGorderOrderStatusAndMerchantOrderId(String gorderId, String orderId, String ip,
      String merchantOrderId, Integer toState) throws Exception {
    if (StringUtils.isBlank(gorderId)) {
      throw new ValidationException(" gorderId must not be null");
    }

    if (StringUtils.isBlank(orderId)) {
      throw new ValidationException(" orderId must not be null");
    }
    if (StringUtils.isBlank(ip)) {
      throw new ValidationException(" ip must not be null");
    }

    if (StringUtils.isBlank(merchantOrderId)) {
      throw new ValidationException(" merchantOrderId must not be null");
    }

    Gorder gorder = new Gorder();
    Map<String, String> condition = new HashMap<String, String>();
    condition.put("id", gorderId);
    gorder.setGorderStatus(toState);
    orderServiceFacade.updateGorder(condition, gorder);

    Order order = new Order();
    order.setId(orderId);
    order.setMerchantOrderId(merchantOrderId);
    order.setOrderStatus(toState);
    orderServiceFacade.updateOrder(order, null, ip);
  }

  public void updateGorderPreCloseTime(String gorderId, String ip, Gorder gorderUpdateInfo) {
    if (StringUtils.isBlank(gorderId)) {
      throw new ValidationException(" gorderId must not be null");
    }
    if (StringUtils.isBlank(ip)) {
      throw new ValidationException(" ip must not be null");
    }
    if (gorderUpdateInfo == null || gorderUpdateInfo.getPreCloseTime() == null) {
      throw new ValidationException(" preCloseTime must not be null");
    }

    Gorder gorder = new Gorder();
    gorder.setId(gorderId);
    gorder.setPreCloseTime(gorderUpdateInfo.getPreCloseTime());
    orderServiceFacade.updateGorder(gorder, ip);
  }

}
