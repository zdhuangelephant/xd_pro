package com.xiaodou.oms.service.facade;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.xiaodou.oms.constant.order.FraudConstant;
import com.xiaodou.oms.constant.order.TagConstant;
import com.xiaodou.oms.dao.order.GorderDao;
import com.xiaodou.oms.dao.order.OrderDao;
import com.xiaodou.oms.entity.order.Gorder;
import com.xiaodou.oms.entity.order.Order;
import com.xiaodou.oms.entity.order.OrderItem;
import com.xiaodou.oms.entity.order.PayRecord;
import com.xiaodou.oms.entity.order.PayRequest;
import com.xiaodou.oms.entity.order.WayBill;
import com.xiaodou.oms.exception.OrderException;
import com.xiaodou.oms.exception.ValidationException;
import com.xiaodou.oms.service.oms.ShopTagService;
import com.xiaodou.oms.service.order.CascadeQueryService;
import com.xiaodou.oms.service.order.GorderService;
import com.xiaodou.oms.service.order.OrderItemService;
import com.xiaodou.oms.service.order.PayService;
import com.xiaodou.oms.service.order.QueryService;
import com.xiaodou.oms.service.order.UpdateService;
import com.xiaodou.oms.service.order.WayBillService;
import com.xiaodou.oms.util.IDGenerator;
import com.xiaodou.oms.vo.UpdateBuyAccountIdByGorderIdResponse;
import com.xiaodou.oms.vo.input.order.UpdateBuyAccountIdByGorderIdPojo;
import com.xiaodou.oms.vo.input.pay.ContinuePayPojo;
import com.xiaodou.oms.vo.input.pay.GetTokenPojo;
import com.xiaodou.oms.vo.input.pay.QueryFirstPayInfoPojo;
import com.xiaodou.oms.vo.input.pay.QueryOrderPayDetailPojo;
import com.xiaodou.oms.vo.input.pay.QueryRecordPojo;
import com.xiaodou.oms.vo.result.ResultType;
import com.xiaodou.oms.vo.result.pay.ContinuePayVO;
import com.xiaodou.oms.vo.result.pay.FirstPayInfoVO;
import com.xiaodou.oms.vo.result.pay.GetTokenVO;
import com.xiaodou.oms.vo.result.pay.PayRecordListVO;
import com.xiaodou.payment.vo.PayMerchant;
import com.xiaodou.payment.vo.response.PayDetailResponse;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * @author zhaoyang
 * @version V1.0
 * @Title:OrderServiceFacade.java
 * @Description:OrderServiceFacade实现类
 * @date Jan 21, 2014 1:53:21 PM
 */
@Service("orderServiceFacade")
public class OrderServiceFacadeImpl implements OrderServiceFacade {

  @Resource
  private GorderService gorderService;

  @Resource
  private OrderItemService orderItemService;

  @Resource
  private QueryService queryService;

  @Resource
  private UpdateService updateService;

  @Resource
  private CascadeQueryService cascadeQueryService;

  @Resource
  private ThreadPoolTaskExecutor taskExecutor;

  @Resource
  private PayService payService;

  @Resource
  GorderDao gorderDao;

  @Resource
  OrderDao orderDao;

  @Resource
  WayBillService wayBillService;

  @Resource
  ShopTagService shopTagService;


  @Override
  public Gorder createGorder(String productLine, Gorder gorder, String relationType,
      String relations) throws ValidationException, OrderException {
    return gorderService.createGorder(gorder, relationType, relations);
  }

  @Override
  public Gorder createGorder(String productLine, Gorder gorder, String relationType,
      String relations, String fraudJson) throws ValidationException, OrderException {
    return gorderService.createGorder(gorder, relationType, relations, fraudJson);
  }

  @Override
  public void updateOrder(Order order, String remark, String remoteIp) throws ValidationException,
      OrderException {
    updateService.updateOrder(order, remark, remoteIp);
  }

  @Override
  public void updateOrderNote(String orderId, String note) throws ValidationException,
      OrderException {
    updateService.updateOrderNote(orderId, note);
  }

  @Override
  public void updateOrderStatus(String orderId, Integer orderStatus, String note, String remoteIp)
      throws ValidationException, OrderException {
    Order order = new Order();
    order.setId(orderId);
    order.setOrderStatus(orderStatus);
    updateService.updateOrderStatus(order, note, remoteIp);
  }

  // @Override
  // public void closePreDeterminedGorder(String productType, String beginOrderDate,String
  // endOrderDate, List<Integer> orderStatusList) throws ValidationException,
  // OrderException {
  // if (productType == null) {
  // throw new RuntimeException("productType不能为空");
  // }
  // if (beginOrderDate != null && beginOrderDate.length() != 8) {
  // throw new RuntimeException("beginOrderDate格式必须为yyyymmdd");
  // }
  // if (endOrderDate != null && endOrderDate.length() != 8) {
  // throw new RuntimeException("endOrderDate格式必须为yyyymmdd");
  // }
  //
  // Map input = new HashMap();
  // input.put("productType", productType);
  // input.put("preCloseTimeUpper", new Timestamp(System.currentTimeMillis()));
  // input.put("gorderStatus", OrderConstant.STATUS_INITIATE);
  // input.put("orderDescend", "1");
  //
  // if (beginOrderDate != null) {
  // input.put("idLower", beginOrderDate);
  // }
  // if (endOrderDate != null) {
  // input.put("idUpper", endOrderDate);
  // }
  //
  // Map output = new HashMap();
  // output.put("id", "1");
  // output.put("gorderStatus", "1");
  // output.put("note", "1");
  //
  // // List<Gorder> listGorder = queryGOrderList(input,output);
  // Page<Gorder> page = new Page<Gorder>();
  // page.setPageNo(1);
  // page.setPageSize(1000);
  // Page pageResult = queryPagedGorderList(input, output, page);
  // this.closeGorder(pageResult.getResult(), orderStatusList);
  //
  // }
  //
  // /*
  // * 关闭订单，后台主动关闭订单
  // */
  // private String closeGorder(List<Gorder> listGorder, List<Integer> orderStatusList) {
  // if (null == listGorder || listGorder.size() <= 0) {
  // return "";
  // }
  // InetAddress addr = null;
  // try {
  // addr = InetAddress.getLocalHost();
  // } catch (Exception e) {
  // // LoggerUtil.error("获取IP失败",e);
  // }
  // String ip = (addr != null ? addr.getHostAddress() : "127.0.0.1");
  //
  // for (Gorder gorder : listGorder) {
  //
  // // gorderService.closeGorder(gorder, ip, "后台关闭订单", orderStatusList);
  // orderTaskExecutor.execute(new CloseOrderTask(gorderService, gorder, ip, "后台关闭订单",
  // orderStatusList));
  // }
  // return "";
  // }


  @Override
  public Order queryOrderDetail(String id) throws ValidationException, OrderException {
    return this.queryService.queryOrderDetailNoLock(id);
  }

  @SuppressWarnings("rawtypes")
  @Override
  public OrderItem queryCascadeOrderDetail(String orderItemId, Map output)
      throws ValidationException, OrderException {
    return this.cascadeQueryService.queryOrderItemDetail(orderItemId, output);
  }

  @SuppressWarnings("rawtypes")
  @Override
  public OrderItem queryCascadeOrderDetail(Map input, Map output) throws ValidationException,
      OrderException {
    return this.cascadeQueryService.queryOrderItemDetail(input, output);
  }

  @SuppressWarnings("rawtypes")
  @Override
  public Page<OrderItem> queryPagedCascadeOrderList(Map parameterMap, Map outputMap,
      Page<OrderItem> page) throws ValidationException, OrderException {
    return this.cascadeQueryService.queryPagedOrderItemList(parameterMap, outputMap, page);
  }

  /**
   * queryPagedOrderOrderItemList
   * 
   * @param queryParam
   * @param outputMap
   * @param page
   * @return
   * @Title: queryPagedOrderOrderItemList
   * @Description: order - orderItem 两级联查
   */
  public Page<OrderItem> queryPagedOrderOrderItemList(Map<String, Object> queryParam,
      Map<String, Object> outputMap, Page<OrderItem> page) throws ValidationException,
      OrderException {
    return this.cascadeQueryService.queryPagedOrderOrderItemList(queryParam, outputMap, page);
  }

  @SuppressWarnings("rawtypes")
  @Override
  public Order queryCascadeGorderOrderDetail(Map input, Map output) throws ValidationException,
      OrderException {
    return this.cascadeQueryService.queryGorderOrderDetail(input, output);
  }

  @Override
  public boolean closeGorder(Gorder gorder, String ip, String note, List<Integer> orderStatusList,
      String closedReason, boolean isNeedCloseOrderItem, String uuid) throws ValidationException,
      OrderException {
    Gorder dbGorder = this.queryGorderDetailById(gorder.getId());
    this.payService.createPayRequest(dbGorder.getGpayAmount(), dbGorder.getBuyAccountId(),
        dbGorder.getProductType(), dbGorder.getId(), PayRecord.OPER_TYPE_REFUND,
        PayRequest.PAY_TYPE_ONE, "refund_notify.do", uuid);
    return this.gorderService.closeGorder(gorder, ip, note, orderStatusList, closedReason,
        isNeedCloseOrderItem);
  }

  @Override
  public boolean closeGorderWithOutRefund(Gorder gorder, String ip, String note,
      List<Integer> orderStatusList, String closedReason, boolean isNeedCloseOrderItem)
      throws ValidationException, OrderException {
    return this.gorderService.closeGorder(gorder, ip, note, orderStatusList, closedReason,
        isNeedCloseOrderItem);
  }

  @SuppressWarnings("rawtypes")
  @Override
  public Order queryOrderDetail(String id, Map output) throws ValidationException, OrderException {
    return this.queryService.queryOrderDetail(id, output);
  }

  @SuppressWarnings("rawtypes")
  @Override
  public Order queryOrderDetail(Map input, Map output) throws ValidationException, OrderException {
    return this.queryService.queryOrderDetail(input, output);
  }

  @Override
  public Gorder gorderPayCallback(Integer type, Gorder gorder, String remoteIp)
      throws ValidationException, OrderException {
    return this.gorderService.gorderPayCallback(type, gorder, remoteIp);
  }

  @Override
  public void addPayRequest(PayRequest payRequest) throws ValidationException, OrderException {
    payService.addPayRequest(payRequest);
  }

  @Override
  public void addPayRecord(PayRecord payRecord) throws ValidationException, OrderException {
    payService.addPayRecord(payRecord);
  }


  @Override
  public Page<PayRequest> queryPagedPayRequestList(Map<String, Object> input,
      Map<String, Object> output, Page<PayRequest> page) throws ValidationException, OrderException {
    return payService.queryPayRequestList(input, output, page);
  }

  @SuppressWarnings("rawtypes")
  @Override
  public Page<PayRecord> queryPagedPayRecordList(Map input, Map output, Page page)
      throws ValidationException, OrderException {
    return payService.queryPayRecordList(input, output, page);
  }

  @Override
  public PayRecord queryLastPayNo(String gorderId) throws ValidationException, OrderException {
    PayRecord payRecord = new PayRecord();
    payRecord.setGorderId(gorderId);
    return payService.queryLastPayNo(payRecord);
  }

  @Override
  public void paymentCallback(String payNo, Integer paymentStatus, Timestamp paymentTime,
      String failureReason, String processDays) throws ValidationException, OrderException {
    // 根据payNo找到唯一的这一条payRecord
    PayRecord currPayRecord = this.queryPayRecordByPayNo(payNo);

    // 判断状态，必须是处理中或者失败，才修改状态
    // 如果是成功，则不修改，避免先回调支付成功 后回调支付失败的情况
    if (currPayRecord.getPayStatus().equals(PayRecord.PAY_STATUS_FAILURE)
        || currPayRecord.getPayStatus().equals(PayRecord.PAY_STATUS_PROCESSING)) {
      PayRecord condition = new PayRecord();
      condition.setPayNo(payNo);
      PayRecord entity = new PayRecord();
      entity.setPayStatus(paymentStatus);
      entity.setPaymentTime(paymentTime);
      entity.setFailureReason(failureReason);
      entity.setProcessDays(processDays);
      payService.updatePayRecord(condition, entity);
    }
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  public WayBill queryWayBillByGorderId(String gorderId) throws ValidationException, OrderException {
    if (StringUtils.isBlank("gorderId")) {
      throw new ValidationException("gorderId为空");
    }
    Map input = new HashMap();
    input.put("gorderId", gorderId);

    Map cond = new HashMap();
    cond.put("input", input);
    Page result = wayBillService.findEntityListByCond(cond, null);

    if (result == null) {
      return null;
    } else if (result.getResult() == null || result.getResult().size() == 0) {
      return null;
    } else {
      return (WayBill) result.getResult().get(0);
    }
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public int continuePay(String gorderId) throws ValidationException, OrderException {

    if (StringUtils.isBlank(gorderId)) {
      return 5;
    }
    Map input = new HashMap();
    input.put("id", gorderId);
    input.put("forUpdate", "1");
    Map output = new HashMap();
    output.put("id", "1");
    output.put("gorderStatus", "1");

    Gorder gorder = gorderService.queryGorderDetail(input, output);

    return payService.continuePay(gorder);
  }

  public ContinuePayVO continuePay(ContinuePayPojo pojo) throws ValidationException, OrderException {
    int result = continuePay(pojo.getGorderId());
    switch (result) {
      case 0:
        return new ContinuePayVO(ResultType.SUCCESS);
      case 1:
        return new ContinuePayVO(20301, "订单不存在");
      case 2:
        return new ContinuePayVO(20302, "订单状态不是支付失败");
      case 5:
        return new ContinuePayVO(20305, "订单Id为空");
      default:
        return new ContinuePayVO(20306, "orderServiceFacade.continuePay返回值错误");

        // 后续需要调用payment是否可继续支付接口
    }
  }

  @Override
  public GetTokenVO getToken(GetTokenPojo pojo) {
    return payService.getToken(pojo);
  }

  public void updateGorder(Gorder gorder, String ip) {
    gorderService.updateGorderAndOrderList(gorder, ip);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public Order queryOrderByPayNo(String payNo) throws ValidationException, OrderException {
    Map input = new HashMap();
    input.put("payNo", payNo);

    Map output = new HashMap();
    output.put("gorderId", "1");
    output.put("orderId", "1");
    output.put("productType", "1");

    Page page = payService.queryPayRecordList(input, output, null);

    if (page.getResult().size() != 0) {
      PayRecord payRecord = (PayRecord) page.getResult().get(0);
      Order order = new Order();
      order.setProductType(payRecord.getProductType());
      order.setGorderId(payRecord.getGorderId());
      order.setId(payRecord.getOrderId());
      return order;
    } else {
      return null;
    }
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public PayRecord queryPayRecordByPayNo(String payNo) throws ValidationException, OrderException {
    Map input = new HashMap();
    input.put("payNo", payNo);

    Map output = new HashMap();
    output.put("gorderId", "1");
    output.put("orderId", "1");
    output.put("productType", "1");
    output.put("uuid", "1");
    output.put("createTime", "1");
    output.put("outerOrigin", "1");
    output.put("buyAccountId", "1");
    output.put("payStatus", "1");

    Page<PayRecord> page = payService.queryPayRecordList(input, output, null);

    if (page != null && page.getResult() != null && page.getResult().size() != 0) {
      return page.getResult().get(0);
    } else {
      return null;
    }
  }

  public void movePayRequestToRecord(String payRequestId, String processDays, Integer payStatus,
      String failReason) throws ValidationException, OrderException {
    this.payService.movePayRequestToRecord(payRequestId, processDays, payStatus, failReason);
  }

  public void movePayRequestToRecord(PayRequest payRequest, PayRecord payRecord)
      throws ValidationException, OrderException {
    this.payService.movePayRequestToRecord(payRequest, payRecord);
  }

  public Integer updateWayBill(WayBill condition, WayBill wayBill) throws ValidationException,
      OrderException {
    return this.wayBillService.updateEntity(condition, wayBill);
  }


  public boolean updatePayRequest(PayRequest condition, PayRequest entity)
      throws ValidationException, OrderException {
    return this.payService.updatePayRequest(condition, entity);
  }

  public boolean updatePayRecord(PayRecord condition, PayRecord entity) throws ValidationException,
      OrderException {
    return this.payService.updatePayRecord(condition, entity);
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  public Page<WayBill> queryPagedWayBillList(Map parameterMap, Page<WayBill> page)
      throws ValidationException, OrderException {
    Map cond = new HashMap();
    cond.put("input", parameterMap);
    cond.put("output", null);
    return this.wayBillService.findEntityListByCond(cond, page);

  }

  @Override
  public PayRecordListVO queryPayRecordList(QueryRecordPojo pojo) throws ValidationException,
      OrderException {
    return payService.queryPayRecordList(pojo);
  }

  @Override
  public List<PayRecord> queryFirstPayRecord(Gorder gorder) {
    return payService.queryFirstPayRecord(gorder);
  }

  /**
   * 存在并发问题，但只要不是毫秒级用同一个gorderId调用就没事 有多台服务器，synchronized也不管用
   * 多列唯一索引(gorder_id,oper_type,pay_type)也不能加，因为退款可能有多条
   * <p/>
   * gorder update的时候，sql语句写where id = x and pay_order_id is null,
   * 如果更新成功，才addPayRecord，更新不成功，递归queryFirstPayInfo(QueryFirstPayInfoPojo pojo) 然后if (payRecords ==
   * null || payRecords.size() == 0){}里面最后一行改成递归queryFirstPayInfo(QueryFirstPayInfoPojo pojo) 可破。
   * 未来如果发生并发问题，可以这么改
   * 
   * @param pojo
   * @return
   */
  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public FirstPayInfoVO queryFirstPayInfo(QueryFirstPayInfoPojo pojo) {
    // 1 找到g order
    Map input = new HashMap();
    input.put("id", pojo.getGorderId());
    Map output = new HashMap();
    output.put("id", "");
    output.put("gpayAmount", "");
    output.put("productType", "");
    output.put("payOrderId", "");
    output.put("buyAccountId", "");
    output.put("clientType", "");
    output.put("outerOrigin", "");
    Gorder gorder = gorderService.queryGorderDetail(input, output);
    if (gorder == null) {
      return new FirstPayInfoVO(ResultType.UNKNOWNPAYMENT);
    }

    // 2 找到g order的第一次支付 payRecord
    Map<String, Object> payRecordInput = new HashMap<String, Object>();
    payRecordInput.put("gorderId", pojo.getGorderId());
    payRecordInput.put("operType", PayRequest.OPER_TYPE_PAY);
    payRecordInput.put("payType", PayRequest.PAY_TYPE_ONE);
    Map<String, Object> payRecordOutput = new HashMap<String, Object>();
    payRecordOutput.put("id", "1");
    payRecordOutput.put("amount", "1");
    payRecordOutput.put("payNo", "1");
    Page<PayRecord> payRecordPage =
        payService.queryPayRecordList(payRecordInput, payRecordOutput, null);
    List<PayRecord> payRecords = payRecordPage.getResult();
    PayRecord payRecord = null;
    if (payRecords == null || payRecords.size() == 0) {
      // FirstPayInfoVO vo = new FirstPayInfoVO(ResultType.UNKNOWNPAYMENT);
      // return vo;
      GetTokenPojo getTokenPojo = new GetTokenPojo();
      getTokenPojo.setProductLine(gorder.getProductType().substring(0, 2));
      GetTokenVO getTokenVO = payService.getToken(getTokenPojo);
      if (!getTokenVO.getRetcode().equals("0")) {
        FirstPayInfoVO firstPayInfoVO = new FirstPayInfoVO(ResultType.UNKNOWNPAYMENT);
        firstPayInfoVO.setRetdesc("获取token失败,请重试");
        return firstPayInfoVO;
      }

      PayRecord pr = new PayRecord();
      pr.setId(IDGenerator.getSeqID("xd_order_sequence_id"));
      pr.setAmount(gorder.getGpayAmount());
      pr.setBuyAccountId(gorder.getBuyAccountId());
      pr.setGorderId(gorder.getId());
      pr.setOperType(PayRequest.OPER_TYPE_PAY);
      pr.setPayNo(getTokenVO.getToken());
      pr.setPayStatus(PayRequest.PAY_STATUS_PROCESSING);
      pr.setProductType(gorder.getProductType());
      pr.setPayType(PayRequest.PAY_TYPE_ONE);
      pr.setClientType(gorder.getClientType());
      pr.setOuterOrigin(gorder.getOuterOrigin());
      Timestamp timestamp = new Timestamp(System.currentTimeMillis());
      pr.setCreateTime(timestamp);

      List<Order> orderList = this.queryOrderListByGorderId(pojo.getGorderId());
      if (orderList != null) {
        if (orderList.size() == 1) {
          pr.setOrderId(orderList.get(0).getId());
        } else if (gorder.getProductType().equals("0501") || gorder.getProductType().equals("1201")) {
          pr.setOrderId(orderList.get(0).getId());
        }
      }

      // 修改gorder的payOrderId
      gorder.setPayOrderId(getTokenVO.getToken());
      gorderService.updateGorder(gorder);

      // 代付模式需要设置fraudRequest表
      if (FraudConstant.PRODUCT_LINE_SELF_PAY.equals(gorder.getProductType().substring(0, 2))) {
        Map<String, Object> updateEntity = new HashMap<>();
        updateEntity.put("payNo", getTokenVO.getToken());
      }

      payService.addPayRecord(pr);
      payRecord = pr;
    } else {
      payRecord = payRecords.get(0);
    }

    // 3 拼装参数
    FirstPayInfoVO vo = new FirstPayInfoVO(ResultType.SUCCESS);
    vo.setPayAmount(gorder.getGpayAmount().doubleValue());
    vo.setTradeNo(payRecord.getPayNo());
    vo.setNotifyUrl(PayMerchant.getCallbackHost() + "first_pay_notify.do");
    return vo;
  }

  @SuppressWarnings({"rawtypes", "unchecked"})
  @Override
  public PayDetailResponse queryOrderPayDetail(QueryOrderPayDetailPojo pojo) {
    // 1 找到g order
    Map input = new HashMap();
    input.put("id", pojo.getGorderId());
    Map output = new HashMap();
    output.put("id", "");
    output.put("productType", "");
    Gorder gorder = gorderService.queryGorderDetail(input, output);
    if (gorder == null) {
      PayDetailResponse response = new PayDetailResponse();
      response.setRetcode("-1");
      response.setRetdesc("无此订单");
    }
    return payService.queryOrderPayDetail(gorder);
  }

  public boolean addOrderItem(OrderItem orderItem) throws ValidationException, OrderException {
    return orderItemService.addOrderItem(orderItem);
  }

  @SuppressWarnings("rawtypes")
  public Page<Order> queryPagedOrderList(Map parameterMap, Map outputMap, Page<Order> page)
      throws ValidationException, OrderException {
    return cascadeQueryService.queryPagedOrderList(parameterMap, outputMap, page);
  }

  @SuppressWarnings("rawtypes")
  @Override
  public Page<Order> queryPagedSingleOrderList(Map parameterMap, Map outputMap, Page<Order> page) {
    return this.queryService.queryPagedOrderList(parameterMap, outputMap, page);
  }

  @SuppressWarnings("rawtypes")
  @Override
  public PayRecord queryLastFailedPayRecord(Map input, Map output) throws ValidationException,
      OrderException {
    List<PayRecord> payRecordList = queryPagedPayRecordList(input, output, null).getResult();
    int size = payRecordList.size();

    if (size > 0) {
      return payRecordList.get(size - 1);
    } else {
      return null;
    }
  }

  @SuppressWarnings("rawtypes")
  @Override
  public Page<Gorder> queryPagedGorderList(Map inputArgument, Map output, Page<Gorder> page) {
    return gorderService.getPagedGorderList(inputArgument, output, page);
  }

  /*
   * ================================================================================================
   * ================
   */
  @Override
  public List<String> createGorderList(List<Gorder> guorderList, String buyAccountId) {
    return gorderService.createGorderList(guorderList, buyAccountId);
  }


  // @Override
  // public List queryGOrderList(Map input) {
  // return gorderService.queryGOrderList(input);
  // }
  //
  // @Override
  // public List queryGOrderList(Map input,Map output) {
  // return gorderService.queryGOrderList(input,output);
  // }
  //

  //
  // @Override
  // public Page<Gorder> queryPagedGorderList(Map inputArgument,Integer pageSize,Integer pageNumber)
  // {
  // return gorderService.getPagedGorderList(inputArgument,pageSize,pageNumber);
  // }

  // @Override
  // public void deliver(Order order) {
  // this.orderService.deliver(order);
  // }

  // @Override
  // public String genPayUrl(Order order, String paymethod) throws Exception {
  // return this.orderService.genPayUrl(order, paymethod);
  // }

  // @Override
  // public String pay(Order order, String paymethod) throws Exception {
  // return this.orderService.pay(order, paymethod);
  // }

  // @Override
  // public String pay(Gorder gorder, String paymethod,boolean feeIndex) throws Exception {
  // return this.gorderService.createEpayRequest(gorder, paymethod, feeIndex);
  // }

  // @Override
  // public String pay(Gorder gorder, String paymethod) throws Exception {
  // return this.gorderService.createEpayRequest(gorder, paymethod, false);
  // }
  // @Override
  // public boolean lockInventory(Order order) {
  // return this.orderService.lockInventory(order);
  // }

  // @Override
  // public void deliverResult(Order order, ChargeResultMsg message, String url) {
  // this.orderService.deliverResult(order, message, url);
  // }

  @Override
  public boolean checkExistence(String orderId) {
    return this.queryService.checkExistence(orderId);
  }

  @SuppressWarnings("rawtypes")
  @Override
  public List<Order> queryUndeliveredList(Map condition) {
    return this.queryService.queryUndeliveredList(condition);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public List queryOrderList(Map input) {
    return this.queryService.queryOrderList(input);
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Override
  public List queryOrderListByGorderId(String gorderId) {
    return this.queryService.queryOrderListByGorderId(gorderId);
  }

  // @Override
  // public Page<Order> queryPagedOrderList(Map inputArgument, Integer pageSize,Integer pageNumber){
  // return this.queryService.queryPagedOrderList(inputArgument,pageSize, pageNumber);
  // }

  @SuppressWarnings("rawtypes")
  @Override
  public Integer updateGorder(Map condition, Gorder gorder) {
    return this.gorderService.updateGorder(condition, gorder);
  }

  @SuppressWarnings("rawtypes")
  @Override
  public Integer updateOrderItem(Map codition, OrderItem orderItem) {
    return this.orderItemService.updateOrderItem(codition, orderItem);
  }

  @SuppressWarnings("rawtypes")
  @Override
  public Order queryOrderDetail(Map input) {
    return this.queryService.queryOrderDetail(input);
  }


  @SuppressWarnings("rawtypes")
  @Override
  public List<OrderItem> queryOrderItemList(Map condition) {
    return this.orderItemService.queryOrderItemList(condition);
  }

  // @Override
  // public Page<OrderItem> pagedQuery(Map condition, Integer pageSize,
  // Integer pageNo) {
  // return this.orderItemService.pagedQuery(condition, pageSize, pageNo);
  // }

  // @Override
  // public String autoCloseGorder(int interval) throws IOException {
  // return this.gorderService.autoCloseGorder(interval);
  // }

  // @Override
  // public String createEpayRequest(List<Gorder> gorderList, String paymethod,
  // boolean feeIndex){
  // try {
  // return gorderService.createEpayRequest(gorderList, paymethod, feeIndex);
  // } catch (Exception e) {
  // LoggerUtil.alarmInfo("[支付模块][一般][生成支付请求产生未知异常！]");
  // return null;
  // }
  // }

  // @Override
  // public String createEpayRequestById(String[] arrayGorderId,
  // String paymethod, boolean feeIndex) throws Exception {
  // return this.gorderService.createEpayRequestById(arrayGorderId, paymethod, feeIndex);
  // }

  // @Override
  // public String createEpayRequest(Gorder gorder, String paymethod,
  // boolean feeIndex) throws Exception{
  // return this.gorderService.createEpayRequest(gorder, paymethod, feeIndex);
  // }


  // @Override
  // public void closePreDeterminedOrder(Map input) {
  // this.orderService.closePreDeterminedOrder(input);
  //
  // }

  @Override
  public List<OrderItem> queryOrderItemByOrderId(String orderId) {
    return this.orderItemService.queryByOrderId(orderId);
  }

  @Override
  public Gorder queryGorderDetailById(String gorderId) {
    return this.gorderService.queryGorderDetail(gorderId);
  }

  @SuppressWarnings("rawtypes")
  @Override
  public Gorder queryGorderDetail(Map input, Map output) throws ValidationException, OrderException {
    return this.gorderService.queryGorderDetail(input, output);
  }

  @Override
  public OrderItem queryOrderItemDetailById(String orderItemId) {
    return this.orderItemService.getOrderItemById(orderItemId);
  }

  // @Override
  // public void tradeSuccess(String orderId){
  // this.orderService.tradeSuccess(orderId);
  // }

  /**
   * 接口58：根据大订单号查找用户一次下单所购买的商品列表
   * 
   * @param gorderId
   * @return
   */
  @Override
  public List<OrderItem> queryOrderItemListByGorderId(String gorderId) {
    return this.orderItemService.queryItemListByGorderid(gorderId);
  }

  @Override
  public OrderItem queryOrderItemDetail(Map<String, Object> inputParam,
      Map<String, Object> outputParam) {
    return this.orderItemService.queryOrderItemDetail(inputParam, outputParam);
  }

  @SuppressWarnings("rawtypes")
  @Override
  public List<OrderItem> queryOrderItemList(Map condition, Map output) {
    return this.orderItemService.queryOrderItemList(condition, output);
  }

  @Override
  public void updateItemNote(String orderItemId, String note) throws ValidationException,
      OrderException {
    updateService.updateItemNote(orderItemId, note);
  }

  /**
   * override updateBuyAccountIdByGorderId
   * <p>
   * Title: updateBuyAccountIdByGorderId
   * </p>
   * <p>
   * Description:
   * </p>
   * 
   * @param pojo
   * @return
   */
  @Override
  public UpdateBuyAccountIdByGorderIdResponse updateBuyAccountIdByGorderId(
      UpdateBuyAccountIdByGorderIdPojo pojo) {
    // TODO Auto-generated method stub
    UpdateBuyAccountIdByGorderIdResponse response = new UpdateBuyAccountIdByGorderIdResponse();

    if (pojo == null || pojo.getGorder() == null) {
      response.setRetcode(-1);
      response.setRetdesc("request is null or empty.");
      return response;
    }
    Gorder gorder = pojo.getGorder();
    Map<String, Object> input = new HashMap<String, Object>();
    input.put("gorderId", gorder.getId());
    int updateOrderResult = updateService.updateBuyAccountIdByGorderId(input, gorder);
    if (updateOrderResult <= 0) {
      response.setRetcode(1);
      response.setRetdesc("update order fail, order does not exists, the gorderId = "
          + gorder.getId());
      return response;
    }

    int updateGOrderResult = gorderService.updateBuyAccountIdByGorderId(input, gorder);
    if (updateGOrderResult <= 0) {
      response.setRetcode(1);
      response.setRetdesc("update gorder fail, gorder does not exists, the gorderId = "
          + gorder.getId());
      return response;
    }

    response.setRetdesc("success");

    return response;
  }

  @Override
  public void updateGorderPreCloseTime(String gorderId, Timestamp preCloseTime) {
    gorderService.updateGorderPreCloseTime(gorderId, preCloseTime).equals(1);
  }

  @Override
  public boolean queryTagForStart(String tag) {
    if (null != shopTagService.queryTagForUpdate(tag)
        && TagConstant.Init.equals(shopTagService.queryTagForUpdate(tag).getStatus())) return true;
    return false;
  }

  @Override
  public boolean updateTagForFinish(String tag) {
    return shopTagService.updateTagStatus(tag, TagConstant.Init);
  }

  // @Override
  // public boolean transferSuccess(TransferOrder transferOrder) {
  // try {
  // transferOrder = rsService.getTransferOrderByPkForUpdate(transferOrder.getTransferId());
  // if(null == transferOrder){
  // LoggerUtil.alarmInfo("订单转账记录" + transferOrder.getTransferId() + "不存在，不允许打款");
  // return false;
  // }
  // /** 先判断是否需要打款 */
  // if(OrderConstant.VALID.equals(transferOrder.getStatus())){
  // LoggerUtil.alarmInfo("订单" + transferOrder.getTransferId() + "的已经完成打款, 不允许重复打款");
  // return true;
  // }
  // LoggerUtil.epayInfo("打款订单id：" + transferOrder.getTransferId() + "|||大订单ID：" +
  // transferOrder.getGorderId()+"小订单ID:"+transferOrder.getOrderId());
  // //打款成功后更新转账表中对应订单转账记录的转账状态
  // Order order = queryOrderDetail(transferOrder.getOrderId());
  // if(null != order && (order.getOrderStatus().equals(OrderConstant.STATUS_INITIATE)
  // ||order.getOrderStatus().equals(OrderConstant.STATUS_CLOSED))){
  // LoggerUtil.alarmInfo("[支付模块][一般][非法的转账/打款请求，订单ID："+order.getId());
  // return false;
  // }
  // if(null == order){
  // Gorder gorder = gorderService.queryGorderDetail(transferOrder.getGorderId());
  // if(null == gorder || (gorder.getGorderStatus().equals(OrderConstant.STATUS_INITIATE)
  // ||gorder.getGorderStatus().equals(OrderConstant.STATUS_CLOSED))){
  // LoggerUtil.alarmInfo("[支付模块][一般][非法的转账/打款请求，大订单ID："+gorder.getId());
  // return false;
  // }
  // }
  // String transferId = transferOrder.getTransferId();
  // rsService.updateTransferStatus(transferId);
  // // LoggerUtil.epayInfo("打款订单id：" + order.getId() + "打款成功");
  // if(null != transferOrder.getTransferType()
  // &&transferOrder.TYPE_B2C_TO_MERCHANT == transferOrder.getTransferType()){
  // IOrderAction action = OrderActionProxy.createOrderAction(order, orderService);
  // action.settleUp();
  // }
  // return true;
  // }catch (Exception e) {
  // String msg = "调用网易宝打款接口失败|||大订单ID：" +
  // transferOrder.getGorderId()+"小订单ID:"+transferOrder.getOrderId();
  // LoggerUtil.epayInfo(msg);
  // LoggerUtil.error(msg, e);
  // throw new RuntimeException(e);
  // }
  // }

  // @Override
  // public boolean refundResult(RefundOrder refundOrder, boolean refundResult) {
  // try{
  // refundOrder = refundDao.getRefundOrderByPkForUpdate(refundOrder.getRefundId());
  // if(null == refundOrder){
  // LoggerUtil.alarmInfo("订单退款记录" + refundOrder.getRefundId() + "不存在，不允许退款");
  // return false;
  // }
  // /** 先判断是否需要退款 */
  // if(OrderConstant.VALID.equals(refundOrder.getStatus())){
  // LoggerUtil.alarmInfo("订单退款记录" +refundOrder.getRefundId() + "的已经完成打款, 不允许重复退款");
  // return true;
  // }
  // Order order = this.queryOrderDetail(refundOrder.getOrderId());
  // //初始状态及订单关闭状态不允许打款
  // if(null != order && (order.getOrderStatus().equals(OrderConstant.STATUS_INITIATE)
  // ||order.getOrderStatus().equals(OrderConstant.STATUS_CLOSED))){
  // LoggerUtil.alarmInfo("[支付模块][一般][非法的退款，订单ID："+order.getId());
  // return false;
  // }
  // if(null == order){
  // Gorder gorder = gorderService.queryGorderDetail(refundOrder.getGorderId());
  // if(null == gorder || (gorder.getGorderStatus().equals(OrderConstant.STATUS_INITIATE)
  // ||gorder.getGorderStatus().equals(OrderConstant.STATUS_CLOSED))){
  // LoggerUtil.alarmInfo("[支付模块][一般][非法的退款请求，大订单ID："+gorder.getId());
  // return false;
  // }
  // }
  // if (true == refundResult) {
  // rsService.updateRefundStatus(refundOrder.getRefundId());
  // LoggerUtil.epayInfo("退款成功，refundId:"
  // + refundOrder.getRefundId() + "|||退款" + refundOrder.getRefundAmount());
  // //非单个货品退款：整笔退款
  // if((refundOrder.getOrderItemId() == null || "".equalsIgnoreCase(refundOrder.getOrderItemId()))
  // && (null != refundOrder.getOrderId() && null != order)){
  // IOrderAction action = OrderActionProxy.createOrderAction(order, orderService);
  // action.refundSuccess("退款成功");
  // }
  // return true;
  //
  // } else {
  // LoggerUtil.epayInfo("退款失败，refundId:"
  // + refundOrder.getRefundId() + "|||退款" + refundOrder.getRefundAmount());
  // LoggerUtil.alarmInfo("[订单模块][紧急][网易宝返回退款失败，refundId:"
  // + refundOrder.getRefundId() + "|||退款" + refundOrder.getRefundAmount()+"]");
  // //非单个货品退款：整笔退款
  // if(refundOrder.getOrderItemId() == null || "".equalsIgnoreCase(refundOrder.getOrderItemId())){
  // IOrderAction action = OrderActionProxy.createOrderAction(order, orderService);
  // action.refundFail(null);
  // }
  // return false;
  // }
  // }catch(Exception e){
  // String msg = "调用网易宝打款接口失败|||大订单ID：" +
  // refundOrder.getGorderId()+"小订单ID:"+refundOrder.getOrderId();
  // LoggerUtil.epayInfo(msg);
  // LoggerUtil.error(msg, e);
  // throw new RuntimeException(e);
  // }
  // }

  // public Integer queryOrderCount(Map input){
  // return queryService.queryOrderCount(input);
  // }

  // @Override
  // public boolean zeroGorderPaySuccess(String gorderId, String buyAccountId,
  // String remoteIp) throws Exception {
  // return gorderService.zeroGorderPaySuccess(gorderId, buyAccountId, remoteIp);
  // }
  //
  // public String createGatesEpayRequest(Gorder gorder,String paymethod,boolean feeIndex)throws
  // Exception{
  // return gorderService.createGatesEpayRequest(gorder, paymethod, feeIndex);
  // }

  // @Override
  // public boolean adjustActivityGorderMoney(String gorderId,
  // BigDecimal gsaveAmount, String codeId) {
  // return gorderService.adjustActivityGorderMoney(gorderId, gsaveAmount, codeId);
  // }
  //


}
