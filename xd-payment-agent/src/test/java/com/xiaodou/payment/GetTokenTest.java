package com.xiaodou.payment;

import java.util.UUID;

import org.junit.Test;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.payment.out.PaymentService;
import com.xiaodou.payment.service.QueryService;
import com.xiaodou.payment.vo.request.GetTokenRequest;

/**
 * 获取token Date: 2014/7/18 Time: 15:49
 * 
 * @author Tian.Dong
 */
public class GetTokenTest {
  @Test
  public void test() {
    System.out.println(FastJsonUtil.toJson(PaymentService.getToken("05")));
  }

  @Test
  public void testGetToken2() {
    GetTokenRequest req = new GetTokenRequest();
    req.setMerchantId("190010003");
    req.setOutTradeNo(UUID.randomUUID().toString());
    req.setBusinessType(1101);
    String key = "3721";
    System.out.println(FastJsonUtil.toJson(QueryService.getToken(req, key)));
  }
}
