package com.xiaodou.oms.service.order;

import com.xiaodou.oms.constant.order.OrderConstant;
import com.xiaodou.oms.dao.order.OrderDao;
import com.xiaodou.oms.entity.order.Order;
import com.xiaodou.oms.entity.order.OrderItem;
import com.xiaodou.oms.util.OrderLoggerUtil;

import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Action可以安全访问的方法
 * 
 * @author Administrator
 * 
 */
public class OrderService {

  @Resource
  OrderDao orderDao = null;// 订单的数据访问接口

  @Resource
  protected QueryService queryService;

  @Resource
  protected UpdateService updateService;

  @Resource
  private OrderItemService orderItemService;


  public Order createOrder(Order order, String payNo) {

    // 生成订单
    if (null == order.getId() || "".equalsIgnoreCase(order.getId().trim())) {
      order.setId(orderDao.querySeqId(""));// 订单号
    }
    if (order.getOrderTime() == null) {
      order.setOrderTime(new Timestamp(System.currentTimeMillis()));// 订单时间
    }

    if(order.getOrderStatus() == null) {
      order.setOrderStatus(OrderConstant.STATUS_INITIATE);// 订单状态
    }
    order.setUpdateTime(order.getOrderTime());// 更新时间
    if (null == order.getSettleUp()) {
      order.setSettleUp(OrderConstant.SETTLEUP_FALSE);// 打款标示
    }
    if (null == order.getCanDeliver()) {
      order.setCanDeliver(OrderConstant.VALID);
    }
    if (null == order.getCanSettleUp()) {
      order.setCanSettleUp(OrderConstant.VALID);
    }
    if (null == order.getCanRefund()) {
      order.setCanRefund(OrderConstant.VALID);
    }
    if (null == order.getFee()) {
      order.setFee(BigDecimal.ZERO);
    }
    Integer ret = orderDao.insertOrder(order);

    // 若添加小订单成功，则继续添加订单条目表
    if (null != ret && ret > 0 && null != order.getOrderItemList()) {
      String orderId = order.getId();
      for (OrderItem orderItem : order.getOrderItemList()) {
        orderItem.setOrderId(orderId);
        if (StringUtils.isBlank(orderItem.getBuyAccountId())) {
          orderItem.setBuyAccountId(order.getBuyAccountId());
        }

        this.orderItemService.addOrderItem(orderItem);
      }
    }

    // 记日志
    updateService.writeStatusLog(order.getId(), order.getOrderStatus(), order.getOrderIp(),
        null, order.getBuyAccountId());

    return order;
  }

  /**
   * 关闭订单
   * 
   * @param orderIdArray
   * @return
   */
  public boolean closeOrder(String[] orderIdArray, String note, String ip,
      List<Integer> orderStatusList, String closedReason, boolean isNeedCloseOrderItem) {
    if (null == orderIdArray) {
      return false;
    }
    boolean ret = true;
    for (int i = 0; i < orderIdArray.length; i++) {
      Order order = new Order();
      order.setId(orderIdArray[i]);
      ret =
          updateService.closeOrder(order, ip, note, orderStatusList, closedReason,
              isNeedCloseOrderItem);
      if (!ret) {
        OrderLoggerUtil.orderInfo("[订单关闭失败，订单ID:" + orderIdArray[i] + "]");
      }
    }
    return ret;
  }

  /*
   * ================================================================================================
   * ================*
   * 
   * 
   * /***************************************** 创建订单 end ***************************************
   */
  /**
   * 发送到合作方
   * 
   * @throws DocumentException
   * @throws IOException
   */
  // public void deliver(Order order) {
  // if(null == order.getCanDeliver()){
  // OrderAlerter.alert("发送订单" + order.getId() + "时order.canDeliver为空。");
  // return;
  // }
  // if(OrderConstant.INVALID.equals(order.getCanDeliver())){
  // return;
  // }
  //
  // // 2.根据商家id调用某个合作方的接口
  // ChargeResultMsg ret;
  // try {
  // ret = protocolProxy.deliver(String.valueOf(order.getMerchantId()), order);
  // if(null == ret){
  // OrderAlerter.alert("[向合作方下单通知异常，deliver下单返回:"+ret+",订单ID:"+order.getId()+"]");
  // return;
  // }
  // ret.setOrderId(order.getId());
  //
  // deliverResult(order,ret, "OrderService.deliver");
  // } catch (Exception e) {
  // LoggerUtil.error("首次向合作方下单通知异常", e);
  // }
  // return;
  // }


  /*************************
   * 生成支付请求 修改逻辑，避免越过锁库存操作 执行中统一走锁库存判断，如果已经为锁库存状态则无需再次执行锁库存操作
   * 
   * @param order
   * @return
   * @throws Exception
   * @throws Exception
   *************************/
  // public String genPayUrl(Order order,String paymethod) throws Exception{
  // List<Order> orderList = new ArrayList<Order>();
  // String[] arrayOrderId = null;
  // if(null !=order){
  // arrayOrderId = new String[1];
  // arrayOrderId[0]=order.getId();
  // }
  // return this.createEpayRequestById(arrayOrderId, orderList,paymethod);
  // // orderList.add(order);
  // // return this.createEpayRequest(orderList);
  // }

  /**
   * 为一些已创建的订单生成网易宝付费参数
   * 
   * @param arrayOrderId 要付费的订单id
   * @param listOrder 可以付费的订单id
   * @return
   * @throws Exception
   */
  // public String createEpayRequestById(String[] arrayOrderId,
  // List<Order> listOrder,String paymethod) throws Exception {
  // if (null == arrayOrderId || arrayOrderId.length == 0) {
  // return null;
  // }
  //
  // // 查询订单。暂时查多次
  // for (int i = 0; i < arrayOrderId.length; i++) {
  // String orderId = arrayOrderId[i];
  // Order order = this.queryService.queryOrderDetail(orderId);
  //
  // if (null != order) {//如果状态为初始状态，则先锁库存
  // IOrderAction action = OrderActionProxy.createOrderAction(order, this);
  // if(OrderConstant.STATUS_INITIATE.equals(order.getOrderStatus())
  // && false == action.lockInventory()){
  // continue;
  // }
  // listOrder.add(order);
  // }
  // }
  //
  // return createEpayRequest(listOrder,paymethod);
  // }

  /**
   * 支付成功
   * 
   * @param param
   * @param remoteIp
   * @throws Exception
   */
  // public Order paySuccess(Map param, String remoteIp) throws Exception {
  // /**支付平台的获取实现放在不同产品，订单jar外部来实现*/
  // if(null == param){
  // LoggerUtil.alarmInfo("[支付模块][紧急][接口调用出错：合作方返回的支付成功消息为NULL，不符合接口要求]");
  // return null;
  // }
  // String orderId = (String) param.get("platformTradeId");
  // return this.paySuccess(param, orderId, remoteIp);
  // }

  /**
   * 支付成功
   * 
   * @param param
   * @param remoteIp
   * @throws Exception
   */
  // public Order paySuccess(Map param, String orderId,String remoteIp) throws Exception {
  // //String payPlatformId = Config.getConfig("config_order.properties", "payPlatformId");
  // /**支付平台的获取实现放在不同产品，订单jar外部来实现*/
  // if(null == param){
  // LoggerUtil.alarmInfo("[支付模块][紧急][接口调用出错：合作方返回的支付成功消息为NULL，不符合接口要求]");
  // return null;
  // }
  // /**
  // * 多笔合并付款接口
  // */
  // // String orderId = (String) param.get("platformTradeId");
  // if(null == orderId || "".equalsIgnoreCase(orderId)){
  // LoggerUtil.epayInfo("[支付模块][紧急][接口调用出错：合作方返回的支付成功消息缺少参数platformTradeId，不符合接口要求"+param+"]");
  // return null;
  // }
  // Order curOrder = queryService.queryOrderDetail(orderId);
  // if(null == curOrder){
  // LoggerUtil.alarmInfo("[支付模块][紧急][接口调用出错：合作方返回的支付成功消息查无此订单："+orderId+"，不符合接口要求]");
  // return null;
  // }
  // String payPlatformId =
  // payPlatformProxy.getPayPlatformId(curOrder);//Config.getConfig("config_order.properties",
  // "payPlatformId");
  //
  // List<Order> orderList = payPlatformProxy.payResult(payPlatformId,
  // param,orderId);//Config.getConfig("config_order.properties", "payPlatformId")
  //
  // if(null == orderList){
  // //LoggerUtil.alarmInfo("[支付模块][紧急][接口调用出错：payResult返回订单列表为NULL]");
  // return null;
  // }
  //
  // if(orderList.size() !=2){
  // LoggerUtil.alarmInfo("[支付模块][紧急][接口调用出错：payResult返回订单列表长度不是2，不符合接口要求]");
  // return null;
  // }
  //
  // if(null == orderList.get(0).getOrderStatus()){
  // LoggerUtil.alarmInfo("[支付模块][紧急][接口调用出错：payResult返回订单列表中orderForUpdate订单状态为NULL]");
  // return null;
  // }
  // if(!OrderConstant.STATUS_PAYSUCCESS.equals(orderList.get(0).getOrderStatus())){
  // return null;
  // }
  //
  // IOrderAction action = OrderActionProxy.createOrderAction(orderList.get(0).getId(), this);
  // action.paySuccess(remoteIp, orderList);
  // return action.getOrder();
  // }
  /**
   * 转账支付成功
   * 
   * @param param
   * @param remoteIp
   * @throws Exception
   */
  // public Order transferPaySuccess(Order order) throws Exception {
  // IOrderAction action = OrderActionProxy.createOrderAction(order.getId(), this);
  // action.transferPaySuccess(order);
  // return action.getOrder();
  // }

  /**
   * 创建广义订单并向下家发货
   */
  // public String createOrderandDeliverToSubtier(Map<String, Object> data, String ip){
  // //暂时用null填充参数，后续如果需要接下家，则根据需求来填充相关数据
  // String orderId=createGeneralizedOrder(data, ip, null,null);
  // Order order=queryService.queryOrderDetail(orderId);
  // deliver(order);
  // return orderId;
  // }

  /**
   * 获取支付url
   * 
   * @param order
   * @return
   * @throws Exception
   */
  // public String pay(Order order,String paymethod) throws Exception {
  // IOrderAction action = OrderActionProxy.createOrderAction(order, this);
  //
  // String redirectUrl = action.pay(paymethod);
  // return redirectUrl;
  // }
  /**
   * 通过直接转账实现并完成支付的接口 注意：两次转账由于网络或者网易宝系统原因，可能不能同时成功，如果没有同时成功需要报警，并返回false 然后必要时需要操作退款给应用账户。
   * 
   * @param order
   * @return 是否支付成功
   * @throws Exception
   */
  // public boolean transferPay(Order order) throws Exception {
  // IOrderAction action = OrderActionProxy.createOrderAction(order, this);
  // // if(false == action.lockInventory()){
  // // return false;
  // // }
  // Order payOrder = new Order();
  // payOrder.setId(order.getId());
  // //设置支付完成方式为转账接口
  // try {
  // String transferPayUrl = Config.getConfig("wyb.transfer.url");
  // //TODO 字段转移，暂时注释
  // //payOrder.setPayURL(transferPayUrl);
  // } catch (IOException e) {
  // OrderAlerter.alert("转账支付成功读取配置文件失败！");
  // }
  // //order.setMisc(XmlTool.mapToXml(map, "misc"));
  // updateService.updateOrder(payOrder, "");
  // return action.transferPay();//网易应用自己完成转账
  // }
  /**
   * 网易宝代扣支付方式，通过委托代扣接口实现支付
   * 
   * @throws Exception
   */
  // public boolean authPay(Order order) throws Exception{
  // IOrderAction action = OrderActionProxy.createOrderAction(order, this);
  // return action.authPay();
  // }
  //
  /** 该逻辑依据交由网易应用完成 */
  // public boolean doTransferPay(Order order)throws Exception{
  // try {
  // /**
  // * 通过转账完成支付的过程平台ID，和下家合作方私钥均根据订单标识信息来获取
  // * origin字段存放的是下家来源字段，通过该字段可以查询商家表的网易宝平台账户payPlatformID
  // * */
  // String origin = order.getOrigin();
  // Merchant merchant = merchantService.getMerchantByMerchantCode(origin);
  // //获取下家平台ID
  // String payPlatformId = Config.getConfig("payPlatformId");
  // String platformId = merchant.getPlatformId();
  // String privateFilePath = merchant.getKeyPath();//下家密钥文件位置
  // String outAccount = merchant.getPassport();//获取下家网易宝平台账户
  // /**1.转账1：转账成本价格到网易商城b2c账户*/
  // String inB2CAccount = Config.getConfig("wyb.shop_b2c.accountId");
  // BigDecimal costAmount = order.getPayAmount().subtract(order.getFee());//order.getCostAmount();
  // boolean result = payPlatformProxy.doTransfer(payPlatformId, order.getId(), platformId,
  // outAccount, inB2CAccount, costAmount, "从网易应用扣除成本价："+costAmount,privateFilePath);
  // if(result){
  // //记录下转账日志到下家结算转账表：状态置为成功
  // updateService.addSubtierTransferRequest(order.getId(), costAmount, "从网易应用扣除成本价："+costAmount,
  // order.getOrderIp(), inB2CAccount, outAccount, SubtierTransfer.TYPE_IN,
  // order.getPartner2OrderId(),SubtierTransfer.SUCCESS,platformId,privateFilePath);
  //
  // /**2.执行转账2：转账手续费到商城手续费账户，注意，是否允许从手续费账户退款？*/
  // String incomeAccount = Config.getConfig("wyb.shop_income.accountId");
  // BigDecimal handFee = order.getFee();
  // boolean feeResult = payPlatformProxy.doTransfer(payPlatformId, order.getId(), platformId,
  // outAccount, incomeAccount, handFee, "从网易应用扣除手续费："+handFee, privateFilePath);
  // if(feeResult){
  // /**记录手续费转账成功记录到转账表，状态为成功*/
  // updateService.addSubtierTransferRequest(order.getId(), handFee, "从网易应用扣除手续费："+handFee,
  // order.getOrderIp(), incomeAccount, outAccount, SubtierTransfer.TYPE_IN,
  // order.getPartner2OrderId(),SubtierTransfer.SUCCESS,platformId,privateFilePath);
  // //此处是否必要调用转账支付成功接口，还是等外部调用
  // //this.transferPaySuccess(order);
  // return true;
  // }else{
  // //记录将成本从商城b2c退回给网易应用账户，等待批处理完成退还操作
  // String shop_b2c_platform = Config.getConfig("wyb.b2c.platformId");
  // String shop_b2c_keypath = Config.getConfig("shop.netease.rsa.prikey");
  // updateService.addSubtierTransferRequest(order.getId(), costAmount, "从网易商城退还成本价："+costAmount,
  // order.getOrderIp(), outAccount, inB2CAccount, SubtierTransfer.TYPE_OUT,
  // order.getPartner2OrderId(),SubtierTransfer.INIT,shop_b2c_platform,shop_b2c_keypath);
  // return false;
  // }
  // }else{
  // return false;
  // }
  // } catch (IOException e) {
  // LoggerUtil.alarmInfo("订单模块，读取配置文件获取支付平台IO异常！");
  // return false;
  // } catch(Exception e){
  // LoggerUtil.alarmInfo("订单模块，转账请求未知异常！");
  // return false;
  // }
  // }



  /**
   * 请求退款
   * 
   * @param reason
   * @return
   */
  // public boolean refund(String[] orderIdArray, String reason){
  // if(null == orderIdArray){
  // return true;
  // }
  //
  // boolean ret = true;
  // for(int i = 0; i < orderIdArray.length; i ++){
  // ChargeResultMsg msg = new ChargeResultMsg();
  // msg.setActionResult("fail");
  // msg.setTextMessage(reason);
  // msg.setOrderId(orderIdArray[i]);
  // IOrderAction action = OrderActionProxy.createOrderAction(orderIdArray[i], this);
  //
  // action.refundOrder(msg);
  // }
  //
  // return ret;
  // }

  /**
   * 可以发货
   * 
   * @param orderIdArray
   * @param reason
   * @return
   */
  public boolean canDeliver(String[] orderIdArray, String reason) {
    if (null == orderIdArray) {
      return true;
    }

    boolean ret = true;

    Order order = new Order();
    order.setCanDeliver(OrderConstant.VALID);

    Map input = new HashMap();
    input.put("listId", orderIdArray);
    orderDao.updateOrder(input, order);
    return true;
  }

  /**
   * 交易成功
   * 
   * @param order
   */
  // public void tradeSuccess(String orderId) {
  // if(null == orderId || "".equalsIgnoreCase(orderId)){
  // OrderAlerter.alert("[交易成功消息处理失败，订单ID："+orderId+"不存在！]");
  // return;
  // }
  // Order order = queryService.queryOrderDetail(orderId);
  // if(null == order){
  // OrderAlerter.alert("[交易成功消息处理失败，订单ID："+orderId+"不存在！]");
  // return;
  // }
  // IOrderAction action = OrderActionProxy.createOrderAction(order, this);
  // //交易成功不需要合作方消息
  // action.tradeSuccess(null);
  // }
  //


  // public void completeOrder(int interval) throws Exception {
  // LoggerUtil.orderInfo("batch complete order begin");
  //
  // Map input = new HashMap();
  // Integer[] statusArray = {OrderConstant.STATUS_HALF_DELIVERED, OrderConstant.STATUS_DELIVERED};
  // input.put("listOrderStatus", statusArray);
  // Calendar c = Calendar.getInstance();
  // c.add(Calendar.DATE, -interval);
  // input.put("updateTimeUpper", c.getTime());
  // input.put("forUpdate", OrderConstant.VALID);
  //
  // Order order = new Order();
  // order.setOrderStatus(OrderConstant.STATUS_TRANSACTIONSUCCESS);
  // updateService.updateOrderStatus(input, order, "orderService.completeOrder");
  //
  // LoggerUtil.orderInfo("batch complete order end");
  // }
  //
  /**
   * 支付前校验接口调用
   * 
   * @param order
   */
  // public boolean lockInventory(Order order) {
  // IOrderAction action = OrderActionProxy.createOrderAction(order, this);
  // return action.lockInventory();
  // }
  /**
   * 自动关闭preCloseTime非空且本日到期的订单
   * 
   * @return
   */
  // public void closePreDeterminedOrder(Map input) {
  // if(null == input){input = new HashMap();}
  //
  // Calendar end = Calendar.getInstance();
  // end.set(Calendar.HOUR_OF_DAY,23);
  // end.set( Calendar.MINUTE,59);
  // end.set(Calendar.SECOND,59);
  // end.set(Calendar.DAY_OF_MONTH, 0);
  //
  // input.put("preCloseTimeUpper",end);
  // /**只能关闭初始及未支付状态的订单*/
  // String closeStr = null;
  // try {
  // closeStr = Config.getConfig("state.canclose");
  // } catch (IOException e) {
  // LoggerUtil.error("关闭订单失败：读取config错", e);
  // }
  //
  // String[] closeStatus = closeStr.split(",");
  // //Integer status[]={OrderConstant.STATUS_HALF_DELIVERED,OrderConstant.STATUS_DILIVERED,
  // OrderConstant.STATUS_TRANSACTIONSUCCESS};
  // input.put("listOrderStatus", closeStatus );
  //
  // List<Order> listOrder = queryService.queryOrderList(input);
  // this.closeOrder(listOrder);
  // }
  /*
   * 关闭订单，后台主动关闭订单
   */
  // public String closeOrder(List<Order> listOrder){
  // if(null == listOrder || listOrder.size() <= 0){
  // return "";
  // }
  //
  // List<String> idList = new ArrayList();
  // for(int i = 0; i < listOrder.size(); i ++){
  // IOrderAction action = OrderActionProxy.createOrderAction(listOrder.get(i), this);
  // action.closeOrder("后台自动关闭");
  // }
  // return "";
  // }
  /**
   * 根据时间段和订单状态关闭订单
   * 
   * @param interval
   * @throws Exception
   */
  // public String closeOrderByTime(String start, String end, String note){
  // LoggerUtil.epayInfo("开始批量关闭订单，orderId:");
  // Map input = new HashMap();
  // input.put("orderTimeLower",DateUtil.formatToDate(start,"yyyyMMdd"));
  // input.put("orderTimeUpper",DateUtil.formatToDate(end,"yyyyMMdd"));
  // input.put("forUpdate", 1);
  // input.put("settleUp", OrderConstant.SETTLEUP_FALSE);
  //
  // String closeStr = null;
  // try {
  // closeStr = Config.getConfig("state.canclose");
  // } catch (IOException e) {
  // LoggerUtil.error("关闭订单失败：读取config错", e);
  // }
  //
  // String[] closeStatus = closeStr.split(",");
  // //Integer status[]={OrderConstant.STATUS_HALF_DELIVERED,OrderConstant.STATUS_DILIVERED,
  // OrderConstant.STATUS_TRANSACTIONSUCCESS};
  // input.put("listOrderStatus", closeStatus );
  // List<Order> ordersToClose = queryService.queryOrderList(input);
  // return closeOrder(ordersToClose);
  //
  // }
  /*
   * 自动关闭interval天前创建且为未付款状态的订单
   */
  // public String autoCloseOrder(int interval) throws IOException{
  // /** 1.查询interval天前创建且为未付款状态的订单*/
  // Calendar c = Calendar.getInstance();
  // c.add(Calendar.DATE, -interval);
  //
  // String canCloseStr = Config.getConfig("config_order.properties", "state.canclose");
  // String[] canClose = canCloseStr.split("\\,");
  //
  // Map input = new HashMap();
  // input.put("listOrderStatus", canClose);
  // input.put("orderTimeUpper", c.getTime());
  //
  // List<Order> orderList = this.queryService.queryOrderList(input);
  // if(null == orderList || orderList.size() <= 0){
  // return "";
  // }
  //
  // List<String> idList = new ArrayList();
  // for(int i = 0; i < orderList.size(); i ++){
  // // String currentMode=null;
  // // try {
  // // currentMode = Config.getConfig("config_order.properties", "order.currentMode");
  // // } catch (IOException e) {
  // // LoggerUtil.alarmInfo("[订单模块][一般][读取配置文件异常]");
  // // }
  //
  // IOrderAction action = OrderActionProxy.createOrderAction(orderList.get(i), this);
  //
  // action.closeOrder("后台关闭未付款的订单！");
  // }
  //
  // return "";
  // }


  /**
   * 比较两个Order数据中第一个Order不为空的字段是否一致 （1）参数1：比较的标准 （2）参数2: 待比较的订单
   * 
   * @return
   * @throws IllegalAccessException
   * @throws IllegalArgumentException
   */
  public boolean compareOrder(Order firstOrder, Order secondOrder) throws IllegalArgumentException,
      IllegalAccessException {
    boolean result = true;
    if (null == firstOrder) {
      result = true;
      return result;
    }
    Field[] allAttributes = firstOrder.getClass().getDeclaredFields();
    for (int i = 0; i < allAttributes.length; i++) {
      allAttributes[i].setAccessible(true);
      Object value1 = allAttributes[i].get(firstOrder);
      Object value2 = allAttributes[i].get(secondOrder);
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


//  /**
//   * 解析发货结果
//   *
//   * @param message
//   * @param url
//   */
  // public void deliverResult(Order order,ChargeResultMsg message, String url) {
  // // String orderId = order.getId();
  // /**锁记录*/
  // IOrderAction action = OrderActionProxy.createOrderAction(order.getId(), this);
  //
  // String ret = message.getActionResult();
  //
  // // 订单重复
  // if (OrderProtocolConstant.PARTNER_REPEATED.equalsIgnoreCase(ret)) {
  // return;
  // }
  //
  // if (OrderProtocolConstant.PARTNER_PREPARE_DELIVER.equalsIgnoreCase(ret)) {
  // action.delivering(message);
  // }else if(OrderProtocolConstant.PARTNER_DELIVER_SUCCESS.equalsIgnoreCase(ret)) {
  // action.deliverSuccess(message);
  // }else if(OrderProtocolConstant.PARTNER_TRADE_SUCCESS.equalsIgnoreCase(ret)) {
  // action.tradeSuccess(message);
  // }else if(OrderProtocolConstant.PARTNER_REFUND_ORDER.equalsIgnoreCase(ret)) {
  // action.refundOrder(message);
  // }else if(OrderProtocolConstant.PARTNER_DELIVER_FAIL.equalsIgnoreCase(ret)) {
  // action.deliverFail(message);
  // }else if(OrderProtocolConstant.PARTNER_DELIVER_PARTIAL_SUCCESS.equalsIgnoreCase(ret)) {
  // action.deliverHalfSuccess(message);
  // }else if(OrderProtocolConstant.PARTNER_PAY_SUCCESS.equalsIgnoreCase(ret)){
  // //保险重查订单状态返回，不会返回状态之外的其他信息
  // List<Order> orderList = new ArrayList<Order>();
  // orderList.add(action.getOrder());
  // orderList.add(action.getOrder());
  // try{
  // action.paySuccess(url, orderList);
  // }catch(Exception e){
  // OrderAlerter.alert("[合作方订单状态为支付成功，消息处理失败]");
  // }
  // }else if(OrderProtocolConstant.PARTNER_CLOSS_ORDER.equalsIgnoreCase(ret)){
  // action.closeOrder("[合作方订单状态为关闭状态]");
  // }else if(OrderProtocolConstant.PARTNER_ALERT.equalsIgnoreCase(ret)) {
  // OrderAlerter.alert("收到异常订单消息. 当前状态：" + order.getOrderStatusName() + "; 消息内容：" +
  // message.getTextMessage() + "; 订单 id = " + order.getId());
  // }else if(null!=ret &&
  // OrderProtocolConstant.PARTNER_LESS_ACCOUNT_BALANCE.equalsIgnoreCase(ret)){
  // order.setMerchantOrderId(OrderProtocolConstant.PARTNER_LESS_ACCOUNT_BALANCE);
  // orderNotifyDispatch.dispatchNotice(order);
  // }else{
  // OrderAlerter.alert("收到异常订单消息. 当前状态：" + order.getOrderStatusName() + "; 消息内容：" +
  // message.getTextMessage() + "; 订单 id = " + order.getId());
  // }
  // }
  //



  public List<Order> queryLatestFailOrderByMerchantProductId(String merchantPrctId) {
    return this.orderDao.queryLatestFailOrderByMerchantProductId(merchantPrctId);
  }

  public String queryLogisticsId(String logisticsName) {
    return orderDao.queryLogisticsId(logisticsName);
  }

  public QueryService getQueryService() {
    return queryService;
  }

  public void setQueryService(QueryService queryService) {
    this.queryService = queryService;
  }

  public UpdateService getUpdateService() {
    return updateService;
  }

  public void setUpdateService(UpdateService updateService) {
    this.updateService = updateService;
  }

}
