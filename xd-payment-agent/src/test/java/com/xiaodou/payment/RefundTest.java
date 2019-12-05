package com.xiaodou.payment;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.payment.service.RefundService;
import com.xiaodou.payment.vo.OrderInfo;
import com.xiaodou.payment.vo.PayMerchant;
import com.xiaodou.payment.vo.request.RefundRequest;
import com.xiaodou.payment.vo.response.RefundResponse;

import java.io.UnsupportedEncodingException;

/**
 * 退款测试 Date: 2014/7/18 Time: 15:54
 * 
 * @author Tian.Dong
 */
public class RefundTest {
  // @Test
  public void testOld() throws Exception {
    OrderInfo orderInfo = new OrderInfo();
    orderInfo.setOrderId("20147211aaaabbb");
    orderInfo.setTotalAmt(100.00);
    orderInfo.setNotifyUrl(PayMerchant.getCallbackHost() + "refund_notify.do");
    RefundResponse response =
        RefundService.refund("02", "-100000000000006185", orderInfo.getOrderId(),
            "-100000000000012468", orderInfo);
    System.out.println(FastJsonUtil.toJson(response));
  }



  // @Test
  public void testRefundFlow() {
    RefundRequest request = new RefundRequest();
    request.setNotifyUrl("http://192.168.14.99:8102/pay/refund_notify.do");
    request.setOrderId("437852");
    request.setAmt(0.0);
    request.setTradeNo(-100000000000026580L);
    request.setBusinessType(1022);
    request.setMerchantId("190010007");
    request.setSignType("MD5");
    RefundResponse response = RefundService.refund(request, "CB0FAD9081F98DD992E6E49DBD70FE44");
    System.out.println(FastJsonUtil.toJson(response));
  }
}
