package com.xiaodou.server.pay.payplatform.ca;

import javax.annotation.Resource;

import com.xiaodou.server.pay.enums.PayStatus;
import com.xiaodou.server.pay.exception.CaPayfailException;
import com.xiaodou.server.pay.payplatform.IPayPlatform;
import com.xiaodou.server.pay.request.PayRequest;
import com.xiaodou.server.pay.request.inner.MixPaymentCa;
import com.xiaodou.summer.vo.out.ResultType;
import com.xiaodou.wallet.agent.response.PayAmountResponse;
import com.xiaodou.wallet.agent.service.WalletService;

/**
 * @name @see com.xiaodou.server.pay.payplatform.ca.CaPayPlatform.java
 * @CopyRright (c) 2016 by Corp.XiaodouTech
 * 
 * @author <a href="mailto:zhaodan@corp.51xiaodou.com">zhaodan</a>
 * @date 2016年5月30日
 * @description 现金账户支付平台
 * @version 1.0
 */
public class CaPayPlatform implements IPayPlatform {

  @Resource(name = "walletService")
  WalletService walletService;

  @SuppressWarnings("unchecked")
  @Override
  public CaPayOrderResponse payOrder(PayRequest request) {
    CaPayOrderResponse caPayOrderResponse = new CaPayOrderResponse(ResultType.SUCCESS);
    MixPaymentCa ca = request.getCa();
    PayAmountResponse caPayResponse =
        WalletService.payAmount(ca.getProductLine(), ca.getMemberCardNo(), ca.getBusinessType(),
            ca.getCaCurrency(), ca.getSubject(), ca.getBody(), ca.getCaAmt(), request.getTradeNo());
    if (null == caPayResponse) throw new CaPayfailException("调用钱包支付，无数据返回！");
    if (caPayResponse.isRetOk()) {
      caPayOrderResponse.setPayStatus(PayStatus.SUCCESS.getCode());
      caPayOrderResponse.setPayInfo(PayStatus.SUCCESS.getStatus());
      caPayOrderResponse.setTotalAmount(ca.getCaAmt());
    } else {
      caPayOrderResponse.setRetcode(caPayResponse.getRetcode());
      caPayOrderResponse.setRetdesc(caPayResponse.getRetdesc());
    }
    return caPayOrderResponse;
  }

}
