package com.xiaodou.server.pay.payplatform.dc.wxpay;

import com.xiaodou.common.util.IPUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.server.pay.enums.PayStatus;
import com.xiaodou.server.pay.model.PayRecord;
import com.xiaodou.server.pay.payplatform.dc.DcPayOrderResponse;
import com.xiaodou.server.pay.payplatform.dc.DcPayStatusResponse;
import com.xiaodou.server.pay.payplatform.dc.IDcPayPlatform;
import com.xiaodou.server.pay.payplatform.dc.PlatformPayStatus;
import com.xiaodou.server.pay.request.PayRequest;
import com.xiaodou.server.pay.service.facade.IPayServiceFacade;
import com.xiaodou.summer.util.SpringWebContextHolder;

/**
 * @name @see com.xiaodou.server.pay.payplatform.dc.alipay.AlipayPayPlatform.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhouhuan@corp.51xiaodou.com">zhouhuan</a>
 * @date 2016年11月18日
 * @description 微信支付平台
 * @version 1.0
 */
public class WxpayPayPlatform implements IDcPayPlatform {

  @SuppressWarnings("unchecked")
  @Override
  public DcPayOrderResponse payOrder(PayRequest request) {
    DcPayOrderResponse dcResponse = new DcPayOrderResponse();
    try {
      WxPay demo = new WxPay();
      WxPayDto tpWxPay = new WxPayDto();
      tpWxPay.setBusinessType(request.getBusinessType());
      tpWxPay.setBody(request.getDc().getBody());
      tpWxPay.setOrderId(request.getTradeNo());
      tpWxPay.setSpbillCreateIp(IPUtil.getServerIp());
      tpWxPay.setTotalFee(request.getDc().getDcAmt().toString());
      String packageData = demo.getPackage(tpWxPay);
      dcResponse.setPayStatus(PayStatus.PAYING.getCode());
      // String sign = AndroidPackaging.sign(orderInfo);
      // sign = URLEncoder.encode(sign, AlipayConfig._input_charset);
      // 2、生成本地数据
      String payInfo = packageData;
      dcResponse.setPayInfo(payInfo);
      dcResponse.setTotalAmount(request.getDc().getDcAmt());
    } catch (Exception e) {
      LoggerUtil.error("wxPay获取订单异常", e);
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
}
