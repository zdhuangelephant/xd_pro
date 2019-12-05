package com.xiaodou.oms.web.controller;
//package com.elong.oms.web.controller;
//
//import com.elong.common.util.log.LoggerUtil;
//import com.elong.common.util.warp.FastJsonUtil;
//import com.elong.oms.BaseSpringTest;
//import com.elong.oms.entity.order.Order;
//import com.elong.oms.entity.order.PayRecord;
//import com.elong.oms.exception.ValidationException;
//import com.elong.oms.service.facade.OrderServiceFacade;
//import com.elong.oms.service.oms.OmsOrderService;
//import com.elong.oms.statemachine.Context;
//import com.elong.oms.statemachine.param.CoreParams;
//import com.elong.oms.statemachine.param.PayParam;
//import com.elong.oms.vo.input.order.FirePojo;
//import com.elong.oms.vo.input.pay.PaymentNotifyPojo;
//import com.elong.oms.vo.result.ResultInfo;
//import com.elong.payment.constant.PaymentStatus;
//import org.junit.Test;
//
//import javax.annotation.Resource;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Date: 2014/7/2
// * Time: 17:30
// *
// * @author Tian.Dong
// */
//public class OmsNotifyControllerTest extends BaseSpringTest {
//
//  @Resource
//  OrderServiceFacade orderServiceFacade;
//
//  @Resource
//  OmsOrderService omsOrderService;
//
//  /**
//   * 一次支付回调状态流转测试
//   * 支付成功
//   */
//  @Test
//  public void testFirstPayNotifySuccess() {
//    PaymentNotifyPojo pojo = new PaymentNotifyPojo();
//    pojo.setMerchant_id("1900");
//    pojo.setSign("sign");
//    pojo.setSign_type("MD5");
//    pojo.setTrade_no("-100000000000111806");
//    pojo.setError_info("");
//    pojo.setPay_time("");
//    pojo.setResult_status(1);
//
//    PayRecord record = null;
//    try {
//      record = orderServiceFacade.queryPayRecordByPayNo(pojo.getTrade_no());
//    } catch (Exception e) {
//      return;
//    }
//    if (record == null) {
//      return;
//    }
//    Context context = new Context();
//    FirePojo firePojo = new FirePojo();
//    CoreParams coreParams = new CoreParams();
//    String productLine = record.getProductType().substring(0, 2);
//    coreParams.setProductLine(productLine);
//    coreParams.setIp("127.0.0.1");
//    if (pojo.getResult_status().equals(PaymentStatus.NOTIFY_OKCODE)) {
//      coreParams.setEvent("PaySuccess");
//    } else {
//      coreParams.setEvent("PayFailure");
//    }
//
//    coreParams.setGorderId(record.getGorderId());
//    coreParams.setOrderId(record.getOrderId());
//    PayParam payParam = new PayParam();
//    payParam.setPayNo(pojo.getTrade_no());
//
//    payParam.setFailureReason(pojo.getError_info());
//    coreParams.setPayParam(payParam);
//
//    firePojo.setCoreParams(coreParams);
//    LoggerUtil.outInInfo("[payment回调]omsOrderService.fireEvent");
//
//
//    try {
//      omsOrderService.fireEvent(firePojo);
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//  }
//
//  /**
//   * 一次支付回调状态流转测试
//   * 支付失败
//   */
//  @Test
//  public void testFirstPayNotifyFail() {
//    PaymentNotifyPojo pojo = new PaymentNotifyPojo();
//    pojo.setMerchant_id("1900");
//    pojo.setSign("sign");
//    pojo.setSign_type("MD5");
//    pojo.setTrade_no("100000000000068911");
//    pojo.setError_info("失败");
//    pojo.setPay_time("");
//    pojo.setResult_status(0);
//
//    Order order = null;
//    try {
//      order = orderServiceFacade.queryOrderByPayNo(pojo.getTrade_no());
//    } catch (ValidationException e) {
//      e.printStackTrace();
//    } catch (Exception e) {
//      e.printStackTrace();
//      return;
//    }
//    if (order == null) {
//      return;
//    }
//    Context context = new Context();
//    FirePojo firePojo = new FirePojo();
//    CoreParams coreParams = new CoreParams();
//    String productLine = order.getProductType().substring(0, 2);
//    coreParams.setProductLine(productLine);
//    coreParams.setIp("127.0.0.1");
//    if (pojo.getResult_status().equals(PaymentStatus.NOTIFY_OKCODE)) {
//      coreParams.setEvent("PaySuccess");
//    } else {
//      coreParams.setEvent("PayFailure");
//    }
//
//    coreParams.setGorderId(order.getGorderId());
//    PayParam payParam = new PayParam();
//    payParam.setPayNo(pojo.getTrade_no());
//
//    payParam.setFailureReason(pojo.getError_info());
//    coreParams.setPayParam(payParam);
//
//    firePojo.setCoreParams(coreParams);
//    LoggerUtil.outInInfo("[payment回调]omsOrderService.fireEvent");
//
//
//    try {
//      omsOrderService.fireEvent(firePojo);
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//  }
//
//  @Test
//  public void testGetOrder() {
//    try {
//      Map output = new HashMap();
//      output.put("productType", "1");
//      Order order = orderServiceFacade.queryOrderDetail("20140522205717505", output);
//      String productLine = order.getProductType().substring(0, 2);
//      System.out.println("***********" + productLine);
//    } catch (Exception e){
//
//    }
//  }
//
//  public void testRefund() {
//    PaymentNotifyPojo pojo = new PaymentNotifyPojo();
//    pojo.setMerchant_id("1900");
//    pojo.setSign("sign");
//    pojo.setSign_type("MD5");
//    pojo.setTrade_no("-100000000000007583");
//    pojo.setPay_time("");
//    pojo.setResult_status(1);
//
//    Order order = null;
//    try {
//      order = orderServiceFacade.queryOrderByPayNo(pojo.getTrade_no());
//    } catch (Exception e) {
//      return;
//    }
//    Context context = new Context();
//    FirePojo firePojo = new FirePojo();
//    CoreParams coreParams = new CoreParams();
//    String productLine = order.getProductType().substring(0, 2);
//    coreParams.setProductLine(productLine);
//    coreParams.setIp("127.0.0.1");
//    if (pojo.getResult_status().equals(PaymentStatus.NOTIFY_OKCODE)) {
//      coreParams.setEvent("RefundSuccess");
//    } else {
//      coreParams.setEvent("RefundFailure");
//    }
//
//    coreParams.setGorderId(order.getGorderId());
//    PayParam payParam = new PayParam();
//    payParam.setPayNo(pojo.getTrade_no());
//
//    payParam.setFailureReason(pojo.getError_info());
//    coreParams.setPayParam(payParam);
//
//    firePojo.setCoreParams(coreParams);
//    try {
//      omsOrderService.fireEvent(firePojo);
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//  }
//
//
//
//
//
//
//
//
//
//
//  /**
//   * 一次支付回调状态流转测试
//   * 支付成功
//   */
//  //@Test
//  public void testOrderReturn() {
//    FirePojo firePojo = new FirePojo();
//    CoreParams coreParams = new CoreParams();
//    String productLine = "05";
//    coreParams.setProductLine(productLine);
//    coreParams.setIp("127.0.0.1");
//    coreParams.setEvent("OrderReturn");
//
//    coreParams.setOrderId("20141209214499344");
//
//
//    firePojo.setCoreParams(coreParams);
//
//    try {
//      ResultInfo resultInfo = omsOrderService.fireEvent(firePojo);
//      System.out.println(FastJsonUtil.toJson(resultInfo));
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//  }
//
//}
