package com.xiaodou.server.pay.service.callback;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.server.pay.enums.PayStatus;
import com.xiaodou.server.pay.model.PayRecord;
import com.xiaodou.server.pay.payplatform.dc.alipay.util.AlipayNotifyUtil;
import com.xiaodou.server.pay.request.callback.AliCallbackRequest;
import com.xiaodou.server.pay.response.PayResponse;
import com.xiaodou.server.pay.response.resultType.PaymentResType;
import com.xiaodou.server.pay.service.facade.IPayServiceFacade;
import com.xiaodou.server.pay.service.queue.QueueService;
import com.xiaodou.server.pay.vo.CallbackBusinessPojo;
import com.xiaodou.summer.vo.out.ResultType;

@Service("aliCallBackService")
public class AliCallBackService implements ICallbackService<AliCallbackRequest, PayResponse> {

  @Resource(name = "payServiceFacade")
  IPayServiceFacade payServiceFacade;
  @Resource(name = "queueService")
  QueueService queueService;

  @Override
  public PayResponse callback(AliCallbackRequest pojo) {
    PayResponse response = new PayResponse(ResultType.SUCCESS);
    try {
      // 1、验证签名
      Map<String, String> sParaTemp = setParam(pojo);
      boolean flag = AlipayNotifyUtil.verify(sParaTemp);
      // TODO selfVerify();
      if (flag) {
        // 2、改库
        PayRecord payRecord =
            payServiceFacade.queryPayRecordByTradeNo4Update(pojo.getOut_trade_no());
        if (null == payRecord) {
          return new PayResponse(PaymentResType.FindPayFailed);
        } else {
          payRecord.setPayStatus(PayStatus.SUCCESS.getCode());
          payRecord.setPayResult(PayStatus.SUCCESS.getStatus());
          if (!payServiceFacade.updatePayRecord(payRecord))
            return new PayResponse(PaymentResType.FindPayFailed);
          // 处理业务操作
          queueService.callBackBusiness(new CallbackBusinessPojo(payRecord));
        }
        response.setPayStatus(PayStatus.SUCCESS.getCode().toString());
      } else {
        response.setPayStatus(PayStatus.FAIL.getCode().toString());
      }
    } catch (Exception e) {
      LoggerUtil.error("支付宝回调通知失败！", e);
    }
    return response;
  }

  private Map<String, String> setParam(AliCallbackRequest pojo) {
    Map<String, String> sParaTemp = Maps.newHashMap();
    // DateFormat df=new SimpleDateFormat(UtilDate.simple);
    if (StringUtils.isNotBlank(pojo.getSign())) {
      sParaTemp.put("sign", pojo.getSign());
    }
    if (StringUtils.isNotBlank(pojo.getSign_type())) {
      sParaTemp.put("sign_type", pojo.getSign_type());
    }
    if (StringUtils.isNotBlank(pojo.getBody())) {
      sParaTemp.put("body", pojo.getBody());
    }
    if (StringUtils.isNotBlank(pojo.getBuyer_email())) {
      sParaTemp.put("buyer_email", pojo.getBuyer_email());
    }
    if (StringUtils.isNotBlank(pojo.getBuyer_id())) {
      sParaTemp.put("buyer_id", pojo.getBuyer_id());
    }
    if (StringUtils.isNotBlank(pojo.getExtra_common_param())) {
      sParaTemp.put("extra_common_param", pojo.getExtra_common_param());
    }
    if (null != pojo.getGmt_create()) {
      // sParaTemp.put("gmt_create", df.format(pojo.getGmt_create()));
      sParaTemp.put("gmt_create", pojo.getGmt_create());
    }
    if (null != pojo.getGmt_payment()) {
      // sParaTemp.put("gmt_payment", df.format(pojo.getGmt_payment()));
      sParaTemp.put("gmt_payment", pojo.getGmt_payment());
    }
    if (StringUtils.isNotBlank(pojo.getIs_total_fee_adjust())) {
      sParaTemp.put("is_total_fee_adjust", pojo.getIs_total_fee_adjust());
    }
    if (StringUtils.isNotBlank(pojo.getNotify_id())) {
      sParaTemp.put("notify_id", pojo.getNotify_id());
    }
    if (null != pojo.getNotify_time()) {
      // sParaTemp.put("notify_time", df.format(pojo.getNotify_time()));
      sParaTemp.put("notify_time", pojo.getNotify_time());
    }
    if (StringUtils.isNotBlank(pojo.getNotify_type())) {
      sParaTemp.put("notify_type", pojo.getNotify_type());
    }
    if (StringUtils.isNotBlank(pojo.getOut_trade_no())) {
      sParaTemp.put("out_trade_no", pojo.getOut_trade_no());
    }
    if (null != pojo.getPrice()) {
      sParaTemp.put("price", pojo.getPrice().toString());
    }
    if (null != pojo.getQuantity()) {
      sParaTemp.put("quantity", pojo.getQuantity().toString());
    }
    if (StringUtils.isNotBlank(pojo.getSeller_email())) {
      sParaTemp.put("seller_email", pojo.getSeller_email());
    }
    if (StringUtils.isNotBlank(pojo.getSeller_id())) {
      sParaTemp.put("seller_id", pojo.getSeller_id());
    }
    if (StringUtils.isNotBlank(pojo.getSubject())) {
      sParaTemp.put("subject", pojo.getSubject());
    }
    if (null != pojo.getTotal_fee()) {
      sParaTemp.put("total_fee", pojo.getTotal_fee().toString());
    }
    if (StringUtils.isNotBlank(pojo.getTrade_no())) {
      sParaTemp.put("trade_no", pojo.getTrade_no());
    }
    if (StringUtils.isNotBlank(pojo.getTrade_status())) {
      sParaTemp.put("trade_status", pojo.getTrade_status());
    }
    if (StringUtils.isNotBlank(pojo.getUse_coupon())) {
      sParaTemp.put("use_coupon", pojo.getUse_coupon());
    }
    return sParaTemp;
  }

}
