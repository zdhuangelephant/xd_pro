package com.xiaodou.oms.service.oms;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.oms.BaseSpringTest;
import com.xiaodou.oms.entity.order.Gorder;
import com.xiaodou.oms.entity.order.Order;
import com.xiaodou.oms.entity.order.OrderItem;
import com.xiaodou.oms.entity.order.WayBill;
import com.xiaodou.oms.vo.input.Page;
import com.xiaodou.oms.vo.input.order.CreateOrderPojo;
import com.xiaodou.oms.vo.input.order.GorderOrderItemListPojo;
import com.xiaodou.oms.vo.input.order.OrderOrderItemListPojo;
import com.xiaodou.oms.vo.input.order.UpdateMerchantPojo;
import com.xiaodou.oms.vo.result.order.GorderOrderItemListVO;
import com.xiaodou.oms.vo.result.order.OrderOrderItemListVO;

public class OmsOrderServiceTest extends BaseSpringTest {

  @Resource
  OmsOrderService omsOrderService;

   @Test
  public void testCreateGorder() throws Exception {

    Gorder gorder = new Gorder();
    gorder.setGorderTime(new Timestamp(System.currentTimeMillis()));
    gorder.setGpayAmount(new BigDecimal("20"));
    gorder.setGpayAmount(new BigDecimal("20"));
    gorder.setOriginalGpayAmount(new BigDecimal("20"));
    gorder.setGsaveAmount(BigDecimal.ZERO);
    gorder.setGfee(BigDecimal.ZERO);
    gorder.setGorderStatus(1);
    gorder.setGoodsName("艺龙火车票");
    gorder.setBuyAccountId("testzyp");
    gorder.setReceiverPhone("15210582496");
    gorder.setReceiverName("zhangyupeng");
    gorder.setExpectedShippingTime(new Timestamp(System.currentTimeMillis()));
    gorder.setOuterOrigin("外部来源"); // 注意事项
    gorder.setUpdateTime(new Timestamp(System.currentTimeMillis()));
    gorder.setClientType("ios");
    gorder.setProductType("0501");
    gorder.setIsNeedInvoice(1);
    gorder.setGorderIp("127.0.0.1");


    WayBill wayBill = new WayBill();
    wayBill.setProvince("北京");
    wayBill.setCity("北京");
    wayBill.setArea("海淀");
    wayBill.setAddress("北京市海淀区");
    wayBill.setAmount(new BigDecimal("20"));
    wayBill.setBuyAccountId("zyptest");
    wayBill.setContent("保险");
    wayBill.setInvoiceStatus(1);
    wayBill.setPostcode("postcode");
    wayBill.setProductType("0506");
    wayBill.setReceiverName("zyp");
    wayBill.setReceiverPhone("手机号");
    wayBill.setTitle("保险");
    wayBill.setInvoiceItem("保险");
    wayBill.setPostItem(1);

    gorder.setWayBill(wayBill);
    gorder.setPayOrderId("12345678token");

    OrderItem trainOrderItem1 = new OrderItem();
    trainOrderItem1.setProductType("0501");
    trainOrderItem1.setProductName("k12");
    trainOrderItem1.setBuyCount(1);
    trainOrderItem1.setItemOrderAmount(new BigDecimal("20"));
    trainOrderItem1.setOriginalItemPayAmount(new BigDecimal("20"));
    trainOrderItem1.setItemSaveAmount(BigDecimal.ZERO);
    trainOrderItem1.setItemPayAmount(new BigDecimal("20"));
    trainOrderItem1.setItemCostAmount(BigDecimal.ZERO);
    trainOrderItem1.setItemFee(BigDecimal.ZERO);
    trainOrderItem1.setCreateTime(new Timestamp(System.currentTimeMillis()));
    trainOrderItem1.setUpdateTime(new Timestamp(System.currentTimeMillis()));
    trainOrderItem1.setItemStatus(1);
    trainOrderItem1.setMisc("misc");
    trainOrderItem1.setVirtualRelationId("train1");

    OrderItem trainOrderItem2 = new OrderItem();
    trainOrderItem2.setProductType("0501");
    trainOrderItem2.setProductName("k12");
    trainOrderItem2.setBuyCount(1);
    trainOrderItem2.setItemOrderAmount(new BigDecimal("20"));
    trainOrderItem2.setOriginalItemPayAmount(new BigDecimal("20"));
    trainOrderItem2.setItemSaveAmount(BigDecimal.ZERO);
    trainOrderItem2.setItemPayAmount(new BigDecimal("20"));
    trainOrderItem2.setItemCostAmount(BigDecimal.ZERO);
    trainOrderItem2.setItemFee(BigDecimal.ZERO);
    trainOrderItem2.setCreateTime(new Timestamp(System.currentTimeMillis()));
    trainOrderItem2.setUpdateTime(new Timestamp(System.currentTimeMillis()));
    trainOrderItem2.setItemStatus(1);
    trainOrderItem2.setMisc("misc");
    trainOrderItem2.setVirtualRelationId("train2");

    List<OrderItem> trainOrderItemList = new ArrayList<OrderItem>();
    trainOrderItemList.add(trainOrderItem1);
    trainOrderItemList.add(trainOrderItem2);

    Order trainOrder = new Order();
    trainOrder.setProductType("0501"); // 火车产品类型
    trainOrder.setOrderTime(new Timestamp(System.currentTimeMillis())); // 下单时间
    trainOrder.setOrderStatus(1); // 订单状态
    trainOrder.setOrderAmount(new BigDecimal("20")); // 车票总价格
    trainOrder.setSaveAmount(BigDecimal.ZERO); // 节省费用默认0
    trainOrder.setPayAmount(new BigDecimal("20")); // 支付价格默认0
    trainOrder.setFee(BigDecimal.ZERO);
    trainOrder.setOriginalPayAmount(new BigDecimal("20"));
    trainOrder.setCostAmount(BigDecimal.ZERO);
    trainOrder.setBuyAccountId("testuid");
    trainOrder.setUpdateTime(new Timestamp(System.currentTimeMillis()));
    trainOrder.setSettleUp(0);
    trainOrder.setMerchantId(50);
    trainOrder.setMerchantName("新腾火车");
    trainOrder.setMerchantTel("7070707");
    trainOrder.setMisc("misc");
    trainOrder.setStatusDesc("状态");
    trainOrder.setOrderIp("127.0.0.1");
    trainOrder.setOrderItemList(trainOrderItemList);

    OrderItem insuranceOrderItem1 = new OrderItem();
    insuranceOrderItem1.setPrctId(new Long(123));
    insuranceOrderItem1.setMerchantPrctId("123");
    insuranceOrderItem1.setProductType("0601");
    insuranceOrderItem1.setProductName("保险");
    insuranceOrderItem1.setUnitPrice(new BigDecimal("20"));
    insuranceOrderItem1.setBuyCount(1);
    insuranceOrderItem1.setItemOrderAmount(new BigDecimal("20"));
    insuranceOrderItem1.setOriginalItemPayAmount(new BigDecimal("20"));
    insuranceOrderItem1.setItemSaveAmount(BigDecimal.ZERO);
    insuranceOrderItem1.setItemPayAmount(new BigDecimal("20"));
    insuranceOrderItem1.setItemCostAmount(BigDecimal.ZERO);
    insuranceOrderItem1.setItemFee(BigDecimal.ZERO);
    insuranceOrderItem1.setCreateTime(new Timestamp(System.currentTimeMillis()));
    insuranceOrderItem1.setUpdateTime(new Timestamp(System.currentTimeMillis()));


    OrderItem insuranceOrderItem2 = new OrderItem();
    insuranceOrderItem2.setPrctId(new Long(123));
    insuranceOrderItem2.setMerchantPrctId("123");
    insuranceOrderItem2.setProductType("0601");
    insuranceOrderItem2.setProductName("保险");
    insuranceOrderItem2.setUnitPrice(new BigDecimal("20"));
    insuranceOrderItem2.setBuyCount(1);
    insuranceOrderItem2.setItemOrderAmount(new BigDecimal("20"));
    insuranceOrderItem2.setOriginalItemPayAmount(new BigDecimal("20"));
    insuranceOrderItem2.setItemSaveAmount(BigDecimal.ZERO);
    insuranceOrderItem2.setItemPayAmount(new BigDecimal("20"));
    insuranceOrderItem2.setItemCostAmount(BigDecimal.ZERO);
    insuranceOrderItem2.setItemFee(BigDecimal.ZERO);
    insuranceOrderItem2.setCreateTime(new Timestamp(System.currentTimeMillis()));
    insuranceOrderItem2.setUpdateTime(new Timestamp(System.currentTimeMillis()));

    List<OrderItem> insuranceOrderItemList1 = new ArrayList<OrderItem>();
    insuranceOrderItemList1.add(insuranceOrderItem1);

    List<OrderItem> insuranceOrderItemList2 = new ArrayList<OrderItem>();
    insuranceOrderItemList2.add(insuranceOrderItem2);

    Order insuranceOrder1 = new Order();
    insuranceOrder1.setProductType("0601");
    insuranceOrder1.setOrderTime(new Timestamp(System.currentTimeMillis()));
    insuranceOrder1.setOrderStatus(2);
    insuranceOrder1.setOrderAmount(new BigDecimal("20"));
    insuranceOrder1.setSaveAmount(BigDecimal.ZERO);
    insuranceOrder1.setPayAmount(new BigDecimal("20"));
    insuranceOrder1.setFee(BigDecimal.ZERO);
    insuranceOrder1.setOriginalPayAmount(new BigDecimal("20"));
    insuranceOrder1.setCostAmount(BigDecimal.ZERO);
    insuranceOrder1.setBuyAccountId("zyptest");
    insuranceOrder1.setUpdateTime(new Timestamp(System.currentTimeMillis()));
    insuranceOrder1.setSettleUp(0);
    insuranceOrder1.setMerchantId(1);
    insuranceOrder1.setMerchantName("供应商");
    insuranceOrder1.setMerchantTel("404404");
    insuranceOrder1.setStatusDesc("订单状态");
    insuranceOrder1.setOrderIp("127.0.0.1");
    insuranceOrder1.setMisc("misc");
    insuranceOrder1.setOrderItemList(insuranceOrderItemList1);
    insuranceOrder1.setVirtualRelationId("insurance1");


    Order insuranceOrder2 = new Order();
    insuranceOrder2.setProductType("0601");
    insuranceOrder2.setOrderTime(new Timestamp(System.currentTimeMillis()));
    insuranceOrder2.setOrderStatus(2);
    insuranceOrder2.setOrderAmount(new BigDecimal("20"));
    insuranceOrder2.setSaveAmount(BigDecimal.ZERO);
    insuranceOrder2.setPayAmount(new BigDecimal("20"));
    insuranceOrder2.setFee(BigDecimal.ZERO);
    insuranceOrder2.setOriginalPayAmount(new BigDecimal("20"));
    insuranceOrder2.setCostAmount(BigDecimal.ZERO);
    insuranceOrder2.setBuyAccountId("zyptest");
    insuranceOrder2.setUpdateTime(new Timestamp(System.currentTimeMillis()));
    insuranceOrder2.setSettleUp(0);
    insuranceOrder2.setMerchantId(1);
    insuranceOrder2.setMerchantName("供应商");
    insuranceOrder2.setMerchantTel("404404");
    insuranceOrder2.setStatusDesc("订单状态");
    insuranceOrder2.setOrderIp("127.0.0.1");
    insuranceOrder2.setMisc("misc");
    insuranceOrder2.setOrderItemList(insuranceOrderItemList2);
    insuranceOrder2.setVirtualRelationId("insurance2");

    List<Order> orderList = new ArrayList<Order>();
    orderList.add(trainOrder);
    orderList.add(insuranceOrder1);
    orderList.add(insuranceOrder2);

    gorder.setOrderList(orderList);

    CreateOrderPojo pojo = new CreateOrderPojo();
    pojo.setProductLine("0101");
    pojo.setGorder(gorder);
    pojo.setRelationType("3");
    pojo.setRelations("train1:insurance1||train2:insurance2");
    
    System.out.println(FastJsonUtil.toJson(pojo));

//    CreateOrderVO vo = omsOrderService.createGorder(pojo);
//    assert "0".equals(vo.getRetcode());
  }

  // @Test
  public void testChangeMerchant() {
    UpdateMerchantPojo pojo = new UpdateMerchantPojo();
    pojo.setMerchantId(5);
    pojo.setProductLine("05");
    pojo.setOrderId("20140813000000001");
    pojo.setMerchantName("火车票");
    pojo.setMerchantTel("13800138000");
    pojo.setClientIp("127.0.0.1");
    assert "0".equals(omsOrderService.changeMerchant(pojo).getRetcode());
  }

  /**
   * testorderOrderItemList
   * 
   * @Title: testorderOrderItemList
   * @Description: order-orderitem两级联查
   */
  @Test
  public void testorderOrderItemList() {
    OrderOrderItemListPojo pojo = new OrderOrderItemListPojo();
    pojo.setProductLine("11");
    // 设置page
    Page page = new Page();
    page.setPageNo(1);
    page.setPageSize(3);
    pojo.setPage(page);

    /**
     * 设置查询条件
     */
    com.xiaodou.oms.vo.input.order.Order orderQueryParams = new com.xiaodou.oms.vo.input.order.Order();
    orderQueryParams.setProductType("1101");
    pojo.setOrderQueryParams(orderQueryParams);

    /**
     * 设置输出属性
     */
    Map<String, Object> orderoutput = new HashMap<String, Object>();
    orderoutput.put("id", "1");
    pojo.setOrderOutputProperties(orderoutput);
    try {
      OrderOrderItemListVO result = omsOrderService.orderOrderItemList(pojo);
      System.out.println(result.getPage());
    } catch (Exception e) {
      System.out.println("error");
    }
  }

  /**
   * testorderOrderItemList
   * 
   * @Title: testorderOrderItemList
   * @Description: order-orderitem两级联查
   */
  /**
   * 
   */
  @Test
  public void testgorderOrderOrderItemList() {
    GorderOrderItemListPojo pojo = new GorderOrderItemListPojo();
    pojo.setProductLine("05");
    // 设置page
    Page page = new Page();
    page.setPageNo(1);
    page.setPageSize(100);
    pojo.setPage(page);

    /**
     * 设置查询条件
     */
    com.xiaodou.oms.vo.input.order.Gorder gorderQueryParams = new com.xiaodou.oms.vo.input.order.Gorder(); 
    gorderQueryParams.setBuyAccountId("190000032101060709");
    gorderQueryParams.setProductType("0501");
    pojo.setGorderQueryParams(gorderQueryParams);
    com.xiaodou.oms.vo.input.order.Order orderQueryParams = new com.xiaodou.oms.vo.input.order.Order();
    ArrayList<Integer> newArrayList = Lists.newArrayList();
    newArrayList.add(0);
    newArrayList.add(-1);
    orderQueryParams.setListOrderStatus(newArrayList);;
    pojo.setOrderQueryParams(orderQueryParams);
    pojo.setOrderItemQueryParams(new com.xiaodou.oms.vo.input.order.OrderItem());
    /**
     * 设置输出属性
     */
    Map<String, Object> orderoutput = new HashMap<String, Object>();
    pojo.setGorderOutputProperties(new HashMap<String, Object>());
    pojo.setOrderItemOutputProperties(new HashMap<String, Object>());
    orderoutput.put("id", "1");
    orderoutput.put("gorderId", "1");
    orderoutput.put("orderStatus", "1");
    pojo.setOrderOutputProperties(orderoutput);
    try {
      GorderOrderItemListVO result = omsOrderService.gorderOrderItemList(pojo);
      System.out.println(FastJsonUtil.toJson(result));
    } catch (Exception e) {
      System.out.println("error");
    }
  }
}
