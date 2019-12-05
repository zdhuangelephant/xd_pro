package com.xiaodou.oms.web.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.oms.service.order.PayService;
import com.xiaodou.payment.vo.CreditCardInfo;
import com.xiaodou.payment.vo.OrderInfo;
import com.xiaodou.payment.vo.PayCreditCardInfo;
import com.xiaodou.payment.vo.request.PayRequest;
import com.xiaodou.payment.vo.request.inner.MixPaymentCa;
import com.xiaodou.payment.vo.request.inner.MixPaymentCc;
import com.xiaodou.payment.vo.response.PayResponse;

/**
 * 用来模拟一次支付 只为测试提供
 * 
 * @author Jiejun.Gao
 */
@Controller
@RequestMapping("one")
public class OnePayController {


  /**
   * @param amt cc交易金额
   * @param totalAmt 订单总金额
   * @param serviceAmt cc手续费
   * @param orderId 给payment的订单号（g order id)
   * @param token 即pay_no trade_no
   * @param creditCard 信用卡号
   * @param notifyUrl 回调url
   * @return 成功返回0
   * @throws UnsupportedEncodingException
   */
  @ResponseBody
  @RequestMapping("pay")
  public String pay(double amt, double totalAmt, double serviceAmt, String orderId, String token,
      String creditCard, String notifyUrl, String productLine) throws UnsupportedEncodingException {
    CreditCardInfo card = new CreditCardInfo();
    card.setAmt(amt);
    card.setCardHolder("jj");
    card.setCardNo(creditCard);
    card.setCurrentcy(4061);
    card.setEcpireDate("2014-07-21");
    card.setIdNo("141124199004170223");
    card.setIdType(8001);
    card.setServiceAmt(serviceAmt);
    card.setVerifyCode("111");
    OrderInfo order = new OrderInfo();
    order.setNotifyUrl(notifyUrl);
    order.setOrderFrom("20000001");
    order.setOrderId(orderId);
    order.setPaymentProductId(0);
    order.setTotalAmt(totalAmt);
    order.setPayFrom(20000001);
    order.setPaymentProductId(0);
    PayCreditCardInfo payCardInfo = new PayCreditCardInfo();

    payCardInfo.setOrderInfo(order);
    payCardInfo.setPayMerchant(productLine);
    payCardInfo.setTrade_no(token);
    payCardInfo.setCc(card);
    payCardInfo.setPayment_product_id(0);
    PayResponse response = PayService.pay(payCardInfo);
    return FastJsonUtil.toJson(response);
  }

  @ResponseBody
  @RequestMapping("pay_ca")
  public String payCA(double amt, String gorderId, long token, String cashAccountNo,
      String notifyUrl, String productLine) throws UnsupportedEncodingException {
    PayRequest payRequest = new PayRequest();
    MixPaymentCa ca = new MixPaymentCa();
    ca.setAmt(amt);
    ca.setCaCurrency(4061);
    ca.setMemberCardNo(cashAccountNo);
    payRequest.setMixPaymentCa(ca);

    payRequest.setOrderId(gorderId);
    payRequest.setTradeNo(token);


    if (notifyUrl == null) {
      notifyUrl = "http://192.168.14.99:8102/pay/first_pay_notify.do";
    }
    payRequest.setNotifyUrl(notifyUrl);
    payRequest.setOrderFrom("20000001");
    payRequest.setPayFrom(20000001);
    payRequest.setSignType("MD5");
    payRequest.setTotalAmt(amt);
    payRequest.setMixPaymentType(3201);
    PayResponse response = PayService.pay(payRequest, productLine, true);
    return FastJsonUtil.toJson(response);
  }

  @ResponseBody
  @RequestMapping("pay_cacc")
  public String payCACC(double caamt, String gorderId, long token, String cashAccountNo,

  double ccamt, double totalAmt, double ccserviceAmt, String creditCard, String notifyUrl,
      String productLine) throws UnsupportedEncodingException {
    PayRequest payRequest = new PayRequest();
    MixPaymentCa ca = new MixPaymentCa();
    ca.setAmt(caamt);
    ca.setCaCurrency(4061);
    ca.setMemberCardNo(cashAccountNo);
    payRequest.setMixPaymentCa(ca);

    MixPaymentCc cc = new MixPaymentCc();
    cc.setAmt(ccamt);
    cc.setCcCurrency(4601);
    cc.setCardHolder("dd");
    cc.setCreditCardNo(creditCard);
    cc.setCustomerServiceAmt(ccserviceAmt);
    cc.setExpireDate("2016-12-12");
    cc.setIdNo("141124199004170223");
    cc.setIdType(8001);
    cc.setVerifyCode("123");
    payRequest.setMixPaymentCc(cc);

    payRequest.setOrderId(gorderId);
    payRequest.setTradeNo(token);


    if (notifyUrl == null) {
      notifyUrl = "http://192.168.14.99:8102/pay/first_pay_notify.do";
    }
    payRequest.setNotifyUrl(notifyUrl);
    payRequest.setOrderFrom("20000001");
    payRequest.setPayFrom(20000001);
    payRequest.setSignType("MD5");
    payRequest.setTotalAmt(totalAmt);
    payRequest.setMixPaymentType(3203);
    PayResponse response = PayService.pay(payRequest, productLine, true);
    return FastJsonUtil.toJson(response);
  }

  @ResponseBody
  @RequestMapping(value = "pay_any", headers = {"content-type=application/json;charset=utf-8"})
  public String payAny(@RequestBody PayRequest payRequest) {
    PayResponse response = PayService.pay(payRequest, "05", true);
    return FastJsonUtil.toJson(response);
  }
}
