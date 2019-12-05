package com.xiaodou.server.pay.payplatform.dc.alipay;

import java.util.Map;

import com.google.common.collect.Maps;
import com.xiaodou.common.util.CommUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.server.pay.enums.PayStatus;
import com.xiaodou.server.pay.model.PayRecord;
import com.xiaodou.server.pay.payplatform.dc.DcPayOrderResponse;
import com.xiaodou.server.pay.payplatform.dc.DcPayStatusResponse;
import com.xiaodou.server.pay.payplatform.dc.IDcPayPlatform;
import com.xiaodou.server.pay.payplatform.dc.PlatformPayStatus;
import com.xiaodou.server.pay.payplatform.dc.alipay.util.AndroidPackaging;
import com.xiaodou.server.pay.request.PayRequest;
import com.xiaodou.server.pay.request.inner.MixPaymentDc;
import com.xiaodou.server.pay.service.facade.IPayServiceFacade;
import com.xiaodou.summer.util.SpringWebContextHolder;

/**
 * @name @see com.xiaodou.server.pay.payplatform.dc.alipay.AlipayPayPlatform.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年5月30日
 * @description 支付宝支付平台
 * @version 1.0
 */
public class AlipayPayPlatform implements IDcPayPlatform {

  @SuppressWarnings("unchecked")
  @Override
  public DcPayOrderResponse payOrder(PayRequest request) {
    DcPayOrderResponse dcResponse = new DcPayOrderResponse();
    try {
      // if (Integer.valueOf(PayConstant.PAY_FROM_ANDROID)== request.getOrderFrom()) {
      // 1、生成支付宝订单
      // String mysign = produceAliPayTrade(request);
      MixPaymentDc dc = request.getDc();
      String orderInfo =
          AndroidPackaging.getOrderInfo(request.getTradeNo(), dc.getSubject(), request.getDc()
              .getBody(), request.getTotalAmt().toString());
      dcResponse.setPayStatus(PayStatus.PAYING.getCode());
      // String sign = AndroidPackaging.sign(orderInfo);
      // sign = URLEncoder.encode(sign, AlipayConfig._input_charset);
      // 2、生成本地数据
      String payInfo = orderInfo;
      // + "&sign=\"" + sign + "\"&" + AndroidPackaging.getSignType();
      payInfo += "&rsa_private=" + "\"" + AlipayConfig.RSA_PRIVATE + "\"";// RSA_PRIVATE
      dcResponse.setPayInfo(payInfo);
      dcResponse.setTotalAmount(request.getDc().getDcAmt());
      // }
    } catch (Exception e) {
      LoggerUtil.error("aliPay获取订单异常", e);
    }
    return dcResponse;
  }

  @Override
  public DcPayStatusResponse payStatus(String tradeNo) {
    DcPayStatusResponse response = new DcPayStatusResponse();
    // @Resource(name = "payServiceFacade")
    IPayServiceFacade payServiceFacade = SpringWebContextHolder.getBean("payServiceFacade");
    PayRecord payRecord = payServiceFacade.queryPayRecordByTradeNo4Update(tradeNo);
    PlatformPayStatus platformPayStatus = PlatformPayStatus.SUCCESS;
    if (null != payRecord) {
      Short payStatus = payRecord.getPayStatus();
      switch (payStatus) {
        case 0:
          platformPayStatus = PlatformPayStatus.SUCCESS;
          break;
        case 1:
          platformPayStatus = PlatformPayStatus.PAYERROR;
          break;
        case 2:
          platformPayStatus = PlatformPayStatus.USERPAYING;
          break;
        default:
          break;
      }
    } else {
      platformPayStatus = PlatformPayStatus.PAYERROR;
    }
    response.setPayStatus(platformPayStatus);
    return response;

  }

  /**
   * 生成前端需要的支付宝订单信息
   */
  @SuppressWarnings("unused")
  private static String produceAliPayTrade(PayRequest request) {
    AliPayOrderModel aliPayOrderModel = new AliPayOrderModel();
    aliPayOrderModel.set_input_charset(AlipayConfig._input_charset);
    aliPayOrderModel.setBody("支付宝支付");
    aliPayOrderModel.setIt_b_pay(AlipayConfig.it_b_pay);
    aliPayOrderModel.setNotify_url(AlipayConfig.notify_url);
    aliPayOrderModel.setOut_trade_no(request.getTradeNo());
    aliPayOrderModel.setPartner(AlipayConfig.partner);
    aliPayOrderModel.setPayment_type(AlipayConfig.payment_type);
    aliPayOrderModel.setSeller_id(AlipayConfig.seller_id);
    aliPayOrderModel.setService(AlipayConfig.service);
    aliPayOrderModel.setSign(request.getSign());
    aliPayOrderModel.setSign_type(AlipayConfig.sign_type);
    aliPayOrderModel.setSubject(String.format("%s-%s", AlipayConfig.subject,
        System.currentTimeMillis()));
    aliPayOrderModel.setTotal_fee(String.valueOf(request.getTotalAmt()));
    Map<String, Object> params = Maps.newHashMap();
    CommUtil.transferFromVO2Map(params, aliPayOrderModel);
    String mysign = AlipayFunction.buildMysign(params, AlipayConfig.key);
    return mysign;
  }
}
