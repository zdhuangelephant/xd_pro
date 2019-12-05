package com.xiaodou.oms.service.task.thread;

import java.sql.Timestamp;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.log.model.BatchProcessEntity;
import com.xiaodou.oms.entity.order.PayRecord;
import com.xiaodou.oms.entity.order.PayRequest;
import com.xiaodou.oms.service.facade.OrderServiceFacade;
import com.xiaodou.payment.constant.PaymentStatus;
import com.xiaodou.payment.out.PaymentService;
import com.xiaodou.payment.vo.CreditCardInfo;
import com.xiaodou.payment.vo.OrderInfo;
import com.xiaodou.payment.vo.PayMerchant;
import com.xiaodou.payment.vo.input.RefundPojo;
import com.xiaodou.payment.vo.response.PayResponse;
import com.xiaodou.payment.vo.response.RefundResponse;
import com.xiaodou.payment.vo.response.TokenResponse;

/**
 * 批处理向payment发送付款/退款请求
 * <p/>
 * Date: 2014/7/23 Time: 15:32
 * 
 * @author Tian.Dong
 */
public class SendRequestThread implements Runnable {

  OrderServiceFacade orderServiceFacade;
  PayRequest payRequest;

  public SendRequestThread(OrderServiceFacade orderServiceFacade, PayRequest payRequest) {
    this.orderServiceFacade = orderServiceFacade;
    this.payRequest = payRequest;
  }

  @Override
  public void run() {
    try {
      sendPaymentRequest();
    } catch (Exception e) {
      LoggerUtil.error("批处理发送payRequest请求异常", e);
    }
  }

  /**
   * 向payment发送请求
   */
  private void sendPaymentRequest() {
    String gorderId = payRequest.getGorderId();
    String productLine = payRequest.getProductType().substring(0, 2);

    if (payRequest.getPayNo() == null) {
      // 获取token
      TokenResponse tokenResponse = PaymentService.getToken(productLine);
      if (!tokenResponse.getRetcode().equals("0")) {
        BatchProcessEntity batchProcessEntity = new BatchProcessEntity();
        batchProcessEntity.setTargetClass("SendRequestThread");
        batchProcessEntity.setTargetMethod("sendPaymentRequest获取token");
        batchProcessEntity.setParams("gorderId:" + payRequest.getGorderId());
        batchProcessEntity.setContent("未获取到token");
        LoggerUtil.batchProcessInfo(batchProcessEntity);
        return;
      }
      payRequest.setPayNo(tokenResponse.getTradeNo());
      PayRequest conditon = new PayRequest();
      conditon.setId(payRequest.getId());
      orderServiceFacade.updatePayRequest(conditon, payRequest);
      return;
    }
    if (payRequest.getOperType().equals(PayRequest.OPER_TYPE_PAY)) {
      sendPayRequest(gorderId, productLine);
    } else if (payRequest.getOperType().equals(PayRequest.OPER_TYPE_REFUND)) {
      sendRefundRequest(gorderId, productLine);
    }
  }

  /**
   * 发送支付请求
   * 
   * @param gorderId 大订单号
   * @param productLine 产品线
   */
  public void sendPayRequest(String gorderId, String productLine) {
    // 付款
    OrderInfo order = new OrderInfo();
    order.setOrderFrom("Android");
    order.setOrderId(gorderId);
    order.setPayFrom(20000007);
    order.setTotalAmt(payRequest.getAmount().doubleValue());
    order.setNotifyUrl(PayMerchant.getCallbackHost() + payRequest.getCallbackUrl());
    CreditCardInfo creditCard = new CreditCardInfo();
    creditCard.setAmt(payRequest.getAmount().doubleValue());
    creditCard.setServiceAmt(0.00f);
    creditCard.setCurrentcy(PayMerchant.getCurrency());
    PayResponse payResponse =
        PaymentService.twicePay(productLine, payRequest.getPayNo(), order,
            payRequest.getPrePayNo(), creditCard);

    PayRecord payRecord = createPayRecord(payRequest);
    if (!PaymentStatus.REP_OKCODE.equals(payResponse.getRetcode())) {
      // paymentagent返回的状态码不是0
      return;
    }
    orderServiceFacade.movePayRequestToRecord(payRequest, payRecord);
  }



  /**
   * 发送退款请求
   * 
   * @param gorderId 大订单号
   * @param productLine 产品线
   */
  public void sendRefundRequest(String gorderId, String productLine) {
    // 退款
    RefundPojo refundPojo = new RefundPojo();
    refundPojo.setNotifyUrl(PayMerchant.getCallbackHost() + payRequest.getCallbackUrl());
    refundPojo.setToken(payRequest.getPayNo());
    refundPojo.setAmt(payRequest.getAmount().doubleValue());
    refundPojo.setGorderId(gorderId);
    refundPojo.setProductLine(productLine);
    RefundResponse refundResponse = PaymentService.refund(refundPojo);

    PayRecord payRecord = createPayRecord(payRequest);
    if (!PaymentStatus.REP_OKCODE.equals(refundResponse.getRetcode())) {
      // payment-agent返回不是0 报警
      // TODO 报警
      // AlarmEntity entity = new AlarmEntity(AlarmEntityType.PAYMENT_REFUND_ERROR.getType());
      // entity.setUrl(
      // CommUtil.getServerName(),
      // "[批处理退款]",
      // FastJsonUtil.toJson(payRequest),
      // refundResponse.getRepCode(),
      // refundResponse.getErrorInfo()
      // );
      // LoggerUtil.alarmInfo(entity);
      // 如果返回交易重复，有可能上次发送超时但已经处理，也按正常移走
      if (refundResponse.getMessage().contains("交易重复")) {
        orderServiceFacade.movePayRequestToRecord(payRequest, payRecord);
      }
      return;
    }
    orderServiceFacade.movePayRequestToRecord(payRequest, payRecord);
  }

  /**
   * 用payRequest创建payRecord
   * 
   * @param payRequest
   * @return
   */
  private PayRecord createPayRecord(PayRequest payRequest) {
    PayRecord payRecord = new PayRecord();
    payRecord.setId(payRequest.getId());
    payRecord.setProcessDays(payRequest.getProcessDays());
    payRecord.setPayStatus(PayRecord.PAY_STATUS_PROCESSING);
    payRecord.setSentTime(new Timestamp(System.currentTimeMillis()));
    payRecord.setFailureReason(payRequest.getFailureReason());
    payRecord.setPayNo(payRequest.getPayNo());
    return payRecord;
  }
}
