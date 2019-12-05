package com.xiaodou.payment;

import com.xiaodou.payment.util.SignUtil;
import com.xiaodou.payment.vo.request.PayRequest;
import com.xiaodou.payment.vo.request.RefundRequest;
import com.xiaodou.payment.vo.request.inner.MixPaymentCc;

import org.junit.Test;

/**
 * Date: 2014/7/18 Time: 16:55
 * 
 * @author Tian.Dong
 */
public class SignTest {

  @Test
  public void testSign() throws IllegalAccessException {
    RefundRequest request = new RefundRequest();
    request.setMerchantId("merchantId");
    request.setSignType("md5");
    request.setBusinessType(111);
    request.setTradeNo(123123123L);
    request.setOrderId("o");
    request.setAmt(100.22);
    request.setNotifyUrl("www.baidu.com");

    System.out.println(SignUtil.sign(request, "key"));
  }

  @Test
  public void testSignPay() throws IllegalAccessException {

    PayRequest payRequest = new PayRequest();
    MixPaymentCc cc = new MixPaymentCc();
    payRequest.setMixPaymentCc(cc);
    payRequest.setBusinessType(1011);
    cc.setCardHolder("dt");
    cc.setAmt(100.02);
    cc.setCcCurrency(2222);
    cc.setCustomerServiceAmt(20.22);
    cc.setCreditCardNo("298777777");
    cc.setExpireDate("2014-07-22");
    cc.setIdNo("11231231");
    cc.setIdType(888);
    payRequest.setMerchantId("190000007");
    payRequest.setMixPaymentType(3002);
    payRequest.setNotifyUrl("www.baidu.com");
    payRequest.setOrderFrom("ios");
    payRequest.setOrderId("orderId");
    payRequest.setPayFrom(222);
    payRequest.setSignType("MD5");
    payRequest.setTotalAmt(288.93);
    payRequest.setTradeNo(2222222222L);
    cc.setVerifyCode("789");

    System.out.println(SignUtil.sign(payRequest, "key"));
  }
}
