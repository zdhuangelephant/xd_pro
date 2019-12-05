package com.xiaodou.payment.out;

import com.xiaodou.common.util.warp.FastJsonUtil;
import com.xiaodou.payment.out.PaymentService;
import com.xiaodou.payment.vo.input.RefundPojo;
import com.xiaodou.payment.vo.response.RefundResponse;

import org.junit.Test;

/**
 * Date: 2014/7/21
 * Time: 17:19
 *
 * @author Tian.Dong
 */
public class PaymentServiceTest {

  @Test
  public void refund() {
    RefundPojo pojo = new RefundPojo();
    pojo.setNotifyUrl("http://www.baidu.com");
    pojo.setToken("-100000000000006237");
    pojo.setAmt(100.2);
    pojo.setGorderId("20147211");
    pojo.setProductLine("02");
    RefundResponse response = PaymentService.refund(pojo);
    System.out.println(FastJsonUtil.toJson(response));
  }
}
