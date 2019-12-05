package com.xiaodou.oms.service.order;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.xiaodou.oms.constant.order.OrderConstant;
import com.xiaodou.oms.dao.order.GorderDao;
import com.xiaodou.oms.entity.order.Gorder;
import com.xiaodou.oms.entity.order.Order;
import com.xiaodou.oms.entity.order.OrderItem;
import com.xiaodou.oms.entity.order.PayRecord;
import com.xiaodou.oms.entity.order.PayRequest;
import com.xiaodou.oms.exception.OrderException;
import com.xiaodou.oms.exception.ValidationException;
import com.xiaodou.oms.util.IDGenerator;
import com.xiaodou.oms.util.OrderLoggerUtil;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * 根据多级订单数据结构来创建并生成大订单 同时调用之前的小订单创建方法实现对小订单的创建
 * 
 * @author bjjpdu
 * 
 */
public class GorderService {
  @Resource
  GorderDao gorderDao = null;// 大订单的数据访问接口
  @Resource
  protected OrderService orderService;
  @Resource
  protected OrderItemService orderItemService;
  @Resource
  private WayBillService wayBillService;

  @Resource
  private PayService payService;



  /**
   *
   */
  public Gorder createGorder(Gorder guorder, String relationType, String relations) {
    return createGorder(guorder, relationType, relations, null);
  }

  /**
   * 创建单个大订单数据含下属小订单数据
   * 
   * @param guorder
   * @param relationType
   * @param relations
   * @return
   */
  public Gorder createGorder(Gorder guorder, String relationType, String relations, String fraudJson) {

    // 插入大订单数据
    if (null == guorder.getId() || "".equalsIgnoreCase(guorder.getId().trim())) {
      guorder.setId(gorderDao.querySeqId(""));// 大订单的订单号
    }

    // String originalGoodsName = null == guorder.getGoodsName()?"":guorder.getGoodsName();
    // guorder.setGoodsName(originalGoodsName+"(订单号："+guorder.getId()+")");

    // 大订单状态初始创建只能为0
    guorder.setGorderStatus(OrderConstant.STATUS_INITIATE);
    if (guorder.getGorderTime() == null) {
      guorder.setGorderTime(new Timestamp(System.currentTimeMillis()));
    }
    gorderDao.insertGorder(guorder);

    // 创建大订单下面包含的小订单，调用之前的创建小订单的接口完成创建
    String gorderId = guorder.getId();
    List<Order> orderList = guorder.getOrderList();
    for (Order curOrder : orderList) {
      // 设置小订单相关的大订单号
      curOrder.setGorderId(gorderId);
      curOrder.setOrderIp(guorder.getGorderIp());
      curOrder.setGorder(guorder);
      curOrder = orderService.createOrder(curOrder, guorder.getPayOrderId());
    }

    // 写支付请求记录表
    if (guorder.getPayOrderId() != null) {
      PayRecord pr = new PayRecord();
      pr.setId(IDGenerator.getSeqID("xd_order_sequence_id"));
      pr.setAmount(guorder.getGpayAmount());
      pr.setBuyAccountId(guorder.getBuyAccountId());
      pr.setGorderId(guorder.getId());
      pr.setOperType(PayRequest.OPER_TYPE_PAY);
      pr.setPayNo(guorder.getPayOrderId());
      pr.setPayStatus(PayRequest.PAY_STATUS_PROCESSING);
      pr.setProductType(guorder.getProductType());
      pr.setPayType(PayRequest.PAY_TYPE_ONE);
      pr.setClientType(guorder.getClientType());
      pr.setOuterOrigin(guorder.getOuterOrigin());
      Timestamp timestamp = new Timestamp(System.currentTimeMillis());
      pr.setCreateTime(timestamp);
      if (orderList.size() == 1) {
        pr.setOrderId(guorder.getOrderList().get(0).getId());
      } else if (guorder.getProductType().equals("0501") || guorder.getProductType().equals("1201")) {
        pr.setOrderId(guorder.getOrderList().get(0).getId());
      }
      payService.addPayRecord(pr);
    }

    // 写waybill数据
    if (guorder.getWayBill() != null) {
      guorder.getWayBill().setGorderId(gorderId);
      guorder.getWayBill().setPostStatus(0);

      if (guorder.getWayBill().getTitle() == null) {
        guorder.getWayBill().setTitle("");
      }
      if (guorder.getWayBill().getContent() == null) {
        guorder.getWayBill().setContent("");
      }
      if (guorder.getWayBill().getPostcode() == null) {
        guorder.getWayBill().setPostcode("");
      }
      if (guorder.getWayBill().getReceiverName() == null) {
        guorder.getWayBill().setReceiverName("");
      }
      if (guorder.getWayBill().getReceiverPhone() == null) {
        guorder.getWayBill().setReceiverPhone("");
      }
      if (guorder.getWayBill().getInvoiceStatus() == null) {
        guorder.getWayBill().setInvoiceStatus(0);
      }

      wayBillService.addEntity(guorder.getWayBill());
    }

    if (StringUtils.isNotBlank(relationType) && StringUtils.isNotBlank(relations)) {
      // 关联订单项列表
      List<OrderItem> relationOrderItemList = new ArrayList<OrderItem>();
      // 获取关联订单列表
      List<Order> relationOrderList = guorder.getOrderList();
      // 获取关联订单项列表
      for (Order order : relationOrderList) {
        relationOrderItemList.addAll(order.getOrderItemList());
      }
      // 关联关系解析
      Map<String, String> relationMap = new HashMap<String, String>();
      String[] relationItems = relations.split("\\|\\|");
      for (String relationItem : relationItems) {
        String[] relationItemSplit = relationItem.split(":");
        relationMap.put(relationItemSplit[0], relationItemSplit[1]);
      }
      // order-order
      if (relationType.equals("1")) {

      }
      // order_orderItem
      if (relationType.equals("2")) {

      }
      // orderItem_order
      if (relationType.equals("3")) {
        for (String key : relationMap.keySet()) {
          String relationOrderItemId = key;
          String relationOrderId = relationMap.get(key);
          Order relationOrder = null;
          OrderItem relationOrderItem = null;
          // 获取到关联的order
          for (Order order : relationOrderList) {
            if (order.getVirtualRelationId() != null
                && order.getVirtualRelationId().equals(relationOrderId)) {
              relationOrder = order;
            }
          }
          // 获取到关联的orderItem
          for (OrderItem orderItem : relationOrderItemList) {
            if (orderItem.getVirtualRelationId() != null
                && orderItem.getVirtualRelationId().equals(relationOrderItemId)) {
              relationOrderItem = orderItem;
            }
          }
          if (relationOrder == null || relationOrderItem == null) {
            continue;
          }
          // 更新order
          orderService.getUpdateService().updateOrderRealtionId(relationOrder.getId(),
              relationOrderItem.getId());
          // 更新orderItem
          OrderItem updateOrderItem = new OrderItem();
          updateOrderItem.setId(relationOrderItem.getId());
          updateOrderItem.setRelationId(relationOrder.getId());
          orderItemService.updateOrderItem(updateOrderItem);
        }
      }
      // orderItem_orderItem
      if (relationType.equals("4")) {

      }
    }

    return guorder;
  }

  /**
   * 关闭大订单
   * 
   * @param gorder
   * @param ip
   * @param note
   * @return
   */
  public boolean closeGorder(Gorder gorder, String ip, String note, List<Integer> orderStatusList,
      String closedReason, boolean isNeedCloseOrderItem) {
    try {
      if (null == gorder || gorder.getId() == null) {
        return false;
      }

      if (null == ip) {
        InetAddress addr = null;
        try {
          addr = InetAddress.getLocalHost();
        } catch (Exception e) {

        }
        ip = (addr != null ? addr.getHostAddress() : "127.0.0.1");
      }
      // 关闭大订单下的各小订单
      List<Order> orderList = gorder.getOrderList();
      if (null == orderList || orderList.size() <= 0) {

        Map inputOrder = new HashMap();
        inputOrder.put("gorderId", gorder.getId());
        inputOrder.put("listOrderStatus", orderStatusList);
        Map outputOrder = new HashMap();
        outputOrder.put("id", "1");
        orderList = this.orderService.getQueryService().queryOrderList(inputOrder, outputOrder);
      }

      if (null == orderList || orderList.size() <= 0) {
        OrderLoggerUtil.orderInfo("[订单模块][关闭大订单时未找到其下的小订单列表,gorderId:" + gorder.getId() + "]");
        return false;
      } else {

        String[] orderIdsArr = new String[orderList.size()];

        for (int i = 0; i < orderList.size(); i++) {
          orderIdsArr[i] = orderList.get(i).getId();
        }

        boolean closeOrderRet =
            this.orderService.closeOrder(orderIdsArr, note, ip, orderStatusList, closedReason,
                isNeedCloseOrderItem);

        if (!closeOrderRet) {
          OrderLoggerUtil.orderInfo("[小订单关单失败，事务回滚！]");
          throw new RuntimeException();
        }
      }

      // 小订单关闭成功，再关闭大订单
      // 关闭本地大订单订单 修改大订单本地状态为 已关闭
      gorder.setGorderStatus(OrderConstant.STATUS_CLOSED);

      // 设置关单时间
      gorder.setCloseTime(new Timestamp(System.currentTimeMillis()));
      if (!StringUtils.isBlank(note)) {
        gorder.setNote("关闭大订单,ip:" + ip + ",note:" + note);
      } else {
        gorder.setNote("关闭大订单,ip:" + ip);
      }

      Map input = new HashMap();
      input.put("id", gorder.getId());
      Integer updateGorderRet = this.gorderDao.updateGorder(input, gorder);

      if (null == updateGorderRet || updateGorderRet <= 0) {
        return false;
      }
    } catch (Exception e) {
      OrderLoggerUtil.error("关单失败", e);
      OrderLoggerUtil.orderInfo("关单失败:gorderId=" + gorder.getId());
    }
    OrderLoggerUtil.orderInfo("关单成功:gorderId=" + gorder.getId());
    return true;
  }

  /**
   * 查询指定id的订单明细
   * 
   * @param id 订单id
   * @return
   */
  public Gorder queryGorderDetailForUpdate(String id) {
    Map input = new HashMap(); // 查询条件
    if (null == id || "".equalsIgnoreCase(id)) {
      return null;
    }
    input.put("id", id);
    input.put("forUpdate", 1);
    Gorder gorder = queryGorderDetail(input);

    return gorder;
  }

  public Gorder queryGorderDetail(Map input) {
    Map output = setGorderDetail();// 要查询哪些明细属性

    Gorder gorder = gorderDao.queryGorderDetail(input, output);

    return gorder;
  }

  public Gorder queryGorderDetail(Map input, Map output) {

    Gorder gorder = gorderDao.queryGorderDetail(input, output);

    return gorder;
  }


  /**
   * 大订单支付回调通知处理接口：收到支付消息后同步更新大订单及小订单支付状态等信息
   * 
   * @param type
   * @param gorder
   * @param remoteIp
   * @return
   * @throws ValidationException
   * @throws OrderException
   */
  public Gorder gorderPayCallback(Integer type, Gorder gorder, String remoteIp)
      throws ValidationException, OrderException {
    Gorder gorderForUpdate = new Gorder();
    gorderForUpdate.setId(gorder.getId());
    if (OrderConstant.STATUS_PAYSUCCESS.equals(type)) {
      gorderForUpdate.setGorderStatus(OrderConstant.STATUS_PAYSUCCESS);
    }
    if (OrderConstant.STATUS_PAYFAILURE.equals(type)) {
      gorderForUpdate.setGorderStatus(OrderConstant.STATUS_PAYFAILURE);
    }
    updateGorder(gorderForUpdate);

    // 处理小订单
    if (gorder.getOrderList() == null || gorder.getOrderList().size() == 0) {
      Map inputOrder = new HashMap();
      inputOrder.put("gorderId", gorder.getId());
      Map outputOrder = new HashMap();
      outputOrder.put("id", "1");
      gorder.setOrderList(this.orderService.getQueryService().queryOrderList(inputOrder,
          outputOrder));
    }

    if (gorder.getOrderList() != null) {
      for (Order curOrder : gorder.getOrderList()) {
        Order order = new Order();
        order.setId(curOrder.getId());
        if (OrderConstant.STATUS_PAYSUCCESS.equals(type)) {
          order.setOrderStatus(OrderConstant.STATUS_PAYSUCCESS);
        }
        if (OrderConstant.STATUS_PAYFAILURE.equals(type)) {
          order.setOrderStatus(OrderConstant.STATUS_PAYFAILURE);
        }
        order.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        orderService.getUpdateService().updateOrder(order, null, remoteIp);
      }

    }

    return gorderForUpdate;
  }

  /*
   * ================================================================================================
   * ================
   */

  /**
   * 创建多个大订单数据，并实现大订单自身的内部关联
   * 
   * @param guorderList
   * @param buyAccountId
   * @return
   */
  public List<String> createGorderList(List<Gorder> guorderList, String buyAccountId) {
    List<String> gorderIdList = new ArrayList();

    /**
     * 1.新增parent_gorder_id的概念，实现多级订单的关联，关联多个用户大订单 如果用户大订单个数大于2则创建该记录
     * */
    if (null != guorderList && guorderList.size() > 1) {
      Gorder guorder = new Gorder();
      if (null == guorder.getId() || "".equalsIgnoreCase(guorder.getId().trim())) {
        guorder.setId(gorderDao.querySeqId(""));// 大订单的订单号
      }
      guorder.setGorderTime(new Timestamp(System.currentTimeMillis()));// 订单时间
      guorder.setBuyAccountId(buyAccountId);
      BigDecimal totalGorderAmount = new BigDecimal(0);
      BigDecimal totalGpayAmount = new BigDecimal(0);
      BigDecimal totalGsaveAmount = new BigDecimal(0);
      /**
       * 2. 创建大订单下面包含的二级大订单，调用之前的创建大订单的接口完成创建
       */
      String parentGorderId = guorder.getId();
      for (Gorder curGorder : guorderList) {
        curGorder.setParentGorderId(parentGorderId);// 关联父订单ID
        String curGorderId = createGorder(curGorder, null, null).getId();
        gorderIdList.add(curGorderId);// 拼接订单ID列表，用于多笔合并付款结算
        totalGorderAmount.add(curGorder.getGorderAmount());
        totalGpayAmount.add(curGorder.getGpayAmount());
        totalGsaveAmount.add(curGorder.getGsaveAmount());
      }
      guorder.setGorderAmount(totalGorderAmount);
      guorder.setGpayAmount(totalGpayAmount);
      guorder.setGsaveAmount(totalGsaveAmount);
      gorderDao.insertGorder(guorder);
    } else {
      if (null != guorderList) {
        String curGorderId = createGorder(guorderList.get(0), null, null).getId();
        gorderIdList.add(curGorderId);
      }
    }
    return gorderIdList;
  }


  public Page<Gorder> getPagedGorderList(Map inputArgument, Map output, Page<Gorder> page) {
    Map mapParam = new HashMap<String, Map>();
    mapParam.put("input", inputArgument);
    if (output == null) {
      mapParam.put("output", setGorderDetail());
    } else {
      mapParam.put("output", output);
    }

    return gorderDao.findEntityListByCond(mapParam, page);
  }

  /**
   * 设置哪些是订单明细
   * 
   * @return
   */
  public Map setGorderDetail() {
    Map output = new HashMap();// 要查询哪些明细属性
    output.put("id", OrderConstant.STRING_NEEDED); // 订单编号
    output.put("gorderTime", OrderConstant.DATE_NEEDED); // 下单时间
    output.put("gorderIp", OrderConstant.STRING_NEEDED); // 下单ip
    output.put("preCloseTime", OrderConstant.DATE_NEEDED); // 大订单预期关闭时间
    output.put("gorderAmount", OrderConstant.BIGDECIMAL_NEEDED);// 大订单总金额
    output.put("originalGpayAmount", OrderConstant.BIGDECIMAL_NEEDED);// 原始应付金额
    output.put("gpayAmount", OrderConstant.BIGDECIMAL_NEEDED);// 实付金额
    output.put("gfee", OrderConstant.BIGDECIMAL_NEEDED); // 大订单净利润
    output.put("goodsName", OrderConstant.STRING_NEEDED); // 大订单中所购买商品名称
    output.put("gorderStatus", OrderConstant.INTEGER_NEEDED);// 大订单状态
    output.put("realPayMethod", OrderConstant.STRING_NEEDED);// 大订单实际支付方式
    output.put("payOrderId", OrderConstant.STRING_NEEDED); // 大订单支付时的网易宝订单号
    output.put("payUrl", OrderConstant.STRING_NEEDED); // 支付url
    output.put("paySuccessTime", OrderConstant.DATE_NEEDED);// 支付成功时间
    output.put("sellerNickName", OrderConstant.STRING_NEEDED);// 卖家昵称
    output.put("invoice", OrderConstant.STRING_NEEDED); // 发票信息
    output.put("shippingAddress", OrderConstant.STRING_NEEDED);// 发货地址
    output.put("receiverEmail", OrderConstant.STRING_NEEDED);// 收货人邮箱
    output.put("receiverPhone", OrderConstant.STRING_NEEDED);// 收货人电话
    output.put("deliveryDateType", OrderConstant.INTEGER_NEEDED);// 发货日期类型
    output.put("expectedShippingTime", OrderConstant.DATE_NEEDED);// 用户期望的收货时间
    output.put("outerOrigin", OrderConstant.STRING_NEEDED); // 下单外部来源
    output.put("innerOrigin", OrderConstant.STRING_NEEDED); // 下单内部来源
    output.put("productType", OrderConstant.STRING_NEEDED); // 下单时搜索的关键词
    output.put("buyAccountId", OrderConstant.STRING_NEEDED); // 用户ID
    output.put("parentGorderId", OrderConstant.STRING_NEEDED); // 父大订单ID，用于实现多级订单
    output.put("note", OrderConstant.STRING_NEEDED); // 备注
    output.put("updateTime", OrderConstant.DATE_NEEDED);// 更新时间
    output.put("clientType", OrderConstant.INTEGER_NEEDED);// 客户端类型
    output.put("closeTime", OrderConstant.DATE_NEEDED); // 大订单实际关闭时间
    output.put("gorderDesc", OrderConstant.STRING_NEEDED); // 大订单扩展字段
    output.put("city", OrderConstant.STRING_NEEDED); // 大订单扩展字段
    output.put("gsaveAmount", OrderConstant.STRING_NEEDED); // 大订单扩展字段
    output.put("tags", OrderConstant.INTEGER_NEEDED);
    return output;
  }

  /**
   * 接口44：自动关闭interval天前创建且为未付款状态的大订单及下属的小订单
   * 
   * @param interval
   * @return 100--调用成功
   * @throws IOException
   * @author bjfyzhao
   */
  // public String autoCloseGorder(int interval) throws IOException{
  // Calendar gorderTimeUpper = Calendar.getInstance();
  // gorderTimeUpper.add(Calendar.DATE, -interval);
  //
  // Integer gorderStatus = OrderConstant.STATUS_INITIATE;
  // Map input = new HashMap();
  // input.put("gorderStatus", gorderStatus);
  // input.put("gorderTimeUpper", new Timestamp(gorderTimeUpper.getTimeInMillis()));
  //
  // Map outputField = new HashMap();
  // outputField.put("id", OrderConstant.STRING_NEEDED);
  // outputField.put("gorderTime", OrderConstant.DATE_NEEDED);
  // outputField.put("note", OrderConstant.STRING_NEEDED);
  //
  // List<Gorder> gorderList = this.gorderDao.queryGorderList(input, outputField);
  // if(null == gorderList && gorderList.size() <= 0){
  // return null;
  // }
  //
  // for(Gorder tempGorder : gorderList){
  // this.closeGorder(tempGorder, "127.0.0.1", "自动关闭" + interval + "天前的未支付订单");
  // }
  //
  // return "100";
  // }

  /**
   * 查询指定id的订单明细
   * 
   * @param id 订单id
   * @return
   */
  public Gorder queryGorderDetail(String id) {
    Map input = new HashMap(); // 查询条件
    if (null == id || "".equalsIgnoreCase(id)) {
      return null;
    }
    input.put("id", id);
    Gorder gorder = queryGorderDetail(input);

    return gorder;
  }



  public void updateGorder(Gorder gorder) {
    Map input = new HashMap();
    if (null == gorder || null == gorder.getId() || "".equalsIgnoreCase(gorder.getId())) {
      OrderAlerter.alert("更新订单所需ID信息为空，请检查参数！");
      throw new RuntimeException();
    }
    input.put("id", gorder.getId());// .setId(order.getId());
    if (OrderConstant.STATUS_PAYSUCCESS.equals(gorder.getGorderStatus())) {
      gorder.setPaySuccessTime(new Timestamp(System.currentTimeMillis()));
    }
    gorder.setUpdateTime(new Timestamp(System.currentTimeMillis()));
    gorderDao.updateGorder(input, gorder);
  }

  public void updateGorderAndOrderList(Gorder gorder, String ip) {
    Map input = new HashMap();
    if (null == gorder || null == gorder.getId() || "".equalsIgnoreCase(gorder.getId())) {
      OrderAlerter.alert("更新订单所需ID信息为空，请检查参数！");
      throw new RuntimeException();
    }
    input.put("id", gorder.getId());// .setId(order.getId());
    if (OrderConstant.STATUS_PAYSUCCESS.equals(gorder.getGorderStatus())) {
      gorder.setPaySuccessTime(new Timestamp(System.currentTimeMillis()));
    }
    gorder.setUpdateTime(new Timestamp(System.currentTimeMillis()));
    gorderDao.updateGorder(input, gorder);

    // 更新小订单
    if (gorder.getOrderList() != null) {
      for (Order order : gorder.getOrderList()) {
        orderService.getUpdateService().updateOrder(order, null, ip);
      }
    }

  }

  /**
   * 修改大订单
   * 
   * @param input where条件，不作修改
   * @param gorder 修改内容
   * @return 成功更新了多少条数据
   */
  public Integer updateGorder(Map input, Gorder gorder) {
    return gorderDao.updateGorder(input, gorder);
  }

  /**
   * 根据GorderId修改BusAccountID updateBuyAccountIdByGorderId
   * 
   * @Title: updateBuyAccountIdByGorderId
   * @Description: TODO
   * @param input
   * @param gorder
   * @return
   */
  public Integer updateBuyAccountIdByGorderId(Map input, Gorder gorder) {
    return gorderDao.updateBuyAccountIdByGorderId(input, gorder);
  }

  /**
   * 更新大订单关单时间pre_close_time字段
   * 
   * @param gorderId
   * @param preCloseTime
   * @return 成功更新了多少条数据
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  public Integer updateGorderPreCloseTime(String gorderId, Timestamp preCloseTime) {
    Map input = new HashMap();
    input.put("id", gorderId);
    Gorder gorder = new Gorder();
    gorder.setPreCloseTime(preCloseTime);
    return gorderDao.updateGorder(input, gorder);
  }


  /**
   * 接口9：为一些已创建的订单生成网易宝付费参数：支付前有判断是否锁库存成功的逻辑
   * 
   * @param arrayOrderId 要付费的大订单id
   * @param listGorder 可以付费的订单id
   * @return 支付URL地址
   * @throws Exception
   */
  // public String createEpayRequestById(String[] arrayGorderId,String paymethod,boolean feeIndex)
  // throws Exception{
  // if (null == arrayGorderId || arrayGorderId.length == 0) {
  // return null;
  // }
  //
  // //判断整个大订单能否支付
  // List<Gorder> payableGorderList = new ArrayList<Gorder>();
  // for (int i = 0; i < arrayGorderId.length; i++) {
  // String gorderId = arrayGorderId[i];
  // Gorder gorder = this.queryGorderDetail(gorderId);
  // //如果状态为初始状态，是否有必要锁下属的小订单的库存，只要有一个下属小订单锁库存失败都不允许支付
  // boolean payable = true;
  // if (null != gorder) {
  // List<Order> orderList = orderService.getQueryService().queryOrderListByGorderId(gorderId);
  // for(Order curOrder:orderList){
  // IOrderAction action = OrderActionProxy.createOrderAction(curOrder, orderService);
  // if((OrderConstant.STATUS_INITIATE.equals(curOrder.getOrderStatus())
  // ||OrderConstant.STATUS_LOCKED.equals(curOrder.getOrderStatus()))
  // && false == action.checkBeforePay(curOrder)){//已经锁库存不会再次锁
  // payable= false;
  // break;
  // }
  // }
  // if(payable){
  // payableGorderList.add(gorder);
  // }
  // }
  // }
  //
  // return createEpayRequest(payableGorderList,paymethod,feeIndex);
  // }

  // public String createGatesEpayRequestById(String[] arrayGorderId,String paymethod,boolean
  // feeIndex) throws Exception{
  // if (null == arrayGorderId || arrayGorderId.length == 0) {
  // return null;
  // }
  //
  // //判断整个大订单能否支付
  // List<Gorder> payableGorderList = new ArrayList<Gorder>();
  // for (int i = 0; i < arrayGorderId.length; i++) {
  // String gorderId = arrayGorderId[i];
  // Gorder gorder = this.queryGorderDetail(gorderId);
  // //如果状态为初始状态，是否有必要锁下属的小订单的库存，只要有一个下属小订单锁库存失败都不允许支付
  // boolean payable = true;
  // if (null != gorder) {
  // List<Order> orderList = orderService.getQueryService().queryOrderListByGorderId(gorderId);
  // for(Order curOrder:orderList){
  // IOrderAction action = OrderActionProxy.createOrderAction(curOrder, orderService);
  // if((OrderConstant.STATUS_INITIATE.equals(curOrder.getOrderStatus())
  // ||OrderConstant.STATUS_LOCKED.equals(curOrder.getOrderStatus()))
  // && false == action.checkBeforePay(curOrder)){//已经锁库存不会再次锁
  // payable= false;
  // break;
  // }
  // }
  // if(payable){
  // payableGorderList.add(gorder);
  // }
  // }
  // }
  //
  // return createGatesEpayRequest(payableGorderList,paymethod,feeIndex);
  // }

  /**
   * 接口10:单笔大订单支付接口：可以通过调用多笔接口实现
   * 
   * @param gorder 大订单对象
   * @param paymethod 支付网关
   * @param feeIndex 是否手续费支付方式
   * @return
   */
  // public String createEpayRequest(Gorder gorder,String paymethod,boolean feeIndex) throws
  // Exception{
  // List<Gorder> gorderList = new ArrayList<Gorder>();
  // String[] arrayGorderId = null;
  // if(null !=gorder){
  // arrayGorderId = new String[1];
  // arrayGorderId[0]=gorder.getId();
  // }
  // return createEpayRequestById(arrayGorderId,paymethod,feeIndex);
  // }

  /**
   * 接口10:单笔大订单网关支付接口：网易宝暂不支持多笔
   * 
   * @param gorder 大订单对象
   * @param paymethod 支付网关
   * @param feeIndex 是否手续费支付方式
   * @return
   */
  // public String createGatesEpayRequest(Gorder gorder,String paymethod,boolean feeIndex) throws
  // Exception{
  // List<Gorder> gorderList = new ArrayList<Gorder>();
  // String[] arrayGorderId = null;
  // if(null != gorder){
  // arrayGorderId = new String[1];
  // arrayGorderId[0]=gorder.getId();
  // }
  // return createGatesEpayRequestById(arrayGorderId,paymethod,feeIndex);
  // }



  /**
   * 接口12:大订单支付成功通知处理接口：收到支付消息后同步更新大订单及小订单支付状态等信息
   * 
   * @param param
   * @param remoteIp
   * @throws Exception
   */
  // public Gorder gorderGatesPaySuccess(Map param, String remoteIp) throws Exception{
  // /**支付平台的获取实现放在不同产品，订单jar外部来实现*/
  // if(null == param){
  // LoggerUtil.alarmInfo("[支付模块][紧急][接口调用出错：合作方返回的支付成功消息为NULL，不符合接口要求]");
  // return null;
  // }
  // /**
  // * 大订单付款接口数据校验
  // */
  // String gorderId = (String) param.get("platformTradeId");
  // if(null == gorderId || "".equalsIgnoreCase(gorderId)){
  // LoggerUtil.epayInfo("[支付模块][紧急][接口调用出错：合作方返回的支付成功消息缺少参数platformTradeId，不符合接口要求"+param+"]");
  // return null;
  // }
  // Gorder curGorder = queryGorderDetailForUpdate(gorderId);
  // if(null == curGorder){
  // LoggerUtil.alarmInfo("[支付模块][紧急][接口调用出错：合作方返回的支付成功消息查无此订单："+curGorder.getId()+"，不符合接口要求]");
  // return null;
  // }
  // if(OrderConstant.STATUS_PAYSUCCESS.equals(curGorder.getGorderStatus())){
  // LoggerUtil.orderInfo("[支付模块][紧急][重复的支付成功消息，订单："+curGorder.getId()+"，不做任何处理]");
  // return curGorder;
  // }
  // // if(!OrderConstant.STATUS_INITIATE.equals(curGorder.getGorderStatus())){
  // //
  // // }
  // String payPlatformId =
  // payPlatformProxy.getPayPlatformId(curGorder);//Config.getConfig("config_order.properties",
  // "payPlatformId");
  //
  // List<Gorder> gorderList = payPlatformProxy.gatesPayResultGorder(payPlatformId,
  // param,gorderId);//Config.getConfig("config_order.properties", "payPlatformId")
  //
  // if(null == gorderList){
  // LoggerUtil.alarmInfo("[支付模块][紧急][接口调用出错：payResult返回订单列表为NULL]");
  // return null;
  // }
  //
  // if(gorderList.size() !=2){
  // LoggerUtil.alarmInfo("[支付模块][紧急][接口调用出错：payResult返回订单列表长度不是2，不符合接口要求]");
  // return null;
  // }
  //
  // if(!OrderConstant.STATUS_PAYSUCCESS.equals(gorderList.get(0).getGorderStatus())){
  // LoggerUtil.alarmInfo("[支付模块][紧急][接口调用出错：payResult支付成功消息非法，处理出错]");
  // return null;
  // }
  // /**校验网易宝返回与本地订单关键字段*/
  // if(compareGorder(gorderList.get(1), curGorder) == false){
  // OrderAlerter.alert("支付平台订单数据与商城数据库中订单信息不一致，大订单ID:"+ curGorder.getId(),"紧急");
  // return null;
  // }
  // /**支付成功，完成上述校验后，更新大订单及小订单数据*/
  // if(OrderConstant.STATUS_CLOSED.equals(curGorder.getGorderStatus())){
  // this.refundTotalGorder(curGorder, remoteIp);
  // OrderAlerter.alert("[由于订单已经关闭，收到支付成功消息后直接将整个大订单退款给用户！]");
  // }else if(OrderConstant.STATUS_INITIATE.equals(curGorder.getGorderStatus())){
  // updateGorder(gorderList.get(0));
  // Gorder tempCurGorder = gorderList.get(0);
  // //更新大订单关联的小订单数据，模拟数据执行更新
  // List<Order> orderList = orderService.getQueryService().queryOrderListByGorderId(gorderId);
  // for(Order curOrder:orderList){
  // Order orderForUpdate = new Order();
  // orderForUpdate.setId(curOrder.getId());
  // //TODO 字段转移，暂时注释
  // //orderForUpdate.setWybOrderId(tempCurGorder.getWybOrderId());
  // //orderForUpdate.setRealPayMethod(tempCurGorder.getRealPayMethod());
  // Order orderForCheck = new Order();
  // orderForCheck.setId(curOrder.getId());
  // List<Order> payOrderList = new ArrayList<Order>();
  // payOrderList.add(orderForUpdate);
  // payOrderList.add(orderForCheck);
  // try{
  // IOrderAction action = OrderActionProxy.createOrderAction(curOrder.getId(),orderService);
  // action.paySuccess(remoteIp, payOrderList);
  // }catch(Exception e){
  // OrderAlerter.alert("[合作方订单状态为支付成功，消息处理失败]");
  // throw new RuntimeException(e);
  // }
  // }
  // /**如果用户使用了优惠券，则需要将推广金额转账到中间账户(插入一条转账记录)*/
  // if(null != curGorder.getGsaveAmount() && curGorder.getGsaveAmount().compareTo(new
  // BigDecimal(0))>0){
  // try {
  // //从推广账户到中间账户
  // String platformType = Config.getConfig("config_order.properties", "payPlatformId");
  // String inAccount = Config.getConfig("config_order.properties", platformType+".accountId");
  // String outAccount = Config.getConfig("config_order.properties", "wyb.shop_promote.accountId");
  // String platformId = Config.getConfig("config_order.properties", "wyb.shop_promote.platformId");
  // String keyPath = Config.getConfig("config_order.properties", "shop.netease.rsa.prikey");
  // this.orderService.getUpdateService().addTransferRequest(curGorder.getId(),"", null,
  // curGorder.getGsaveAmount(), new BigDecimal(0), "推广经费转账到中间账户", remoteIp, inAccount, outAccount,
  // TransferOrder.TYPE_PROMOTE_TO_B2C, platformId, keyPath);
  // }catch(IOException e){
  // OrderAlerter.alert("[插入推广账户到中间账户转账记录失败，大订单ID："+curGorder.getId()+"!]");
  // LoggerUtil.error("[插入推广账户到中间账户转账记录失败，大订单ID："+curGorder.getId()+"!]", e);
  // throw new RuntimeException(e);
  // }
  // }
  // }
  //
  // return curGorder;
  // }

  /**
   * 接口12:大订单支付成功通知处理接口：收到支付消息后同步更新大订单及小订单支付状态等信息
   * 
   * @param param
   * @param remoteIp
   * @throws Exception
   */
  // public boolean zeroGorderPaySuccess(String gorderId,String buyAccountId, String remoteIp)
  // throws Exception{
  // /**支付平台的获取实现放在不同产品，订单jar外部来实现*/
  // /**
  // * 大订单付款接口数据校验
  // */
  // if(null == gorderId || "".equalsIgnoreCase(gorderId)){
  // LoggerUtil.alarmInfo("[支付模块][紧急][接口调用出错：订单ID为空，不符合接口要求]");
  // return false;
  // }
  // if(null == buyAccountId || "".equalsIgnoreCase(buyAccountId)){
  // LoggerUtil.alarmInfo("[支付模块][紧急][接口调用出错：当前用户ID为空，不符合接口要求]");
  // return false;
  // }
  // Gorder curGorder = queryGorderDetailForUpdate(gorderId);
  // if(null == curGorder){
  // LoggerUtil.alarmInfo("[支付模块][紧急][接口调用出错：合作方返回的支付成功消息查无此订单："+curGorder.getId()+"，不符合接口要求]");
  // return false;
  // }
  // if(null == curGorder.getBuyAccountId()||
  // !curGorder.getBuyAccountId().equalsIgnoreCase(buyAccountId)){
  // LoggerUtil.alarmInfo("[支付模块][紧急][接口调用出错：当前用户ID"+buyAccountId+"非法，非法的支付请求]");
  // return false;
  // }
  // if(OrderConstant.STATUS_PAYSUCCESS.equals(curGorder.getGorderStatus())){
  // LoggerUtil.orderInfo("[支付模块][紧急][重复的支付成功消息，订单："+curGorder.getId()+"，不做任何处理]");
  // return true;
  // }
  // /**推广金额为空或0，非法（对于事先采购的货品推广费用为0）*/
  // if(null==curGorder.getGsaveAmount() ||
  // curGorder.getGsaveAmount().compareTo(BigDecimal.ZERO)<0){
  // return false;
  // }
  // /**支付金额不为0，非法调用*/
  // if(null!=curGorder.getGpayAmount() && curGorder.getGpayAmount().compareTo(BigDecimal.ZERO)>0){
  // return false;
  // }
  // List<Gorder> gorderList = new ArrayList<Gorder>();//Config.getConfig("config_order.properties",
  // "payPlatformId")
  // Gorder updateGorder = new Gorder();
  // updateGorder.setId(curGorder.getId());
  // updateGorder.setGorderStatus(OrderConstant.STATUS_PAYSUCCESS);
  // gorderList.add(updateGorder);
  // Gorder checkGorder = new Gorder();
  // checkGorder.setId(curGorder.getId());
  // gorderList.add(checkGorder);
  //
  // if(gorderList.size() !=2){
  // LoggerUtil.alarmInfo("[支付模块][紧急][接口调用出错：payResult返回订单列表长度不是2，不符合接口要求]");
  // return false;
  // }
  //
  // if(!OrderConstant.STATUS_PAYSUCCESS.equals(updateGorder.getGorderStatus())){
  // LoggerUtil.alarmInfo("[支付模块][紧急][接口调用出错：payResult支付成功消息非法，处理出错]");
  // return false;
  // }
  // /**校验网易宝返回与本地订单关键字段*/
  // if(compareGorder(checkGorder, curGorder) == false){
  // OrderAlerter.alert("支付平台订单数据与商城数据库中订单信息不一致，大订单ID:"+ curGorder.getId(),"紧急");
  // return false;
  // }
  // /**支付成功，完成上述校验后，更新大订单及小订单数据*/
  // // if(OrderConstant.STATUS_CLOSED.equals(curGorder.getGorderStatus())){
  // // this.refundTotalGorder(curGorder, remoteIp);
  // // OrderAlerter.alert("[由于订单已经关闭，收到支付成功消息后直接将整个大订单退款给用户！]");
  // // }else
  // if(OrderConstant.STATUS_INITIATE.equals(curGorder.getGorderStatus())){
  // updateGorder(updateGorder);
  // Gorder tempCurGorder = gorderList.get(0);
  // //更新大订单关联的小订单数据，模拟数据执行更新
  // List<Order> orderList = orderService.getQueryService().queryOrderListByGorderId(gorderId);
  // for(Order curOrder:orderList){
  // Order orderForUpdate = new Order();
  // orderForUpdate.setId(curOrder.getId());
  // // orderForUpdate.setWybOrderId(tempCurGorder.getWybOrderId());
  // // orderForUpdate.setRealPayMethod(tempCurGorder.getRealPayMethod());
  // Order orderForCheck = new Order();
  // orderForCheck.setId(curOrder.getId());
  // List<Order> payOrderList = new ArrayList<Order>();
  // payOrderList.add(orderForUpdate);
  // payOrderList.add(orderForCheck);
  // try{
  // IOrderAction action = OrderActionProxy.createOrderAction(curOrder.getId(),orderService);
  // action.paySuccess(remoteIp, payOrderList);
  // }catch(Exception e){
  // OrderAlerter.alert("[合作方订单状态为支付成功，消息处理失败]");
  // throw new RuntimeException(e);
  // }
  // }
  // /**如果用户使用了优惠券，则需要将推广金额转账到中间账户(插入一条转账记录)*/
  // if(null != curGorder.getGsaveAmount() && curGorder.getGsaveAmount().compareTo(new
  // BigDecimal(0))>0){
  // try {
  // //从推广账户到中间账户
  // String platformType = Config.getConfig("config_order.properties", "payPlatformId");
  // String inAccount = Config.getConfig("config_order.properties", platformType+".accountId");
  // String outAccount = Config.getConfig("config_order.properties", "wyb.shop_promote.accountId");
  // String platformId = Config.getConfig("config_order.properties", "wyb.shop_promote.platformId");
  // String keyPath = Config.getConfig("config_order.properties", "shop.netease.rsa.prikey");
  // this.orderService.getUpdateService().addTransferRequest(curGorder.getId(),"", null,
  // curGorder.getGsaveAmount(), new BigDecimal(0), "推广经费转账到中间账户", remoteIp, inAccount, outAccount,
  // TransferOrder.TYPE_PROMOTE_TO_B2C, platformId, keyPath);
  // }catch(IOException e){
  // OrderAlerter.alert("[插入推广账户到中间账户转账记录失败，大订单ID："+curGorder.getId()+"!]");
  // LoggerUtil.error("[插入推广账户到中间账户转账记录失败，大订单ID："+curGorder.getId()+"!]", e);
  // throw new RuntimeException(e);
  // }
  // }
  // }
  //
  // return true;
  // }
  //
  /**
   * 比较两个Gorder数据中第一个Gorder不为空的字段是否一致 （1）参数1：比较的标准 （2）参数2: 待比较的订单
   * 
   * @return
   * @throws IllegalAccessException
   * @throws IllegalArgumentException
   */
  public boolean compareGorder(Gorder firstGorder, Gorder secondGorder)
      throws IllegalArgumentException, IllegalAccessException {
    boolean result = true;
    if (null == firstGorder) {
      result = true;
      return result;
    }
    Field[] allAttributes = firstGorder.getClass().getDeclaredFields();
    for (int i = 0; i < allAttributes.length; i++) {
      allAttributes[i].setAccessible(true);
      Object value1 = allAttributes[i].get(firstGorder);
      Object value2 = allAttributes[i].get(secondGorder);
      String type = allAttributes[i].getType().getName();
      if (null == value1) {
        continue;
      }
      if (null != value1 && null == value2) {
        result = false;
        return result;
      }
      // bigdecimal用compareTo比较
      if ("java.math.BigDecimal".equalsIgnoreCase(type)) {
        BigDecimal v1 = (BigDecimal) value1;
        BigDecimal v2 = (BigDecimal) value2;
        if (v1.compareTo(v2) != 0) {
          result = false;
          return result;
        }
        // 其它用equals比较
      } else {
        if (!value1.equals(value2)) {
          result = false;
          return result;
        }
      }
    }
    return result;
  }

  /**
   * 用于生成订单之后调整订单活动金额，及实际支付金额 原始支付金额、成本、收入字段无须调整 只需调整pay_amount及save_amount和refund_amount
   * 活动金额下属小订单及订单条目均分
   * 
   * @return
   */
  // public boolean adjustActivityGorderMoney(String gorderId,BigDecimal gsaveAmount,String codeId){
  // if(StringUtils.isEmpty(gorderId) || null == gsaveAmount ||
  // gsaveAmount.compareTo(BigDecimal.ZERO)<=0 ){
  // return false;
  // }
  // try{
  // Gorder gorder = queryGorderDetail(gorderId);
  // if(null == gorder){
  // return false;
  // }
  // /**1.更新大订单gorder实际支付金额及优惠金额*/
  // Gorder activityGorder = new Gorder();
  // activityGorder.setId(gorder.getId());
  // /**如果当前订单金额小于优惠券金额，则优惠券使用后直接抵扣所有支付金额，减免费用设置为实际抵扣金额*/
  // gsaveAmount =
  // gorder.getGpayAmount().compareTo(gsaveAmount)<=0?gorder.getGpayAmount():gsaveAmount;
  // activityGorder.setGsaveAmount(gsaveAmount);
  // activityGorder.setGorderDesc(codeId);
  // BigDecimal actualGpayAmount = gorder.getGpayAmount().subtract(gsaveAmount).setScale(2,
  // BigDecimal.ROUND_UP);
  // actualGpayAmount = (actualGpayAmount.compareTo(new BigDecimal(0))<=0)?new
  // BigDecimal(0):actualGpayAmount;
  // activityGorder.setGpayAmount(actualGpayAmount);
  // updateGorder(activityGorder);
  // /**2.更新小订单order实际支付金额及优惠金额*/
  // List<Order> orderList = orderService.getQueryService().queryOrderListByGorderId(gorderId);
  // if(null == orderList || orderList.isEmpty()){
  // throw new RuntimeException();
  // }
  // BigDecimal osaveAmount = gsaveAmount.divide(new BigDecimal(orderList.size()));
  // for(Order curOrder:orderList){
  // Order activityOrder = new Order();
  // activityOrder.setId(curOrder.getId());
  // activityOrder.setSaveAmount(osaveAmount);
  // BigDecimal actualPayAmount = curOrder.getPayAmount().subtract(osaveAmount).setScale(2,
  // BigDecimal.ROUND_UP);
  // actualPayAmount = (actualPayAmount.compareTo(new BigDecimal(0))<=0)?new
  // BigDecimal(0):actualPayAmount;
  // activityOrder.setPayAmount(actualPayAmount);
  // activityOrder.setActivityId(OrderConstant.VALID);
  // orderService.getUpdateService().updateOrder(activityOrder, "活动订单更新活动金额");
  // }
  // /**3.更新orderItem实际支付金额、优惠金额及退款金额*/
  // List<OrderItem> orderItemList= orderItemService.queryItemListByGorderid(gorderId);
  // if(null == orderItemList || orderItemList.isEmpty()){
  // throw new RuntimeException();
  // }
  // BigDecimal itemSaveAmount = gsaveAmount.divide(new BigDecimal(orderItemList.size()));
  // for(OrderItem curOrderItem:orderItemList){
  // OrderItem activityOrderItem = new OrderItem();
  // activityOrderItem.setId(curOrderItem.getId());
  // activityOrderItem.setItemSaveAmount(itemSaveAmount);
  // BigDecimal actualItemPayAmount =
  // curOrderItem.getItemPayAmount().subtract(itemSaveAmount).setScale(2, BigDecimal.ROUND_UP);
  // actualItemPayAmount = (actualItemPayAmount.compareTo(new BigDecimal(0))<=0)?new
  // BigDecimal(0):actualItemPayAmount;
  // activityOrderItem.setItemPayAmount(actualItemPayAmount);
  // activityOrderItem.setRefundAmount(activityOrderItem.getItemPayAmount());
  // orderItemService.updateOrderItem(activityOrderItem);
  // }
  // return true;
  // }catch(Exception e){
  // return false;
  // }
  // }

  public OrderService getOrderService() {
    return orderService;
  }

  public void setOrderService(OrderService orderService) {
    this.orderService = orderService;
  }

  public GorderDao getGorderDao() {
    return gorderDao;
  }

  public void setGorderDao(GorderDao gorderDao) {
    this.gorderDao = gorderDao;
  }
}
