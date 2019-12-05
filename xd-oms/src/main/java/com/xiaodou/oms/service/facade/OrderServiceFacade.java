package com.xiaodou.oms.service.facade;


import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.xiaodou.oms.entity.order.Gorder;
import com.xiaodou.oms.entity.order.Order;
import com.xiaodou.oms.entity.order.OrderItem;
import com.xiaodou.oms.entity.order.PayRecord;
import com.xiaodou.oms.entity.order.PayRequest;
import com.xiaodou.oms.entity.order.WayBill;
import com.xiaodou.oms.exception.OrderException;
import com.xiaodou.oms.exception.ValidationException;
import com.xiaodou.oms.vo.UpdateBuyAccountIdByGorderIdResponse;
import com.xiaodou.oms.vo.input.order.UpdateBuyAccountIdByGorderIdPojo;
import com.xiaodou.oms.vo.input.pay.ContinuePayPojo;
import com.xiaodou.oms.vo.input.pay.GetTokenPojo;
import com.xiaodou.oms.vo.input.pay.QueryFirstPayInfoPojo;
import com.xiaodou.oms.vo.input.pay.QueryOrderPayDetailPojo;
import com.xiaodou.oms.vo.input.pay.QueryRecordPojo;
import com.xiaodou.oms.vo.result.pay.ContinuePayVO;
import com.xiaodou.oms.vo.result.pay.FirstPayInfoVO;
import com.xiaodou.oms.vo.result.pay.GetTokenVO;
import com.xiaodou.oms.vo.result.pay.PayRecordListVO;
import com.xiaodou.payment.vo.response.PayDetailResponse;
import com.xiaodou.summer.dao.pagination.Page;

/**
 * @author zhaoyang
 * @version V1.0
 * @Title:IGeneralOrderService.java
 * @Description:订单及支付模块对外的统一Facade接口，封装了所有业务逻辑。
 * @date Jan 21, 2014 1:45:46 PM
 */
public interface OrderServiceFacade {

  /**
   * 支付成功通知
   */
  public static Integer PAY_CALLBACK_TYPE_SUCCESS = 1;

  /**
   * 支付失败通知
   */
  public static Integer PAY_CALLBACK_TYPE_FAILURE = -1;

  /********************************************** GOrder相关接口 开始 **************************************************/

  /**
   * 基于Gorder对象创建多个大订单（含下属子订单）数据，并实现大订单自身的内部关联
   * 
   * @param guorderList 大订单对象列表
   * @param buyAccountId 购买人账户
   * @return List<String>是所有大订单Order的id组成的列表
   */
  public List<String> createGorderList(List<Gorder> guorderList, String buyAccountId);

  /**
   * 基于Gorder对象创建单个大订单数据，并创建下属的小订单数据，不会向风控推送订单信息
   * 
   * @param productLine 产品线
   * @param guorder 大订单对象
   * @param relationType 关联类型
   * @param relations 关联关系
   * @return String大订单gorderId
   */
  public Gorder createGorder(String productLine, Gorder guorder, String relationType,
      String relations) throws ValidationException, OrderException;

  /**
   * 基于Gorder对象创建单个大订单数据，并创建下属的小订单数据，向风控推送订单信息
   * 
   * @param productLine 产品线
   * @param guorder 大订单对象
   * @param relationType 关联类型
   * @param relations 关联关系
   * @param fraudJson 风控推单参数json
   * @return String大订单gorderId
   */
  public Gorder createGorder(String productLine, Gorder gorder, String relationType,
      String relations, String fraudJson) throws ValidationException, OrderException;

  /**
   * 查询大订单订单概要信息列表，不分页，不含下属小订单数据
   * 
   * @param input 查询参数map数据
   * @return 大订单数据列表
   */
  // public List<Gorder> queryGOrderList(Map input);

  /**
   * 查询大订单订单概要信息列表，不分页，不含下属小订单数据
   * 
   * @param input 查询参数map数据
   * @param output 输出map数据
   * @return 大订单数据列表
   */
  // public List<Gorder> queryGOrderList(Map input,Map output);

  /**
   * 新增分页查询接口
   * 
   * @param parameterMap
   * @param pageSize 每页大小
   * @param pageNumber 当前页
   * @return
   */
  // public Page<Gorder> queryPagedGorderList(Map inputArgument,Integer pageSize,Integer
  // pageNumber);

  /**
   * 分页查询接口
   * 
   * @param inputArgument
   * @param output
   * @param page 分页对象
   * @return
   */
  @SuppressWarnings("rawtypes")
  public Page<Gorder> queryPagedGorderList(Map inputArgument, Map output, Page<Gorder> page);

  /**
   * 根据gorderId查找大订单明细
   * 
   * @param gorderId
   * @return
   */
  public Gorder queryGorderDetailById(String gorderId);

  /**
   * 查询指定id的大订单明细
   * 
   * @param input 输入字段
   * @param output 输出字段
   * @return
   */
  @SuppressWarnings("rawtypes")
  public Gorder queryGorderDetail(Map input, Map output) throws ValidationException, OrderException;

  /**
   * 修改大订单信息
   * 
   * @param condition
   * @param gorder
   * @return
   */
  @SuppressWarnings("rawtypes")
  public Integer updateGorder(Map condition, Gorder gorder);

  /**
   * 修改大订单及下属小订单信息
   * 
   * @param gorder
   * @param ip
   */
  public void updateGorder(Gorder gorder, String ip);

  /**
   * 修改大订单关单时间
   * 
   * @param gorderId
   * @param preCloseTime
   */
  public void updateGorderPreCloseTime(String gorderId, Timestamp preCloseTime);

  /**
   * 关闭大订单、及下属小订单,生成退款请求记录
   * 
   * @param gorder 大订单对象
   * @param ip 关单调用的ip地址
   * @param note 关单备注
   * @param orderStatusList 要关闭的小订单的状态列表
   * @param closedReason 关单原因
   * @param isNeedCloseOrderItem 是否需要关闭orderItem
   * @param uuid payRequest UUId
   */
  public boolean closeGorder(Gorder gorder, String ip, String note, List<Integer> orderStatusList,
      String closedReason, boolean isNeedCloseOrderItem, String uuid) throws ValidationException,
      OrderException;

  /**
   * 关闭大订单 以及下属小订单，不生成退款请求
   * 
   * @param gorder
   * @param ip
   * @param note
   * @param orderStatusList
   * @param closedReason
   * @param isNeedCloseOrderItem
   * @return
   * @throws ValidationException
   * @throws OrderException
   */
  public boolean closeGorderWithOutRefund(Gorder gorder, String ip, String note,
      List<Integer> orderStatusList, String closedReason, boolean isNeedCloseOrderItem)
      throws ValidationException, OrderException;

  /**
   * 接口39：自动关闭interval天前创建且为未付款状态的大订单及下属的小订单
   * 
   * @param 定期关闭大订单数据
   */
  // public String autoCloseGorder(int interval) throws IOException;


  /**
   * 大订单支付回调通知处理接口：收到支付消息后同步更新大订单及小订单支付状态等信息
   * 
   * @param type 1成功，2失败
   * @param gorder
   * @param remoteIp
   * @throws Exception
   */
  public Gorder gorderPayCallback(Integer type, Gorder gorder, String remoteIp) throws Exception;


  /**
   * 零元支付接口
   * 
   * @param gorderId
   * @param buyAccountId
   * @param remoteIp
   * @return
   * @throws Exception
   */
  // public boolean zeroGorderPaySuccess(String gorderId,String buyAccountId, String remoteIp)
  // throws Exception;


  /**
   * 用于生成订单之后调整订单活动金额，及实际支付金额。活动金额下属小订单及订单条目均分。
   * 
   * @param gorderId
   * @param gsaveAmount
   * @param codeId
   * @return
   */
  // public boolean adjustActivityGorderMoney(String gorderId,BigDecimal gsaveAmount,String codeId);

  /********************************************** GOrder相关接口 结束 **************************************************/

  /********************************************** Order相关接口 开始 **************************************************/
  /**
   * 小订单到合作方下单：发送到合作方
   * 
   * @throws DocumentException
   * @throws IOException
   */
  // public void deliver(Order order);

  /**
   * 合作方异步通知：解析发货结果
   * 
   * @param message
   * @param url
   */
  // public void deliverResult(Order order,ChargeResultMsg message, String url);

  /**
   * 支付前校验接口调用
   * 
   * @param order
   */
  // public boolean lockInventory(Order order);


  /**
   * 查看指定orderId的订单是否存在
   * 
   * @param orderId
   * @return
   */
  public boolean checkExistence(String orderId);

  /**
   * 查询已付费未取货的订单
   * 
   * @param condition 查询条件
   * @return List<Order>
   */
  @SuppressWarnings("rawtypes")
  public List<Order> queryUndeliveredList(Map condition);

  /**
   * 查询订单概要信息列表,不分页
   * 
   * @param input
   * @return
   */
  @SuppressWarnings("rawtypes")
  public List<Order> queryOrderList(Map input);

  /**
   * 查询订单概要信息列表:含分页参数
   * 
   * @param sqlId
   * @param inputArgument 查询条件
   * @param outputField 查询字段
   * @param pageSize 每页大小
   * @param pageNumber 当前页
   * @return
   */
  // public Page<Order> queryPagedOrderList(Map inputArgument,Integer pageSize,Integer pageNumber);

  /**
   * Order列表查询(order表单表查询)
   * 
   * @param parameterMap 参数
   * @param outputMap 输出
   * @param page 分页对象
   * @return 订单列表
   * @throws ValidationException
   * @throws OrderException
   */
  @SuppressWarnings("rawtypes")
  public Page<Order> queryPagedSingleOrderList(Map parameterMap, Map outputMap, Page<Order> page)
      throws ValidationException, OrderException;

  /**
   * 查询订单明细
   * 
   * @param input
   * @return Order
   */
  @SuppressWarnings("rawtypes")
  public Order queryOrderDetail(Map input);

  /**
   * 查询指定id的订单明细
   * 
   * @param id 订单id
   * @return
   */
  public Order queryOrderDetail(String id) throws ValidationException, OrderException;

  /**
   * 查询指定id的订单明细
   * 
   * @param id 订单id
   * @param output 输出字段
   * @return
   */
  @SuppressWarnings("rawtypes")
  public Order queryOrderDetail(String id, Map output) throws ValidationException, OrderException;

  /**
   * 查询指定id的订单明细
   * 
   * @param input 输入字段
   * @param output 输出字段
   * @return
   */
  @SuppressWarnings("rawtypes")
  public Order queryOrderDetail(Map input, Map output) throws ValidationException, OrderException;

  /**
   * 自动关闭preCloseTime非空且本日到期的订单
   * 
   * @return
   */
  // public void closePreDeterminedOrder(Map condition);

  /**
   * 根据大订单ID查询下属的小订单
   * 
   * @param gorderId
   * @return
   */
  public List<Order> queryOrderListByGorderId(String gorderId);

  /**
   * 小订单交易成功
   * 
   * @param orderId
   */
  // public void tradeSuccess(String orderId);

  /**
   * 查询订单数量
   * 
   * @param input
   * @return
   */
  // public Integer queryOrderCount(Map input);

  /**
   * 修改订单
   * 
   * @param order
   * @param remark
   * @param remoteIp
   */
  public void updateOrder(Order order, String remark, String remoteIp) throws ValidationException,
      OrderException;

  /**
   * 修改订单备注
   * 
   * @param orderId
   * @param note
   * @throws ValidationException
   * @throws OrderException
   */
  public void updateOrderNote(String orderId, String note) throws ValidationException,
      OrderException;

  /**
   * 修改订单item备注
   * 
   * @param orderItemId
   * @param note
   * @throws ValidationException
   * @throws OrderException
   */
  public void updateItemNote(String orderItemId, String note) throws ValidationException,
      OrderException;

  /**
   * 修改订单状态
   * 
   * @param orderId
   * @param orderStatus
   * @param note
   * @param remoteIp
   */
  public void updateOrderStatus(String orderId, Integer orderStatus, String note, String remoteIp)
      throws ValidationException, OrderException;

  /********************************************** Order相关接口 结束 **************************************************/

  /********************************************** OrderItem相关接口 开始 **************************************************/
  /**
   * 接口27：修改订单货品关联表，更新发货、退/换货等信息
   * 
   * @param codition
   * @param orderItem
   * @return 成功更新订单条目记录条数
   */
  @SuppressWarnings("rawtypes")
  public Integer updateOrderItem(Map codition, OrderItem orderItem);

  /**
   * 接口30：查找订单条目列表，不分页
   * 
   * @param condition
   * @return
   */
  @SuppressWarnings("rawtypes")
  public List<OrderItem> queryOrderItemList(Map condition);

  /**
   * 接口30：查找订单条目列表，不分页
   * 
   * @param condition
   * @return
   */
  @SuppressWarnings("rawtypes")
  public List<OrderItem> queryOrderItemList(Map condition, Map output);

  /**
   * 接口31：分页查询订单条目列表
   * 
   * @param condition:查询条件
   * @param pageSize:页面大小
   * @param pageNo:当前页
   * @return
   */
  // public Page<OrderItem> pagedQuery(Map condition,Integer pageSize,Integer pageNo);

  /**
   * 根据OrderID查询下属orderitem列表
   * 
   * @param orderId
   * @return
   */
  public List<OrderItem> queryOrderItemByOrderId(String orderId);

  /**
   * 根据订单条目id查找该条目的明细
   * 
   * @param orderItemId
   * @return
   */
  public OrderItem queryOrderItemDetailById(String orderItemId);

  /**
   * 根据查询条件查找该条目的明细
   * 
   * @param inputParam
   * @param outputParam
   * @return
   */
  public OrderItem queryOrderItemDetail(Map<String, Object> inputParam,
      Map<String, Object> outputParam);

  /**
   * 根据大订单号查找用户一次下单所购买的商品列表
   * 
   * @param gorderId
   * @return
   */
  public List<OrderItem> queryOrderItemListByGorderId(String gorderId);

  /**
   * 添加订单条目明细
   * 
   * @param orderItem
   * @return true--添加成功 false--添加失败
   */
  public boolean addOrderItem(OrderItem orderItem) throws ValidationException, OrderException;

  /********************************************** OrderItem相关接口 结束 **************************************************/

  /********************************************** 级联查询相关接口 开始 **************************************************/


  /**
   * Gorder-Order-OrderItem级联查询
   * 
   * @param orderItemId
   * @param output 定义输出字段
   * @return 返回的OrderItem对象内含Order对象，Gorder对象
   */
  @SuppressWarnings("rawtypes")
  public OrderItem queryCascadeOrderDetail(String orderItemId, Map output)
      throws ValidationException, OrderException;

  /**
   * Gorder-Order-OrderItem级联查询
   * 
   * @param input 定义查询参数
   * @param output 定义输出字段
   * @return 返回的OrderItem对象内含Order对象，Gorder对象
   */
  @SuppressWarnings("rawtypes")
  public OrderItem queryCascadeOrderDetail(Map input, Map output) throws ValidationException,
      OrderException;

  /**
   * Gorder-Order-OrderItem级联查询
   * 
   * @param parameterMap
   * @param outputMap
   * @param page
   * @return
   * @throws ValidationException
   * @throws OrderException
   */
  @SuppressWarnings("rawtypes")
  public Page<OrderItem> queryPagedCascadeOrderList(Map parameterMap, Map outputMap,
      Page<OrderItem> page) throws ValidationException, OrderException;

  /**
   * queryPagedOrderOrderItemList
   * 
   * @Title: queryPagedOrderOrderItemList
   * @Description: order - orderItem 两级联查
   * @param queryParam
   * @param outputMap
   * @param page
   * @return
   */
  public Page<OrderItem> queryPagedOrderOrderItemList(Map<String, Object> queryParam,
      Map<String, Object> outputMap, Page<OrderItem> page);

  /**
   * Gorder-Order级联查询
   * 
   * @param input 定义查询参数
   * @param output 定义输出字段
   * @return 返回的OrderItem对象内含Order对象，Gorder对象
   */
  @SuppressWarnings("rawtypes")
  public Order queryCascadeGorderOrderDetail(Map input, Map output) throws ValidationException,
      OrderException;

  /**
   * Gorder-Order级联查询
   * 
   * @param parameterMap 参数
   * @param outputMap 输出
   * @param page 分页对象
   * @return 订单列表
   * @throws ValidationException
   * @throws OrderException
   */
  @SuppressWarnings("rawtypes")
  public Page<Order> queryPagedOrderList(Map parameterMap, Map outputMap, Page<Order> page)
      throws ValidationException, OrderException;

  /********************************************** 级联查询相关接口 结束 **************************************************/


  /********************************************** 支付相关接口 开始 **************************************************/

  /**
   * 生成payment请求记录
   * 
   * @param payRequest 封装支付请求
   */
  public void addPayRequest(PayRequest payRequest) throws ValidationException, OrderException;

  /**
   * 生成payrecord记录
   * 
   * @param payRecord 封装支付请求
   */
  public void addPayRecord(PayRecord payRecord) throws ValidationException, OrderException;

  /**
   * 查询payrequest请求记录列表
   * 
   * @param input
   * @param output
   * @param page
   * @return
   * @throws Exception
   */
  public Page<PayRequest> queryPagedPayRequestList(Map<String, Object> input,
      Map<String, Object> output, Page<PayRequest> page) throws ValidationException, OrderException;

  /**
   * 查询payrecord记录列表
   * 
   * @param input
   * @param output
   * @param page
   * @return
   * @throws Exception
   */
  @SuppressWarnings("rawtypes")
  public Page<PayRecord> queryPagedPayRecordList(Map input, Map output, Page page)
      throws ValidationException, OrderException;

  /**
   * 查询某订单上次支付的payment交易流水号
   * 
   * @param gorderId
   * @return
   * @throws ValidationException
   * @throws OrderException
   */
  public PayRecord queryLastPayNo(String gorderId) throws ValidationException, OrderException;

  /**
   * 修改payrecord记录
   * 
   * @param payNo 交易流水号
   * @param paymentStatus 通知状态，2处理成功，-1处理失败
   * @param paymentTime
   * @param failureReason
   * @param processDays
   * @return
   * @throws ValidationException
   * @throws OrderException
   */
  public void paymentCallback(String payNo, Integer paymentStatus, Timestamp paymentTime,
      String failureReason, String processDays) throws ValidationException, OrderException;

  /**
   * 支付失败，用户再次支付时调用此方法写pay_request记录
   * 
   * @return 0 正常；1 订单不存在；2 订单状态不是支付失败；3 记录重复;4 找不到失败记录;5 订单Id为空
   * @throws ValidationException
   * @throws OrderException
   */
  public int continuePay(String gorderId) throws ValidationException, OrderException;

  /**
   * 支付失败，用户再次支付时调用此方法写pay_request记录
   * 
   * @return 0 正常；1 订单不存在；2 订单状态不是支付失败；3 记录重复;4 找不到失败记录;5 订单Id为空
   * @throws ValidationException
   * @throws OrderException
   */
  public ContinuePayVO continuePay(ContinuePayPojo pojo) throws ValidationException, OrderException;

  /**
   * 获取token
   * 
   * @param pojo
   * @return
   */
  public GetTokenVO getToken(GetTokenPojo pojo);

  /**
   * 支付回调时，根据交易流水号查询这笔交易对应的gorderId和orderId
   * 
   * @param payNo 支付交易流水号
   * @return order对象，内含gorderId,id属性的值
   * @throws ValidationException
   * @throws OrderException
   */
  public Order queryOrderByPayNo(String payNo) throws ValidationException, OrderException;

  /**
   * 根据交易流水号（token)查出PayRecord
   * 
   * @param payNo
   * @return
   * @throws ValidationException
   * @throws OrderException
   */
  public PayRecord queryPayRecordByPayNo(String payNo) throws ValidationException, OrderException;

  /**
   * @deprecated 将payrequest记录转移至payrecord中
   * 
   * @param payRequestId
   * @param processDays 处理天数
   * @throws ValidationException
   * @throws OrderException
   * @payStatus 1处理 中；3未找到前置交易号（退款请求过去，未找到扣款请求的情况）
   */
  public void movePayRequestToRecord(String payRequestId, String processDays, Integer payStatus,
      String failReason) throws ValidationException, OrderException;

  /**
   * 将payrequest记录转移至payrecord中
   * 
   * @param payRequest
   * @param payRecord
   */
  public void movePayRequestToRecord(PayRequest payRequest, PayRecord payRecord);

  /**
   * 更新payrequest记录
   * 
   * @param condition 条件
   * @param entity 更新内容
   * @return true成功更新一条记录
   * @throws ValidationException
   * @throws OrderException
   */
  public boolean updatePayRequest(PayRequest condition, PayRequest entity)
      throws ValidationException, OrderException;

  /**
   * 更新payrecord记录
   * 
   * @param condition 条件
   * @param entity 更新内容
   * @return true成功更新一条记录
   * @throws ValidationException
   * @throws OrderException
   */
  public boolean updatePayRecord(PayRecord condition, PayRecord entity) throws ValidationException,
      OrderException;

  /**
   * 查询最后一次失败的payrecord记录
   * 
   * @param input
   * @param output
   * @return
   * @throws ValidationException
   * @throws OrderException
   */
  @SuppressWarnings("rawtypes")
  public PayRecord queryLastFailedPayRecord(Map input, Map output) throws ValidationException,
      OrderException;

  /********************************************** 支付相关接口 结束 **************************************************/

  /********************************************** 发票相关接口 开始 **************************************************/

  /**
   * 根据gorderId取运单信息
   * 
   * @param gorderId
   * @return 运单信息
   * @throws ValidationException
   * @throws OrderException
   */
  public WayBill queryWayBillByGorderId(String gorderId) throws ValidationException, OrderException;

  /**
   * 修改运单信息
   * 
   * @param condition where条件
   * @param wayBill 修改内容
   * @return
   * @throws ValidationException
   * @throws OrderException
   */
  public Integer updateWayBill(WayBill condition, WayBill wayBill) throws ValidationException,
      OrderException;

  /**
   * 查询运单列表
   * 
   * @param parameterMap 封装查询条件
   * @return 运单列表
   */
  @SuppressWarnings("rawtypes")
  public Page<WayBill> queryPagedWayBillList(Map parameterMap, Page<WayBill> page)
      throws ValidationException, OrderException;

  /**
   * 查询支付记录列表
   * 
   * @param pojo
   * @return
   */
  public PayRecordListVO queryPayRecordList(QueryRecordPojo pojo) throws ValidationException,
      OrderException;

  /**
   * 查询一个gorder的第一次支付记录
   * 
   * @param gorder
   * @return
   */
  public List<PayRecord> queryFirstPayRecord(Gorder gorder);

  public FirstPayInfoVO queryFirstPayInfo(QueryFirstPayInfoPojo pojo);

  public PayDetailResponse queryOrderPayDetail(QueryOrderPayDetailPojo pojo);


  /********************************************** 发票相关接口 结束 **************************************************/
  public UpdateBuyAccountIdByGorderIdResponse updateBuyAccountIdByGorderId(
      UpdateBuyAccountIdByGorderIdPojo pojo);


  /********************************************** 公共判重相关接口 开始 **************************************************/

  /**
   * 查询tag，判断是否已经处理过 如果没有处理过，返回true 或许可以处理这个操作 否则，返回false
   * 
   * @param tag
   * @return
   */
  public boolean queryTagForStart(String tag);

  /**
   * 查询tag，判断是否已经处理过 如果没有处理过，返回true && 更新记录为处理完成状态 否则，返回false
   * 
   * @param tag
   * @return
   */
  public boolean updateTagForFinish(String tag);

  /********************************************** 公共判重相关接口 结束 **************************************************/

}
