package com.xiaodou.payment;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.payment.service.PayService;
import com.xiaodou.payment.vo.CreditCardInfo;
import com.xiaodou.payment.vo.OrderInfo;
import com.xiaodou.payment.vo.PayCreditCardInfo;

import java.io.UnsupportedEncodingException;

/**
 * 模拟支付
 */
public class PayCardTest {


  //@Test
  public void test() throws UnsupportedEncodingException {
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
    order.setOrderId("420420");
    order.setPaymentProductId(0);
    order.setTotalAmt(300.00);
    order.setPayFrom(20000001);
    order.setPaymentProductId(0);
    PayCreditCardInfo payCardInfo = new PayCreditCardInfo();

    payCardInfo.setOrderInfo(order);
    payCardInfo.setPayMerchant("02");
    payCardInfo.setTrade_no("-100000000000006179");
    payCardInfo.setCc(card);
    payCardInfo.setPayment_product_id(0);
    System.out.println(FastJsonUtil.toJson(PayService.pay(payCardInfo)));
  }
}
