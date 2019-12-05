package com.xiaodou.oms.statemachine;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.oms.BaseSpringTest;
import com.xiaodou.oms.entity.order.Gorder;
import com.xiaodou.oms.entity.order.Order;
import com.xiaodou.oms.entity.order.OrderItem;
import com.xiaodou.oms.service.oms.OmsOrderService;
import com.xiaodou.oms.statemachine.param.CoreParams;
import com.xiaodou.oms.statemachine.param.PayParam;
import com.xiaodou.oms.vo.input.order.FirePojo;

/**
 * Created by zyp on 14-7-10.
 */
public class ServiceAdapterTest extends BaseSpringTest {

  @Resource
  ServiceAdapter serviceAdapter;

  @Test
  public void testGorderPayCallback() throws Exception {

  }

  @Test
  public void testUpdatePayRecord() throws Exception {

  }

  @Test
  public void testCreatePayRequest() throws Exception {

  }

  @Test
  public void testCreatePayRequestFromOrder() throws Exception {

  }

  @Test
  public void testUpdateOrderStatus() throws Exception {

  }

  @Test
  public void testUpdateOrderStatusAndMerchantOrderId() throws Exception {

  }

  @Test
  public void testUpdateOrderStatusAndMerchantInfo() throws Exception {

  }

  @Test
  public void testAddOrderNote() throws Exception {

  }

  @Test
  public void testUpdateOrderItemStatusAndMisc() throws Exception {

  }

  @Test
  public void testUpdateOrderItemStatusAndMerchantInfo() throws Exception {

  }

  @Test
  public void testUpdateOrderItemStatus() throws Exception {

  }

  @Test
  public void testUpdateOrderMisc() throws Exception {

  }

  @Test
  public void testUpdateOrderSuccessTime() throws Exception {

  }

  @Test
  public void testCloseGorder() throws Exception {

  }

  @Test
  public void testUpdateOrderItemRefundAmount() throws Exception {
    //
    String json = "{\"refundAmount\":148.500,\"okAmount\":148.50}";
    OrderItem orderItem = FastJsonUtil.fromJson(json, OrderItem.class);
    BigDecimal updateRefundAmount = orderItem.getRefundAmount();

    if (updateRefundAmount.compareTo(orderItem.getOkAmount()) > 0) {
      System.out.println(">0");
    }
    System.out.println(updateRefundAmount.compareTo(orderItem.getOkAmount()));
  }
    
    /*@Test
    public void testUpdateOrderRefundAmount() throws Exception{
      Order order = new Order();
      order.setId("20150312666666");
      order.setRefundAmount(new BigDecimal("48.5"));
      serviceAdapter.updateOrderRefundAmount(order);
    }*/

  @Resource
  OmsOrderService omsOrderService;

  @Test
  public void testFire() throws Exception {
    FirePojo pojo = new FirePojo();
    pojo.setProductLine("12");
    CoreParams coreParams = new CoreParams();
    coreParams.setProductLine("12");
    coreParams.setEvent("RefundRequest");
    coreParams.setOrderId("20150311224404647");
    coreParams.setIp(CommUtil.getServerIp());
    PayParam payParam = new PayParam();
    payParam.setAmount(new BigDecimal("1"));
    payParam.setOperType(1);
    coreParams.setPayParam(payParam);
    Order orderUpdateInfo = new Order();
    orderUpdateInfo.setId("20150311224404647");
    orderUpdateInfo.setRefundAmount(payParam.getAmount());
    coreParams.setOrderUpdateInfo(orderUpdateInfo);
    Map<String, Object> otherParams = new HashMap<String, Object>();
    otherParams.put("id", "1");
    otherParams.put("tag", "82c53287-da62-456b-a3e4-071e5b426e36");
    pojo.setCoreParams(coreParams);
    pojo.setOtherParams(otherParams);

    //omsOrderService.fireEvent(pojo);
  }

}