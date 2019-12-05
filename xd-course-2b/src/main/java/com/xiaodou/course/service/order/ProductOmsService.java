package com.xiaodou.course.service.order;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xiaodou.common.cache.redis.JedisUtil;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.log.model.AlarmEntityImpl;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.course.constant.CourseConstant;
import com.xiaodou.course.enums.BuyProductStatus;
import com.xiaodou.course.enums.ProductOrderState;
import com.xiaodou.course.model.http.ApplyAlarmEntity;
import com.xiaodou.course.model.http.ApplyRequest.ApplyRequestDTO;
import com.xiaodou.course.model.http.ApplyResponse.ApplyResponseDTO;
import com.xiaodou.course.model.order.ProductOrderModel;
import com.xiaodou.course.model.product.ProductModel;
import com.xiaodou.course.service.AbstractService;
import com.xiaodou.course.service.facade.ProductServiceFacade;
import com.xiaodou.course.service.product.ProductCategoryService;
import com.xiaodou.course.service.product.ProductService;
import com.xiaodou.course.util.DateUtil;
import com.xiaodou.course.web.request.BaseRequest;
import com.xiaodou.course.web.request.order.CreateOrderRequest;
import com.xiaodou.course.web.request.order.PayOrderRequest;
import com.xiaodou.course.web.response.order.PayOrderResponse;
import com.xiaodou.course.web.response.order.ProductOrderResponse;
import com.xiaodou.course.web.response.resultType.ProductResType;
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
import com.xiaodou.payment.constant.PaymentStatus;
import com.xiaodou.payment.out.PaymentService;
import com.xiaodou.payment.service.PayService;
import com.xiaodou.payment.util.ClientUtil;
import com.xiaodou.payment.vo.PayMerchant;
import com.xiaodou.payment.vo.request.PayRequest;
import com.xiaodou.payment.vo.request.inner.MixPaymentCa;
import com.xiaodou.payment.vo.response.PayResponse;
import com.xiaodou.payment.vo.response.TokenResponse;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.wallet.agent.util.AccountClassifyUtil;

@Service("productOmsService")
public class ProductOmsService extends AbstractService {

  @Resource
  ProductServiceFacade productServiceFacade;

  @Resource
  OrderService orderService;

  @Resource
  ProductCategoryService productCategoryService;

  @Resource
  ProductService productService;

  /**
   * 下单
   */
  public ProductOrderResponse creatGorder(CreateOrderRequest request) {
    Context context = new Context(new StrategyByProp());
    if (context.strategyMethod(request.getProductId())) {
      return new ProductOrderResponse(ProductResType.CAN_NOT_ORDER);
    }
    ProductOrderResponse orderResult = null;
    ProductModel productModel = productService.findProductById(request.getProductId());
    if (!(null != productModel && null != productModel.getBeginApplyTime()
        && null != productModel.getEndApplyTime() && DateUtil.ifIsValid(
        productModel.getBeginApplyTime(), productModel.getEndApplyTime()))) {
      return new ProductOrderResponse(ProductResType.NotFindCourse);
    }

    // 1.重单（ok）
    if (this.isRepeat(this.buildOrderRepeatValidateString(request))) {
      return new ProductOrderResponse(ProductResType.OrderRepeat);
    }

    // 4.获取验证课程信息及价格信息(ok)
    ProductModel product = new ProductModel();
    product.setId(request.getProductId());
    product = productServiceFacade.queryProductById(product);
    if (product == null) {
      return new ProductOrderResponse(ProductResType.NotFindCourse);
    }
    // 判断课程的有效性
    if (!DateUtil.ifIsValid(productModel.getBeginApplyTime(), productModel.getEndApplyTime()))
      return new ProductOrderResponse(ProductResType.IsNotValid);
    BigDecimal price = BigDecimal.ZERO;
    if (null != product.getPayAmount())
      price = BigDecimal.valueOf(product.getPayAmount());
    else if (null != product.getOriginalAmount())
      price = BigDecimal.valueOf(product.getOriginalAmount());

    // 5.查看课程是否已够买
    IQueryParam param = new QueryParam();
    param.addInput("uid", request.getUid());
    param.addInput("productId", request.getProductId());
    param.addInput("module", request.getModule());
    param.addInput("status", CourseConstant.BUY_COURSE_FINISH);
    param.addOutput("id", "1");
    Page<ProductOrderModel> userProductOrderList =
        productServiceFacade.queryProductOrderModelByCond(param);
    if (null != userProductOrderList && null != userProductOrderList.getResult()
        && userProductOrderList.getResult().size() > 0)
      return new ProductOrderResponse(ProductResType.HasOrder);

    // 7.获取支付token（外部系统）
    String token = this.getPaymentToken();
    if (StringUtils.isBlank(token)) {
      return new ProductOrderResponse(ProductResType.GetTokenFail);
    }

    // 8.1 构建下单信息
    Gorder gorder = this.buildGorder(request, product, price);
    gorder.setPayOrderId(token);
    // 8.3 构建接口参数
    NewOrderRequest newOrderRequest = new NewOrderRequest();
    newOrderRequest.setGorder(gorder);
    newOrderRequest.setProductLine(formatProductLine());

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

  private String getPaymentToken() {
    TokenResponse payToken = PaymentService.getToken(formatProductLine());
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
    orderItem.setPrctId(product.getId().longValue());
    orderItem.setProductType(formatProductLine() + CourseConstant.PRODUCT_TYPE);
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
    orderItem.setItemStatus(BuyProductStatus.INIT.getStatusId());
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
    order.setProductType(formatProductLine() + CourseConstant.PRODUCT_TYPE); // 自考课程产品类型
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
    gorder.setGoodsName(product.getName());
    gorder.setGorderDesc("北京小逗网络-课程订单");
    gorder.setBuyAccountId(pojo.getUid());
    // 拟定订单完成时间
    gorder.setExpectedShippingTime(new Timestamp(System.currentTimeMillis()));
    gorder.setUpdateTime(now);
    gorder.setClientType(pojo.getClientType());
    gorder.setProductType(formatProductLine() + CourseConstant.PRODUCT_TYPE);
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
    String result = JedisUtil.getStringFromJedis(repeatValidateString);
    if (result != null) {
      return true;
    } else {
      JedisUtil.addStringToJedis(repeatValidateString, CourseConstant.YES,
          CourseConstant.DEFAULT_REPEAT_TIME);
      return false;
    }
  }

  /**
   * 生成重单字符串
   * 
   * @return
   */
  private String buildOrderRepeatValidateString(BaseRequest request) {
    return FastJsonUtil.toJson(request);
  }

  /**
   * 发起支付请求
   * 
   * @param request
   * @return
   */
  public PayOrderResponse pay(PayOrderRequest request) {
    PayOrderResponse response = new PayOrderResponse(ResultType.SUCCESS);
    // 1.重单（ok）
    if (this.isRepeat(this.buildOrderRepeatValidateString(request))) {
      return new PayOrderResponse(ProductResType.HasOrder);
    }
    PayRequest payReq = new PayRequest();
    GorderDetailResponse gorderResponse =
        queryGorderInfo(request.getGorderId(), formatProductLine(),
            request.getUid());
    if (!gorderResponse.isRetOk()) {
      response.setRetcode(Integer.toString(gorderResponse.getRetCode()));
      response.setRetdesc(gorderResponse.getRetDesc());
      return response;
    }
    Gorder gorder = gorderResponse.getGorder();

    // 判断订单是否可支付
    if (GorderState.Success.getState().equals(gorder.getGorderStatus())) {
      response.setPayStatus(PaymentStatus.NOTIFY_OKCODE.toString());
      return response;
    } else if (!GorderState.Init.getState().equals(gorder.getGorderStatus()))
      return new PayOrderResponse(ProductResType.HasOrder);
    MixPaymentCa ca = new MixPaymentCa();
    ca.setSubject(gorder.getGoodsName());
    ca.setBody(gorder.getGorderDesc());
    ca.setAmt(gorder.getGorderAmount().doubleValue());
    ca.setCaCurrency(Integer.valueOf(AccountClassifyUtil.getAccountClassifyByDesc(
        request.getClientType()).getCode()));
    ca.setBusinessType(CourseConstant.BUSINESS_TYPE_ZKJ);
    ca.setProductLine(formatProductLine());
    ca.setMemberCardNo(request.getUid());
    payReq.setMixPaymentCa(ca);

    payReq.setOrderId(request.getGorderId());
    payReq.setTradeNo(Long.parseLong(gorder.getPayOrderId()));

    payReq.setBusinessType(PayMerchant.getBusinessType(formatProductLine()));
    payReq.setMerchantId(PayMerchant.getMerchantId(formatProductLine()));
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
        PayService.payWithProductLine(payReq, formatProductLine());
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



  /**
   * 
   * @description 导入报名课程
   * @author 李德洪
   * @Date 2017年4月26日
   * @param pojo
   * @return
   */
  public ApplyResponseDTO importProduct(ApplyRequestDTO pojo) {
    ApplyResponseDTO response = new ApplyResponseDTO();
    try {
      // 查看课程是否已够买
      IQueryParam param = new QueryParam();
      param.addInput("uid", pojo.getUserId());
      param.addInput("productId", pojo.getProductId());
      param.addInput("module", pojo.getModule());
      param.addInput("status", CourseConstant.BUY_COURSE_FINISH);
      param.addOutput("id", "1");
      Page<ProductOrderModel> userProductOrderList =
          productServiceFacade.queryProductOrderModelByCond(param);
      if (null != userProductOrderList && null != userProductOrderList.getResult()
          && userProductOrderList.getResult().size() > 0) {
        AlarmEntityImpl alarm = new ApplyAlarmEntity("业务系统中已经存在导入课程", pojo);
        LoggerUtil.alarmInfo(alarm);
        response.setProductId(pojo.getProductId());
        response.setStudentId(pojo.getStudentId());
        response.setTelephone(pojo.getTelephone());;
        response.setApplyStatus(CourseConstant.BUSSINESS_APPLY_ALREADY);
        return response;
      }
      // 插入产品订单记录
      ProductOrderModel productOrder = new ProductOrderModel();
      productOrder.setModule(pojo.getModule());
      productOrder.setProductLine(super.formatProductLine());
      productOrder.setProductType(super.formatProductLine()
          + CourseConstant.PRODUCT_TYPE);
      productOrder.setGorderId(System.currentTimeMillis() + "");
      productOrder.setProductId(pojo.getProductId().toString());
      productOrder.setUid(pojo.getUserId().toString());
      productOrder.setCreateTime(new Timestamp(System.currentTimeMillis()));
      productOrder.setId(UUID.randomUUID().toString());
      productOrder.setStatus(CourseConstant.BUY_COURSE_FINISH);
      productOrder.setFinishTime(new Timestamp(System.currentTimeMillis()));
      ProductOrderModel model = productServiceFacade.insertProductOrderModel(productOrder);
      if (null != model) {
        response.setProductId(pojo.getProductId());
        response.setStudentId(pojo.getStudentId());
        response.setApplyStatus(CourseConstant.BUSSINESS_APPLY_SUCCESS);
      }
      return response;
    } catch (Exception e) {
      AlarmEntityImpl alarm = new ApplyAlarmEntity("导入课程报警", pojo);
      LoggerUtil.alarmInfo(alarm);
      LoggerUtil.error("service层：导入课程报警", e);
      return new ApplyResponseDTO();
    }
  }
}
