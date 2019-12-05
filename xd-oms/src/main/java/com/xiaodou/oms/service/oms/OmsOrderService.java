package com.xiaodou.oms.service.oms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.oms.constant.order.OrderConstant;
import com.xiaodou.oms.constant.order.RepCode;
import com.xiaodou.oms.dao.OperationType;
import com.xiaodou.oms.dao.OperationTypeWrapper;
import com.xiaodou.oms.entity.order.Gorder;
import com.xiaodou.oms.entity.order.Order;
import com.xiaodou.oms.entity.order.OrderItem;
import com.xiaodou.oms.entity.order.PayRecord;
import com.xiaodou.oms.entity.order.UpdateGorderPojo;
import com.xiaodou.oms.exception.OrderRuntimeException;
import com.xiaodou.oms.exception.PreCheckFailException;
import com.xiaodou.oms.exception.UnknownTransitionException;
import com.xiaodou.oms.service.facade.OrderServiceFacade;
import com.xiaodou.oms.statemachine.Context;
import com.xiaodou.oms.statemachine.StateMachineProxy;
import com.xiaodou.oms.statemachine.param.CoreParams;
import com.xiaodou.oms.statemachine.param.PayParam;
import com.xiaodou.oms.util.ErrorsWrapper;
import com.xiaodou.oms.util.FileUtils;
import com.xiaodou.oms.util.IPUtil;
import com.xiaodou.oms.util.OrderLoggerUtil;
import com.xiaodou.oms.vo.UpdateBuyAccountIdByGorderIdResponse;
import com.xiaodou.oms.vo.input.order.CreateOrderPojo;
import com.xiaodou.oms.vo.input.order.FireListPojo;
import com.xiaodou.oms.vo.input.order.FirePojo;
import com.xiaodou.oms.vo.input.order.GorderDetailPojo;
import com.xiaodou.oms.vo.input.order.GorderListPojo;
import com.xiaodou.oms.vo.input.order.GorderOrderDetailPojo;
import com.xiaodou.oms.vo.input.order.GorderOrderItemDetailPojo;
import com.xiaodou.oms.vo.input.order.GorderOrderItemListPojo;
import com.xiaodou.oms.vo.input.order.GorderOrderListPojo;
import com.xiaodou.oms.vo.input.order.ItemNotePojo;
import com.xiaodou.oms.vo.input.order.OrderDetailPojo;
import com.xiaodou.oms.vo.input.order.OrderItemDetailPojo;
import com.xiaodou.oms.vo.input.order.OrderItemListPojo;
import com.xiaodou.oms.vo.input.order.OrderListPojo;
import com.xiaodou.oms.vo.input.order.OrderNotePojo;
import com.xiaodou.oms.vo.input.order.OrderOrderItemListPojo;
import com.xiaodou.oms.vo.input.order.UpdateBuyAccountIdByGorderIdPojo;
import com.xiaodou.oms.vo.input.order.UpdateMerchantPojo;
import com.xiaodou.oms.vo.result.PageInfo;
import com.xiaodou.oms.vo.result.ResultInfo;
import com.xiaodou.oms.vo.result.ResultInfoList;
import com.xiaodou.oms.vo.result.ResultType;
import com.xiaodou.oms.vo.result.order.CreateOrderVO;
import com.xiaodou.oms.vo.result.order.FireVO;
import com.xiaodou.oms.vo.result.order.GorderDetailVO;
import com.xiaodou.oms.vo.result.order.GorderListVO;
import com.xiaodou.oms.vo.result.order.GorderOrderItemDetailVO;
import com.xiaodou.oms.vo.result.order.GorderOrderItemListVO;
import com.xiaodou.oms.vo.result.order.OrderDetailVO;
import com.xiaodou.oms.vo.result.order.OrderItemListVO;
import com.xiaodou.oms.vo.result.order.OrderListVO;
import com.xiaodou.oms.vo.result.order.OrderOrderItemListVO;
import com.xiaodou.payment.constant.PaymentStatus;
import com.xiaodou.payment.out.PaymentService;
import com.xiaodou.payment.vo.input.GetPayStatusPojo;
import com.xiaodou.payment.vo.response.PayStatusResponse;
import com.xiaodou.summer.dao.pagination.Page;

@Service("omsOrderService")
public class OmsOrderService {
  private static final String PAY_SUCCESS = "PaySuccess";
  // private static final String PAY_FAILURE = "PayFailure";
  // private static final String PRODUCT_LINE = "05";
  /**
   * 支付成功的状态码
   */
  // private static final Integer PAY_RESULT_STATUS_SUCCESS = 1;

  @Resource
  OrderServiceFacade orderServiceFacade;

  @Resource
  StateMachineProxy stateMachineProxy;

  /**
   * 下单业务逻辑方法
   * 
   * @param pojo 参数pojo
   * @return 下单结果VO
   * @throws Exception 异常信息
   */
  public CreateOrderVO createGorder(CreateOrderPojo pojo) throws Exception {
    CreateOrderVO res = null;
    try {
      Gorder gorder =
          orderServiceFacade.createGorder(pojo.getProductLine(), pojo.getGorder(),
              pojo.getRelationType(), pojo.getRelations(), pojo.getFraudJson());
      res = new CreateOrderVO(ResultType.SUCCESS);
      res.setGorderId(gorder.getId());
      StringBuffer orderIdList = new StringBuffer(200);
      for (Order order : gorder.getOrderList()) {
        orderIdList.append(order.getId()).append(",");
      }
      res.setOrderIdList(orderIdList.substring(0, orderIdList.length() - 1));
    } catch (Exception e) {
      throw new RuntimeException("[OmsOrder][下单失败]", e);
    }
    return res;
  }

  /**
   * 出发状态机事件的方法
   * 
   * @param pojo 参数pojo
   * @return 结果信息
   * @throws Throwable
   */
  public ResultInfo fireEvent(FirePojo pojo) throws Exception {
    fireEvent(pojo, new Context());
    if (null != ErrorsWrapper.getWrapper().getValue()) {
      Throwable e1 = ErrorsWrapper.getWrapper().getAndRemove();
      if (e1 instanceof OrderRuntimeException) {
        ResultInfo res = new ResultInfo(ResultType.STATEMACHINEERR);
        if (e1 instanceof UnknownTransitionException)
          res = new ResultInfo(ResultType.UNKNOWNTRANSITION);
        if (e1 instanceof PreCheckFailException) res = new ResultInfo(ResultType.PRECHECKFAIL);
        res.appendRetdesc(e1.getMessage());
        return res;
      }
      throw new RuntimeException("[OmsOrder][触发事件失败:orderId=" + pojo.getCoreParams().getOrderId()
          + ",gorderId=" + pojo.getCoreParams().getGorderId() + ",event="
          + pojo.getCoreParams().getEvent() + "]", e1);
    }
    return new ResultInfo(ResultType.SUCCESS);
  }

  /**
   * 触发状态机时间调用方法
   * 
   * @param pojo 参数pojo
   * @param context 上线文
   * @throws Exception
   */
  private void fireEvent(FirePojo pojo, Context context) {
    try {
      context.setCoreParams(pojo.getCoreParams());
      context.setOtherParams(pojo.getOtherParams());
      stateMachineProxy.fire(context);
    } catch (Exception e) {
      if (null == ErrorsWrapper.getWrapper().getValue()) ErrorsWrapper.getWrapper().setValue(e);
    }
  }

  /**
   * 批量触发状态机事件方法
   * 
   * @param pojoList 参数集合
   * @return
   */
  public ResultInfoList<FireVO> fireListEvent(FireListPojo pojoList) {
    ResultInfoList<FireVO> res = null;
    try {
      res = new ResultInfoList<FireVO>(ResultType.SUCCESS);
      List<FireVO> result = Lists.newArrayList();
      String productLine = pojoList.getProductLine();
      String event = pojoList.getEvent();
      String ip = pojoList.getIp();
      for (FirePojo pojo : pojoList.getPojoList()) {
        result.add(fireListEvent(pojo, productLine, event, ip));
      }
      res.setList(result);
    } catch (Exception e) {
      throw new RuntimeException("[OmsOrder][批量触发事件失败]", e);
    }
    return res;
  }

  /**
   * 批量触发状态机时间-触发事件方法
   * 
   * @param pojo 参数pojo
   * @param productLine 所属业务线
   * @param event 触发事件
   * @param ip 事件来源IP
   * @return
   */
  private FireVO fireListEvent(FirePojo pojo, String productLine, String event, String ip) {
    FireVO fireVo = new FireVO(ResultType.SUCCESS);
    try {
      pojo.getCoreParams().setProductLine(productLine);
      pojo.getCoreParams().setEvent(event);
      pojo.getCoreParams().setIp(ip);
      fireEvent(pojo, new Context());
      if (null != ErrorsWrapper.getWrapper().getValue()) {
        Throwable e1 = ErrorsWrapper.getWrapper().getAndRemove();
        if (e1 instanceof OrderRuntimeException) {
          FireVO res = new FireVO(ResultType.STATEMACHINEERR);
          if (e1 instanceof UnknownTransitionException)
            res = new FireVO(ResultType.UNKNOWNTRANSITION);
          res.appendRetdesc(e1.getMessage());
          fireVo = res;
        }
      }
    } catch (Exception e) {
      Throwable e1 = ErrorsWrapper.getWrapper().getAndRemove();
      OrderLoggerUtil.error("[OmsOrder][批量触发事件失败][触发事件失败:orderId="
          + pojo.getCoreParams().getOrderId() + ",gorderId=" + pojo.getCoreParams().getGorderId()
          + "event=" + pojo.getCoreParams().getEvent() + "]", e1 != null ? e1 : e);
      fireVo = new FireVO(ResultType.SYSFAIL);
    }
    fireVo.setGorderId(pojo.getCoreParams().getGorderId());
    fireVo.setOrderId(pojo.getCoreParams().getOrderId());
    return fireVo;
  }

  /**
   * 查询Gorder详情的方法
   * 
   * @param pojo 参数pojo
   * @return 结果信息
   * @throws Exception
   */
  public GorderDetailVO gorderDetail(GorderDetailPojo pojo) throws Exception {
    GorderDetailVO res = null;
    try {
      Map<String, Object> queryParam = Maps.newHashMap();
      queryParam.put("id", pojo.getGorderId());
      if (StringUtils.isNotBlank(pojo.getBuyAccountId())) {
        queryParam.put("buyAccountId", pojo.getBuyAccountId());
      }
      Gorder gorder = orderServiceFacade.queryGorderDetail(queryParam, pojo.getOutputProperties());
      if (null == gorder) {
        res = new GorderDetailVO(ResultType.ORDERMIS);
      } else {
        res = new GorderDetailVO(ResultType.SUCCESS);
        res.setGorder(gorder);
      }
    } catch (Exception e) {
      throw new RuntimeException("[OmsOrder][获取Gorder详情失败]", e);
    }
    return res;
  }

  /**
   * 查询Order详情的方法
   * 
   * @param pojo 参数pojo
   * @return 结果信息
   * @throws Exception
   */
  public OrderDetailVO orderDetail(OrderDetailPojo pojo) throws Exception {
    OrderDetailVO res = null;
    try {
      Map<String, Object> queryParam = Maps.newHashMap();
      queryParam.put("id", pojo.getOrderId());
      if (StringUtils.isNotBlank(pojo.getBuyAccountId())) {
        queryParam.put("buyAccountId", pojo.getBuyAccountId());
      }
      Order order = orderServiceFacade.queryOrderDetail(queryParam, pojo.getOutputProperties());
      if (null == order) {
        res = new OrderDetailVO(ResultType.ORDERMIS);
      } else {
        res = new OrderDetailVO(ResultType.SUCCESS);
        res.setOrder(order);
      }
    } catch (Exception e) {
      throw new RuntimeException("[OmsOrder][获取Order详情失败]", e);
    }
    return res;
  }

  /**
   * 查询OrderItem详情方法
   * 
   * @param pojo 参数Pojo
   * @return 结果信息
   * @throws Exception
   */
  public GorderOrderItemDetailVO orderItemDetail(OrderItemDetailPojo pojo) throws Exception {
    GorderOrderItemDetailVO res = null;
    try {
      Map<String, Object> queryParam = Maps.newHashMap();
      queryParam.put("id", pojo.getOrderItemId());
      if (StringUtils.isNotBlank(pojo.getBuyAccountId())) {
        queryParam.put("buyAccountId", pojo.getBuyAccountId());
      }

      OrderItem item =
          orderServiceFacade.queryOrderItemDetail(queryParam, pojo.getOutputProperties());
      if (null == item) {
        res = new GorderOrderItemDetailVO(ResultType.ORDERMIS);
      } else {
        res = new GorderOrderItemDetailVO(ResultType.SUCCESS);
        res.setOrderItem(item);
      }
    } catch (Exception e) {
      throw new RuntimeException("[OmsOrder][获取OrderItemDetailVO详情失败]", e);
    }
    return res;
  }

  /**
   * 查询Gorder-Order详情的方法
   * 
   * @param pojo 参数pojo
   * @return 结果信息
   * @throws Exception
   */
  public OrderDetailVO gorderOrderDetail(GorderOrderDetailPojo pojo) throws Exception {
    OrderDetailVO res = null;
    try {
      Order order =
          orderServiceFacade.queryCascadeGorderOrderDetail(
              initOrderIdParamMap(pojo.getOrderId(), pojo.getBuyAccountId()),
              getGorderOrderDetailOutput(pojo));
      if (null == order || null == order.getGorder()) {
        res = new OrderDetailVO(ResultType.ORDERMIS);
      } else {
        res = new OrderDetailVO(ResultType.SUCCESS);
        order.setGorder(order.getGorder());
        res.setOrder(order);
      }
    } catch (Exception e) {
      throw new RuntimeException("[OmsOrder][获取Order详情失败]", e);
    }
    return res;
  }

  /**
   * 获取Gorder-Order级联查询入参
   * 
   * @param pojo 参数pojo
   * @return
   */
  private Map<String, Object> getGorderOrderDetailOutput(GorderOrderDetailPojo pojo) {
    Map<String, Object> gorderProperties = pojo.getGorderOutputProperties();
    Map<String, Object> orderProperties = pojo.getOrderOutputProperties();
    return initCascadeQueryOutput(gorderProperties, orderProperties, null);
  }

  /**
   * 查询Gorder-Order-OrderItem详情方法
   * 
   * @param pojo 参数Pojo
   * @return 结果信息
   * @throws Exception
   */
  public GorderOrderItemDetailVO gorderOrderItemDetail(GorderOrderItemDetailPojo pojo)
      throws Exception {
    GorderOrderItemDetailVO res = null;
    try {
      String id = null;
      OrderItem item = null;
      if (null != pojo.getOrderItemId()) {
        id = pojo.getOrderItemId();
        item = orderServiceFacade.queryCascadeOrderDetail(id, getGorderOrderItemDetailOutput(pojo));
      } else {
        item =
            orderServiceFacade.queryCascadeOrderDetail(
                initOrderIdParamMap(pojo.getOrderId(), pojo.getBuyAccountId()),
                getGorderOrderItemDetailOutput(pojo));
      }
      if (null == item || null == item.getOrder() || null == item.getGorder()) {
        res = new GorderOrderItemDetailVO(ResultType.ORDERMIS);
      } else {
        res = new GorderOrderItemDetailVO(ResultType.SUCCESS);
        res.setOrderItem(item);
      }
    } catch (Exception e) {
      throw new RuntimeException("[OmsOrder][获取GorderOrderItemDetailVO详情失败]", e);
    }
    return res;
  }

  /**
   * 根据orderId构造参数Map
   * 
   * @param orderId 订单号
   * @param buyAccountId 会员号/设备号
   * @return 参数Map
   */
  private Map<String, Object> initOrderIdParamMap(String orderId, String buyAccountId) {
    // 构造查询条件
    Map<String, Object> qOrderMap = Maps.newHashMap();
    // order 查询
    if (StringUtils.isNotBlank(orderId)) qOrderMap.put("id", orderId);// 订单ID
    if (StringUtils.isNotBlank(buyAccountId)) qOrderMap.put("buyAccountId", buyAccountId);// 会员号/设备号

    return initCascadeQueryParams(null, qOrderMap, null);
  }

  /**
   * 构造三级联查输出Map
   * 
   * @param pojo 参数Pojo
   * @return
   */
  private Map<String, Object> getGorderOrderItemDetailOutput(GorderOrderItemDetailPojo pojo) {
    Map<String, Object> gorderOutputProperties = pojo.getGorderOutputProperties();
    Map<String, Object> orderOutputProperties = pojo.getOrderOutputProperties();
    Map<String, Object> orderItemOutputProperties = pojo.getOrderItemOutputProperties();
    return initCascadeQueryOutput(gorderOutputProperties, orderOutputProperties,
        orderItemOutputProperties);
  }

  /**
   * 查询GorderList的方法
   * 
   * @param pojo 参数pojo
   * @return
   */
  public GorderListVO gorderList(GorderListPojo pojo) {
    GorderListVO res = null;
    try {
      Page<Gorder> page =
          new Page<Gorder>(pojo.getPage().getPageNo(), pojo.getPage().getPageSize());
      Map<String, Object> inputParams = CommUtil.getParams(pojo.getQueryParams());
      page = orderServiceFacade.queryPagedGorderList(inputParams, pojo.getOutputProperties(), page);
      res = new GorderListVO(ResultType.SUCCESS);
      res.setPage(new PageInfo(page));
      res.setList(page.getResult());
    } catch (Exception e) {
      throw new RuntimeException("[OmsOrder][获取Gorder列表失败]", e);
    }
    return res;
  }

  /**
   * 查询OrderList的方法
   * 
   * @param pojo 参数pojo
   * @return
   */
  public OrderListVO orderList(OrderListPojo pojo) throws Exception {
    OrderListVO res = null;
    try {
      Page<Order> page = new Page<Order>(pojo.getPage().getPageNo(), pojo.getPage().getPageSize());
      Map<String, Object> inputParams = CommUtil.getParams(pojo.getQueryParams());
      page =
          orderServiceFacade.queryPagedSingleOrderList(inputParams, pojo.getOutputProperties(),
              page);
      res = new OrderListVO(ResultType.SUCCESS);
      res.setPage(new PageInfo(page));
      res.setList(page.getResult());
    } catch (Exception e) {
      throw new RuntimeException("[OmsOrder][获取Gorder列表失败]", e);
    }
    return res;
  }

  /**
   * 查询OrderItemList的方法
   * 
   * @param pojo 参数pojo
   * @return
   */
  public OrderItemListVO orderItemList(OrderItemListPojo pojo) {
    OrderItemListVO res = null;
    try {
      res = new OrderItemListVO(ResultType.SUCCESS);
      Map<String, Object> queryParam = Maps.newHashMap();
      queryParam.put("orderId", pojo.getOrderId());
      if (StringUtils.isNotEmpty(pojo.getBuyAccountId())) {
        queryParam.put("buyAccountId", pojo.getBuyAccountId());
      }
      List<OrderItem> orderItemList =
          orderServiceFacade.queryOrderItemList(queryParam, pojo.getOutputProperties());
      res.setList(orderItemList);
    } catch (Exception e) {
      throw new RuntimeException("[OmsOrder][获取Gorder列表失败]", e);
    }
    return res;
  }

  /**
   * 二级联查查询订单列表的方法
   * 
   * @param pojo 参数pojo
   * @return
   * @throws Exception
   */
  public OrderListVO gorderOrderList(GorderOrderListPojo pojo) throws Exception {
    OrderListVO res = null;

    try {
      Page<Order> page = new Page<Order>(pojo.getPage().getPageNo(), pojo.getPage().getPageSize());
      page =
          orderServiceFacade.queryPagedOrderList(initCascadeQueryParams(pojo),
              initCascadeQueryOutput(pojo), page);
      res = new OrderListVO(ResultType.SUCCESS);
      res.setPage(new PageInfo(page));
      res.setList(page.getResult());
    } catch (Exception e) {
      throw new RuntimeException("[OmsOrder][获取Gorder列表失败]", e);
    }
    return res;
  }

  /**
   * 初始化级联查询结果Map
   * 
   * @param pojo 参数Pojo
   * @return 入参Map
   */
  private Map<String, Object> initCascadeQueryOutput(GorderOrderListPojo pojo) {
    Map<String, Object> gorderOutput = pojo.getGorderOutputProperties();
    Map<String, Object> orderOutput = pojo.getOrderOutputProperties();
    return initCascadeQueryParams(gorderOutput, orderOutput, null);
  }

  /**
   * 初始化级联查询入参Map
   * 
   * @param pojo 参数Pojo
   * @return 入参Map
   */
  private Map<String, Object> initCascadeQueryParams(GorderOrderListPojo pojo) {
    Map<String, Object> gorderInputParams = CommUtil.getParams(pojo.getGorderQueryParams());
    Map<String, Object> orderInputParams = CommUtil.getParams(pojo.getOrderQueryParams());
    return initCascadeQueryParams(gorderInputParams, orderInputParams, null);
  }

  /**
   * 三级联查查询订单列表的方法
   * 
   * @param pojo 参数pojo
   * @return
   * @throws Exception
   */
  public GorderOrderItemListVO gorderOrderItemList(GorderOrderItemListPojo pojo) throws Exception {
    GorderOrderItemListVO res = null;

    try {
      Page<OrderItem> page =
          new Page<OrderItem>(pojo.getPage().getPageNo(), pojo.getPage().getPageSize());
      page =
          orderServiceFacade.queryPagedCascadeOrderList(initCascadeQueryParams(pojo),
              initCascadeQueryOutput(pojo), page);
      res = new GorderOrderItemListVO(ResultType.SUCCESS);
      res.setPage(new PageInfo(page));
      res.setList(page.getResult());
    } catch (Exception e) {
      throw new RuntimeException("[OmsOrder][获取Gorder列表失败]", e);
    }
    return res;
  }

  /**
   * order - orderItem 两级联查
   * 
   * @param pojo 参数pojo
   * @return
   * @throws Exception
   */
  public OrderOrderItemListVO orderOrderItemList(OrderOrderItemListPojo pojo) throws Exception {
    OrderOrderItemListVO res = null;

    try {
      Page<OrderItem> page =
          new Page<OrderItem>(pojo.getPage().getPageNo(), pojo.getPage().getPageSize());
      /**
       * 查询参数
       */
      Map<String, Object> orderInputParams = null;
      if (pojo.getOrderQueryParams() != null) {
        orderInputParams = CommUtil.getParams(pojo.getOrderQueryParams());
      }
      Map<String, Object> orderItemInputParams = null;
      if (pojo.getOrderItemQueryParams() != null) {
        orderItemInputParams = CommUtil.getParams(pojo.getOrderItemQueryParams());
      }
      Map<String, Object> queryParam =
          initCascadeQueryParams(null, orderInputParams, orderItemInputParams);
      /**
       * 输出参数
       */
      Map<String, Object> outputProperties =
          initCascadeQueryParams(null, pojo.getOrderOutputProperties(),
              pojo.getOrderItemOutputProperties());
      page = orderServiceFacade.queryPagedOrderOrderItemList(queryParam, outputProperties, page);
      res = new OrderOrderItemListVO(ResultType.SUCCESS);
      res.setPage(new PageInfo(page));
      res.setList(page.getResult());
    } catch (Exception e) {
      throw new RuntimeException("[OmsOrder][获取order-orderItem列表失败]", e);
    }
    return res;
  }

  /**
   * 初始化级联查询入参Map
   * 
   * @param pojo 参数Pojo
   * @return 入参Map
   */
  private Map<String, Object> initCascadeQueryParams(GorderOrderItemListPojo pojo) {
    Map<String, Object> gorderInputParams = CommUtil.getParams(pojo.getGorderQueryParams());
    Map<String, Object> orderInputParams = CommUtil.getParams(pojo.getOrderQueryParams());
    Map<String, Object> orderItemInputParams = CommUtil.getParams(pojo.getOrderItemQueryParams());
    return initCascadeQueryParams(gorderInputParams, orderInputParams, orderItemInputParams);
  }

  /**
   * 初始化级联查询结果Map
   * 
   * @param pojo 参数Pojo
   * @return 入参Map
   */
  private Map<String, Object> initCascadeQueryOutput(GorderOrderItemListPojo pojo) {
    Map<String, Object> gorderOutput = pojo.getGorderOutputProperties();
    Map<String, Object> orderOutput = pojo.getOrderOutputProperties();
    Map<String, Object> orderItemOutput = pojo.getOrderItemOutputProperties();
    return initCascadeQueryParams(gorderOutput, orderOutput, orderItemOutput);
  }


  /**
   * 构造三级联查询入参Map
   * 
   * @param gorderInputParams gorder查询字段
   * @param orderInputParams order查询字段
   * @param orderItemInputParams orderItem查询字段
   * @return 入参Map
   */
  private Map<String, Object> initCascadeQueryParams(Map<String, Object> gorderInputParams,
      Map<String, Object> orderInputParams, Map<String, Object> orderItemInputParams) {
    Map<String, Object> parameterMap = Maps.newHashMap();
    if (null != gorderInputParams && gorderInputParams.size() > 0)
      parameterMap.put("gorder", gorderInputParams);
    if (null != orderInputParams && orderInputParams.size() > 0)
      parameterMap.put("order", orderInputParams);
    if (null != orderItemInputParams && orderItemInputParams.size() > 0)
      parameterMap.put("orderItem", orderItemInputParams);
    parameterMap.put("orderByTimeDesc", "true");// 下单时间排序
    return parameterMap;
  }

  /**
   * 构造三级联查输出Map
   * 
   * @param gorderOutput gorder输出字段
   * @param orderOutput order输出字段
   * @param orderItemOutput orderItem输出字段
   * @return 输出字段Map
   */
  private Map<String, Object> initCascadeQueryOutput(Map<String, Object> gorderOutput,
      Map<String, Object> orderOutput, Map<String, Object> orderItemOutput) {
    Map<String, Object> outputMap = Maps.newHashMap();
    if (null != gorderOutput && gorderOutput.size() > 0) outputMap.put("gorder", gorderOutput);
    if (null != orderOutput && orderOutput.size() > 0) outputMap.put("order", orderOutput);
    if (null != orderItemOutput && orderItemOutput.size() > 0)
      outputMap.put("orderItem", orderItemOutput);
    return outputMap;
  }

  /**
   * 添加Order备注
   * 
   * @param pojo 参数pojo
   * @return
   */
  public ResultInfo addOrderNote(OrderNotePojo pojo) {
    ResultInfo res = null;
    try {
      orderServiceFacade.updateOrderNote(pojo.getOrderId(), pojo.getNote());
      res = new ResultInfo(ResultType.SUCCESS);
    } catch (Exception e) {
      throw new RuntimeException("[OmsOrder][更新Note失败:orderId=]" + pojo.getOrderId(), e);
    }
    return res;
  }

  /**
   * 添加OrderItem备注
   * 
   * @param pojo 参数pojo
   * @return
   */
  public ResultInfo addOrderItemNote(ItemNotePojo pojo) {
    ResultInfo res = null;
    try {
      orderServiceFacade.updateItemNote(pojo.getOrderItemId(), pojo.getNote());
      res = new ResultInfo(ResultType.SUCCESS);
    } catch (Exception e) {
      throw new RuntimeException("[OmsOrder][更新Note失败:orderId=]" + pojo.getOrderItemId(), e);
    }
    return res;
  }

  /**
   * 修改合作商信息
   * 
   * @param pojo 参数pojo
   * @return
   */
  public ResultInfo changeMerchant(UpdateMerchantPojo pojo) {
    ResultInfo res = null;
    try {
      Map<String, Object> queryParam = Maps.newHashMap();
      queryParam.put("id", pojo.getOrderId());
      if (StringUtils.isNotBlank(pojo.getBuyAccountId())) {
        queryParam.put("buyAccountId", pojo.getBuyAccountId());
      }
      Map<String, Object> outputProperties = Maps.newHashMap();
      outputProperties.put("id", "1");
      Order order = orderServiceFacade.queryOrderDetail(queryParam, outputProperties);
      if (null == order) {
        res = new ResultInfo(ResultType.ORDERMIS);
      } else {
        orderServiceFacade.updateOrder(pojo.getUpdateOrder(), "更改合作商信息", pojo.getClientIp());
        res = new ResultInfo(ResultType.SUCCESS);
      }
    } catch (Exception e) {
      throw new RuntimeException("[OmsOrder][更新合作商信息失败:orderId=]" + pojo.getOrderId(), e);
    }
    return res;
  }


  public ResultInfo queryPaymentAndFireEvent(String gorderId) {
    OperationTypeWrapper.getWrapper().setValue(OperationType.WRITE);
    /* 1 查询gorder并判断状态是否是新单或支付失败 */
    Map<String, String> queryGorderInput = new HashMap<String, String>();
    queryGorderInput.put("id", gorderId);
    Map<String, String> queryGorderOutput = new HashMap<String, String>();
    queryGorderOutput.put("id", "");
    queryGorderOutput.put("productType", "");
    queryGorderOutput.put("gorderStatus", "");
    queryGorderOutput.put("gorderIp", "");
    Gorder gorder = orderServiceFacade.queryGorderDetail(queryGorderInput, queryGorderOutput);
    if (gorder == null) {
      ResultInfo resultInfo = new ResultInfo();
      resultInfo.setRetcode("20600");
      resultInfo.setRetdesc("查到的gorder为空");
      return resultInfo;
    }
    // 判断gorder的业务类型是否是配置文件配置的类型之一(暂时只是0501)
    String productTypeList =
        FileUtils.PAYMENT_PROPERTIES.getProperties("gorder_query_payment_product_type_list");
    if (!productTypeList.contains(gorder.getProductType())) {
      ResultInfo resultInfo = new ResultInfo();
      resultInfo.setRetcode("20601");
      resultInfo.setRetdesc("不支持的业务类型");
      return resultInfo;
    }
    int gorderStatus = gorder.getGorderStatus();
    // 如果数据库中的订单状态不是新单并且不是支付失败，返回
    if (gorderStatus != OrderConstant.STATUS_INITIATE
        && gorderStatus != OrderConstant.STATUS_PAYFAILURE) {
      ResultInfo resultInfo = new ResultInfo();
      resultInfo.setRetcode("20602");
      resultInfo.setRetdesc("gorderId" + gorderId + "状态不是新单或支付失败，实际状态为" + gorderStatus);
      return resultInfo;
    }

    /* 2 查询gorder下的处理中的第一次支付的payRecord */
    List<PayRecord> payRecordList = orderServiceFacade.queryFirstPayRecord(gorder);
    PayRecord payRecord = null;
    for (PayRecord record : payRecordList) {
      // 找到处理中的payRecord
      if (record.getPayStatus().equals(PayRecord.PAY_STATUS_PROCESSING)) {
        payRecord = record;
      }
    }
    // 查不到处理中的第一次支付的payRecord，返回
    if (payRecord == null) {
      ResultInfo resultInfo = new ResultInfo();
      resultInfo.setRetcode("20603");
      resultInfo.setRetdesc("该gorder下没有处理中的第一次支付payRecord记录");
      return resultInfo;
    }

    /* 3 处理,查询PMT最新状态，并触发我们的状态机 */
    String productType = gorder.getProductType();
    String productLine = productType.substring(0, 2);
    // 向payment查询最新支付状态
    GetPayStatusPojo getPayStatusPojo = new GetPayStatusPojo();
    getPayStatusPojo.setProductLine(productLine);
    getPayStatusPojo.setGorderId(gorderId);
    getPayStatusPojo.setToken(payRecord.getPayNo());
    PayStatusResponse response = PaymentService.getPayStatus(getPayStatusPojo);
    // 如果PMT返回结果不是成功
    if (!RepCode.REPCODE_SUCCESS.equals(response.getRetcode())) {
      ResultInfo resultInfo = new ResultInfo();
      resultInfo.setRetcode("20604");
      resultInfo.setRetdesc("调用PaymentService出错|" + response.getRetcode() + "|"
          + response.getRetdesc());
      return resultInfo;
    }

    FirePojo firePojo = new FirePojo();
    CoreParams coreParams = new CoreParams();
    // 设置coreParams的参数
    coreParams.setProductLine(productLine);
    coreParams.setGorderId(payRecord.getGorderId());
    coreParams.setOrderId(payRecord.getOrderId());
    PayParam payParam = new PayParam();
    payParam.setPayNo(payRecord.getPayNo());// 流水号(token)
    payParam.setFailureReason(response.getMessage());
    payParam.setUuid(payRecord.getUuid());
    coreParams.setPayParam(payParam);
    firePojo.setCoreParams(coreParams);
    // 设置coreParams的ip和event
    coreParams.setIp(gorder.getGorderIp());
    // 根据payment回调结果设置事件是成功还是失败 设置CoreParams event
    if (response.getResultStatus().equals(PaymentStatus.NOTIFY_OKCODE)) {
      coreParams.setEvent(PAY_SUCCESS);
    } else if (response.getResultStatus().equals(PaymentStatus.NOTIFY_FAILCODE)) {
      // 如果查到支付失败，不处理

      /*
       * 用户支付失败，我们主动查到支付失败，（payment这时候还没回调过来支付失败） 此时触发状态流转 新单->支付失败
       * 用户点继续支付，（已经生成了一条新的payRecord记录）填写表单的时候，payment回调过来支付失败 这样就有问题了，因为生成的新的payRecord记录变成了失败状态。
       * 就算继续支付成功，由于没有了处理中的payRecord记录，导致订单状态流转失败，无法从支付失败到支付成功。
       * 或者是有可能客人点了继续支付，正在填写，查到的支付失败可能是上次的失败，无法判断; 所以在支付失败使用同一个token的情况下，只能是只处理支付成功，不处理支付失败。否则会有问题
       */
      ResultInfo resultInfo = new ResultInfo();
      resultInfo.setRetcode("20607");
      resultInfo.setRetdesc("查询最新支付状态是支付失败，这种情况不处理" + response.getResultStatus());
      return resultInfo;
    } else {
      ResultInfo resultInfo = new ResultInfo();
      resultInfo.setRetcode("20605");
      resultInfo.setRetdesc("查询最新支付状态不是0/1.result_stauts:" + response.getResultStatus());
      return resultInfo;
    }

    try {
      return this.fireEvent(firePojo);
    } catch (Exception e) {
      ResultInfo resultInfo = new ResultInfo();
      resultInfo.setRetcode("20606");
      resultInfo.setRetdesc("触发状态机出错");
      LoggerUtil.error("oms支付状态主动查询-触发状态机出错", e);
      return resultInfo;
    }

  }

  /**
   * 
   * updateBuyAccountIdByGorderId
   * 
   * @Title: updateBuyAccountIdByGorderId
   * @Description: 更新用户账户
   * @return
   */
  public UpdateBuyAccountIdByGorderIdResponse updateBuyAccountIdByGorderId(
      UpdateBuyAccountIdByGorderIdPojo pojo) {
    UpdateBuyAccountIdByGorderIdResponse response = null;

    response = orderServiceFacade.updateBuyAccountIdByGorderId(pojo);

    return response;
  }

  /**
   * 更新Gorder Tags
   * 
   * @title: updateGorderTags
   * @date 2015年1月29日 下午4:34:39
   * @param pojo
   * @return Integer
   */
  public ResultInfo updateGorderTags(UpdateGorderPojo pojo) {
    Map<String, String> condition = new HashMap<String, String>();
    condition.put("id", String.valueOf(pojo.getGorderId()));
    condition.put("buyAccountId", pojo.getUid());
    Gorder gorder = new Gorder();
    gorder.setTags(pojo.getGorder().getTags());
    int success = 0;
    try {
      success = this.orderServiceFacade.updateGorder(condition, gorder);
    } catch (Exception e) {
      LoggerUtil.error("Gorder tags更新失败", e);
      throw e;
    }

    ResultInfo res = new ResultInfo();
    res.setServerIp(IPUtil.getServerIp());
    if (success == 0) {
      res.setRetcode("-1");
      res.setRetdesc("操作失败");
    } else {
      res.setRetcode("200");
      res.setRetdesc("操作成功");
    }
    return res;
  }
}
