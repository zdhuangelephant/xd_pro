package com.xiaodou.server.pay.payplatform.dc.applepay;

import javax.annotation.Resource;

import com.xiaodou.server.pay.enums.PayStatus;
import com.xiaodou.server.pay.model.PayRecord;
import com.xiaodou.server.pay.payplatform.dc.DcPayOrderResponse;
import com.xiaodou.server.pay.payplatform.dc.DcPayStatusResponse;
import com.xiaodou.server.pay.payplatform.dc.IDcPayPlatform;
import com.xiaodou.server.pay.payplatform.dc.PlatformPayStatus;
import com.xiaodou.server.pay.request.PayRequest;
import com.xiaodou.server.pay.service.facade.IPayServiceFacade;
import com.xiaodou.server.pay.service.queue.QueueService;

/**
 * @name @see com.xiaodou.server.pay.payplatform.dc.applepay.ApplePayPlatform.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年5月30日
 * @description 苹果支付支付平台
 * @version 1.0
 */

public class ApplePayPlatform implements IDcPayPlatform {

  @Resource(name = "payServiceFacade")
  IPayServiceFacade payServiceFacade;
  @Resource(name = "queueService")
  QueueService queueService;

  @SuppressWarnings("unchecked")
  @Override
  public DcPayOrderResponse payOrder(PayRequest request) {
    DcPayOrderResponse dcResponse = new DcPayOrderResponse();
    dcResponse.setPayStatus(PayStatus.PAYING.getCode());
    dcResponse.setPayInfo(PayStatus.PAYING.getStatus());
    dcResponse.setTotalAmount(request.getDc().getDcAmt());
    return dcResponse;
  }

  @Override
  public DcPayStatusResponse payStatus(String tradeNo) {
    DcPayStatusResponse response = new DcPayStatusResponse();
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
      // 对应关系？
    } else {
      platformPayStatus = PlatformPayStatus.PAYERROR;
    }
    response.setPayStatus(platformPayStatus);
    return response;
  }

}
