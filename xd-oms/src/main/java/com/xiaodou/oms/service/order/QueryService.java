package com.xiaodou.oms.service.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.xiaodou.oms.constant.order.OrderConstant;
import com.xiaodou.oms.dao.order.OrderDao;
import com.xiaodou.oms.entity.order.Order;
import com.xiaodou.summer.dao.pagination.Page;

public class QueryService {

  @Resource
  OrderDao orderDao = null;// 订单的数据访问接口
  @Resource
  OrderItemService orderItemService = null;// 订单条目数据访问接口

  /**
   * 查询订单列表
   * 
   * @param input
   * @return
   */
  public List queryOrderList(Map input) {
    Map output = setOrderDetail();
    List result = orderDao.queryOrderList(input, output);

    return result;
  }

  public List queryOrderList(Map input, Map output) {
    List result = orderDao.queryOrderList(input, output);

    return result;
  }

  public List<String> queryOrderIdList(Map input, Map output) {
    List<String> result = orderDao.queryOrderIdList(input);

    return result;
  }

  /**
   * 查询指定id的订单明细
   * 
   * @param id 订单id
   * @return
   */
  public Order queryOrderDetail(String id) {
    Map input = new HashMap(); // 查询条件
    if (null == id || "".equalsIgnoreCase(id)) {
      // OrderAlerter.alert("[订单查询id为空或不存在]");
      return null;
    }
    input.put("id", id);
    input.put("forUpdate", 1);
    Order order = queryOrderDetail(input);

    return order;
  }

  /**
   * 查询指定id的订单明细
   * 
   * @param id 订单id
   * @return
   */
  public Order queryOrderDetailNoLock(String id) {
    Map input = new HashMap(); // 查询条件
    if (null == id || "".equalsIgnoreCase(id)) {
      // OrderAlerter.alert("[订单查询id为空或不存在]");
      return null;
    }
    input.put("id", id);
    Order order = queryOrderDetail(input);

    return order;
  }

  public Order queryOrderDetail(String id, Map output) {

    Map input = new HashMap(); // 查询条件
    if (null == id || "".equalsIgnoreCase(id)) {
      return null;
    }
    input.put("id", id);

    Order order = orderDao.queryOrderDetail(input, output);

    return order;
  }

  public Order queryOrderDetail(Map input) {
    Map output = setOrderDetail();// 要查询哪些明细属性

    Order order = orderDao.queryOrderDetail(input, output);

    return order;
  }

  public Order queryOrderDetail(Map input, Map output) {

    Order order = orderDao.queryOrderDetail(input, output);

    return order;
  }

  public Page<Order> queryPagedOrderList(Map parameterMap, Map outputMap, Page<Order> page) {
    Map mapParam = new HashMap<String, Map>();
    mapParam.put("input", parameterMap);
    mapParam.put("output", outputMap);
    return orderDao.findEntityListByCond(mapParam, page);

  }


  /*
   * ================================================================================================
   * ================
   */


  /**
   * 设置哪些是订单明细
   * 
   * @return
   */
  @SuppressWarnings("unchecked")
  public Map setOrderDetail() {
    Map output = new HashMap();// 要查询哪些明细属性
    output.put("merchantName", OrderConstant.STRING_NEEDED);// 交易对方
    output.put("id", OrderConstant.STRING_NEEDED);// 订单编号
    output.put("orderTime", OrderConstant.DATE_NEEDED);// 下单时间
    output.put("payAmount", OrderConstant.BIGDECIMAL_NEEDED);// 实付金额
    output.put("orderStatus", OrderConstant.INTEGER_NEEDED);// 交易状态
    output.put("updateTime", OrderConstant.DATE_NEEDED);// 最后更新时间
    output.put("merchantTel", OrderConstant.STRING_NEEDED);// 联系电话
    output.put("merchantAccount", OrderConstant.STRING_NEEDED);
    output.put("fee", OrderConstant.BIGDECIMAL_NEEDED);
    output.put("merchantId", OrderConstant.INTEGER_NEEDED);
    output.put("buyAccountId", OrderConstant.STRING_NEEDED);// 验证用
    output.put("orderAmount", OrderConstant.BIGDECIMAL_NEEDED);
    output.put("merchantOrderId", OrderConstant.STRING_NEEDED);
    output.put("settleUp", OrderConstant.INTEGER_NEEDED);
    output.put("logisticsAmount", OrderConstant.BIGDECIMAL_NEEDED);
    output.put("activityId", OrderConstant.INTEGER_NEEDED);
    output.put("activityType", OrderConstant.INTEGER_NEEDED);
    output.put("misc", OrderConstant.STRING_NEEDED);
    output.put("remark", OrderConstant.STRING_NEEDED);
    output.put("canDeliver", OrderConstant.VALID);
    output.put("canSettleUp", OrderConstant.VALID);
    output.put("canRefund", OrderConstant.VALID);
    output.put("preCloseTime", OrderConstant.DATE_NEEDED);
    output.put("gorderId", OrderConstant.VALID);
    output.put("originalPayAmount", OrderConstant.VALID);
    output.put("costAmount", OrderConstant.VALID);
    output.put("saveAmount", OrderConstant.VALID);
    output.put("merchantAmount", OrderConstant.VALID);
    output.put("successTime", OrderConstant.DATE_NEEDED);// 发货成功时间
    output.put("merchantProtocol", OrderConstant.STRING_NEEDED);
    output.put("orderDesc", OrderConstant.STRING_NEEDED);// 描述内部使用
    output.put("productType", OrderConstant.STRING_NEEDED);// 业务类型/产品类型
    output.put("keywords", OrderConstant.STRING_NEEDED);// 搜索关键词
    output.put("deliveryBeginTime", OrderConstant.STRING_NEEDED);// 开始发货时间
    output.put("deliveryEndTime", OrderConstant.STRING_NEEDED);// 货品送达时间
    output.put("relationId", OrderConstant.STRING_NEEDED);// 此字段值相同的order,代表具有一定的关联
    output.put("orderIp", OrderConstant.STRING_NEEDED);
    return output;
  }

  /**
   * 查询订单概要信息列表.含分页参数
   * 
   * @param account
   * @return 个人账号
   */
  // public Map queryOrderList(Map input, Integer pageNumber) {
  // Map output = setOrderDetail();
  // Map result = orderDao.queryPageOrderList(input, output, pageNumber);
  //
  // return result;
  // }
  /**
   * 分页查询
   * 
   * @param inputArgument
   * @param outputField
   * @param pageSize
   * @param pageNumber
   * @return
   */
  // public Page<Order> queryPagedOrderList(Map inputArgument, Integer pageSize,Integer pageNumber){
  // Page pageObject = new Page();
  // pageObject.setPageSize(pageSize);
  // pageObject.setPageNo(pageNumber);
  // Map outputField = setOrderDetail();
  // return orderDao.queryPagedOrderList(inputArgument, outputField, pageObject);
  // }



  /**
   * 根据大订单ID查询订单概要信息列表.
   * 
   * @param gorderId:大订单号
   * @return 当前大订单下的所有小订单列表
   */
  public List<Order> queryOrderListByGorderId(String gorderId) {
    Map input = new HashMap();
    input.put("gorderId", gorderId);
    Map output = setOrderDetail();
    List<Order> result = orderDao.queryOrderList(input, output);

    return result;
  }

  /**
   * 根据大订单ID查询订单概要信息列表.
   * 
   * @param gorderId:大订单号
   * @return 当前大订单下的所有小订单列表
   */
  public List<Order> queryOrderListByGorderIdForUpdate(String gorderId) {
    Map input = new HashMap();
    if (null == gorderId || "".equalsIgnoreCase(gorderId)) {
      return null;
    }
    input.put("gorderId", gorderId);
    input.put("forUpdate", 1);
    Map output = setOrderDetail();
    List<Order> result = orderDao.queryOrderList(input, output);

    return result;
  }

  /**
   * 查询订单数量
   */
  // public Integer queryOrderCount(Map input){
  // Map output = new HashMap();// 要查询哪些明细
  // return orderDao.queryOrderCount(input, output);
  // }
  /**
   * 查询满足条件的订单数量，金额
   */
  // public Map<String, Integer> queryOrderCount(Map input,
  // Map condition) {
  // Map output = new HashMap();// 要查询哪些明细
  // Map<String, Integer> countMap = new HashMap();// <分类名，数量>
  //
  // // 总量
  // if (null != condition && null != condition.get("total")) {
  // int totalCount = orderDao.queryOrderCount(input, output);
  // countMap.put("total", totalCount);
  // }
  //
  // // 待付款(初始化)
  // if (null != condition && null != condition.get("unpaid")) {
  // input.put("orderStatus",
  // OrderConstant.STATUS_INITIATE);//.setListOrderStatus(OrderConstant.STATUS_INITIATE);
  // int unprocessedCount = orderDao.queryOrderCount(input, output);
  // countMap.put("unpaid", unprocessedCount);
  // }
  //
  // // 本月买入
  // if (null != condition && null != condition.get("monthBuying")) {
  // input.put("listOrderStatus", (List) null);//.setListOrderStatus((List) null);
  //
  // Date today = Calendar.getInstance().getTime();
  // input.put("orderTimeLower", new Timestamp(today.getTime()));//.setOrderTimeLower(new
  // Timestamp(today.getTime()));
  //
  // today.setDate(today.getDate() - 30);
  // input.put("orderTimeLower", new Timestamp(today.getTime()));//.setOrderTimeLower(new
  // Timestamp(today.getTime()));
  //
  // int unprocessedCount = orderDao.queryOrderCount(input, output);
  // countMap.put("monthBuying", unprocessedCount);
  // }
  //
  // // 待确认(已发货)
  // if (null != condition && null != condition.get("unconfirmed")) {
  // input.put("listOrderStatus",
  // OrderConstant.STATUS_DELIVERED);//.setListOrderStatus(OrderConstant.STATUS_DILIVERED);
  // int unprocessedCount = orderDao.queryOrderCount(input, output);
  // countMap.put("unconfirmed", unprocessedCount);
  // }
  //
  // // 购买成功
  // if (null != condition && null != condition.get("buySuccess")) {
  // input.put("listOrderStatus",
  // OrderConstant.STATUS_TRANSACTIONSUCCESS);//.setListOrderStatus(OrderConstant.STATUS_TRANSACTIONSUCCESS);
  // int unprocessedCount = orderDao.queryOrderCount(input, output);
  // countMap.put("buySuccess", unprocessedCount);
  // }
  //
  // return countMap;
  // }


  /**
   * 查询已付费未取货的商品
   * 
   * @param condition 附加的查询条件
   * @return
   */
  public List<Order> queryUndeliveredList(Map condition) {
    if (null == condition) {
      condition = new HashMap(); // 查询条件
    }
    Map output = setOrderDetail();// 要查询哪些明细属性

    /** 1.查询条件 */
    if (null == condition.get("listOrderStatus")) {
      Integer[] statusArray = {OrderConstant.STATUS_PAYSUCCESS, OrderConstant.STATUS_PREPARE};
      condition.put("listOrderStatus", statusArray);// .setListOrderStatus(OrderConstant.STATUS_PAYSUCCESS);
      // input.put("productType",
      // OrderConstant.TYPE_CARD);//.setProductType(OrderConstant.TYPE_CARD);
    }
    List<Order> result = orderDao.queryOrderList(condition, output);

    return result;
  }


  /*****************************************
   * 查询订单 end *
   ****************************************/

  /** 查看指定id的订单是否存在 */
  public boolean checkExistence(String id) {
    Order order = queryOrderDetailNoLock(id);
    if (null == order) {
      return false;
    }
    return true;
  }

}
