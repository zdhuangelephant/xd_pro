package com.xiaodou.payment;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.payment.service.PayService;
import com.xiaodou.payment.vo.CreditCardInfo;
import com.xiaodou.payment.vo.OrderInfo;
import com.xiaodou.payment.vo.PayCreditCardInfo;
import com.xiaodou.payment.vo.request.PayRequest;
import com.xiaodou.payment.vo.request.inner.MixPaymentCa;
import com.xiaodou.payment.vo.request.inner.MixPaymentCc;
import com.xiaodou.payment.vo.response.PayResponse;

import org.junit.Test;

import java.io.UnsupportedEncodingException;

/**
 * 测试支付接口 Date: 2014/7/18 Time: 17:32
 * 
 * @author Tian.Dong
 */
public class PayTest {
  @Test
  public void testCA() {
    PayRequest payRequest = new PayRequest();
    MixPaymentCa ca = new MixPaymentCa();
    ca.setAmt(1.00);
    ca.setCaCurrency(4061);
    ca.setMemberCardNo("190000032101085486");
    payRequest.setMixPaymentCa(ca);

    payRequest.setOrderId("100010");
    payRequest.setTradeNo(-100000000000360455L);

    payRequest.setBusinessType(1022);
    payRequest.setMerchantId("190010007");
    payRequest.setMixPaymentType(3202);
    payRequest.setNotifyUrl("http://192.168.14.99:8102/pay/first_pay_notify.do");
    payRequest.setOrderFrom("20000001");
    payRequest.setPayFrom(20000001);
    payRequest.setSignType("MD5");
    payRequest.setTotalAmt(1.00);
    payRequest.setMixPaymentType(3201);
    PayResponse response = PayService.payWithKey(payRequest, "CB0FAD9081F98DD992E6E49DBD70FE44");
    System.out.println(FastJsonUtil.toJson(response));
  }

  // @Test
  public void test() {
    PayRequest payRequest = new PayRequest();
    MixPaymentCc cc = new MixPaymentCc();
    payRequest.setMixPaymentCc(cc);
    // MixPaymentCa ca = new MixPaymentCa();
    // payRequest.setMixPaymentCa(ca);


    cc.setCardHolder("dt");
    cc.setAmt(100.00);
    cc.setCcCurrency(4061);
    cc.setCustomerServiceAmt(200.00);
    cc.setCreditCardNo("1234456789098700");
    cc.setExpireDate("2014-08-22");
    cc.setIdNo("141124199004170223");
    cc.setIdType(8001);

    // ca.setAmt(1.0);
    // ca.setCurrency(4061);
    // ca.setMemberCardNo("190000002101054941");

    // g order id
    payRequest.setOrderId("100010");
    payRequest.setTradeNo(-100000000000016356L);

    payRequest.setBusinessType(1022);
    payRequest.setMerchantId("190010007");
    payRequest.setMixPaymentType(3202);
    payRequest.setNotifyUrl("http://192.168.14.99:8102/pay/first_pay_notify.do");
    payRequest.setOrderFrom("20000001");
    payRequest.setPayFrom(20000001);
    payRequest.setSignType("MD5");
    payRequest.setTotalAmt(300.00);
    cc.setVerifyCode("789");
    PayResponse response = PayService.payWithKey(payRequest, "CB0FAD9081F98DD992E6E49DBD70FE44");
    System.out.println(FastJsonUtil.toJson(response));
  }

  // @Test
  public void testOldPay() throws UnsupportedEncodingException {
    CreditCardInfo card = new CreditCardInfo();
    card.setAmt(100.00);
    card.setCardHolder("jj");
    card.setCardNo("1234456789098700");
    card.setCurrentcy(4061);
    card.setEcpireDate("2014-10-21");
    card.setIdNo("141124199004170223");
    card.setIdType(8001);
    card.setServiceAmt(200.00);
    card.setVerifyCode("111");
    OrderInfo order = new OrderInfo();
    order.setNotifyUrl("http://www.baidu.com");
    order.setOrderFrom("20000001");
    order.setOrderId("20147212");
    order.setPaymentProductId(0);
    order.setTotalAmt(300.00);
    order.setPayFrom(20000001);
    order.setPaymentProductId(0);
    PayCreditCardInfo payCardInfo = new PayCreditCardInfo();

    payCardInfo.setOrderInfo(order);
    payCardInfo.setPayMerchant("02");
    payCardInfo.setTrade_no("-100000000000016285");
    payCardInfo.setCc(card);
    payCardInfo.setPayment_product_id(0);
    System.out.println(FastJsonUtil.toJson(PayService.pay(payCardInfo)));
  }
}
