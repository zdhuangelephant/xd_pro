package com.xiaodou.oms.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.MDC;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.oms.dao.OperationType;
import com.xiaodou.oms.dao.OperationTypeWrapper;
import com.xiaodou.oms.entity.order.Order;
import com.xiaodou.oms.entity.order.PayRecord;
import com.xiaodou.oms.exception.ValidationException;
import com.xiaodou.oms.service.facade.OrderServiceFacade;
import com.xiaodou.oms.service.oms.OmsOrderService;
import com.xiaodou.oms.statemachine.param.CoreParams;
import com.xiaodou.oms.statemachine.param.PayParam;
import com.xiaodou.oms.vo.input.order.FirePojo;
import com.xiaodou.oms.vo.input.pay.PaymentNotifyPojo;
import com.xiaodou.payment.constant.PaymentStatus;

/**
 * Date: 2014/7/1 Time: 13:52
 * 
 * @author Tian.Dong
 */
@Controller
@RequestMapping("pay")
public class OmsNotifyController {

  private static final String FIRST_PAY = "Pay";
  private static final String TWICE_PAY = "SecondPay";
  private static final String REFUND = "Refund";

  private static final String SUCCESS = "success";
  /**
   * pmt表示,接收到就返回success 另外由于我们有了主动查的机制
   */
  private static final String EXCEPTION = "success";

  @Resource
  OrderServiceFacade orderServiceFacade;
  @Resource
  OmsOrderService omsOrderService;

  /**
   * 第一次支付
   * 
   * @param pojo
   * @return
   */
  @ResponseBody
  @RequestMapping(value = "first_pay_notify", method = RequestMethod.POST, headers = {"content-type=application/json;charset=utf-8"})
  public String firstPayNotify(@RequestBody PaymentNotifyPojo pojo) {
    if (doFlow(pojo, FIRST_PAY)) {
      return SUCCESS;
    }
    return EXCEPTION;
  }


  /**
   * 二次支付 对应火车票的退差价回调
   * 
   * @param pojo
   * @return
   */
  @ResponseBody
  @RequestMapping("twice_pay_notify")
  public String twicePayNotify(@RequestBody PaymentNotifyPojo pojo) {
    if (pojo.getResultStatus().equals(0)) {
      // 报警
      // TODO 报警
      // AlarmEntity entity = new AlarmEntity(AlarmEntityType.PAYMENT_REFUND_ERROR.getType());
      // entity.setUrl(CommUtil.getServerName(), "pay_no=" + pojo.getTradeNo());
      // LoggerUtil.alarmInfo(entity);
      return SUCCESS;
    }
    if (doFlow(pojo, TWICE_PAY)) {
      return SUCCESS;
    }
    return EXCEPTION;
  }


  /**
   * 退货引起的退款回调
   * 
   * @param pojo
   * @return
   */
  @ResponseBody
  @RequestMapping("refund_notify")
  public String refundNotify(@RequestBody PaymentNotifyPojo pojo) {
    if (pojo.getResultStatus().equals(0)) {
      // 报警
      // TODO 报警
      // AlarmEntity entity = new AlarmEntity(AlarmEntityType.PAYMENT_REFUND_ERROR.getType());
      // entity.setUrl(CommUtil.getServerName(), "pay_no=" + pojo.getTradeNo());
      // LoggerUtil.alarmInfo(entity);
    }
    if (doFlow(pojo, REFUND)) {
      return SUCCESS;
    }
    return EXCEPTION;
  }

  private FirePojo createFirePojo(PaymentNotifyPojo pojo, String eventPrefix)
      throws ValidationException {

    // 1 验证参数
    if (pojo.getTradeNo() == null || pojo.getTradeNo().equals("")) {
      throw new ValidationException("[payment回调][参数不正确]" + "Trade_no：" + pojo.getTradeNo());
    }
    if (pojo.getResultStatus() == null) {
      throw new ValidationException("[payment回调][参数不正确]" + "result_status："
          + pojo.getResultStatus());
    }
    if (pojo.getResultStatus() != 0 && pojo.getResultStatus() != 1) {
      throw new ValidationException("[payment回调][参数不正确][不是0/1]" + "result_status："
          + pojo.getResultStatus());
    }
    PayRecord payRecord = null;
    payRecord = orderServiceFacade.queryPayRecordByPayNo(pojo.getTradeNo());
    if (payRecord == null) {
      throw new NullPointerException("[payment回调][payRecord为空]" + "Trade_no：" + pojo.getTradeNo());
    }

    // 产品线
    String productLine = payRecord.getProductType().substring(0, 2);
    if (payRecord.getOrderId() != null) {
      // 如果payRecord记录中orderId不为空，productLine赋值为order的productLine
      // 比如保险退保，payRecord中为05，orderId不为空，order的productLine为06，赋值为06
      // 这样才能触发保险的状态机
      Map output = new HashMap();
      output.put("productType", "1");
      Order order = orderServiceFacade.queryOrderDetail(payRecord.getOrderId(), output);
      productLine = order.getProductType().substring(0, 2);
    }
    FirePojo firePojo = new FirePojo();
    CoreParams coreParams = new CoreParams();
    // 2 设置coreParams的参数
    coreParams.setProductLine(productLine);
    coreParams.setGorderId(payRecord.getGorderId());
    coreParams.setOrderId(payRecord.getOrderId());
    PayParam payParam = new PayParam();
    payParam.setPayNo(pojo.getTradeNo());// 流水号(token)
    payParam.setFailureReason(pojo.getErrorInfo());
    payParam.setUuid(payRecord.getUuid());
    coreParams.setPayParam(payParam);
    firePojo.setCoreParams(coreParams);

    // 设置coreParams的ip和event
    coreParams.setIp((String) MDC.get("clientIP"));
    // 根据payment回调结果设置事件是成功还是失败 设置CoreParams event
    if (pojo.getResultStatus().equals(PaymentStatus.NOTIFY_OKCODE)) {
      coreParams.setEvent(eventPrefix + "Success");

    } else {
      coreParams.setEvent(eventPrefix + "Failure");

      // 考虑新单-失败-成功的情况
      // 如果继续支付成功，但上一次失败的回调过来
      // 考虑是否加入第一次支付失败时，再主动查询一下payment最新状态
      /*
       * if (eventPrefix.equals(FIRST_PAY)) { GetPayStatusPojo getPayStatusPojo = new
       * GetPayStatusPojo(); getPayStatusPojo.setGorderId(payRecord.getGorderId());
       * getPayStatusPojo.setToken(pojo.getTradeNo()); getPayStatusPojo.setProductLine(productLine);
       * PayStatusResponse payStatusResponse = PaymentService.getPayStatus(getPayStatusPojo); if
       * (payStatusResponse.getRepCode().equals("0")) { if
       * (payStatusResponse.getResultStatus().equals(PaymentStatus.NOTIFY_OKCODE)) {
       * coreParams.setEvent(eventPrefix + "Success"); } else if
       * (payStatusResponse.getResultStatus().equals(-1)) { //如果是处理中 throw new
       * ValidationException("[payment回调支付失败][主动查询支付状态为处理中]" + "Trade_no：" + pojo.getTradeNo()); } }
       * }
       */
    }
    return firePojo;
  }

  private boolean doFlow(PaymentNotifyPojo pojo, String eventPrefix) {
    OperationTypeWrapper.getWrapper().setValue(OperationType.WRITE);
    try {
      FirePojo firePojo = null;
      try {
        firePojo = createFirePojo(pojo, eventPrefix);
      } catch (Exception e) {
        LoggerUtil.error("[payment回调][createFirePojo异常]", e);
        return false;
      }

      try {
        omsOrderService.fireEvent(firePojo);
      } catch (Exception e) {
        LoggerUtil.error("[payment回调触发状态机异常]", e);
        return false;
      }
      return true;
    } catch (Exception e) {
      LoggerUtil.error("[payment回调doFlow异常]", e);
      return false;
    }
  }
}
