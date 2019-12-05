package com.xiaodou.resources.service.product.order;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.xiaodou.common.cache.redis.JedisUtil;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.oms.agent.common.enums.GorderState;
import com.xiaodou.oms.agent.model.Gorder;
import com.xiaodou.oms.agent.model.Order;
import com.xiaodou.oms.agent.model.OrderItem;
import com.xiaodou.oms.agent.service.OrderService;
import com.xiaodou.oms.agent.util.OrderConfigService;
import com.xiaodou.oms.agent.vo.request.GorderDetailRequest;
import com.xiaodou.oms.agent.vo.request.NewOrderRequest;
import com.xiaodou.oms.agent.vo.response.GorderDetailResponse;
import com.xiaodou.oms.agent.vo.response.NewOrderResponse;
import com.xiaodou.payment.constant.PaymentConfig;
import com.xiaodou.payment.out.PaymentService;
import com.xiaodou.payment.service.PayService;
import com.xiaodou.payment.util.ClientUtil;
import com.xiaodou.payment.vo.PayMerchant;
import com.xiaodou.payment.vo.request.PayRequest;
import com.xiaodou.payment.vo.request.inner.MixPaymentCa;
import com.xiaodou.payment.vo.response.PayResponse;
import com.xiaodou.payment.vo.response.TokenResponse;
import com.xiaodou.resources.constant.product.CourseConstant;
import com.xiaodou.resources.enums.product.ProductOrderState;
import com.xiaodou.resources.model.order.ProductOrderModel;
import com.xiaodou.resources.model.product.ProductModel;
import com.xiaodou.resources.model.user.UserProductOrderModel;
import com.xiaodou.resources.request.order.CreateOrderRequest;
import com.xiaodou.resources.request.order.PayOrderRequest;
import com.xiaodou.resources.response.order.PayOrderResponse;
import com.xiaodou.resources.response.order.ProductOrderResponse;
import com.xiaodou.resources.response.resultype.ProductResType;
import com.xiaodou.resources.service.product.AbstractService;
import com.xiaodou.resources.service.product.facade.ProductServiceFacade;
import com.xiaodou.summer.vo.out.ResultType;

@Service("productOmsService")
public class ProductOmsService extends AbstractService {

  @Resource
  ProductServiceFacade productServiceFacade;

  @Resource
  OrderService orderService;

  /**
   * 下单
   */
  public ProductOrderResponse creatGorder(CreateOrderRequest request) {
    ProductOrderResponse orderResult = null;

    // 1.重单（ok）
    if (this.isRepeat(this.buildOrderRepeatValidateString(request))) {
      return new ProductOrderResponse(ProductResType.OrderRepeat);
    }

    // 4.获取验证课程信息及价格信息(ok)
    ProductModel product = new ProductModel();
    product.setId(request.getProductId());
    // product.setMajorCode(request.getTypeCode());
    product = productServiceFacade.queryProductById(product);
    if (product == null) {
      return new ProductOrderResponse(ProductResType.NotFindCourse);
    }
    BigDecimal price = BigDecimal.ZERO;
    if (null != product.getPayAmount())
      price = BigDecimal.valueOf(product.getPayAmount().intValue());
    else if (null != product.getOriginalAmount())
      price = BigDecimal.valueOf(product.getOriginalAmount().intValue());

    // 5.查看课程是否已够买
    Map<String, Object> input = Maps.newHashMap();
    input.put("userId", request.getUid());
    input.put("courseId", request.getProductId());
    input.put("isExp", CourseConstant.IS_EXP);
    List<UserProductOrderModel> userProductOrderList =
        productServiceFacade.queryUserProductOrderByCond(input,
            CommUtil.getAllField(UserProductOrderModel.class));
    if (null != userProductOrderList && userProductOrderList.size() > 0)
      return new ProductOrderResponse(ProductResType.HasOrder);

    // 7.获取支付token（外部系统）
    String token = this.getPaymentToken(request.getModule());
    if (StringUtils.isBlank(token)) {
      return new ProductOrderResponse(ProductResType.GetTokenFail);
    }

    // 8.1 构建下单信息
    Gorder gorder = this.buildGorder(request, product, price);
    gorder.setPayOrderId(token);
    // 8.3 构建接口参数
    NewOrderRequest newOrderRequest = new NewOrderRequest();
    newOrderRequest.setGorder(gorder);
    newOrderRequest.setProductLine(formatProductLine(request.getModule()));

    // 8.4 下单
    NewOrderResponse response = orderService.createGorder(newOrderRequest);

    // 插入产品订单记录
    ProductOrderModel productOrder = new ProductOrderModel();
    productOrder.setModule(request.getModule());
    productOrder.setProductLine(newOrderRequest.getProductLine());
    productOrder.setProductType(gorder.getProductType());
    productOrder.setGorderId(response.getGorderId());
    productOrder.setProductId(request.getProductId().toString());
    productOrder.setUid(request.getUid());
    productOrder.setCreateTime(new Timestamp(System.currentTimeMillis()));
    productOrder.setId(UUID.randomUUID().toString());
    productOrder.setStatus(CourseConstant.BUY_COURSE_INIT);
    productServiceFacade.insertProductOrderModel(productOrder);

    // 8.5 反馈结果
    if (response == null || response.getRetCode() != 0) {
      // 针对异常的校验
      // LoggerUtil.validate12306("下单响应:" + JSON.toJSONString(response));
      return new ProductOrderResponse(ResultType.SYSFAIL);
    } else {
      orderResult = new ProductOrderResponse(ResultType.SUCCESS);
      orderResult.setGorderId(response.getGorderId());
      orderResult.setServerIp(response.getServerIp());
      return orderResult;
    }

  }

  private String getPaymentToken(String module) {
    TokenResponse payToken = PaymentService.getToken(formatProductLine(module));
    if (payToken == null) {
      return null;
    } else {
      if (StringUtils.isBlank(payToken.getTradeNo())) {
        return null;
      } else {
        return payToken.getTradeNo();
      }
    }
  }

  /**
   * 构建订单信息
   * 
   * @param merchant 分配的课程产品供应商
   * @param pojo 下单信息
   * @param startEndTicketInfo 车次信息
   * @param price 当前车次价格
   * @return
   */
  private Gorder buildGorder(CreateOrderRequest pojo, ProductModel product, BigDecimal price) {
    // 1.获取下单时间
    Timestamp now = new Timestamp(System.currentTimeMillis());
    // 3.构建Order列表
    List<Order> orderList = new ArrayList<Order>();
    // 3.1填充课程产品Order
    Order trainOrder = this.fillProductOrder(pojo, now, price);
    orderList.add(trainOrder);
    // 3.2课程产品orderItem列表
    List<OrderItem> trainOrderItemList = new ArrayList<OrderItem>();
    trainOrder.setOrderItemList(trainOrderItemList);
    // 课程产品orderItem设置
    OrderItem orderItem = this.fillProductOrderItem(pojo, product, price, now);
    trainOrderItemList.add(orderItem);

    // 4.生成Gorder
    Gorder gorder = this.fillGorder(pojo, product, price, now);
    gorder.setOrderList(orderList);
    return gorder;
  }

  /**
   * 构建课程产品订单项目
   * 
   * @param pojo
   * @param trainPassenger
   * @param price
   * @return
   */
  private OrderItem fillProductOrderItem(CreateOrderRequest pojo, ProductModel product,
      BigDecimal price, Timestamp now) {
    OrderItem orderItem = new OrderItem();
    orderItem.setProductType(formatProductLine(pojo.getModule()) + CourseConstant.PRODUCT_TYPE);
    orderItem.setProductName(product.getName());
    orderItem.setBuyCount(1);
    orderItem.setItemOrderAmount(price);
    orderItem.setOriginalItemPayAmount(price);
    orderItem.setItemSaveAmount(BigDecimal.ZERO);
    orderItem.setItemPayAmount(price);
    orderItem.setItemCostAmount(BigDecimal.ZERO);
    orderItem.setItemFee(BigDecimal.ZERO);
    orderItem.setCreateTime(now);
    orderItem.setUpdateTime(now);
    // orderItem.setItemStatus(BuyProductStatus.INIT.getStatusId());
    return orderItem;
  }

  /**
   * 构建产品订单order
   * 
   * @param pojo
   * @return
   */
  private Order fillProductOrder(CreateOrderRequest pojo, Timestamp now, BigDecimal price) {
    Order order = new Order();
    order.setProductType(formatProductLine(pojo.getModule()) + CourseConstant.PRODUCT_TYPE); // 自考课程产品类型
    order.setOrderTime(now); // 下单时间
    order.setOrderStatus(ProductOrderState.Init.getName()); // 订单状态
    order.setOrderAmount(price); // 车票总价格
    order.setSaveAmount(BigDecimal.ZERO); // 节省费用默认0
    order.setPayAmount(price); // 支付价格默认0
    order.setFee(BigDecimal.ZERO);
    order.setOriginalPayAmount(price);
    order.setCostAmount(BigDecimal.ZERO);
    order.setBuyAccountId(pojo.getUid());
    order.setUpdateTime(now);
    order.setSettleUp(0);
    order.setMerchantId(0);
    order.setMerchantName("北京小逗网络科技有限公司");
    order.setOrderIp(pojo.getClientIp());
    return order;
  }

  /**
   * @param startEndTicketInfo 火车信息
   * @param pojo 下单信息
   * @param amount 总金额
   * @param now 当前时间
   * @return
   */
  private Gorder fillGorder(CreateOrderRequest pojo, ProductModel product, BigDecimal amount,
      Timestamp now) {
    // 关单时间间隔
    Gorder gorder = new Gorder();
    gorder.setGorderTime(now);
    gorder.setGorderAmount(amount);
    gorder.setGpayAmount(amount);
    gorder.setOriginalGpayAmount(amount);
    gorder.setGsaveAmount(BigDecimal.ZERO);
    gorder.setGfee(BigDecimal.ZERO);
    gorder.setGorderStatus(GorderState.Init.getState());
    gorder.setGoodsName("小逗网络自考课程");
    gorder.setBuyAccountId(pojo.getUid());
    // 拟定订单完成时间
    gorder.setExpectedShippingTime(new Timestamp(System.currentTimeMillis()));
    gorder.setUpdateTime(now);
    gorder.setClientType(pojo.getClientType());
    gorder.setProductType(formatProductLine(pojo.getModule()) + CourseConstant.PRODUCT_TYPE);
    gorder.setGorderIp(pojo.getClientIp());
    /**
     * DMO参数
     */
    gorder.setIsLogin(1);
    gorder.setUid(pojo.getUid());
    gorder.setOrderFrom(Integer.toString(ClientUtil.getClinetType(pojo.getClientType()).getCode()));
    gorder.setDeviceId(pojo.getDeviceId());
    return gorder;
  }

  /**
   * 判断是否重单
   * 
   * @return
   */
  private Boolean isRepeat(String repeatValidateString) {
    // Long begin = System.currentTimeMillis();`
    String result = JedisUtil.getStringFromJedis(repeatValidateString);
    // TODO actionLog
    // this.actionLog(true, "isRepeat", null, System.currentTimeMillis()
    // - begin, 0);
    if (result != null) {
      return true;
    } else {
      return false;
    }
  }

  /**
   * 生成重单字符串
   * 
   * @return
   */
  private String buildOrderRepeatValidateString(CreateOrderRequest request) {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(request.getModule());
    stringBuilder.append(request.getUid());
    // stringBuilder.append(request.getTypeCode());
    stringBuilder.append(request.getProductId());
    return stringBuilder.toString();
  }

  /**
   * 根据module格式化productLine
   * 
   * @param module
   * @return
   */
  private String formatProductLine(String module) {
    return module.length() > 2 ? module.substring(module.length() - 2) : module.length() == 1
        ? String.format("0%s", module)
        : module;
  }

  /**
   * 发起支付请求
   * 
   * @param request
   * @return
   */
  public PayOrderResponse pay(PayOrderRequest request) {
    PayOrderResponse response = new PayOrderResponse(ResultType.SUCCESS);
    PayRequest payReq = new PayRequest();
    GorderDetailResponse gorderResponse =
        queryGorderInfo(request.getGorderId(), formatProductLine(request.getModule()),
            request.getUid());
    if (!gorderResponse.isRetOk()) {
      response.setRetcode(Integer.toString(gorderResponse.getRetCode()));
      response.setRetdesc(gorderResponse.getRetDesc());
      return response;
    }
    Gorder gorder = gorderResponse.getGorder();
    MixPaymentCa ca = new MixPaymentCa();
    ca.setAmt(gorder.getGorderAmount().doubleValue());
    ca.setCaCurrency(PayMerchant.getCurrency());
    ca.setProductLine(formatProductLine(request.getModule()));
    ca.setMemberCardNo(request.getUid());
    payReq.setMixPaymentCa(ca);

    payReq.setOrderId(request.getGorderId());
    payReq.setTradeNo(Long.parseLong(gorder.getPayOrderId()));

    payReq.setBusinessType(PayMerchant.getBusinessType(formatProductLine(request.getModule())));
    payReq.setMerchantId(PayMerchant.getMerchantId(formatProductLine(request.getModule())));
    payReq.setMixPaymentType(PaymentConfig.MIX_PAYMENT_CA);
    payReq
        .setOrderFrom(Integer.toString(ClientUtil.getClinetType(gorder.getClientType()).getCode()));
    payReq.setPayFrom(ClientUtil.getClinetType(request.getClientType()).getCode());
    payReq.setTotalAmt(gorder.getGorderAmount().doubleValue());
    payReq.setNotifyUrl(String.format("%s://%s:%s%s",
        OrderConfigService.getParams("oms.connection.protocol"),
        OrderConfigService.getParams("oms.connection.host"),
        OrderConfigService.getParams("oms.connection.port"),
        OrderConfigService.getParams("oms.payment.callback")));
    PayResponse payResponse =
        PayService.payWithProductLine(payReq, formatProductLine(request.getModule()));
    if (payResponse.isRetOk()) {
      response.setPayStatus(payResponse.getPayStatus());
      response.setPayInfo(payResponse.getPayInfo());
    } else {
      response.setRetcode(payResponse.getRetcode());
      response.setRetdesc(payResponse.getRetdesc());
    }
    return response;
  }

  /**
   * 查询支付订单详情
   * 
   * @param gorderId 支付订单号
   * @param productLine 产品线
   * @param uid 用户ID
   * @return 支付订单详情
   */
  private GorderDetailResponse queryGorderInfo(String gorderId, String productLine, String uid) {
    GorderDetailRequest request = new GorderDetailRequest();
    request.setBuyAccountId(uid);
    request.setProductLine(productLine);
    request.setGorderId(gorderId);
    request.setOutputProperties(CommUtil.getAllField(Gorder.class));

    return orderService.queryGorderDetail(request);
  }

}
