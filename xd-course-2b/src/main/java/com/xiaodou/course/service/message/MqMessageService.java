package com.xiaodou.course.service.message;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.StringUtils;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.course.constant.CourseConstant;
import com.xiaodou.course.model.message.ApplyCallBackMessage;
import com.xiaodou.course.model.message.ApplyCallBackMessage.ApplyCallBackMessageBody;
import com.xiaodou.course.model.message.ApplyCallBackMessage.ApplyCallBackMessageBodyDTO;
import com.xiaodou.course.model.message.ApplyMessageBody;
import com.xiaodou.course.model.message.ApplyMessageBody.ApplyMessageBodyDTO;
import com.xiaodou.course.model.order.ProductOrderModel;
import com.xiaodou.course.model.user.UserChapterRecordModel;
import com.xiaodou.course.model.user.UserProductOrderModel;
import com.xiaodou.course.service.AbstractService;
import com.xiaodou.course.service.facade.ProductServiceFacade;
import com.xiaodou.course.service.product.ProductItemService;
import com.xiaodou.course.service.product.UserLearnAchieveService;
import com.xiaodou.course.service.product.UserLearnStaticsService;
import com.xiaodou.course.service.product.UserLearnTaskService;
import com.xiaodou.course.service.product.UserProductOrderService;
import com.xiaodou.course.vo.user.UserLearnAchieveVo;
import com.xiaodou.course.web.request.product.UserProductOrderRequest;
import com.xiaodou.course.web.request.user.UserLearnAchieveRequest;
import com.xiaodou.jmsg.client.RabbitMQSender;
import com.xiaodou.jmsg.entity.JMSGPojo;
import com.xiaodou.jmsg.entity.MessageRemoteResult;
import com.xiaodou.jmsg.entity.MessageRemoteResult.MessageRemoteResultType;
import com.xiaodou.mission.engine.event.BuyCourseEvent;
import com.xiaodou.mission.engine.event.EventBuilder;
import com.xiaodou.oms.agent.common.enums.GorderProperty;
import com.xiaodou.oms.agent.common.enums.GorderState;
import com.xiaodou.oms.agent.model.Gorder;
import com.xiaodou.oms.agent.model.Order;
import com.xiaodou.oms.agent.model.OrderItem;
import com.xiaodou.oms.agent.model.statemachine.Message;
import com.xiaodou.oms.agent.model.statemachine.StateMachineFireCoreParams;
import com.xiaodou.oms.agent.service.OrderService;
import com.xiaodou.oms.agent.service.PaymentService;
import com.xiaodou.oms.agent.vo.request.CascadeGorderDetailRequest;
import com.xiaodou.oms.agent.vo.request.OrderFireRequest;
import com.xiaodou.summer.dao.pagination.Page;
import com.xiaodou.summer.dao.param.IQueryParam;
import com.xiaodou.summer.dao.param.QueryParam;

@Service("mqMessageService")
public class MqMessageService extends AbstractService {
  /**
   * 通用订单service
   */
  @Resource
  OrderService orderService;

  /**
   * 支付service
   */
  @Resource
  PaymentService paymentService;

  @Resource
  ProductServiceFacade productServiceFacade;

  @Resource
  UserProductOrderService userProductOrderService;

  @Resource
  UserLearnTaskService userLearnTaskService;

  @Resource
  UserLearnStaticsService userLearnStaticsService;

  @Resource
  UserLearnAchieveService userLearnAchieveService;

  @Resource
  ProductItemService productItemService;

  public MessageRemoteResult refreshChapterRecordStatistics(JMSGPojo pojo) {
    MessageRemoteResult messageRemoteResult =
        new MessageRemoteResult(MessageRemoteResultType.SUCCESS);
    if (StringUtils.isJsonBlank(pojo.getMessage())) {
      LoggerUtil.error("消息体为空。Tag:" + pojo.getTag(), new RuntimeException());
      messageRemoteResult.setExceptionMessgage("消息体为空。");
      return messageRemoteResult;
    }
    UserChapterRecordModel chapterRecord =
        FastJsonUtil.fromJson(pojo.getMessage(), UserChapterRecordModel.class);
    Map<String, Object> input = Maps.newHashMap();
    input.put("starLevelLower", 0);
    input.put("courseId", chapterRecord.getCourseId());
    input.put("chapterId", chapterRecord.getChapterId());
    input.put("itemId", chapterRecord.getItemId());
    Page<UserChapterRecordModel> chapterModelPage = new Page<UserChapterRecordModel>();
    chapterModelPage.setPageSize(10);
    chapterModelPage =
        productServiceFacade.queryUserChapterRecordByCond(input,
            CommUtil.getAllField(UserChapterRecordModel.class), chapterModelPage);
    if (null != chapterModelPage) {
      Set<String> topUserSet = Sets.newHashSet();
      if (chapterModelPage.getResult() != null && chapterModelPage.getResult().size() > 0) {
        for (UserChapterRecordModel _chapterRecord : chapterModelPage.getResult()) {
          String userId = _chapterRecord.getUserId().toString();
          if (topUserSet.contains(userId)) continue;
          topUserSet.add(userId);
        }
      }
      if (null != chapterRecord.getRobotIdList() && chapterRecord.getRobotIdList().size() > 0) {
        for (String robotId : chapterRecord.getRobotIdList()) {
          if (topUserSet.contains(robotId)) continue;
          topUserSet.add(robotId);
        }
      }
      int totalCount = (int) chapterModelPage.getTotalCount();
      if (totalCount < 1000) totalCount += 100;
      productServiceFacade.updateProductItemStatisticsById(chapterRecord.getItemId(), totalCount,
          FastJsonUtil.toJson(topUserSet));
    }
    return messageRemoteResult;
  }

  public MessageRemoteResult from0To1OnPaySuccess(JMSGPojo pojo) {
    try {
      Thread.sleep(500);
    } catch (InterruptedException e) {}
    MessageRemoteResult messageRemoteResult =
        new MessageRemoteResult(MessageRemoteResultType.SUCCESS);
    if (StringUtils.isJsonBlank(pojo.getMessage())) {
      LoggerUtil.error("消息体为空。Tag:" + pojo.getTag(), new RuntimeException());
      messageRemoteResult.setExceptionMessgage("消息体为空。");
      return messageRemoteResult;
    }
    Message message = FastJsonUtil.fromJson(pojo.getMessage(), Message.class);
    Gorder gorder = queryGorderInfo(message);
    // 此处加一个重试
    if (gorder == null) {
      messageRemoteResult = new MessageRemoteResult(MessageRemoteResultType.FAIL);
      messageRemoteResult.appendRetdesc("订单信息查询失败");
      return messageRemoteResult;
    }

    if (gorder.getGorderStatus() != GorderState.Success.getState()) return messageRemoteResult;
    IQueryParam param = new QueryParam();
    param.addInput("gorderId", gorder.getId());
    param.addOutputs(CommUtil.getAllField(ProductOrderModel.class));
    Page<ProductOrderModel> productOrderPage =
        productServiceFacade.queryProductOrderModelByCond(param);
    if (productOrderPage == null || productOrderPage.getResult() == null
        || productOrderPage.getResult().size() == 0) return messageRemoteResult;
    ProductOrderModel model = productOrderPage.getResult().get(0);
    if (model.getStatus() == CourseConstant.BUY_COURSE_FINISH) return messageRemoteResult;
    UserProductOrderRequest request = new UserProductOrderRequest();
    request.setUid(model.getUid());
    request.setCourseId(model.getProductId());
    request.setOrderStatus("1");
    BuyCourseEvent event = EventBuilder.buildBuyCourseEvent();
    event.setModule(model.getModule());
    event.setUserId(model.getUid());
    event.setCourseId(model.getProductId());
    event.send();
    model.setStatus(CourseConstant.BUY_COURSE_FINISH);
    model.setFinishTime(new Timestamp(System.currentTimeMillis()));
    Map<String, Object> cond = Maps.newHashMap();
    cond.put("id", model.getId());
    productServiceFacade.updateProductOrderModelByCond(cond, model);
    callBackOms(model.getProductLine(), message.getGorderId(), message.getOrderId(), model.getId());
    return messageRemoteResult;
  }

  /**
   * 查询支付订单信息
   * 
   * @param message
   * @return
   */
  private Gorder queryGorderInfo(Message message) {
    // 1.获取相关参数
    CascadeGorderDetailRequest cascadeGorderDetailRequest = new CascadeGorderDetailRequest();
    cascadeGorderDetailRequest
        .setProductLine(message.getContext().getCoreParams().getProductLine());
    cascadeGorderDetailRequest.setGorderId(message.getGorderId());

    Map<String, Object> gorderOutputProperties = new HashMap<String, Object>();
    gorderOutputProperties.put(GorderProperty.id.getPropertyName(),
        GorderProperty.id.getPropertyType());
    gorderOutputProperties.put(GorderProperty.gorderStatus.getPropertyName(),
        GorderProperty.gpayAmount.getPropertyType());
    cascadeGorderDetailRequest.setGorderOutputProperties(gorderOutputProperties);

    Map<String, Object> orderOutputProperties = new HashMap<String, Object>();
    cascadeGorderDetailRequest.setOrderOutputProperties(orderOutputProperties);

    Map<String, Object> orderItemOutputProperties = new HashMap<String, Object>();
    cascadeGorderDetailRequest.setOrderItemOutputProperties(orderItemOutputProperties);

    Gorder gorder = null;
    int retry = 3;
    while (gorder == null && retry-- > 0) {
      gorder = orderService.cascadeGorderDetail(cascadeGorderDetailRequest);
    }
    return gorder;
  }

  /**
   * 回调OMS够买成功
   * 
   * @param productLine
   * @param gorderId
   * @param orderId
   * @param merchentOrderId
   */
  private void callBackOms(String productLine, String gorderId, String orderId,
      String merchentOrderId) {
    OrderFireRequest firePojo = new OrderFireRequest();
    StateMachineFireCoreParams coreParam = new StateMachineFireCoreParams();
    coreParam.setProductLine(productLine);
    coreParam.setGorderId(gorderId);
    coreParam.setOrderId(orderId);
    coreParam.setMerchantOrderId(merchentOrderId);
    coreParam.setEvent("DeliveredSuccess");
    coreParam.setIp(CommUtil.getServerIp());
    Order orderUpdateInfo = new Order();
    orderUpdateInfo.setId(orderId);
    orderUpdateInfo.setSuccessTime(new Timestamp(System.currentTimeMillis()));
    orderUpdateInfo.setMisc("购买成功");
    coreParam.setOrderUpdateInfo(orderUpdateInfo);
    List<OrderItem> orderItemUpdateInfoList = Lists.newArrayList();
    coreParam.setOrderItemUpdateInfo(orderItemUpdateInfoList);
    firePojo.setProductLine(productLine);
    firePojo.setCoreParams(coreParam);
    orderService.orderFire(firePojo);
  }

  /**
   * 记录用户最近学习课程至第几章第几节
   * 
   * @param pojo
   * @return
   */
  public MessageRemoteResult saveLearnAchieve(JMSGPojo pojo) {
    MessageRemoteResult messageRemoteResult =
        new MessageRemoteResult(MessageRemoteResultType.SUCCESS);
    if (StringUtils.isJsonBlank(pojo.getMessage())) {
      LoggerUtil.error("消息体为空。Tag:" + pojo.getTag(), new RuntimeException());
      messageRemoteResult.setExceptionMessgage("消息体为空。");
      return messageRemoteResult;
    }
    UserLearnAchieveVo learnAchieve =
        FastJsonUtil.fromJson(pojo.getMessage(), UserLearnAchieveVo.class);
    UserLearnAchieveRequest vo = new UserLearnAchieveRequest();
    vo.setUid(learnAchieve.getUserId());
    vo.setModule(learnAchieve.getModuleId());
    vo.setCourseId(learnAchieve.getCourseId());
    vo.setChapterId(learnAchieve.getChapterId());
    vo.setItemId(learnAchieve.getItemId());
    userLearnAchieveService.saveLearnAchieve(vo);
    return messageRemoteResult;
  }

  /**
   * @deprecated
   * @description 报名消息，生成课程
   * @author 李德洪
   * @Date 2017年4月20日
   * @param pojo
   * @return
   */
  public MessageRemoteResult applyScheduler(JMSGPojo pojo) {
    MessageRemoteResult messageRemoteResult =
        new MessageRemoteResult(MessageRemoteResultType.SUCCESS);
    if (StringUtils.isJsonBlank(pojo.getMessage())) {
      LoggerUtil.error("消息体为空。Tag:" + pojo.getTag(), new RuntimeException());
      messageRemoteResult.setExceptionMessgage("消息体为空。");
      return messageRemoteResult;
    }
    ApplyMessageBody messageBody = FastJsonUtil.fromJson(pojo.getMessage(), ApplyMessageBody.class);
    List<ApplyCallBackMessageBodyDTO> acbmList = Lists.newArrayList();
    for (ApplyMessageBodyDTO amb : messageBody.getMessageBody()) {
      UserProductOrderModel upom = new UserProductOrderModel();
      upom.setCourseId(amb.getProductId());
      upom.setUserId(amb.getUserId());
      productServiceFacade.insertUserProductOrder(upom);
      ApplyCallBackMessageBodyDTO acbm = new ApplyCallBackMessageBodyDTO();
      acbm.setProductId(amb.getProductId());
      acbm.setStudentId(amb.getStudentId());
      acbmList.add(acbm);
    }
    return messageRemoteResult;
  }

  /**
   * @deprecated
   */
  public void sendApplyCallBackMessage(List<ApplyCallBackMessageBodyDTO> acbmList) {
    ApplyCallBackMessageBody messageBody = new ApplyCallBackMessageBody();
    messageBody.setMessageBody(acbmList);
    RabbitMQSender.getInstance().send(new ApplyCallBackMessage(messageBody));
  }

}
