package com.xiaodou.server.pay.payplatform.dc.wxh5pay;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import com.xiaodou.common.util.IPUtil;
import com.xiaodou.common.util.log.LoggerUtil;
import com.xiaodou.server.pay.enums.PayStatus;
import com.xiaodou.server.pay.model.PayRecord;
import com.xiaodou.server.pay.payplatform.dc.DcPayOrderResponse;
import com.xiaodou.server.pay.payplatform.dc.DcPayStatusResponse;
import com.xiaodou.server.pay.payplatform.dc.IDcPayPlatform;
import com.xiaodou.server.pay.payplatform.dc.PlatformPayStatus;
import com.xiaodou.server.pay.payplatform.dc.wxpay.WxPayDto;
import com.xiaodou.server.pay.payplatform.dc.wxpay.utils.MD5Util;
import com.xiaodou.server.pay.request.PayRequest;
import com.xiaodou.server.pay.service.facade.IPayServiceFacade;
import com.xiaodou.summer.util.SpringWebContextHolder;

public class WxH5PayPlatform implements IDcPayPlatform {

  @SuppressWarnings("unchecked")
  @Override
  public DcPayOrderResponse payOrder(PayRequest request) {
    DcPayOrderResponse dcResponse = new DcPayOrderResponse();
    try {
      WxPayDto tpWxPay = new WxPayDto();
      tpWxPay.setBusinessType(request.getBusinessType());
      tpWxPay.setBody(request.getDc().getBody());
      tpWxPay.setOrderId(request.getTradeNo());
      tpWxPay.setSpbillCreateIp(IPUtil.getServerIp());
      tpWxPay.setTotalFee(request.getDc().getDcAmt().toString());
      tpWxPay.setOpenId(request.getDc().getOpenId());
      dcResponse.setPayStatus(PayStatus.PAYING.getCode());
      // 2、生成本地数据
      String payInfo = WxH5PayFucntion.confirm(tpWxPay);
      dcResponse.setPayInfo(payInfo);
      dcResponse.setTotalAmount(request.getDc().getDcAmt());
    } catch (Exception e) {
      LoggerUtil.error("wxH5Pay获取支付數據异常", e);
    }
    return dcResponse;
  }


  @Override
  public DcPayStatusResponse payStatus(String tradeNo) {
    DcPayStatusResponse response = new DcPayStatusResponse();
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
   * 微信支付签名算法sign
   * 
   * @param characterEncoding
   * @param parameters
   * @return
   */
  @SuppressWarnings("rawtypes")
  public static String createSign(String characterEncoding, SortedMap<Object, Object> parameters) {
    StringBuffer sb = new StringBuffer();
    Set es = parameters.entrySet();// 所有参与传参的参数按照accsii排序（升序）
    Iterator it = es.iterator();
    while (it.hasNext()) {
      Map.Entry entry = (Map.Entry) it.next();
      String k = (String) entry.getKey();
      Object v = entry.getValue();
      if (null != v && !"".equals(v) && !"sign".equals(k) && !"key".equals(k)) {
        sb.append(k + "=" + v + "&");
      }
    }
    String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
    return sign;
  }

}
